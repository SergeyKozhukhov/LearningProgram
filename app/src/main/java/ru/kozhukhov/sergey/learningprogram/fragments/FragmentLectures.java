package ru.kozhukhov.sergey.learningprogram.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.ref.WeakReference;
import java.util.Date;
import java.util.List;

import ru.kozhukhov.sergey.learningprogram.R;
import ru.kozhukhov.sergey.learningprogram.adapters.AdapterLectors;
import ru.kozhukhov.sergey.learningprogram.adapters.AdapterLectures;
import ru.kozhukhov.sergey.learningprogram.adapters.AdapterWeeks;
import ru.kozhukhov.sergey.learningprogram.adapters.OnItemLectureClickListener;
import ru.kozhukhov.sergey.learningprogram.adapters.OnItemWeekClickListener;
import ru.kozhukhov.sergey.learningprogram.models.Lecture;
import ru.kozhukhov.sergey.learningprogram.models.Week;
import ru.kozhukhov.sergey.learningprogram.providerdata.ProviderLearningProgram;


/**
 * Фрагмент для отображения списка лекций с возможностью фильтрации по учебной неделе и преподавателям
 */
public class FragmentLectures extends Fragment {

    /**
     * POSITION_ALL - начальня позиция в mSpinnerLectors, отвечающая за отображение всех лекций
     */
    private static final int POSITION_ALL = 0;

    /**
     * dateToday - текущая дата
     */
    private Date dateToday;

    /**
     * indicatorWeek - позиция выбранной недели из списка
     */
    private Week indicatorWeek = null;

    /**
     * mProviderLearningProgram - поставщик данных по учебной программе
     */
    private ProviderLearningProgram mProviderLearningProgram = new ProviderLearningProgram();;

    /**
     * mAdapterLectures - адаптер лекций
     */
    private AdapterLectures mAdapterLectures;

    /**
     * mAdapterLectors - адаптер лекторов
     */
    private AdapterLectors mAdapterLectors;

    /**
     * mAdapterWeeks - адаптер учебных недель
     */
    private AdapterWeeks mAdapterWeeks;

    /**
     * mRecyclerViewLectures - отображение списка лекций
     */
    private RecyclerView mRecyclerViewLectures;

    /**
     * mRecyclerViewWeeks - отображение списка учебных недель
     */
    private RecyclerView mRecyclerViewWeeks;

    /**
     * mSpinnerLectors - отображение списка лекторов
     */
    private Spinner mSpinnerLectors;

    /**
     * mLoadingView - форма отображения процесса загрузки данных
     */
    private View mLoadingView;

    /**
     * mOnItemLectureClickListener - обработчик нажатия на элемент списка лекций
     */
    private OnItemLectureClickListener mOnItemLectureClickListener;

    /**
     * mOnItemWeekClickListener - обработчик нажатия на элемент списка учебных недель
     */
    private OnItemWeekClickListener mOnItemWeekClickListener;


    public static Fragment newInstance() // метод для создания нового экземпляра фрагмента
    {
        return new FragmentLectures();
    }

    /*
    * Создание фрагмента
    * */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true); // нужно для того, чтобы инстанс ProviderLearningProvider не убивался после смены конфигурации
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lectures, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerViewLectures = view.findViewById(R.id.recycler_view_lectures);
        mRecyclerViewWeeks = view.findViewById(R.id.recycler_view_weeks);
        mSpinnerLectors = view.findViewById(R.id.spinner_lectors);
        mLoadingView = view.findViewById(R.id.loading_view);
    }

    /*
    * Окончательная инициализация. Вызывется когда метод onCreate() активности был возвращен. Активность создана, фрагмент в неё вставлен.
    * Используется, например, для восстановления состояния фрагмента.
    * Сюда передаются данные класса Bundle о последнем состоянии фрагмента.
    * */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        dateToday = new Date();

        if (mProviderLearningProgram.provideLectures() == null){
            /*
            * Каждый экземпляр класса AsyncTask может быть запущен всего один раз.
            * Попытка повторного вызова метода execute() приведёт к выбросу исключения.
            * */
            new LoadLecturesTask(this, mProviderLearningProgram.provideLectures() == null).execute();
        }
        else {
            initRecyclerViewLecturesListener();
            initRecyclerViewLectures();
            initRecyclerViewWeeksListener();
            initRecyclerViewWeeks();
            initSpinnerLectors(mProviderLearningProgram.provideLectures() == null);
        }
    }

    /**
     * Инициализация компонентов RecyclerViewLectures
     */
    private void initRecyclerViewLectures() {

        mAdapterLectures = new AdapterLectures();
        mAdapterLectures.setDateToday(dateToday);
        mAdapterLectures.setLectures(mProviderLearningProgram.provideLectures());
        mAdapterLectures.setClickListener(mOnItemLectureClickListener);

        LinearLayoutManager layoutManagerVertical = new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false);

        mRecyclerViewLectures.setLayoutManager(layoutManagerVertical);
        mRecyclerViewLectures.setAdapter(mAdapterLectures);
        mRecyclerViewLectures.scrollToPosition(mAdapterLectures.getPositionToday());
    }

    /**
     * Инициализация обработчика нажатия на ячейку списка лекций
     */
    private void initRecyclerViewLecturesListener(){

        // РЕАЛИЗОВАТЬ В MainActivity!!!
        mOnItemLectureClickListener = new OnItemLectureClickListener() {
            /**
             * Переход к просмотру более детальной информации о лекции
             */
            @Override
            public void onItemClick(@NonNull Lecture lecture) {
                requireActivity()// получение activity, с которым данный фрагмент связан в текущий момент времени
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.activity_main_linear_layout_root, FragmentDetails.newInstance(lecture))
                        .addToBackStack(FragmentDetails.class.getSimpleName()) // добавление в BackStack c идентификатором имени класса
                        .commit();
            }
        };
    }

    /**
     * Инициализация обработчика нажатия на ячейку списка учебных недель
     */
    private void initRecyclerViewWeeksListener() {

        mOnItemWeekClickListener = new OnItemWeekClickListener() {
            /**
             * Обновление списка лекций в соответствии с указанным преподавателем и № недели
             */
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

    /**
     * Инициализация компонентов RecyclerViewWeeks
     */
    private void initRecyclerViewWeeks() {

        mAdapterWeeks = new AdapterWeeks(mProviderLearningProgram.providerWeeks(), dateToday, mOnItemWeekClickListener);

        LinearLayoutManager layoutManagerHorizontal = new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false);

        mRecyclerViewWeeks.setLayoutManager(layoutManagerHorizontal);
        mRecyclerViewWeeks.setAdapter(mAdapterWeeks);
        mRecyclerViewWeeks.scrollToPosition(mAdapterWeeks.getPositionToday());

    }

    /**
     * Инициализация компонентов SpinnerLectors
     */
    private void initSpinnerLectors(final boolean isFirstCreate) {

        final List<String> itemsLectors = mProviderLearningProgram.providerLectors();
        itemsLectors.add(POSITION_ALL, getString(R.string.spinner_lectors_item_all));

        mAdapterLectors = new AdapterLectors(itemsLectors);
        mSpinnerLectors.setAdapter(mAdapterLectors);

        mSpinnerLectors.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            /**
             * Обновление списка лекций в соответствии с указанным преподавателем и № недели
             */
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String name = mAdapterLectors.getItem(position).toString();

                if (position == POSITION_ALL) {

                    if (isFirstCreate)
                        mRecyclerViewLectures.scrollToPosition(mAdapterLectures.getPositionToday());
                    else{
                        // дважды производиться загрузка при первом запуске, т.к. выполняеться обработка по выбранной строке
                        mAdapterLectures.setLectures(mProviderLearningProgram.provideLectures());
                    }
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

    /**
     * Реализация ассинхронной загрузки данных
     *
     * Параметры AsyncTask:
     * <"входной тип для doInBackground",
     * "входной тип для onProgressUpdate",
     * "возвращаемый тип doInBackground и входной тип для onPostExecute">
     */
    private static class LoadLecturesTask extends AsyncTask<Void, Void, List<Lecture>>{

        /**
         * mFragmentRef - слабая ссылка на фрагмент
         * слабая ссылка для того, чтобы не было утечки памяти в случае уничтожения фрагмента в момент работы AsyncTask
         */
        private final WeakReference <FragmentLectures> mFragmentRef;
        /**
         * mProviderLearningProgram - поставщик данных лекций
         */
        private final ProviderLearningProgram mProviderLearningProgram;

        /**
         * mIsFirstCreate - флаг для определения первый ли раз производится заружка данных (true - первый, false - последующие)
         */
        private boolean mIsFirstCreate;

        private LoadLecturesTask(@NonNull FragmentLectures fragmentRef, boolean isFirstCreate) {
            this.mFragmentRef = new WeakReference<>(fragmentRef);
            this.mProviderLearningProgram = fragmentRef.mProviderLearningProgram;
            this.mIsFirstCreate = isFirstCreate;
        }

        @Override
        protected void onPreExecute() {
            FragmentLectures fragmentLectures = mFragmentRef.get();
            if (fragmentLectures != null){
                fragmentLectures.mLoadingView.setVisibility(View.VISIBLE);
            }
        }

        @Override
        protected List<Lecture> doInBackground(Void... voids) {
             //return mProviderLearningProgram.loadLectureFromData();
             return mProviderLearningProgram.loadLecturesFromWeb();
        }

        @Override
        protected void onPostExecute(List<Lecture> lectures) {
            FragmentLectures fragmentLectures = mFragmentRef.get();
            if (fragmentLectures == null)
                return;
            fragmentLectures.mLoadingView.setVisibility(View.GONE);
            if (lectures == null)
                Toast.makeText(fragmentLectures.requireContext(), fragmentLectures.getString(R.string.load_lecture_tesk_message_fail), Toast.LENGTH_SHORT).show();
            else {
                fragmentLectures.initRecyclerViewLecturesListener();
                fragmentLectures.initRecyclerViewLectures();
                fragmentLectures.initRecyclerViewWeeksListener();
                fragmentLectures.initRecyclerViewWeeks();
                fragmentLectures.initSpinnerLectors(mIsFirstCreate);
            }
        }
    }

}
