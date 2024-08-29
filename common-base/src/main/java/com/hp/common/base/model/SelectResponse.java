package com.hp.common.base.model;

import com.hp.common.base.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hp
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SelectResponse implements Response {

    protected Object value;
    protected String label;
    protected boolean disabled;

    public SelectResponse(BaseEnum<?, ?> baseEnum) {
        this.value = baseEnum.getCode();
        this.label = baseEnum.getName();
        this.disabled = baseEnum.disabled();
    }
}
