package com.luban.common.base.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

/**
 * @author hp
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CascadeResponse implements Response {

    private Object value;
    private String label;
    private boolean disabled;
    private Collection<CascadeResponse> children;

}
