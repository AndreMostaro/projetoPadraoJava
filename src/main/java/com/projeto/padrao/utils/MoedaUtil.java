package com.projeto.padrao.utils;

import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public final class MoedaUtil {

    public static String formataMoeda(BigDecimal valor){
        if(!ObjectUtils.isEmpty(valor)){
            Locale localBrasil = new Locale("pt", "BR");
            return NumberFormat.getCurrencyInstance(localBrasil).format(valor);
        }
        return "";
    }
}
