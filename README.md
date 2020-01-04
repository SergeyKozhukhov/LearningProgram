# LearningProgram

[LearningProgram]

Программа, отображающая список занятий.

[ФУНКЦИИ]

1. Вывод списка всех занятий с web страницы "http://landsovet.ru/learning_program.json";
2. фильтрация занятий:
     - по преподавателям
     - по неделям проведения
     - по преподавателям и неделям проведения
3. цветовая индикация текущего момента времени в расписании. Цвета:
     - зеленый - пройденные занятия
     - желтый - занятия в данным момент/период
     - голубой - предстоящие занятия
4. aвтоматическое определение позиции текущего/предстоящего занятия и текущей недели на вкладке "Все";
5. отображение подробной информации о соответствующем занятии при нажатии на элемента списка лекций.

[ТЕХНОЛОГИИ]

Использование:

- Fragments
- работа с сетью
- AsyncTask (для выхода в сеть)
- Jackson (для работы с json)
- RecyclerView
- Spinner
- GregorianCalendar и Date
    
[ПРИМЕР РАБОТЫ ПРОГРАММЫ]

1. Первый запуск приложения и фокусировка на предстоящем занятии.

![Image alt](/scr/01_01.jpg)

2. Фильтрация занятий по преподавателю.

![Image alt](/scr/01_02.jpg)

3. Фильтрация занятий по неделе.

![Image alt](/scr/01_03.jpg)
