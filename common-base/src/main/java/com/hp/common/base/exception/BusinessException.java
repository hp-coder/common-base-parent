package com.hp.common.base.exception;

import com.hp.common.base.enums.BaseEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * @author hp
 */
@Getter
public class BusinessException extends RuntimeException {

    private final BaseEnum<?, Integer> msg;

    @Setter
    private Object data;

    public BusinessException(BaseEnum<?, Integer> msg) {
        super(msg.getName());
        this.msg = msg;
    }

    public BusinessException(BaseEnum<?, Integer> msg, Throwable throwable) {
        super(msg.getName(), throwable);
        this.msg = msg;
    }

    public BusinessException(BaseEnum<?, Integer> msg, Object data) {
        super(msg.getName());
        this.msg = msg;
        this.data = data;
    }
}
