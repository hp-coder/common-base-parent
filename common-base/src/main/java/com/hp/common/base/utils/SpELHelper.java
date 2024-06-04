package com.hp.common.base.utils;

import cn.hutool.core.util.StrUtil;
import com.hp.common.base.visitor.Visitor;
import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.expression.*;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.lang.NonNull;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * 很多判断是Groovy语法
 * <p>
 * Tips:
 * <ul>
 *   <li>字符串单引号. 可以调用方法或访问属性</li>
 *   <li>属性首字母大小写不敏感</li>
 *   <li>集合元素: Map用 {@code map['key']} 获取元素, Array/List用 {@code 集合名称[index]} 获取元素</li>
 *   <li>定义List: {@code {1,2,3,4} 或 {{'a','b'},{'x','y'}} }</li>
 *   <li>instance of: {@code 'xyz' instanceof T(int)}</li>
 *   <li>正则: {@code '字符串' matches '正则表达式'}</li>
 *   <li>逻辑运算符: {@code !非 and与 or或}</li>
 *   <li>类型: {@code java.lang包下直接用, 其他的要用T(全类名)}</li>
 *   <li>构造器: {@code new 全类名(构造参数)}</li>
 *   <li>变量: StandardEvaluationContext当中的变量 {@code  #变量名称 }</li>
 *   <li>#this: 当前解析的对象</li>
 *   <li>#root: 上下文的根对象</li>
 *   <li>Spring Bean引用: {@code @beanName} </li>
 *   <li>三元表达式和Java一样</li>
 *   <li>Elvis Operator: {@code Names?:'Unknown'} Names为空提供默认值</li>
 *   <li>防NPE操作符: {@code PlaceOfBirth?.City} 如果为NULL 防止出现NPE</li>
 *   <li>筛选集合元素: {@code 集合.?[筛选条件]} 如果是Map集合,Map.Entry为当前判断对象</li>
 *   <li>筛选第一个满足集合元素: {@code 集合.^[筛选条件]}</li>
 *   <li>筛选最后一个满足集合元素: {@code 集合.$[筛选条件]}</li>
 *   <li>集合映射,类似StreamAPI的map()再collect(): 使用语法 {@code 集合.![映射规则]}, Map集合类似上述说明</li>
 *   <li>表达式模版: 默认{@code #{} }, 指定解析模版内部的内容</li>
 * </ul>
 *
 * @author hp
 */

@Slf4j
@Configuration
public class SpELHelper implements ApplicationContextAware {

    private BeanResolver beanResolver;
    private final ExpressionParser expressionParser = new SpelExpressionParser();
    private final ParserContext parserContext = ParserContext.TEMPLATE_EXPRESSION;

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        this.beanResolver = new BeanFactoryResolver(applicationContext);
    }

    public <T, R> StandardSpELGetter<T, R> newGetterInstance(String expression) {
        return new StandardSpELGetterImpl<>(expression, new StandardEvaluationContext());
    }

    public <T, R> StandardSpELGetter<T, R> newGetterInstance(String expression, EvaluationContext evaluationContext) {
        return new StandardSpELGetterImpl<>(expression, evaluationContext);
    }

    public <T, R> StandardSpELSetter<T, Collection<R>> newSetterInstance(Field field) {
        return new StandardSpELSetterImpl<>(field);
    }

    public <T, R> StandardSpELSetter<T, Collection<R>> newSetterInstance(Field field, EvaluationContext evaluationContext) {
        return new StandardSpELSetterImpl<>(field, evaluationContext);
    }

    public interface StandardSpELGetter<T, R> extends Function<T, R> {

        R apply(T t, @Nullable Visitor<EvaluationContext> visitor);

        static <T> StandardSpELGetter<T, T> identity() {
            return new StandardSpELGetter<>() {
                @Override
                public T apply(T t, @Nullable Visitor<EvaluationContext> visitor) {
                    return t;
                }

                @Override
                public T apply(T t) {
                    return t;
                }
            };
        }
    }

    public interface StandardSpELSetter<T, R> extends BiConsumer<T, R> {

        void accept(T target, R result, @Nullable Visitor<EvaluationContext> visitor);
    }

    public class StandardSpELGetterImpl<T, R> implements StandardSpELGetter<T, R> {
        private final Expression expression;
        private final EvaluationContext evaluationContext;

        private StandardSpELGetterImpl(String expression) {
            this(expression, new StandardEvaluationContext());
        }

        private StandardSpELGetterImpl(String expression, EvaluationContext evaluationContext) {
            if (StrUtil.isNotEmpty(expression) && expression.startsWith(parserContext.getExpressionPrefix())) {
                this.expression = expressionParser.parseExpression(expression, parserContext);
            } else {
                this.expression = expressionParser.parseExpression(expression);
            }
            this.evaluationContext = Objects.requireNonNull(evaluationContext);
            if (this.evaluationContext instanceof StandardEvaluationContext standardEvaluationContext) {
                standardEvaluationContext.setBeanResolver(beanResolver);
//                standardEvaluationContext.setTypeConverter(new StandardTypeConverter());
            }
        }

        @SuppressWarnings("unchecked")
        @Override
        public R apply(T data, @Nullable Visitor<EvaluationContext> visitor) {
            Optional.ofNullable(visitor).ifPresent(v -> v.visit(evaluationContext));
            return (R) expression.getValue(evaluationContext, data);
        }

        @Override
        public R apply(T data) {
            return apply(data, Visitor.defaultVisitor());
        }
    }

    public class StandardSpELSetterImpl<T, R> implements StandardSpELSetter<T, Collection<R>> {
        private final String fieldName;
        private final boolean isCollection;
        private final Expression expression;
        private final EvaluationContext evaluationContext;

        private StandardSpELSetterImpl(Field field) {
            this(field, new StandardEvaluationContext());
        }

        private StandardSpELSetterImpl(Field field, EvaluationContext evaluationContext) {
            this.fieldName = Objects.requireNonNull(field).getName();
            this.expression = expressionParser.parseExpression(fieldName);
            this.isCollection = Collection.class.isAssignableFrom(Objects.requireNonNull(field).getType());
            this.evaluationContext = evaluationContext;
        }

        @Override
        public void accept(T target, Collection<R> result, @Nullable Visitor<EvaluationContext> visitor) {
            Optional.ofNullable(visitor).ifPresent(i -> i.visit(this.evaluationContext));
            if (isCollection) {
                this.expression.setValue(evaluationContext, target, result);
            } else {
                if (result.size() == 1) {
                    this.expression.setValue(evaluationContext, target, result.stream().findFirst().get());
                } else {
                    log.error("write join result to {} error: Too many results, field is {}, data is {}", target, fieldName, result);
                }
            }
        }

        @Override
        public void accept(T target, Collection<R> result) {
            accept(target, result, Visitor.defaultVisitor());
        }
    }
}

