package billing.step07;

import money.Money;

import java.time.Duration;

/**
 * 일반 요금제
 */
public class RegularPhone extends Phone {
    private Money amount;       // 단위 요금
    private Duration seconds;   // 단위 시간

    public RegularPhone(Money amount, Duration seconds) {
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
