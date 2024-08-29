package com.hp.common.base;

import com.hp.common.base.encryption.PKCS7PaddingLoader;
import com.hp.common.base.enums.BaseEnumBasedConditionalGenericConverter;
import com.hp.common.base.enums.BaseEnumRegistrar;
import com.hp.common.base.utils.SpELHelper;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Role;

/**
 * @author hp
 */
@Configuration
@Import(CommonBaseProperties.class)
public class CommonBaseAutoConfiguration {

    @Bean
    @Role(BeanDefinition.ROLE_APPLICATION)
    @ConditionalOnMissingBean(SpELHelper.class)
    public SpELHelper spELHelper() {
        return new SpELHelper();
    }

    @Bean
    @Role(BeanDefinition.ROLE_APPLICATION)
    @ConditionalOnMissingBean(PKCS7PaddingLoader.class)
    public PKCS7PaddingLoader pkcs7PaddingLoader() {
        return new PKCS7PaddingLoader();
    }

    @Bean
    @Role(BeanDefinition.ROLE_APPLICATION)
    @ConditionalOnMissingBean(BaseEnumRegistrar.class)
    public BaseEnumRegistrar baseEnumRegistrar(CommonBaseProperties commonBaseProperties) {
        return new BaseEnumRegistrar(commonBaseProperties.getBaseEnum());
    }

    @Bean
    @Role(BeanDefinition.ROLE_APPLICATION)
    @ConditionalOnMissingBean(BaseEnumBasedConditionalGenericConverter.class)
    public BaseEnumBasedConditionalGenericConverter baseEnumBasedConditionalGenericConverter(
            BaseEnumRegistrar baseEnumRegistrar
    ) {
        return new BaseEnumBasedConditionalGenericConverter(baseEnumRegistrar);
    }
}
