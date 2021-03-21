package movie.step03;

import money.Money;
import movie.step03.locator.ServiceLocator;
import movie.step03.pricing.AmountDiscountPolicy;
import movie.step03.pricing.PeriodCondition;
import movie.step03.pricing.SequenceCondition;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;

public class Main {
    public static void main(String[] args) {
        ServiceLocator.provide(
                new AmountDiscountPolicy(
                        Money.wons(800),
                        new SequenceCondition(1),
                        new SequenceCondition(10),
                        new PeriodCondition(DayOfWeek.MONDAY, LocalTime.of(10, 0),
                                            LocalTime.of(11, 59)),
                        new PeriodCondition(DayOfWeek.THURSDAY,
                                            LocalTime.of(18, 0), LocalTime.of(21, 0))
                ));
        Movie avatar = new Movie("아바타",
                                 Duration.ofMinutes(120),
                                 Money.wons(1000));
    }
}
