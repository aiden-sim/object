package org.eternity.movie.step01;

public class AmountDiscountPolicy extends DiscountPolicy {
    private Money discountAmount; // 할인 금액

    public AmountDiscountPolicy(Money discountAmount, DiscountCondition... conditions) {
        super(conditions);
        this.discountAmount = discountAmount;
    }

    /**
     * 일정 금액을 할인
     */
    @Override
    protected Money getDiscountAmount(Screening screening) {
        return discountAmount;
    }
}
