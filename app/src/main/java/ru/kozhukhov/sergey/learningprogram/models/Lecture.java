package ru.kozhukhov.sergey.learningprogram.models;

import androidx.annotation.NonNull;

import java.util.GregorianCalendar;

/*
* Модель лекции
* */

public class Lecture {


    /*
     * mNumber - порядковый номер занятия
     * mDate - дата проведения занятия
     * mTheme - тема занятия
     * mLector - фамилия преподавателя
     * */

    private final int mNumber;
    private final GregorianCalendar mDate;
    private final String mTheme;
    private final String mLector;

    public Lecture(int mNumber, @NonNull GregorianCalendar mDate, @NonNull String mTheme, @NonNull String mLector) {
        this.mNumber = mNumber;
        this.mDate = mDate;
        this.mTheme = mTheme;
        this.mLector = mLector;
    }


    public int getNumber() {
        return mNumber;
    }

    public GregorianCalendar getDate() {
        return mDate;
    }

    public String getTheme() {
        return mTheme;
    }

    public String getLector() {
        return mLector;
    }

}
