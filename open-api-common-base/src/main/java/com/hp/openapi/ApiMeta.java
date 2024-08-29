package com.hp.openapi;

import cn.hutool.core.util.StrUtil;
import lombok.Data;

/**
 * @author hp
 */
@Data
public class ApiMeta {

    private String apiGroup;

    private String apiSubGroup;

    private String apiName;

    public void setApiName(String apiName) {
        this.apiName = StrUtil.toCamelCase(apiName, '_');
    }

    private String method;

    private String path;

    private String description;

    private String request;

    private String response;

    // 业务调用RPC配置
    // 限流配置
    // 超时配置
    // 灰度配置
    // ...
}
