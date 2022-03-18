package com.projeto.padrao.utils;

public final class NumberUtil {

    /**
     * Retorna uma cadeia de caracteres contendo apenas um conjunto de
     * caracteres numericos.
     *
     * @param value
     * @return
     */
    public static String toNumber(String value) {
        value = value.replaceAll("[^0-9]", "").trim();
        return value;
    }
}
