package com.hp.sdk;

import com.hp.openapi.ApiMeta;

/**
 * @author hp
 */
public class SdkMeta {
    public static final String PACKAGE_PREFIX = "com.hp";

    protected final ApiMeta apiMeta;

    public SdkMeta(ApiMeta apiMeta) {
        this.apiMeta = apiMeta;
    }

    public String getPackageName() {
        return String.join(".", PACKAGE_PREFIX, apiMeta.getApiGroup(), apiMeta.getApiSubGroup(), apiMeta.getApiName());
    }

    private String getClassName() {
        return apiMeta.getApiName() + "Request/Response";
    }

    private String getDescription() {
        return "/*" + apiMeta.getDescription() + "*/";
    }

    // static fields

    // private fields

    // imports

    // interfaces Request<xxxResponse>

    // apiMeta

}
