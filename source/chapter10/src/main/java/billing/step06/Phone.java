package billing.step06;

import money.Money;

import java.time.Duration;

/**
 * 일반 요금제
 */
public class Phone extends AbstractPhone {
    private Money amount;       // 단위 요금
    private Duration seconds;   // 단위 시간

    public Phone(Money amount, Duration seconds) {
        this.amount = amount;
        this.seconds = seconds;
    }

    public Money getAmount() {
        return amount;
    }

    public Duration getSeconds() {
        return seconds;
    }

    @Override
    protected Money calculateCallFee(Call call) {
        return amount.times(call.getDuration().getSeconds() / seconds.getSeconds());
    }
}
