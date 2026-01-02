package ru.yandex.practicum.gym;
import java.util.*;

public class Timetable {

    private Map<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        // сохраняем занятие в расписании
        DayOfWeek dayOfWeek = trainingSession.getDayOfWeek();
        TimeOfDay timeOfDay = trainingSession.getTimeOfDay();

        TreeMap<TimeOfDay, List<TrainingSession>> dayTraining = timetable.putIfAbsent(dayOfWeek, new TreeMap<>());
        if (dayTraining == null) {
            dayTraining = timetable.get(dayOfWeek);
        }

        List<TrainingSession> trainingSessions = dayTraining.putIfAbsent(timeOfDay, new ArrayList<>());
        if (trainingSessions == null) {
            trainingSessions = dayTraining.get(timeOfDay);
        }

        trainingSessions.add(trainingSession);
    }

    public TreeMap<TimeOfDay, List<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        return timetable.getOrDefault(dayOfWeek, new TreeMap<>());

    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        return timetable.getOrDefault(dayOfWeek, new TreeMap<>())
                .getOrDefault(timeOfDay, new ArrayList<>());
    }

    public TreeSet<CounterOfTrainings> getCountByCoaches() {
        Map<Coach, Integer> counterOfTrainings = new HashMap<>();
        for (TreeMap<TimeOfDay, List<TrainingSession>> trainingMap : timetable.values()) {
            for (List<TrainingSession> traningList : trainingMap.values()) {
                for (TrainingSession training : traningList) {
                    Integer oldCount = counterOfTrainings.getOrDefault(training.getCoach(), 0);
                    Integer newCount = oldCount + 1;
                    counterOfTrainings.put(training.getCoach(), newCount);
                    }
                }
            }
        TreeSet<CounterOfTrainings> countByCoachSet = new TreeSet<>();
        for(Map.Entry<Coach, Integer> entry : counterOfTrainings.entrySet()) {
            Coach coach = entry.getKey();
            Integer count = entry.getValue();
            CounterOfTrainings counter = new CounterOfTrainings(coach, count);
            countByCoachSet.add(counter);
        }
        return countByCoachSet;
    }
}
