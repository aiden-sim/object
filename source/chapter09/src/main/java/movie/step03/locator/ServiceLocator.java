package movie.step03.locator;

import movie.step03.DiscountPolicy;

/**
 * SERVICE LOCATOR 패턴의 가장 큰 단점은 의존성을 감춘다.
 */
public class ServiceLocator {
    private static ServiceLocator soleInstance = new ServiceLocator();
    private DiscountPolicy discountPolicy;

    public static DiscountPolicy discountPolicy() {
        return soleInstance.discountPolicy;
    }

    public static void provide(DiscountPolicy discountPolicy) {
        soleInstance.discountPolicy = discountPolicy;
    }

    private ServiceLocator() {

    }
}
