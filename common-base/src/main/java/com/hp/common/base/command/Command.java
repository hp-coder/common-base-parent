package com.hp.common.base.command;

import com.hp.common.base.validator.ValidateErrorHandler;

/**
 * @author hp
 */
public interface Command {

    default void validate(ValidateErrorHandler validateErrorHandler){}
}
