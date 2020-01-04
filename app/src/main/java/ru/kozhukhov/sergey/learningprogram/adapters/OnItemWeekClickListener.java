package ru.kozhukhov.sergey.learningprogram.adapters;

import ru.kozhukhov.sergey.learningprogram.models.Week;

/**
 * Слушатель нажатия на ячейку RecyclerViewWeeks
 */
public interface OnItemWeekClickListener {
    /**
     * Обработчик нажатия на ячейку RecyclerViewWeeks
     * @param week - неделя, соответствующая нажатой ячейке
     */
    void itemClick(Week week);
}
