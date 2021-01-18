package org.eternity.movie.step02;

public class PercentDiscountPolicy extends DefaultDiscountPolicy {
    private double percent; // 할인 비율

    public PercentDiscountPolicy(double percent, DiscountCondition... conditions) {
        super(conditions);
        this.percent = percent;
    }


    /**
     * 일정 금액 비율을 할인
     */
    @Override
    protected Money getDiscountAmount(Screening screening) {
        return screening.getMovieFee().times(percent);
    }
}
