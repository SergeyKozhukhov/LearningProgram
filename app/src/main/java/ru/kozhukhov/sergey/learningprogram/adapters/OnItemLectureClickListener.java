package ru.kozhukhov.sergey.learningprogram.adapters;

import androidx.annotation.NonNull;

import ru.kozhukhov.sergey.learningprogram.models.Lecture;


/**
 * Слушатель нажатия на ячейку RecyclerViewLectures
 */
public interface OnItemLectureClickListener {

    /**
     * Обработчик нажатия на ячейку RecyclerViewLectures
     * @param lecture - неделя, соответствующая нажатой ячейке
     */
    void onItemClick(@NonNull Lecture lecture);
}
