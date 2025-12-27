package ru.yandex.practicum.gym;
import java.util.*;

public class Timetable {

    private Map<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        //сохраняем занятие в расписании
        DayOfWeek dayOfWeek = trainingSession.getDayOfWeek();
        TimeOfDay timeOfDay = trainingSession.getTimeOfDay();

        TreeMap<TimeOfDay, List<TrainingSession>> dayTraining = timetable.get(dayOfWeek);
        if (Objects.isNull(dayTraining)) {
            dayTraining = new TreeMap<>();
            timetable.put(dayOfWeek, dayTraining);
        }

        List<TrainingSession> trainingSessions = dayTraining.get(timeOfDay);
        if (Objects.isNull(trainingSessions)) {
            trainingSessions = new ArrayList<>();
            dayTraining.put(timeOfDay, trainingSessions);
        }

        trainingSessions.add(trainingSession);


    }

    public TreeMap<TimeOfDay, List<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        return timetable.get(dayOfWeek);

    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        return timetable.get(dayOfWeek).get(timeOfDay);
    }
}
