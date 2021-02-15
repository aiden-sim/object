package org.eternity.step02;

import org.money.Money;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

/**
 * DiscountCondition 개선하기
 * SequenceCondition, PeriodCondition의 목록을 따로 유지 (문제점)
 * 1) 클래스 양쪽 모두에게 결합 (결합도가 높아짐)
 * 2) 새로운 할인 조건을 추가하기 더 어려워짐
 */
public class Screening {
    private Movie movie;
    private int sequence;
    private LocalDateTime whenScreened;


    // 예매하라 메시지
    public Reservation reserve(Customer customer, int audienceCount) {
        return new Reservation(customer, this, calculateFee(audienceCount), audienceCount);
    }

    // movie 에게 가격을 계산하라 메시지 전송
    private Money calculateFee(int audienceCount) {
        return movie.calculateMovieFee(this).times(audienceCount);
    }

    public OffsetDateTime getWhenScreened() {
        return null;
    }

    public int getSequence() {
        return 0;
    }
}
