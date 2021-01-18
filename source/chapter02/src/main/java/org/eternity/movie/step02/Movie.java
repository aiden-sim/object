package org.eternity.movie.step02;

import java.time.Duration;


public class Movie {
    private String title;                   // 제목
    private Duration runningTime;           // 상영시간
    private Money fee;                      // 기본요금
    private DiscountPolicy discountPolicy;  // 할인 정책

    public Movie(String title, Duration runningTime, Money fee, DiscountPolicy discountPolicy) {
        this.title = title;
        this.runningTime = runningTime;
        this.fee = fee;
        this.discountPolicy = discountPolicy;
    }

    public Money getFee() {
        return fee;
    }

    // 기본요금에서 할인 요금 차감
    public Money calculateMovieFee(Screening screening) {
        return fee.minus(discountPolicy.calculateDiscountAmount(screening));
    }

    public void changeDiscountPolicy(DiscountPolicy discountPolicy) {
        this.discountPolicy = discountPolicy;
    }
}
