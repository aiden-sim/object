package billing.step02;

import money.Money;

import java.time.Duration;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        Phone phone = new Phone(Money.wons(5), Duration.ofSeconds(10), 0.5D);
        phone.call(new Call(LocalDateTime.of(2018, 1, 1, 12, 10, 0),
                            LocalDateTime.of(2018, 1, 1, 12, 11, 0)));
        phone.call(new Call(LocalDateTime.of(2018, 1, 2, 12, 10, 0),
                            LocalDateTime.of(2018, 1, 2, 12, 11, 0)));

        System.out.println(phone.calculateFee()); // 60원
    }
}
