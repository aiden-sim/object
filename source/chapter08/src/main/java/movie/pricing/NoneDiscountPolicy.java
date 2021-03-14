package movie.pricing;


import money.Money;
import movie.DiscountPolicy;
import movie.Screening;

/**
 * 할인 정책이 존재하지 않는다는 사실을 예외 케이스로 처리하지 말고 DiscountPolicy가 협력하던 방식을 따르도록 처리
 */
public class NoneDiscountPolicy extends DiscountPolicy {
    @Override
    protected Money getDiscountAmount(Screening screening) {
        return Money.ZERO;
    }
}
