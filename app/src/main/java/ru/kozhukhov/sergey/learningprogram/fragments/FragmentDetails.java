package ru.kozhukhov.sergey.learningprogram.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Locale;

import ru.kozhukhov.sergey.learningprogram.R;
import ru.kozhukhov.sergey.learningprogram.adapters.AdapterLectureDetails;
import ru.kozhukhov.sergey.learningprogram.models.Lecture;


/**
 * Фрагмент для отображения детальной информации о лекции
 */
public class FragmentDetails extends Fragment {

    /**
     * ARG_LECTURE - "ключ" для передачи/получения данных
     */
    private static final String ARG_LECTURE = "ARG_LECTURE";

    public static FragmentDetails newInstance(@NonNull Lecture lecture) {
        FragmentDetails fragmentDetails = new FragmentDetails();
        Bundle args = new Bundle(); // создание объекта для передачи данных
        args.putParcelable(ARG_LECTURE, lecture); // запись данных по лекции lecture c ключом ARG_LECTURE в bundle
        fragmentDetails.setArguments(args); // закрепление данных за данных фрагментом с сохранением их при повороте экрана
        return fragmentDetails;
    }

    /*
     * Реализация визуального интерфейса
     *
     * Формирование вида для отображения. Возвращает вид фрагмента. Может возвращать null для невизуальных компонентов.
     * Сюда передаются данные класса Bundle о последнем состоянии фрагмента,
     * а также контейнер активности, куда будет подключаться фрагмент и «надуватель» разметки.
     * */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(
                R.layout.fragment_details, // id фрагмента для визуализации
                container, // получение параметров от container
                false // отмена закрепления фразмента за container
        );
    }

    /*
    * Выполняеться после onCreateView, но до восстановления состояния
    *
    * Вызывается, когда вид сформирован. Сюда передаётся сформированный вид и данные класса Bundle о последнем состоянии фрагмента.
    * Используется для окончательной инициализации вида перед восстановлением сохранённого состояния.
    * В этой точке вид ещё не прикреплён к фрагменту
    * */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Lecture lecture = getLectureFromArgs(); // получение данных по лекции, переданных как параметр в newInstance

        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "EEEE, d MMMM yyyy", // шаблон форматирования
                Locale.getDefault() // язык отображения (получение языка по-умолчанию)
        );

        ((TextView) view.findViewById(R.id.item_lecture_TextView_number)).setText(String.valueOf(lecture.getNumber()));
        ((TextView) view.findViewById(R.id.item_lecture_TextView_date)).setText(dateFormat.format(lecture.getDate().getTime()));
        ((TextView) view.findViewById(R.id.item_lecture_TextView_theme)).setText(lecture.getTheme());
        ((TextView) view.findViewById(R.id.item_lecture_TextView_lector)).setText(lecture.getLector());

        RecyclerView recyclerView = view.findViewById(R.id.fragment_details_rv_item_info);
        recyclerView.setAdapter(new AdapterLectureDetails(lecture.getSubtopics()));
    }

    /**
     * Получение данных по лекции, переданных как параметр в newInstance
     * @return данные по лекции
     */
    @NonNull
    private Lecture getLectureFromArgs(){
        Bundle arguments = getArguments();
        if (arguments == null)
            throw new IllegalStateException("Arguments must be set");
        Lecture lecture = arguments.getParcelable(ARG_LECTURE);
        if (lecture == null)
            throw new IllegalStateException("Lecture must be set");
        return lecture;
    }
}
