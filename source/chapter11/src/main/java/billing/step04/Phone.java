package billing.step04;

import money.Money;

import java.util.ArrayList;
import java.util.List;

public abstract class Phone {
    private List<Call> calls = new ArrayList<>();

    public Money calculateFee() {
        Money result = Money.ZERO;

        for (Call call : calls) {
            result = result.plus(calculateCallFee(call));
        }
        return result;
    }

    protected Money afterCalculated(Money fee) {
        return fee;
    }

    abstract protected Money calculateCallFee(Call call);
}
