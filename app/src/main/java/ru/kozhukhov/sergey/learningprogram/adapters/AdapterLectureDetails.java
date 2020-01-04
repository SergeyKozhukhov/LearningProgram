package ru.kozhukhov.sergey.learningprogram.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.kozhukhov.sergey.learningprogram.R;

/**
 * Адаптер для отображения детальной информации о лекции
 */
public class AdapterLectureDetails extends RecyclerView.Adapter<AdapterLectureDetails.LectureDetailHolder> {

    /**
     * mSubtopics - список дополнитльной информации по лекции
     */
    private final List<String> mSubtopics;

    public AdapterLectureDetails(@NonNull List<String> subtopics) {
        this.mSubtopics = new ArrayList<>(subtopics);
    }

    @NonNull
    @Override
    public LectureDetailHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new LectureDetailHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LectureDetailHolder holder, int position) {
        holder.bindView(mSubtopics.get(position));
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

        private void bindView(final String subtopic){
            /*
             * Формат отображения доп. информации: "№ пункта: информация"
             * */
            mName.setText(itemView.getResources().getString(R.string.fragment_details_info_text, getAdapterPosition()+1, subtopic));
        }
    }
}
