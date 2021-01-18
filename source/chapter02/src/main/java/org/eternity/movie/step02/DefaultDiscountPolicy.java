package org.eternity.movie.step02;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 원래의 DiscountPolicy 클래스
 */
public abstract class DefaultDiscountPolicy implements DiscountPolicy {
    private List<DiscountCondition> conditions = new ArrayList<>(); // 할인 조건

    public DefaultDiscountPolicy(DiscountCondition... conditions) {
        this.conditions = Arrays.asList(conditions);
    }

    /**
     * 조건이 만족할 경우 getDiscountAmount를 통해 할인 요금 계산
     * 조건이 만족하지 않는다면 할인 요금 0원으로 반환
     */
    @Override
    public Money calculateDiscountAmount(Screening screening) {
        for (DiscountCondition each : conditions) {
            if (each.isSatisfiedBy(screening)) {
                return getDiscountAmount(screening);
            }
        }

        return Money.ZERO;
    }

    abstract protected Money getDiscountAmount(Screening screening);
}
