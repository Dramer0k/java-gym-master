package ru.yandex.practicum.gym;

public class CounterOfTrainings implements Comparable<CounterOfTrainings> {

    private final Coach coach;
    private final Integer trainingCount;

    public CounterOfTrainings(Coach coach, Integer trainingCount) {
        this.coach = coach;
        this.trainingCount = trainingCount;
    }

    public Coach getCoach() {
        return coach;
    }

    public Integer getTrainingCount() {
        return trainingCount;
    }

    @Override
    public int compareTo(CounterOfTrainings o) {
        return o.trainingCount.compareTo(this.trainingCount);
    }




}
