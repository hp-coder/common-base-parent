package com.hp.common.base.model;

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

}
