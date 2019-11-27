package ru.kozhukhov.sergey.learningprogram.models;

/*
 * Модель недели, на которой проводиться занятие
 * */

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Week {

    /*
     * week - порядковый номер недели, начиная с начала года
     * year - год
     * */

    private int week;
    private int year;

    public Week(int week, int year) {
        this.week = week;
        this.year = year;
    }

    public Week(GregorianCalendar calendar) {
        this.week = calendar.get(Calendar.WEEK_OF_YEAR);
        this.year = calendar.get(Calendar.YEAR);
    }


    public Week(Date date) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        this.week = calendar.get(Calendar.WEEK_OF_YEAR);
        this.year = calendar.get(Calendar.YEAR);
    }

    public int getWeek() {
        return week;
    }

    public int getYear() {
        return year;
    }


    /*
     * Проверка равенства недель
     * */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Week that = (Week) o;
        return (week == that.week) && (year == that.year);
    }

    /*
     * Вычисление hashCode для Week
     * */

    @Override
    public int hashCode() {

        int hashcode = 31 * year + 29 * week;
        return Integer.parseInt(String.valueOf(hashcode));

    }

    /*
    * Проверка позже ли идет текущая неделя
    * @param that - неделя, с которой идет сравнение
    * */
    public boolean after(Week that) {

        if (this.year > that.year)
            return true;
        else if (this.year == that.year) {
            if (this.week > that.week)
                return true;
            else return false;
        }
        return false;
    }

}


