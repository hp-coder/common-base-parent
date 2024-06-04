package com.hp.common.base.command;

/**
 * @author hp
 */
public interface CommandForUpdateById<ID> extends CommandForUpdate {

    ID getId();
}
