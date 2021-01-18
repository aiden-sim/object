package org.eternity.movie.step02;

/**
 * DiscountPolicy 클래스의 인터페이스를 구현하도록 변경
 */
public class NoneDiscountPolicy implements DiscountPolicy {
    @Override
    public Money calculateDiscountAmount(Screening screening) {
        return Money.ZERO;
    }
}
