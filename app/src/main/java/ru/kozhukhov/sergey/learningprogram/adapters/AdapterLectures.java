package ru.kozhukhov.sergey.learningprogram.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ru.kozhukhov.sergey.learningprogram.R;
import ru.kozhukhov.sergey.learningprogram.models.Lecture;

/*
 *  Адаптер для отображения списка лекций
 * */

public class AdapterLectures extends RecyclerView.Adapter<AdapterLectures.BaseViewHolder> {

    /*
    * mLectures - список лекций
    * mDateToday - сегодняшняя дата
    * */

    private List<Lecture> mLectures;
    private Date mDateToday;

    public AdapterLectures(){

    }


    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lecture, parent, false);
        return new LectureHolder(view, mDateToday);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {

        Lecture item = mLectures.get(position);
        ((LectureHolder) holder).bindView(item);

    }

    @Override
    public int getItemCount() {
        return mLectures == null ? 0 : mLectures.size();
    }

    public void setDateToday(Date mDateToday) {
        this.mDateToday = mDateToday;
    }

    /*
     *  Установка списка лекций в адаптер
     * */

    public void setLectures(@NonNull List<Lecture> lectures) {
        if (lectures == null) {
            mLectures = new ArrayList<>();
        } else {
            mLectures = new ArrayList<>(lectures);
        }
        notifyDataSetChanged();
    }

    /*
     * Возвращение позиции лекции, проходящей сегодня или ближайшей к сегодняшней дате
     * @param lecture - лекция, определение позиции которой требуется
     * */

    public int getPositionToday()
    {
        for (int i = 0; i < mLectures.size(); i++) {
            if ((mLectures.get(i).getDate().getTime().after(mDateToday))) {
                return i;
            }
        }
        return mLectures.size()-1;
    }

    static abstract class BaseViewHolder extends RecyclerView.ViewHolder {

        public BaseViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    private static class LectureHolder extends BaseViewHolder {

        private final TextView mNumber;
        private final TextView mDate;
        private final TextView mTheme;
        private final TextView mLector;
        private final ImageView mImage;


        private final Date mDateToday;
        private SimpleDateFormat dateFormat;

        private LectureHolder(@NonNull View itemView, Date dateToday) {
            super(itemView);
            mNumber = itemView.findViewById(R.id.item_lecture_TextView_number);
            mDate = itemView.findViewById(R.id.item_lecture_TextView_date);
            mTheme = itemView.findViewById(R.id.item_lecture_TextView_theme);
            mLector = itemView.findViewById(R.id.item_lecture_TextView_lector);
            mImage = itemView.findViewById(R.id._item_lecture_ImageView_indicator);

            mDateToday = dateToday;
            dateFormat = new SimpleDateFormat("EEEE, d MMMM yyyy");
        }

        private void bindView(Lecture lecture) {

            mNumber.setText(String.valueOf(lecture.getNumber()));
            mDate.setText(dateFormat.format(lecture.getDate().getTime()));
            mTheme.setText(String.valueOf(lecture.getTheme()));
            mLector.setText(String.valueOf(lecture.getLector()));

            if (lecture.getDate().getTime().before(mDateToday)) {
                mImage.setImageLevel(1500);
            } else if (lecture.getDate().getTime().equals(mDateToday)) {
                mImage.setImageLevel(5000);
            } else {
                mImage.setImageLevel(8000);
            }
        }

    }


}



