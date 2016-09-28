package com.evgenyshilov.web.contacts.help;

import java.util.ArrayList;

/**
 * Created by Evgeny Shilov on 28.09.2016.
 */
public class MonthListBuilder {
    public static final String JANUARY = "Январь";
    public static final String FEBRUARY = "Февраль";
    public static final String MARCH = "Март";
    public static final String APRIL = "Апрель";
    public static final String MAY = "Май";
    public static final String JUNE = "Июнь";
    public static final String JULY = "Июль";
    public static final String AUGUST = "Август";
    public static final String SEPTEMBER = "Сентябрь";
    public static final String OCTOBER = "Октябрь";
    public static final String NOVEMBER = "Ноябрь";
    public static final String DECEMBER = "Декабрь";

    public ArrayList<String> getMonthList() {
        ArrayList<String> months = new ArrayList<>();

        months.add(JANUARY);
        months.add(FEBRUARY);
        months.add(MARCH);
        months.add(APRIL);
        months.add(MAY);
        months.add(JUNE);
        months.add(JULY);
        months.add(AUGUST);
        months.add(SEPTEMBER);
        months.add(OCTOBER);
        months.add(NOVEMBER);
        months.add(DECEMBER);

        return months;
    }
}
