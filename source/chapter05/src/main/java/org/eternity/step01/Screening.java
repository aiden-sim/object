package org.eternity.step01;

import org.money.Money;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

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
