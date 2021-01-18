package org.eternity.movie.step02;

/**
 * 원래 추상 클래스를 인터페이스로 변경하고 인터페이스만 공유
 */
public interface DiscountPolicy {
    Money calculateDiscountAmount(Screening screening);
}
