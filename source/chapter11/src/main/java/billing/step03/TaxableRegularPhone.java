package billing.step03;

import money.Money;

import java.time.Duration;

public class TaxableRegularPhone extends RegularPhone {
    private double taxRate;

    public TaxableRegularPhone(Money amount, Duration seconds, double taxRate) {
        super(amount, seconds);
        this.taxRate = taxRate;
    }

    @Override
    public Money calculateFee() {
        Money fee = super.calculateFee(); // 자식 클래스와 부모 클래스 사이의 결합도가 높아진다.
        return fee.plus(fee.times(taxRate));
    }
}
