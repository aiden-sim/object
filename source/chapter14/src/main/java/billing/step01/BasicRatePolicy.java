package billing.step01;

import money.Money;

/**
 * 기본 정책 (일반 요금제, 심야 할인 요금제의 중복 로직)
 */
public abstract class BasicRatePolicy implements RatePolicy {
    @Override
    public Money calculateFee(Phone phone) {
        Money result = Money.ZERO;

        for (Call call : phone.getCalls()) {
            result.plus(calculateCallFee(call));
        }

        return result;
    }

    protected abstract Money calculateCallFee(Call call);
}
