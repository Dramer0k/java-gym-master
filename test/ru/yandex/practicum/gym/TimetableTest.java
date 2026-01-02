package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.*;

import java.util.*;

public class TimetableTest {

    Timetable timetable = new Timetable();
    Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
    Coach coach2 = new Coach("Марусов", "Петр", "Алексеевич");
    Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
    Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        TrainingSession singleTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник вернулось одно занятие
        Assertions.assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());
        //Проверить, что за вторник не вернулось занятий
        Assertions.assertEquals(new TreeMap<>(), timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY));
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        // Проверить, что за понедельник вернулось одно занятие
        Assertions.assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());

        // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        // (как будто можно проще, но пока не понял как)
        TreeMap<TimeOfDay, List<TrainingSession>> thursdaySession = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);
        List<TimeOfDay> timeOfDays = new ArrayList<>(thursdaySession.keySet());

        Assertions.assertEquals(2, thursdaySession.size());
        Assertions.assertEquals(13, timeOfDays.get(0).getHours());
        Assertions.assertEquals(0, timeOfDays.get(0).getMinutes());
        Assertions.assertEquals(20, timeOfDays.get(1).getHours());
        Assertions.assertEquals(0, timeOfDays.get(1).getMinutes());



        // Проверить, что за вторник не вернулось занятий
        Assertions.assertEquals(new TreeMap<>(), timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY));
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        TrainingSession singleTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник в 13:00 вернулось одно занятие
        Assertions.assertEquals(1, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(13, 0)).size());
        //Проверить, что за понедельник в 14:00 не вернулось занятий
        Assertions.assertEquals(new ArrayList<>(), timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(14, 0)));
    }

    @Test
    void testGetTwoTrainingSessionsForOneTime() {

        TrainingSession mondayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach2,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(mondayAdultTrainingSession);
        timetable.addNewTrainingSession(mondayChildTrainingSession);

        Assertions.assertEquals(2, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(13, 0)).size());
    }

    @Test
    void testGetCoachForTrainingSession() {
        TrainingSession mondayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(mondayAdultTrainingSession);

        List<TrainingSession> trainingSessions = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        Assertions.assertEquals("Николай", trainingSessions.getFirst().getCoach().getName());
    }

    @Test
    void testGetAgeForTrainingSession() {
        TrainingSession mondayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(mondayAdultTrainingSession);

        List<TrainingSession> trainingSessions = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        Assertions.assertEquals(Age.ADULT, trainingSessions.getFirst().getGroup().getAge());

    }

    @Test
    void testGetCountByCoaches() {

        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession mondayChildTrainingSession2 = new TrainingSession(groupChild, coach2,
                DayOfWeek.MONDAY, new TimeOfDay(14, 0));
        TrainingSession thursdayChildTrainingSession2 = new TrainingSession(groupChild, coach2,
                DayOfWeek.THURSDAY, new TimeOfDay(14, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);
        timetable.addNewTrainingSession(mondayChildTrainingSession2);
        timetable.addNewTrainingSession(thursdayChildTrainingSession2);

        Assertions.assertEquals(3, timetable.getCountByCoaches().getFirst().getTrainingCount());
        Assertions.assertEquals(2, timetable.getCountByCoaches().getLast().getTrainingCount());
        Assertions.assertEquals(2, timetable.getCountByCoaches().size());
    }

    @Test
    void testGetEmptyCountByCoaches() {
        Assertions.assertEquals(new TreeSet<>(), timetable.getCountByCoaches());
    }

}
