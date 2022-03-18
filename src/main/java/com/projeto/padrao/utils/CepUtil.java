package com.projeto.padrao.utils;

public final class CepUtil {

    public static String formataCep(String cep) {
        if (cep != null) {
            return cep.replaceAll("([0-9]{2})([0-9]{3})([0-9]{3})", "$1.$2-$3");
        }
        return cep;
    }
}
