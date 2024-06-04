package com.hp.orm.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author hp
 */
@Getter
@AllArgsConstructor
public class ValidateResult {

    private final String name;
    private final String message;
}
