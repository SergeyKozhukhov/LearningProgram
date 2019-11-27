package ru.kozhukhov.sergey.learningprogram.adapters;

import ru.kozhukhov.sergey.learningprogram.models.Week;

/*
 * Обработчик нажатия на ячейку RecyclerViewWeeks
 * week - неделя, соответствующая нажатой ячейке
 * */

public interface OnItemClickListener {
    void itemClick(Week week);
}
