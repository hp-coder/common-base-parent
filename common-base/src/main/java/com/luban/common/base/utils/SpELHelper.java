package com.luban.common.base.utils;

import cn.hutool.core.util.StrUtil;
import com.luban.common.base.visitor.Visitor;
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
import java.util.function.BiFunction;

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
 *   <li>筛选第一个满足集合元素: {@code 集合.$[筛选条件]}</li>
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

    public <T, R> StandardSpELGetter<T, R> newGetterInstance(String expression) {
        return new StandardSpELGetter<>(expression, new StandardEvaluationContext());
    }

    public <T, R> StandardSpELSetter<T, R> newSetterInstance(Field field) {
        return new StandardSpELSetter<>(field);
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        this.beanResolver = new BeanFactoryResolver(applicationContext);
    }

    public class StandardSpELGetter<T, R> implements BiFunction<T, Visitor<EvaluationContext>, R> {
        private final Expression expression;
        private final EvaluationContext evaluationContext;

        private StandardSpELGetter(String expression, EvaluationContext evaluationContext) {
            if (StrUtil.isNotEmpty(expression) && expression.startsWith(parserContext.getExpressionPrefix())) {
                this.expression = expressionParser.parseExpression(expression, parserContext);
            } else {
                this.expression = expressionParser.parseExpression(expression);
            }
            this.evaluationContext = Objects.requireNonNull(evaluationContext);
            if (this.evaluationContext instanceof StandardEvaluationContext standardEvaluationContext) {
                standardEvaluationContext.setBeanResolver(beanResolver);
            }
        }

        @SuppressWarnings("unchecked")
        @Override
        public R apply(T data, Visitor<EvaluationContext> visitor) {
            Optional.ofNullable(visitor).ifPresent(v -> v.visit(evaluationContext));
            return (R) expression.getValue(evaluationContext, data);
        }

        public R apply(T data) {
            return apply(data, Visitor.defaultVisitor());
        }
    }

    public class StandardSpELSetter<T, R> implements BiConsumer<T, Collection<R>> {
        private final String fieldName;
        private final boolean isCollection;
        private final Expression expression;

        private StandardSpELSetter(Field field) {
            this.fieldName = Objects.requireNonNull(field).getName();
            this.expression = expressionParser.parseExpression(fieldName);
            this.isCollection = Collection.class.isAssignableFrom(Objects.requireNonNull(field).getType());
        }

        @Override
        public void accept(T data, Collection<R> result) {
            if (isCollection) {
                this.expression.setValue(data, result);
            } else {
                int size = result.size();
                if (size == 1) {
                    this.expression.setValue(data, result.stream().findFirst().get());
                } else {
                    log.error("write join result to {} error: Too many results, field is {}, data is {}", data, fieldName, result);
                }
            }
        }
    }
}

