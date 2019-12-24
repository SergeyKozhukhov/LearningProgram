package ru.kozhukhov.sergey.learningprogram.adapters;

import androidx.annotation.NonNull;

import ru.kozhukhov.sergey.learningprogram.models.Lecture;


/*
 * Обработчик нажатия на ячейку RecyclerViewLectures
 * lecture - неделя, соответствующая нажатой ячейке
 * */
public interface OnItemLectureClickListener {
    void onItemClick(@NonNull Lecture lecture);
}
