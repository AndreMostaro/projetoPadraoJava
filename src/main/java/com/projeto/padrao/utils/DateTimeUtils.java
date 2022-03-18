package com.projeto.padrao.utils;

import org.apache.commons.lang3.ObjectUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class DateTimeUtils {

    private static final DateTimeZone DATE_TIME_ZONE = DateTimeZone.forID("America/Porto_Velho");

    public static Date newDate() {
        return DateTime.now().withZone(DATE_TIME_ZONE).toDate();
    }

    public static String formataData(Date dataParam, String formatParam) {
        if (ObjectUtils.isEmpty(dataParam)) {
            return null;
        }
        String df = new SimpleDateFormat(formatParam).format(dataParam);
        return df;
    }

    public static short getAno(Date data){
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        return (short) cal.get(Calendar.YEAR);
    }
}
