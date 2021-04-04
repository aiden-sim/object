package billing.step05;

import money.Money;

import java.time.Duration;

public class Main {
    public static void main(String[] args) {
        // 일반 요금제 규칙
        Phone phone = new Phone(new RegularPolicy(Money.wons(10), Duration.ofSeconds(10)));

        // 심야 할인 요금제
        phone = new Phone(new NightlyDiscountPolicy(Money.wons(5), Money.wons(10), Duration.ofSeconds(10)));

        // 일반 요금제에 세금 정책을 조합
        phone = new Phone(new TaxablePolicy(0.05, new RegularPolicy(Money.wons(10), Duration.ofSeconds(10))));

        // 일반 요금제에 기본 요금 할인 정책을 조합한 결과에 세금 정책을 조합
        phone = new Phone(new TaxablePolicy(0.05, new RateDiscountablePolicy(Money.wons(1000),
                                                                             new RegularPolicy(Money.wons(10),
                                                                                               Duration.ofSeconds(
                                                                                                       10)))));

        // 세금 정책과 기본 요금 할인 정책 적용 순서 변경
        phone = new Phone(new RateDiscountablePolicy(Money.wons(1000), new TaxablePolicy(0.05,
                                                                                         new RegularPolicy(
                                                                                                 Money.wons(10),
                                                                                                 Duration.ofSeconds(10)))));
        // 동일한 정책으로 심야 할인 요금제도 적용
        phone = new Phone(new RateDiscountablePolicy(Money.wons(1000), new TaxablePolicy(0.05,
                                                                                         new NightlyDiscountPolicy(Money.wons(5), Money.wons(10), Duration.ofSeconds(10)))));
    }
}
