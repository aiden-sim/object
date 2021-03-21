package movie.step03.pricing;

import money.Money;
import movie.step03.DiscountPolicy;
import movie.step03.Screening;

public class NoneDiscountPolicy extends DiscountPolicy {
    @Override
    protected Money getDiscountAmount(Screening screening) {
        return Money.ZERO;
    }
}
