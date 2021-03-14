package movie;

import money.Money;

import java.time.Duration;

/**
 * 컴파일 시점에 Movie는 PercentDiscountPolicy, AmountDiscountPolicy 에 대해 알지 못한다.
 * 런타임 시점에 Movie는 PercentDiscountPolicy, AmountDiscountPolicy과 협력 한다.
 */
public class Movie {
    private String title;
    private Duration runningTime;
    private Money fee;
    private DiscountPolicy discountPolicy;

    public Movie(String title, Duration runningTime, Money fee, DiscountPolicy discountPolicy) {
        this.title = title;
        this.runningTime = runningTime;
        this.fee = fee;
        this.discountPolicy = discountPolicy;
    }

    public Money getFee() {
        return fee;
    }

    public Money calculateMovieFee(Screening screening) {
        return fee.minus(discountPolicy.calculateDiscountAmount(screening));
    }
}

