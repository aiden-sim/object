package billing.step05;

import money.Money;

/**
 * 부가 정책 추상화
 */
public abstract class AdditionalRatePolicy implements RatePolicy {
    private RatePolicy next; // 기본 정책이나 다른 부가 정책의 인스턴스를 참조할 수 있어야 함 (어떤 종류의 정책과도 합성될 수 있음)

    public AdditionalRatePolicy(RatePolicy next) {
        this.next = next;
    }

    @Override
    public Money calculateFee(Phone phone) {
        Money fee = next.calculateFee(phone);
        return afterCalculated(fee);
    }

    abstract protected Money afterCalculated(Money fee);
}
