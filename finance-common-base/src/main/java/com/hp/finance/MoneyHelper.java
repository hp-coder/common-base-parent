package com.hp.finance;

import javax.money.CurrencyUnit;
import javax.money.Monetary;

/**
 * @author hp
 */
public final class MoneyHelper {

    private MoneyHelper() {
    }

    public final static CurrencyUnit CNY_UNIT = Monetary.getCurrency("CNY");

}
