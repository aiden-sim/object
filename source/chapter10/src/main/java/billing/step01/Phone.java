package billing.step01;

import money.Money;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * 일반 요금제
 */
public class Phone {
    private Money amount;       // 단위 요금
    private Duration seconds;   // 단위 시간
    private List<Call> calls = new ArrayList<>();   // 전체 통화 목록

    public Phone(Money amount, Duration seconds) {
        this.amount = amount;
        this.seconds = seconds;
    }

    public void call(Call call) {
        calls.add(call);
    }

    public List<Call> getCalls() {
        return calls;
    }

    public Money getAmount() {
        return amount;
    }

    public Duration getSeconds() {
        return seconds;
    }

    public Money calculateFee() {
        Money result = Money.ZERO;

        for (Call call : calls) {
            result = result.plus(amount.times(call.getDuration().getSeconds() / seconds.getSeconds()));
        }
        return result;
    }

}
