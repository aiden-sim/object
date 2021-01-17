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
    
## 02 객체지향 프로그래밍을 향해
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
    public Movie getMovieFee() {
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
  
- 




  
  
  

