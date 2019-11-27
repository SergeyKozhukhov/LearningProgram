package ru.kozhukhov.sergey.learningprogram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import java.util.Date;
import java.util.List;

import ru.kozhukhov.sergey.learningprogram.adapters.AdapterLectors;
import ru.kozhukhov.sergey.learningprogram.adapters.AdapterWeeks;
import ru.kozhukhov.sergey.learningprogram.adapters.AdapterLectures;
import ru.kozhukhov.sergey.learningprogram.adapters.OnItemClickListener;
import ru.kozhukhov.sergey.learningprogram.providerdata.ProviderLearningProgram;
import ru.kozhukhov.sergey.learningprogram.models.Week;

public class MainActivity extends AppCompatActivity {

    /*
    * POSITION_ALL - начальня позиция в mSpinnerLectors, отвечающая за отображение всех лекций
    * dateToday - текущая дата
    * indicatorWeek - позиция выбранной недели из списка
    * */

    private static final int POSITION_ALL = 0;
    private Date dateToday;
    private Week indicatorWeek = null;

    private ProviderLearningProgram mProviderLearningProgram;

    private AdapterLectures mAdapterLectures;
    private AdapterLectors mAdapterLectors;
    private AdapterWeeks mAdapterWeeks;

    private RecyclerView mRecyclerViewLectures;
    private RecyclerView mRecyclerViewWeeks;

    private Spinner mSpinnerLectors;

    OnItemClickListener mOnItemClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dateToday = new Date();

        mProviderLearningProgram = new ProviderLearningProgram();

        initRecyclerViewLectures();
        initRecyclerViewWeeksListener();
        initRecyclerViewWeeks();
        initSpinnerLectors();
    }

    /*
     * Инициализация компонентов RecyclerViewLectures
     * */

    private void initRecyclerViewLectures() {

        mAdapterLectures = new AdapterLectures();
        mAdapterLectures.setDateToday(dateToday);
        mAdapterLectures.setLectures(mProviderLearningProgram.provideLectures());

        mRecyclerViewLectures = findViewById(R.id.recycler_view_lectures);

        LinearLayoutManager layoutManagerVertical = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        mRecyclerViewLectures.setLayoutManager(layoutManagerVertical);
        mRecyclerViewLectures.setAdapter(mAdapterLectures);


    }

    private void initRecyclerViewWeeksListener() {

        /*
         * Обновление списка лекций в соответствии с указанным преподавателем и № недели
         * */

        mOnItemClickListener = new OnItemClickListener() {
            @Override
            public void itemClick(Week week) {
                indicatorWeek = week;

                int spinnerPosition = mSpinnerLectors.getSelectedItemPosition();

                if (spinnerPosition > 0) {
                    String name = mAdapterLectors.getItem(spinnerPosition).toString();
                    mAdapterLectures.setLectures(mProviderLearningProgram.filterBy(name, week));
                } else {
                    mAdapterLectures.setLectures(mProviderLearningProgram.filterBy(week));
                }
                mRecyclerViewLectures.setAdapter(mAdapterLectures);
            }
        };
    }

    /*
     * Инициализация компонентов RecyclerViewWeeks
     * */

    private void initRecyclerViewWeeks() {

        mRecyclerViewWeeks = findViewById(R.id.recycler_view_weeks);

        mAdapterWeeks = new AdapterWeeks(mProviderLearningProgram.providerWeeks(), dateToday, mOnItemClickListener);

        LinearLayoutManager layoutManagerHorizontal = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);

        mRecyclerViewWeeks.setLayoutManager(layoutManagerHorizontal);
        mRecyclerViewWeeks.setAdapter(mAdapterWeeks);
        mRecyclerViewWeeks.scrollToPosition(mAdapterWeeks.getPositionToday());

    }

    /*
     * Инициализация компонентов SpinnerLectors
     * */

    private void initSpinnerLectors() {

        final List<String> itemsLectors = mProviderLearningProgram.providerLectors();
        itemsLectors.add(POSITION_ALL, "Все");

        mAdapterLectors = new AdapterLectors(itemsLectors);

        mSpinnerLectors = findViewById(R.id.spinner_lectors);
        mSpinnerLectors.setAdapter(mAdapterLectors);

        /*
         * Обновление списка лекций в соответствии с указанным преподавателем и № недели
         * */

        mSpinnerLectors.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String name = mAdapterLectors.getItem(position).toString();

                if (position == POSITION_ALL) {
                    indicatorWeek = null;
                    mAdapterLectures.setLectures(mProviderLearningProgram.provideLectures());
                    mRecyclerViewLectures.smoothScrollToPosition(mAdapterLectures.getPositionToday());
                } else if (indicatorWeek != null) {
                    mAdapterLectures.setLectures(mProviderLearningProgram.filterBy(name, indicatorWeek));
                } else {
                    mAdapterLectures.setLectures(mProviderLearningProgram.filterBy(name));
                }

                mRecyclerViewLectures.setAdapter(mAdapterLectures);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

}
