

package com.luban.common.base.command;

/**
 * @author hp
 */
public interface CommandForUpdateByKey<KEY> extends Command {

    KEY getKey();
}
