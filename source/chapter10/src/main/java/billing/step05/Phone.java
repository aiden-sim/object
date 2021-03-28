package billing.step05;

import billing.step01.Call;
import money.Money;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * 일반 요금제
 * 통화 요금에 부과할 세금 계산 (중복 코드)
 */
public class Phone {
    private Money amount;       // 단위 요금
    private Duration seconds;   // 단위 시간
    private List<Call> calls = new ArrayList<>();   // 전체 통화 목록
    private double taxRate;

    public Phone(Money amount, Duration seconds, double taxRate) {
        this.amount = amount;
        this.seconds = seconds;
        this.taxRate = taxRate;
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
        return result.plus(result.times(taxRate));
    }

    public double getTaxRate() {
        return taxRate;
    }
}