package ru.kozhukhov.sergey.learningprogram.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ru.kozhukhov.sergey.learningprogram.R;
import ru.kozhukhov.sergey.learningprogram.models.Lecture;


/**
 * Адаптер для отображения списка лекций
 */
public class AdapterLectures extends RecyclerView.Adapter<AdapterLectures.BaseViewHolder> {

    /**
     * mLectures - список лекций
     */
    private List<Lecture> mLectures;

    /**
     * mDateToday - сегодняшняя дата
     */
    private Date mDateToday;

    /**
     * mOnItemLectureClickListener - обработчик нажатия на элемент списка лекций
     */
    private OnItemLectureClickListener mOnItemLectureClickListener;

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lecture, parent, false);
        return new LectureHolder(view, mDateToday);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {

        Lecture item = mLectures.get(position);
        ((LectureHolder) holder).bindView(item, mOnItemLectureClickListener);

    }

    @Override
    public int getItemCount() {
        return mLectures == null ? 0 : mLectures.size();
    }

    public void setDateToday(Date dateToday) {
        this.mDateToday = dateToday;
    }

    /**
     * Установка обработчика нажатия на элемент списка лекций
     */
    public void setClickListener(@Nullable OnItemLectureClickListener onItemLectureClickListener) {
        this.mOnItemLectureClickListener = onItemLectureClickListener;
    }

    /**
     * Установка списка лекций в адаптер
     */
    public void setLectures(@NonNull List<Lecture> lectures) {
        if (lectures == null) {
            mLectures = new ArrayList<>();
        } else {
            mLectures = new ArrayList<>(lectures);
        }
        notifyDataSetChanged();
    }

    /**
     * Возвращение позиции лекции, проходящей сегодня или ближайшей к сегодняшней дате
     */
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
            dateFormat = new SimpleDateFormat("EEEE, d MMMM yyyy", Locale.getDefault());
        }

        private void bindView(final Lecture lecture, final OnItemLectureClickListener onItemLectureClickListener) {

            mNumber.setText(String.valueOf(lecture.getNumber()));
            mDate.setText(dateFormat.format(lecture.getDate().getTime()));
            mTheme.setText(String.valueOf(lecture.getTheme()));
            mLector.setText(String.valueOf(lecture.getLector()));

            if (lecture.getDate().getTime().before(mDateToday)) {
                // mImage.setBackgroundColor(0xFFB22222) - для хардкор установки цвета
                // важно прописать FF, иначе цвет будет прозрачный
                mImage.setBackgroundColor(itemView.getResources().getColor(R.color.colorBefore));
            } else if (lecture.getDate().getTime().equals(mDateToday)) {
                mImage.setBackgroundColor(itemView.getResources().getColor(R.color.colorNow));
            } else {
                mImage.setBackgroundColor(itemView.getResources().getColor(R.color.colorAfter));
            }
            if (onItemLectureClickListener != null)
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemLectureClickListener.onItemClick(lecture);
                }
            });
        }

    }


}



