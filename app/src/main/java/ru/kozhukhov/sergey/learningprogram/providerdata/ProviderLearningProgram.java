package ru.kozhukhov.sergey.learningprogram.providerdata;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;

import ru.kozhukhov.sergey.learningprogram.models.Lecture;
import ru.kozhukhov.sergey.learningprogram.models.Week;

/**
 * Поставщик данных об учебной программе
 */
public class ProviderLearningProgram {

    /**
     * LECTURES_URL - ссылка с данными по лекциям в json формате
     */
    private static final String LECTURES_URL = "http://landsovet.ru/learning_program.json";

    /**
     * mLectures - список лекций с информацией о них
     */
    private List<Lecture> mLectures;

    /**
     * Загрузка данных по лекциям из массива
     */
    public List<Lecture> loadLectureFromData(){
        mLectures = Arrays.asList(
                new Lecture(1, new GregorianCalendar(2019, Calendar.NOVEMBER, 12), "Вводное занятие", "Соколов"),
                new Lecture(2, new GregorianCalendar(2019, Calendar.NOVEMBER, 14), "View, Layouts", "Соколов"),
                new Lecture(3, new GregorianCalendar(2019, Calendar.NOVEMBER, 16), "Drawables", "Соколов"),
                new Lecture(4, new GregorianCalendar(2019, Calendar.NOVEMBER, 19), "Activity", "Сафарян"),
                new Lecture(5, new GregorianCalendar(2019, Calendar.NOVEMBER, 21), "Адаптеры", "Чумак"),
                new Lecture(6, new GregorianCalendar(2019, Calendar.NOVEMBER, 23), "UI: практика", "Кудрявцев"),
                new Lecture(7, new GregorianCalendar(2019, Calendar.NOVEMBER, 26), "Custom View", "Кудрявцев"),
                new Lecture(8, new GregorianCalendar(2019, Calendar.NOVEMBER, 28), "Touch events", "Бильчук"),
                new Lecture(9, new GregorianCalendar(2019, Calendar.NOVEMBER, 30), "Сложные жесты", "Соколов"),
                new Lecture(10, new GregorianCalendar(2019, Calendar.DECEMBER, 3), "Layout & Measurement", "Кудрявцев"),
                new Lecture(11, new GregorianCalendar(2019, Calendar.DECEMBER, 5), "Custom ViewGroup", "Кудрявцев"),
                new Lecture(12, new GregorianCalendar(2019, Calendar.DECEMBER, 7), "Анимации", "Чумак"),
                new Lecture(13, new GregorianCalendar(2019, Calendar.DECEMBER, 10), "Практика View", "Соколов"),
                new Lecture(14, new GregorianCalendar(2019, Calendar.DECEMBER, 12), "Фрагменты: база", "Бильчук"),
                new Lecture(15, new GregorianCalendar(2019, Calendar.DECEMBER, 14), "Фрагменты: практика", "Соколов"),
                new Lecture(16, new GregorianCalendar(2019, Calendar.DECEMBER, 17), "Фоновая работа", "Чумак"),
                new Lecture(17, new GregorianCalendar(2019, Calendar.DECEMBER, 19), "Абстракции фон/UI", "Леонидов"),
                new Lecture(18, new GregorianCalendar(2019, Calendar.DECEMBER, 21), "Фон: практика", "Чумак"),
                new Lecture(19, new GregorianCalendar(2020, Calendar.JANUARY, 14), "BroadcastReceiver", "Бильчук"),
                new Lecture(20, new GregorianCalendar(2020, Calendar.JANUARY, 16), "Runtime permissions", "Кудрявцев"),
                new Lecture(21, new GregorianCalendar(2020, Calendar.JANUARY, 18), "Service", "Леонидов"),
                new Lecture(22, new GregorianCalendar(2020, Calendar.JANUARY, 21), "Service: практика", "Леонидов"),
                new Lecture(23, new GregorianCalendar(2020, Calendar.JANUARY, 23), "Service: биндинг", "Леонидов"),
                new Lecture(24, new GregorianCalendar(2020, Calendar.JANUARY, 25), "Preferences", "Сафарян"),
                new Lecture(25, new GregorianCalendar(2020, Calendar.JANUARY, 28), "SQLite", "Бильчук"),
                new Lecture(26, new GregorianCalendar(2020, Calendar.JANUARY, 30), "SQLite: Room", "Соколов"),
                new Lecture(27, new GregorianCalendar(2020, Calendar.FEBRUARY, 1), "ContentProvider", "Сафарян"),
                new Lecture(28, new GregorianCalendar(2020, Calendar.FEBRUARY, 4), "FileProvider", "Соколов"),
                new Lecture(29, new GregorianCalendar(2020, Calendar.FEBRUARY, 6), "Геолокация", "Леонидов"),
                new Lecture(30, new GregorianCalendar(2020, Calendar.FEBRUARY, 8), "Material", "Чумак"),
                new Lecture(31, new GregorianCalendar(2020, Calendar.FEBRUARY, 11), "UI-тесты", "Сафарян"),
                new Lecture(32, new GregorianCalendar(2020, Calendar.FEBRUARY, 13), "Финал", "Соколов"));
        return new ArrayList<>(mLectures);
    }

    /**
     * Загрузка данных по лекциям из сети
     */
    public List<Lecture> loadLecturesFromWeb(){
        InputStream inputStream = null;
        try {
            final URL url = new URL(LECTURES_URL);
            URLConnection connection = url.openConnection();
            inputStream = connection.getInputStream();
            ObjectMapper objectMapper = new ObjectMapper();
            Lecture[] lectures = objectMapper.readValue(inputStream, Lecture[].class);
            mLectures = Arrays.asList(lectures);
            List<Lecture> result = new ArrayList<>(mLectures);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * Возвращение всех лекций курса
     */
    public List<Lecture> provideLectures() {
         return mLectures;
    }

    /**
     * Возвращение списка лекторов курса
     */
    public List<String> providerLectors() {
        Set<String> lectorsSet = new HashSet<>();
        for (Lecture lecture : mLectures) {
            lectorsSet.add(lecture.getLector());
        }
        return new ArrayList<>(lectorsSet);
    }

    /**
     * Возвращение списка недель, на которых проводяться лекции
     */
    public List<Week> providerWeeks() {
        Set<Week> weeks = new LinkedHashSet<>();
        for (Lecture lecture : mLectures) {
            Week week = new Week(lecture.getDate());
            weeks.add(week);
        }
        return new ArrayList<>(weeks);
    }

    /**
     * Возвращение списка лекций с заданной фамилии лектора
     * @param lectorName - фамилия лектора, по которой производиться фильтрация
     */
    public List<Lecture> filterBy(String lectorName) {
        List<Lecture> lectures = new ArrayList<>();
        for (Lecture lecture : mLectures) {
            if (lecture.getLector().equals(lectorName)) {
                lectures.add(lecture);
            }
        }
        return lectures;
    }

    /**
     * Возвращение списка лекций, проводящихся на заданной неделе
     * @param week - неделя, на которой производиться поиск лекций
     */
    public List<Lecture> filterBy(Week week) {

        List<Lecture> lectures = new ArrayList<>();
        for (Lecture lecture : mLectures) {

            Week weekLecture = new Week(lecture.getDate());

            if ((weekLecture.equals(week))) {
                lectures.add(lecture);
            }
        }
        return lectures;
    }

    /**
     * Возвращение списка лекций, проводящихся определенным лектором на заданной неделе
     * @param lectorName - имя лектора, проводящего занятие
     * @param week - неделя, на которой производиться поиск лекций
     */
    public List<Lecture> filterBy(String lectorName, Week week) {
        List<Lecture> lectures = new ArrayList<>();

        for (Lecture lecture : mLectures) {

            Week weekLecture = new Week(lecture.getDate());
            if (lecture.getLector().equals(lectorName) && (weekLecture.equals(week))) {
                lectures.add(lecture);
            }
        }
        return lectures;
    }

}
