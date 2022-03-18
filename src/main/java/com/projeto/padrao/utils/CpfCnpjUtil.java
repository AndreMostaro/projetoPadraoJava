package com.projeto.padrao.utils;

public final class CpfCnpjUtil {

    /**
     * Verifica se o CPF informado e valido comtemplando a regra inicial dos
     * onze algarismos em que os dois ultimos sao chamados de digitos
     * verificadores (DV's), criados a partir dos nove primeiros, validando o
     * numero total. O calculo destes digitos e realizado em duas etapas
     * utilizando o modulo de divisao 11.
     *
     * @param cpf
     * @return
     */
    public static boolean isCPF(String cpf) {
        // --
        if (cpf == null || cpf.length() == 0) {
            return false;
        }
        // --
        cpf = NumberUtil.toNumber(cpf);
        // --
        int soma = 0;
        // --
        try {
            Long.parseLong(cpf);
        } catch (Exception e) {
            return false;
        }
        if (cpf.length() == 11) {
            // -- LINHA COMENTADA A PEDIDO DO SERGIO PARA UTILIZACAO DO CPF COM 0 PARA USER MASTER
//            if (cpf.equals("00000000000") || cpf.equals("11111111111")
            if (cpf.equals("11111111111")
                    || cpf.equals("22222222222") || cpf.equals("33333333333")
                    || cpf.equals("44444444444") || cpf.equals("55555555555")
                    || cpf.equals("66666666666") || cpf.equals("77777777777")
                    || cpf.equals("88888888888") || cpf.equals("99999999999")) {
                return false;
            }
            // --
            for (int i = 0; i < 9; i++)
                soma += (10 - i) * (cpf.charAt(i) - '0');
            soma = 11 - (soma % 11);
            if (soma > 9)
                soma = 0;
            if (soma == (cpf.charAt(9) - '0')) {
                soma = 0;
                for (int i = 0; i < 10; i++)
                    soma += (11 - i) * (cpf.charAt(i) - '0');
                soma = 11 - (soma % 11);
                if (soma > 9)
                    soma = 0;
                if (soma == (cpf.charAt(10) - '0')) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Verifica se o CNPJ informado e valido comtemplando a regra inicial dos
     * quatorze algarismos, divididos em tres blocos:
     *
     * - o primeiro, que representa o numero da inscricao propriamente dito; - o
     * segundo, localizado apos a barra, que representa um codigo unico para a
     * matrix ou filial; - o terceiro, representados pelos dois ultimos valores
     * chamados de digitos verificadores (DV).
     *
     * Os digitos verificadores (DV) sao criados a partir dos doze primeiros. O
     * calculo e feito em duas etapas utilizando o modulo de divisao 11.
     *
     * @param cnpj
     * @return
     */
    public static boolean isCNPJ(String cnpj) {
        // --
        boolean retorno = true;
        // --
        if (cnpj == null || cnpj.length() == 0) {
            return false;
        }
        // --
        // Retira os caracteres nao alpha numericos
        cnpj = NumberUtil.toNumber(cnpj);
        // --
        try {
            // Verifica se e um numero
            Long.parseLong(cnpj);
        } catch (Exception e) {
            return false;
        }
        // --
        if (cnpj.length() != 14) {
            return false;
        }
        // --
        if (cnpj.equals("00000000000000") || cnpj.equals("11111111111111")
                || cnpj.equals("22222222222222")
                || cnpj.equals("33333333333333")
                || cnpj.equals("44444444444444")
                || cnpj.equals("55555555555555")
                || cnpj.equals("66666666666666")
                || cnpj.equals("77777777777777")
                || cnpj.equals("88888888888888")
                || cnpj.equals("99999999999999")) {
            return false;
        }
        // --
        int soma = 0, dig;
        // --
        String cnpjCalculado = cnpj.substring(0, 12);
        char[] chrCnpj = cnpj.toCharArray();

        /* Primeira parte */
        for (int i = 0; i < 4; i++) {
            if (chrCnpj[i] - 48 >= 0 && chrCnpj[i] - 48 <= 9) {
                soma += (chrCnpj[i] - 48) * (6 - (i + 1));
            }
        }
        for (int i = 0; i < 8; i++) {
            if (chrCnpj[i + 4] - 48 >= 0 && chrCnpj[i + 4] - 48 <= 9) {
                soma += (chrCnpj[i + 4] - 48) * (10 - (i + 1));
            }
        }
        dig = 11 - (soma % 11);

        if (dig == 10 || dig == 11) {
            cnpjCalculado += "0";
        } else {
            cnpjCalculado += Integer.toString(dig);
        }

        /* Segunda parte */
        soma = 0;
        for (int i = 0; i < 5; i++) {
            if (chrCnpj[i] - 48 >= 0 && chrCnpj[i] - 48 <= 9) {
                soma += (chrCnpj[i] - 48) * (7 - (i + 1));
            }
        }
        for (int i = 0; i < 8; i++) {
            if (chrCnpj[i + 5] - 48 >= 0 && chrCnpj[i + 5] - 48 <= 9) {
                soma += (chrCnpj[i + 5] - 48) * (10 - (i + 1));
            }
        }
        dig = 11 - (soma % 11);

        if (dig == 10 || dig == 11) {
            cnpjCalculado += "0";
        } else {
            cnpjCalculado += Integer.toString(dig);
        }

        retorno = cnpj.equals(cnpjCalculado);
        if (!retorno) {
            return false;
        }
        // --
        return retorno;
    }

    public static String formatarCPF(String cpf) {
        if (cpf != null) {
            if (isCPF(cpf)) {
                return cpf.replaceAll("([0-9]{3})([0-9]{3})([0-9]{3})([0-9]{2})", "$1.$2.$3-$4");
            }
        }
        return cpf;
    }

    public static String formatarCNPJ(String cnpj) {
        if (cnpj != null) {
            if (isCNPJ(cnpj)) {
                return cnpj.replaceAll("([0-9]{2})([0-9]{3})([0-9]{3})([0-9]{4})([0-9]{2})", "$1.$2.$3/$4-$5");
            }
        }
        return cnpj;
    }

    public static String formataCpfCnpj(String doc) {
        if (doc == null) return doc;
        return (doc.length() == 11)
                ? CpfCnpjUtil.formatarCPF(doc)
                : CpfCnpjUtil.formatarCNPJ(doc);
    }

    public static boolean isCpfCnpj(String doc){
        if (isCPF(doc)){
            return true;
        }
        return isCNPJ(doc);
    }
}
