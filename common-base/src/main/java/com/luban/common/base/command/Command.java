package com.luban.common.base.command;

import com.luban.common.base.validator.ValidateErrorHandler;

/**
 * @author hp
 */
public interface Command {

    default void validate(ValidateErrorHandler validateErrorHandler){}
}
