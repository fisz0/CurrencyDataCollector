package com.mokon.nbp.currency.parser;

import java.math.MathContext;
import java.math.RoundingMode;

/**
 * Created by mokon on 09.03.2017.
 */
public final class CurrencyMathContext {

    private CurrencyMathContext() {
    }

    public static MathContext getNewContext() {
        return new MathContext(5, RoundingMode.HALF_EVEN);
    }
}
