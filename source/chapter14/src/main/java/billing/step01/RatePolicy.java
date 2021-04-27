package billing.step01;

import money.Money;

/**
 * 기본 정책과 부가 정책 포괄
 */
public interface RatePolicy {
    Money calculateFee(Phone phone);
}
