package org.eternity.movie.step02;

import java.time.LocalDateTime;

public class Screening {

    private Movie movie;                // 상영할 영화
    private int sequence;               // 순번
    private LocalDateTime whenScreened; // 상영 시작 시간

    public Screening(Movie movie, int sequence, LocalDateTime whenScreened) {
        this.movie = movie;
        this.sequence = sequence;
        this.whenScreened = whenScreened;
    }

    // 상영 시작 시간
    public LocalDateTime getStartTime() {
        return whenScreened;
    }

    // 순번의 일치 여부 검사
    public boolean isSequence(int sequence) {
        return this.sequence == sequence;
    }

    // 기본 요금을 반환
    public Money getMovieFee() {
        return movie.getFee();
    }

    /**
     * 영화를 예매한 후 예매 정보를 담고 있다.
     *
     * @param customer      예매자
     * @param audienceCount 인원수
     */
    public Reservation reserve(Customer customer, int audienceCount) {
        return new Reservation(customer, this, calculateFee(audienceCount), audienceCount);
    }

    /**
     * @param audienceCount 인원수
     * @return 전체 예매 요금  (1인당 예매 요금 * 인원수)
     */
    private Money calculateFee(int audienceCount) {
        return movie.calculateMovieFee(this).times(audienceCount);
    }
}
