package com.hp.sdk;

/**
 * @author hp
 */
public interface ApiResponse<R extends ApiResponse<R>> {

    boolean succeed();
}
