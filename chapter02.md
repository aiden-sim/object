# 객체지향 프로그래밍

# 01 영화 예매 시스템
## 요구사항 살펴보기
- 온라인 영화 예매 시스템
- 용어 정리
  - 영화 : 제목, 상영시간, 가격 정보 등 영화가 가지고 있는 기본정보
  - 상영 : 상영 일자, 시간, 순번 등 실제로 관객들이 영화를 관람하는 사건을 표현
  - 두 용어를 구분하는 이유는 사용자가 실제 예매하는 대상은 영화가 아니라 상영
  
- 할인액을 결정하는 두 가지 규칙
  - 할인 조건(discount condition)
    - 순서 조건 : 상영 순번을 이용해 할인 여부를 결정하는 규칙
    - 기간 조건 : 영화 상영 시작 시간을 이용해 할인 여부 결정
    - 다수의 할인 조건을 함께 지정
  - 할인 정책(discount policy)
    - 금액 할인 정책 : 예매 요금에서 일정 금액을 할인
    - 비율 할인 정책 : 정가에서 일정 비율의 요금을 할인
    - 영화 별로 하나의 할인 정책만 할당 가능 / 할인 없음

- 할인은 할인 조건과 할인 정책을 함께 조합해서 사용 (and 조건)

- 예매 정보
  - 제목, 상영정보, 인원, 정가, 결제금액
    
# 02 객체지향 프로그래밍을 향해
## 협력, 객체, 클래스
- 대부분 사람들은 클래스를 결정한 후에 클래스에 어떤 속성과 메서드가 필요한 고민
  - 객체지향의 본질과는 거리가 멈
  
- 객체지향 패러다임으로의 전환은 클래스가 아닌 객체에 초점을 맞출 때에만 얻을 수 있음 (다음 두가지에 집중)
  - 1) 어떤 클래스가 필요한지를 고민하기 전에 어떤 객체들이 필요한지 고민하라
    - 클래스의 윤곽을 잡기 위해서 어떤 객체들이 어떤 상태와 행동을 가지는지를 먼저 결정해야 됨
    - 객체를 중심에 두는 접근 방법은 설계를 단순하고 깔끔하게 만듬
  - 2) 객체를 독립적인 존재가 아니라 기능을 구현하기 위해 협력하는 공동체 일원으로 봐야 한다.
    - 객체를 협력하는 공동체의 일원으로 바라보는 것은 설계를 유연하고 확장 가능하게 만듬
- 훌륭한 협력이 훌륭한 객체를 낳고 훌륭한 객체가 훌륭한 클래스를 낳는다.

## 도메인의 구조를 따르는 프로그램 구조
- 문제를 해결하기 위해 사용자가 프로그램을 사용하는 분야를 도메인이라고 부른다.

- 요구사항과 프로그램을 객체라는 동일한 관점에서 바라볼 수 있기 때문에 도메인을 구성하는 개념들이 프로그램의 객체와 클래스로 매끄럽게 연결될 수 있다.

- 영화 예매 도메인을 구성하는 타입들의 구조
![2 3](https://user-images.githubusercontent.com/7076334/104843596-d8fff780-590e-11eb-8726-a098a51152c4.png)

- 일반적으로 클래스의 이름은 대응되는 도메인 개념의 이름과 동일하거나 적어도 유사하게 지어야 됨
  - 프로그램 구조를 이해하고 예상하기 쉽게 만듬
  
- 도메인 개념의 구조를 따르는 클래스 구조
![2 4](https://user-images.githubusercontent.com/7076334/104843885-4a8c7580-5910-11eb-8bb3-a835a3a0260c.png)
  - 클래스의 구조는 도메인의 구조와 유사한 형태를 띠어야 함


## 클래스 구현하기
- Screening 클래스는 사용자들이 예매하는 대상인 '상영'을 구현


```java
public class Screening {

    private Movie movie;                // 상영할 영화
    private int sequence;               // 순번
    private LocalDateTime whenScreened; // 상영 시작 시간

    public Screening(Movie movie, int sequence, LocalDateTime whenScreened) {
        this.movie = movie;
        this.sequence = sequence;
        this.whenScreened = whenScreened;
    }

    // 상영 시작 시간
    public LocalDateTime getStartTime() {
        return whenScreened;
    }

    // 순번의 일치 여부 검사
    public boolean isSequence(int sequence) {
        return this.sequence == sequence;
    }

    // 기본 요금을 반환
    public Money getMovieFee() {
        return movie.getFee();
    }
}
```

- 클래스를 구현하거나 다른 개발자에 의해 개발된 클래스를 사용할 때 가장 중요한 것은 클래스의 경계를 구분 짓는 것
- 클래스는 내부와 외부로 구분되며 훌륭한 클래스를 설계하기 위한 핵심은 어떤 부분을 외부에 공개하고 어떤 부분을 감출지 결정하는 것

- 클래스의 내부 외부 구분 이유?
  - 경계의 명확성이 객체의 자율성을 보장
  - 프로그래머에게 구현의 자유를 제공
  
### 자율적이 객체
- 두 가지 중요한 사실
  - 1) 객체가 **상태(state)와 행동(behavior)** 을 함께 가지는 복합적인 존재
  - 2) 객체가 스스로 판단하고 행동하는 **자율적인 존재** 라는 것

- 데이터와 기능을 객체 내부로 함께 묶는 것을 **캡슐화** 라고 부른다.
- 대부분 객체지향 프로그래밍 언어들은 외부에서의 접근을 통제할 수 있는 **접근 제어(access control)** 메커니즘도 함께 제공
  - 접근 수정자(access modifier) 제공

- 객체 내부에 대한 접근을 통제하는 이유는 객체를 자율적인 존재로 만들기 위해서
  - 외부 간섭이 최소화 되어야 함
 
- 캡슐화와 접근 제어는 객체를 두 부분을 나눔
  - 1) 외부에서 접근 가능한 부분, 즉 퍼블릭 인터페이스(public interface)
  - 2) 외부에서 접근 불가능하고 오직 내부에서만 접근 가능한 부분, 즉 구현(implementation)
  - 인터페이스와 구현의 분리 원칙은 훌륭한 객체지향 프로그램을 만들기 위한 핵심 원칙
  
### 프로그래머의 자유
- 프로그래머의 역할을 클래스 작성자(class creator)와 클라이언트 프로그래머로 구분하는 것이 유용

- 클라이언트 프로그래머
  - 필요한 클래스들을 엮어서 애플리케이션을 빠르고 안정적으로 구축
 
- 클래스 작성자
  - 클라이언트 프로그래머에게 필요한 부분만 공개하고 나머지는 꽁꽁 숨김
    - 구현 은닉 : 내부 구현을 숨김으로서 클라이언트 프로그래머에 대한 영향을 걱정하지 않고 내부 구현을 마음대로 변경
    
- 구현 은닉
  - 클래스 작성자와 클라이언트 프로그래머 모두에게 유용한 개념
  - 클라이언트 프로그래머 : 내부 구현은 무시한 채 인터페이스만(public interface) 알고 있으면 됨
  - 클래스 작성자 : 인터페이스를 바꾸지 않는 한 외부에 미치는 영향을 걱정하지 않고 내부 구현을 마음대로 변경 가능
  
- 설계가 필요한 이유는 변경을 관리하기 위해서
  - 객체의 변경을 관리할 수 있는 기법 중에서 가장 대표적인 것이 접근 제어
  
  
## 협력하는 객체들의 공동체
- 영화를 예매하는 기능 구현

```java
public class Screening {
    /**
     * 영화를 예매한 후 예매 정보를 담고 있다.
     * @param customer      예매자
     * @param audienceCount 인원수
     */
    public Reservation reserve(Customer customer, int audienceCount) {
        return new Reservation(customer, this, calculateFee(audienceCount), audienceCount);
    }

    /**
     * @param audienceCount 인원수
     * @return 전체 예매 요금  (1인당 예매 요금 * 인원수)
     */
    private Money calculateFee(int audienceCount) {
        return movie.calculateMovieFee(this).times(audienceCount);
    }
}

```
- reserve 메서드는 calculateFee라는 private 메서드를 호출해서 요금을 계산한 후 그 결과를 Reservation의 생성자에 전달
- calculateFee 메서드는 요금을 계산하기 위해 다시 Movice의 calculateMovieFee 메서드 호출


- Money는 금액과 관련된 다양한 계산을 구현
```java
/**
 * 금액과 관련된 다양한 계산을 구현
 */
public class Money {
    public static final Money ZERO = Money.wons(0);

    private final BigDecimal amount;

    public static Money wons(long amount) {
        return new Money(BigDecimal.valueOf(amount));
    }

    public static Money wons(double amount) {
        return new Money(BigDecimal.valueOf(amount));
    }

    Money(BigDecimal amount) {
        this.amount = amount;
    }

    public Money plus(Money amount) {
        return new Money(this.amount.add(amount.amount));
    }

    public Money minus(Money amount) {
        return new Money(this.amount.subtract(amount.amount));
    }

    public Money times(double percent) {
        return new Money(this.amount.multiply(BigDecimal.valueOf(percent)));
    }

    public boolean isLessThan(Money other) {
        return amount.compareTo(other.amount) < 0;
    }

    public boolean isGreaterThanOrEqual(Money other) {
        return amount.compareTo(other.amount) >= 0;
    }
}

```

- 금액과 관련돼 있다는 의미를 전달할 수 있음
- 금액과 관련된 로직이 서로 다른 곳에 중복되어 구현되는 것을 막을 수 있음
- 객체지향의 장점은 객체를 이용해 도메인의 의미를 풍부하게 표현할 수 있음
  - 하나의 인스터늣 변수만 포함하더라도 개념을 명시적으로 표현하는 것은 전체적인 설계의 명확성과 유연성을 높이는 첫걸음
  
- Reservation 클래스는 고객(customer), 상영 정보(screening), 예매 요금(fee), 인원 수 (audienceCount)를 속성으로 포함
```java
public class Reservation {
    private Customer customer;
    private Screening screening;
    private Money fee;
    private int audienceCount;

    public Reservation(Customer customer, Screening screening, Money fee, int audienceCount) {
        this.customer = customer;
        this.screening = screening;
        this.fee = fee;
        this.audienceCount = audienceCount;
    }
}
```
- 영화를 예매하기 위해 Screening, Movice, Reservation 인스턴스들은 서로 메서드를 호출하며 상호 작용을 함
  - 이처럼 객체들 사이에 이뤄지는 상호작용을 협력 (Collaboration)이라고 부름

- Screening, Reservation, Movice 사이의 협력
![2 5](https://user-images.githubusercontent.com/7076334/104846604-b70e7100-591e-11eb-8443-acafb39f2de4.png)


- 객체지향 프로그램을 작성할 때는 먼저 협력의 관점에서 어떤 객체가 필요한지를 결정하고, 객체들의 공통 상태와 행위를 구현하기 위해 클래스를 작성


## 협력에 관한 짧은 이야기
- 객체는 다른 객체의 인터페이스에 공개된 행동을 수행하도록 **요청(request)** 할 수 있음
- 요청을 받은 객체는 자율적인 방법에 따라 요청을 처리한 후 **응답(response)** 할 수 있음

- 객체가 다른 객체와 상호작용할 수 있는 유일한 방법은 **메시지를 전송** 하는 것 뿐
- 다른 객체에게 요청이 도착할 때 해당 객체가 **메시지를 수신** 했다고 함
  - 수신된 메시지를 처리하기 위한 자신만의 방법을 **메서드(method)** 라고 부른다.
  
- 메시지와 메서드를 구분하는 것은 매우 중요
  - 메시지와 메서드의 구분에서부터 **다형성**의 개념이 출발


# 03 할인 요금 구하기
## 할인 요금 계산을 위한 협력 시작하기
- Movie는 제목과 상영시간, 기본 요금, 할인 정책을 속성으로 가짐
```java
public class Movie {
    private String title;                   // 제목
    private Duration runningTime;           // 상영시간
    private Money fee;                      // 기본요금
    private DiscountPolicy discountPolicy;  // 할인 정책

    public Movie(String title, Duration runningTime, Money fee, DiscountPolicy discountPolicy) {
        this.title = title;
        this.runningTime = runningTime;
        this.fee = fee;
        this.discountPolicy = discountPolicy;
    }

    public Money getFee() {
        return fee;
    }

    // 기본요금에서 할인 요금 차감
    public Money calculateMovieFee(Screening screening) {
        return fee.minus(discountPolicy.calculateDiscountAmount(screening));
    }
}
```
- 기존에 할인 정책이 2가지 있었음 (일정한 금액을 할인 / 일정한 비율에 따른 할인)
- 어디에도 할인 정책을 판단하는 코드는 없고 단지 discountPolicy에게 메시지를 전송하고 있음
  - 이 코드는 객체지향에서 중요하다고 여기는 **상속(inheritance)**과 **다형성**이 숨겨져 있음
  - 기반에는 **추상화(abstraction)** 원리가 숨겨져 있음
  
## 할인 정책과 할인 조건
- 할인 정책은 금액 할인 정책(AmountDiscountPolicy)과 비율 할인 정책(PercentDiscountPolicy)으로 구분


```java
public abstract class DiscountPolicy {
    private List<DisCountCondition> conditions = new ArrayList<>(); // 할인 조건

    public DiscountPolicy(DisCountCondition... conditions) {
        this.conditions = Arrays.asList(conditions);
    }

    /**
     * 조건이 만족할 경우 getDiscountAmount를 통해 할인 요금 계산
     * 조건이 만족하지 않는다면 할인 요금 0원으로 반환
     */
    public Money calculateDiscountAmount(Screening screening) {
        for (DisCountCondition each : conditions) {
            if (each.isSatisfiedBy(screening)) {
                return getDiscountAmount(screening);
            }
        }

        return Money.ZERO;
    }

    protected abstract Money getDiscountAmount(Screening screening);
}
``` 

- 할인 조건이 하나라도 만족하면 **추상 메서드(abstract method)** 인 getDiscountAmount 메서드를 호출해 할인 요금 계산
- 만족하는 조건이 없다면 할인 요금은 0원

- 부모 클래스에 기본적인 알고리즘의 흐름을 구현하고 중간에 필요한 처리를 자식 클래스에게 위임하는 디자인 패턴을 TemplateMethod 패턴


```java
public interface DisCountCondition {
    boolean isSatisfiedBy(Screening screening);
}
``` 
- isSatisfiedBy 오퍼레이션은 인자로 전달된 screening이 할인이 가능한 경우 true, 할인이 불가능한 경우 false

- 영화 예매 시스템은 순번 조건과 기간 조건의 두 가지 할인 조건이 존재
```java
public class SequenceCondition implements DisCountCondition {
    private int sequence; // 할인 여부를 판단하기 위해 사용할 순번

    public SequenceCondition(int sequence) {
        this.sequence = sequence;
    }

    /**
     * screening의 상영 순번과 일치할 경우 할인 가능
     */
    @Override
    public boolean isSatisfiedBy(Screening screening) {
        return screening.isSequence(sequence);
    }
}
``` 


```java
public class PeriodCondition implements DisCountCondition {
    private DayOfWeek dayOfWeek; // 요일
    private LocalTime startTime; // 시작 시간
    private LocalTime endTime;   // 종료 시간

    public PeriodCondition(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * screening의 상영 요일이 dayOfWeek와 같고 상영 시간이 startTime ~ endTime 에 포함되는 경우 할인 가능
     */
    @Override
    public boolean isSatisfiedBy(Screening screening) {
        return screening.getStartTime().getDayOfWeek().equals(dayOfWeek) &&
                startTime.compareTo(screening.getStartTime().toLocalTime()) <= 0 &&
                endTime.compareTo(screening.getStartTime().toLocalTime()) >= 0;
    }
}
``` 

- 할인 정책
- AmountDiscountPolicy는 할인 조건을 만족할 경우 일정한 금액을 할인
```java
public class AmountDiscountPolicy extends DiscountPolicy {
    private Money discountAmount; // 할인 금액

    public AmountDiscountPolicy(Money discountAmount, DisCountCondition... conditions) {
        super(conditions);
        this.discountAmount = discountAmount;
    }

    /**
     * 일정 금액을 할인
     */
    @Override
    protected Money getDiscountAmount(Screening screening) {
        return discountAmount;
    }
}
``` 

- PercentDiscountPolicy는 고정 금액이 아닌 일정 비율로 금액을 할인
```java
public class PercentDiscountPolicy extends DiscountPolicy {
    private double percent; // 할인 비율

    public PercentDiscountPolicy(double percent, DiscountCondition... conditions) {
        super(conditions);
        this.percent = percent;
    }


    /**
     * 일정 금액 비율을 할인
     */
    @Override
    protected Money getDiscountAmount(Screening screening) {
        return screening.getMovieFee().times(percent);
    }
}
``` 

- 할인 정책과 할인 조건
![2 6](https://user-images.githubusercontent.com/7076334/104849956-f6918900-592f-11eb-95a5-b2ba7b323c93.png)


### 오버라이딩과 오버로딩
- 오버라이딩은 부모 클래스에 정의된 같은 이름, 같은 파라미터 목록을 가진 메서드를 자식 클래스에서 재정의
- 오버로딩은 메서드의 이름은 같지만 제공되는 파라미터의 목록이 다르다.
  - ex) Money의 plus 메서드
  

## 할인 정책 구성하기
- Movice의 생성자는 오직 하나의 DiscountPolicy (할인 정책) 만 강제하도록 선언
```java
public class Movie {
    public Movie(String title, Duration runningTime, Money fee, DiscountPolicy discountPolicy) {
        ...
        this.discountPolicy = discountPolicy;
    }
}
```

- DiscountPolicy의 생성자는 여러 개의 DiscountCondition 인스턴스를 허용
```java
public abstract class DiscountPolicy {
    public DiscountPolicy(DiscountCondition... conditions) {
        this.conditions = Arrays.asList(conditions);
    }
}
```

- 이처럼 생성자의 파라미터 목록을 이용해 초기화에 필요한 정보를 전달하도록 강제하면 올바른 상태를 가진 객체의 생성을 보장 가능

- 표[2.1] 아바타에 대한 할인 정책과 할인 조건 설정
![2 1](https://user-images.githubusercontent.com/7076334/104850611-6b19f700-5933-11eb-85c2-d9aaa7bf9c55.png)

```java
        Movie avater = new Movie("아바타",
                                 Duration.ofMinutes(120),
                                 Money.wons(10000),
                                 new AmountDiscountPolicy(Money.wons(800),
                                                          new SequenceCondition(1),
                                                          new SequenceCondition(10),
                                                          new PeriodCondition(DayOfWeek.MONDAY, LocalTime.of(10, 0),
                                                                              LocalTime.of(11, 59)),
                                                          new PeriodCondition(DayOfWeek.THURSDAY,
                                                                              LocalTime.of(18, 0), LocalTime.of(21, 0))
                                 )
```

# 04 상속과 다형성
- 
