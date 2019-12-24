package ru.kozhukhov.sergey.learningprogram.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/*
* Адаптер для отображения детальной информации о лекции
* */
public class AdapterLectureDetails extends RecyclerView.Adapter<AdapterLectureDetails.LectureDetailHolder> {

    /*
    * mSubtopics - список дополнитльной информации по лекции
    * */
    private final List<String> mSubtopics;

    public AdapterLectureDetails(@NonNull List<String> mSubtopics) {
        this.mSubtopics = new ArrayList<>(mSubtopics);
    }

    @NonNull
    @Override
    public LectureDetailHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new LectureDetailHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LectureDetailHolder holder, int position) {
        /*
        * Формат отображения доп. информации: "№ пункта: информация"
        * */
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(position+1);
        stringBuilder.append(": ");
        stringBuilder.append(mSubtopics.get(position));
        holder.mName.setText(stringBuilder);
    }

    @Override
    public int getItemCount() {
        return mSubtopics.size();
    }

    static class LectureDetailHolder extends RecyclerView.ViewHolder {

        private final TextView mName;

        LectureDetailHolder(@NonNull View itemView) {
            super(itemView);
            mName = itemView.findViewById(android.R.id.text1);
        }
    }
}
