# LearningProgram

[LearningProgram]

Программа, отображающая список занятий.

[ФУНКЦИИ]

1. Вывод списка всех занятий;
2. фильтрация занятий:
  - по преподавателям
  - по неделям проведения
  - по преподавателям и неделям проведения
3. цветовая индикация текущего момента времени в расписании.
  Цвета:
  - зеленый - пройденный занятия
  - желтый - занятия в данным момент/период
  - голубой - предстоящие занятия
4. Автоматическое определение позиции текущего/предстоящего занятия и текущей недели при первом запуске приложения.

[ТЕХНОЛОГИИ]

Использование:

- RecyclerView
- Spinner
- GregorianCalendar и Date
    
[ПРИМЕР РАБОТЫ ПРОГРАММЫ]

1. Первый запуск приложения и фокусировка на предстоящем занятии.

![Image alt](/SCR/01_01.jpg)

2. Фильтрация занятий по преподавателю.

![Image alt](/SCR/01_02.jpg)

3. Фильтрация занятий по неделе.

![Image alt](/SCR/01_03.jpg)