package ru.kozhukhov.sergey.learningprogram.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import ru.kozhukhov.sergey.learningprogram.R;
import ru.kozhukhov.sergey.learningprogram.models.Week;


/*
 *  Адаптер для отображения списка недель
 * */
public class AdapterWeeks extends RecyclerView.Adapter<AdapterWeeks.ViewHolder> {

    /*
     * mWeeks - список недель
     * mDateToday - сегодняшняя дата
     * */
    private final List<Week> mWeeks;
    private final GregorianCalendar mDateToday;

    /*
     * mOnItemWeekClickListener - обработчик нажатия на ячейку
     * */
    private OnItemWeekClickListener mOnItemWeekClickListener;

    public AdapterWeeks(List<Week> mWeeks, Date dateToday, OnItemWeekClickListener onItemWeekClickListener) {

        this.mWeeks = mWeeks;
        this.mOnItemWeekClickListener = onItemWeekClickListener;

        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(dateToday);
        this.mDateToday = calendar;
    }

    /*
    * Возвращение позиции недели, которая соответствует сегодняшней дате
    * */
    public int getPositionToday() {

        Week week = new Week(mDateToday);

        for (int i = 0; i < mWeeks.size(); i++) {
            if (mWeeks.get(i).equals(week)) {
                return i;
            } else if (mWeeks.get(i).after(week))
                return i;
        }
        return mWeeks.size() - 1;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_week, parent, false);

        return new ViewHolder(view, mDateToday);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Week week = mWeeks.get(position);
        holder.bindView(week, mOnItemWeekClickListener);
    }

    @Override
    public int getItemCount() {
        return mWeeks.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView mTextViewWeek;
        private final ImageView mImage;
        private Week mWeek;

        private ViewHolder(@NonNull View itemView, GregorianCalendar dateToday) {
            super(itemView);

            mTextViewWeek = itemView.findViewById(R.id.item_week_and_year_textView_period);
            mImage = itemView.findViewById(R.id._item_week_ImageView_indicator);
            mWeek = new Week(dateToday);

        }

        private void bindView(final Week week, final OnItemWeekClickListener listener) {

            if (mWeek.after(week)) {
                mImage.setImageLevel(1500);
            } else if (mWeek.equals(week)) {
                mImage.setImageLevel(5000);
            } else {
                mImage.setImageLevel(8000);
            }

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Год: ");
            stringBuilder.append(week.getYear());
            stringBuilder.append(", неделя: ");
            stringBuilder.append(")\n ");
            stringBuilder.append("Учебная неделя: ");
            stringBuilder.append(getAdapterPosition() + 1);

            mTextViewWeek.setText(stringBuilder);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.itemClick(week);
                }
            });
        }


    }


}
