package ru.kozhukhov.sergey.learningprogram;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import ru.kozhukhov.sergey.learningprogram.fragments.FragmentLectures;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager() // получение менеджера фрагментов
                    .beginTransaction() // открытие транзакции
                    .add( // помещаем фрагмент в контейнер
                            R.id.activity_main_linear_layout_root, // контейнер для фрагмента
                            FragmentLectures.newInstance() // объект фрагмента
                    )
                    .commit(); // фиксация транзакции
        }

    }
}
