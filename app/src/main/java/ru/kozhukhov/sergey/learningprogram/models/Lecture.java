package ru.kozhukhov.sergey.learningprogram.models;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * Модель лекции
 */
public class Lecture implements Parcelable{

    /**
     * mNumber - порядковый номер занятия
     */
    private final int mNumber;

    /**
     * mDate - дата проведения занятия
     */
    private final GregorianCalendar mDate;

    /**
     * mTheme - тема занятия
     */
    private final String mTheme;

    /**
     * mLector - фамилия преподавателя
     */
    private final String mLector;

    /**
     * mSubtopics - детальная информация о лекции
     */
    private final List<String> mSubtopics;

   public Lecture(int number, @NonNull GregorianCalendar date, @NonNull String theme, @NonNull String lector) {
        this.mNumber = number;
        this.mDate = date;
        this.mTheme = theme;
        this.mLector = lector;
        mSubtopics = new ArrayList<>();
    }

    /**
     * Конструктов для работы с jackson
     */
    @JsonCreator
    public Lecture(
            @JsonProperty("number") int number,
            @JsonProperty("date") @NonNull String date,
            @JsonProperty("theme") @NonNull String theme,
            @JsonProperty("lector") @NonNull String lector,
            @JsonProperty("subtopics") @NonNull List<String> subtopics) {
        this.mNumber = number;
        this.mDate = stringToCalendar(date);
        this.mTheme = theme;
        this.mLector = lector;
        this.mSubtopics = new ArrayList<>(subtopics);
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

    public List<String> getSubtopics() {
        return mSubtopics == null ? null : new ArrayList<>(mSubtopics);
    }

    /**
     * Преобразование строки к формату GregorianCalendar
     */
    private GregorianCalendar stringToCalendar(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        try {
            Date dateFromString = simpleDateFormat.parse(date);
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(dateFromString);
            return calendar;
        } catch (ParseException pe) {
            return null;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lecture lecture = (Lecture) o;
        return mNumber == lecture.mNumber &&
                mDate.equals(lecture.mDate) &&
                mTheme.equals(lecture.mTheme) &&
                mLector.equals(lecture.mLector) &&
                mSubtopics.equals(lecture.mSubtopics);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mNumber, mDate, mTheme, mLector, mSubtopics);
    }

    protected Lecture(Parcel in) {
        mNumber = in.readInt();
        mDate = new GregorianCalendar();
        mDate.setTimeInMillis(in.readLong());
        mTheme = in.readString();
        mLector = in.readString();
        mSubtopics = in.createStringArrayList();
    }

    public static final Creator<Lecture> CREATOR = new Creator<Lecture>() {
        @Override
        public Lecture createFromParcel(Parcel in) {
            return new Lecture(in);
        }

        @Override
        public Lecture[] newArray(int size) {
            return new Lecture[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mNumber);
        dest.writeLong(mDate.getTimeInMillis());
        dest.writeString(mTheme);
        dest.writeString(mLector);
        dest.writeStringList(mSubtopics);
    }



}
