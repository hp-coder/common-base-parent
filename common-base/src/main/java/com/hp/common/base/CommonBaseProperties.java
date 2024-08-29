package com.hp.common.base;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author hp
 */
@Data
@ConfigurationProperties(prefix = "common-base")
public class CommonBaseProperties {

    private BaseEnumProperties baseEnum = new BaseEnumProperties();

    @Data
    public static class BaseEnumProperties {
        private String basePackage = "com.hp";
    }
}
