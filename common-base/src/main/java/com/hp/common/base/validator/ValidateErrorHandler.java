package com.hp.common.base.validator;

/**
 * @author hp
 */
public interface ValidateErrorHandler {

    void handleError(String name, String code, String msg);
}
