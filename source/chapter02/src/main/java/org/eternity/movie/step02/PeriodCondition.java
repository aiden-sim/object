package org.eternity.movie.step02;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class PeriodCondition implements DiscountCondition {
    private DayOfWeek dayOfWeek; // 요일
    private LocalTime startTime; // 시작 시간
    private LocalTime endTime;   // 종료 시간

    public PeriodCondition(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * screening의 상영 요일이 dayOfWeek와 같고 상영 시간이 startTime ~ endTime 에 포함되는 경우 할인 가능
     */
    @Override
    public boolean isSatisfiedBy(Screening screening) {
        return screening.getStartTime().getDayOfWeek().equals(dayOfWeek) &&
                startTime.compareTo(screening.getStartTime().toLocalTime()) <= 0 &&
                endTime.compareTo(screening.getStartTime().toLocalTime()) >= 0;
    }
}
