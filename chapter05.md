# 책임 할당하기
- 데이터 중심 설계로 인해 발생하는 문제점을 해결할 수 있는 가장 기본적인 방법은 데이터가 아닌 책임에 초점을 맞추는 것
- 책임에 초점을 맞추는 설계에서 가장 어려움은 어떤 객체에게 어떤 책임을 할당할지를 결정하기 쉽지 않음
- GRASP 패턴을 통해 응집도와 결합도, 캡슐화 같은 다양한 기준에 따라 책임을 할당하고 결과를 트레이드오프할 수 있는 기준을 배우게됨


# 01 책임 주도 설계를 향해
- 데이터 중심의 설계에서 책임 중심의 설계로 전환하기 위해서 다음 두 가지 원칙을 따라야함
  - 데이터보다 행동을 먼저 결정하라
  - 협력이라는 문맥 안에서 책임을 결정하라
  
## 데이터보다 행동을 먼저 결정하라
- 클라이언트의 관점에서 객체가 수행하는 행동이란 곧 객체의 책임을 의미
- 데이터는 객체가 책임을 수행하는 데 필요한 재료를 제공할 뿐
- 우리에게 필요한 것은 객체의 데이터에서 행동으로 무게 중심을 옮기기 위한 기법
  - 책임 중심의 설계에서는 객체의 행동, 즉 책임을 먼저 결정한 후에 객체의 상태를 결정한다는 것

## 협력이라는 문맥 안에서 책임을 결정하라
- 객체에게 할당된 책임의 품질은 협력에 적합한 정도로 결정
  - 책임이 협력에 어울리지 않는다면 그 책임은 나쁨
  - 책임이 조금 어색해 보이더라도 협력에 적합하면 좋은 책임
  - 책임은 객체의 입장이 아니라 참여하는 협력에 적합해야 함
  
- 협력에 적합한 책임이란 메시지 수신자가 아닌 메시지 전송자에게 적합한 책임을 의미
  - 다시 말해 메시지를 전송하는 클라이언트의 의도에 적합한 책임을 할당해야 함

- 객체가 메시지를 선택하는 것이 아니라 메시지가 객체를 선택해야 함

```
클래스를 결정하고 그 클래스의 책임을 찾아 나서는 대신 메시지를 결정하고 메시지를 누구에게 전송할지 찾아보게 되었다.
"메시지를 전송해야 하는데 누구에게 전송해야 하지?" 라고 질문하는 것. 설계의 핵심 질문을 이렇게 바꾸는 것이 메시지 기반 설계로 향하는 첫걸음이다.
객체를 가지고 있기 때문에 메시지를 보내는 것이 아니다. 메시지를 전송하기 때문에 객체를 갖게 된 것이다.
```

- 클라이언트는 단지 임의의 객체가 메시지를 수신할 것이라는 사실을 믿고 자신의 의도를 표현할 메시지를 전송할 뿐
  - 메시지를 수신하기로 결정된 객체는 메시지를 처리할 "책임"을 할당받게 됨
- 메시지를 먼저 결정하기 때문에 메시지 송신자는 메시지 수신자에 대한 어떠한 가정도 할 수 없음
  - 메시지 전송자의 관점에서 메시지 수신자가 깔끔하게 캡슐화 됨
  
- 협력이라는 문맥에서 적절한 책임이란 곧 클라이언트의 관점에서 적절한 책임을 의미


## 책임 주도 설계
- 책임 주도 설계의 흐름을 다시 나열
  - 시스템이 사용자에게 제공해야 하는 기능인 시스템 책임을 파악한다.
  - 시스템 책임을 더 작은 책임으로 분활한다.
  - 분활된 책임을 수행할 수 있는 적절한 객체 또는 역할을 찾아 책임을 할당한다.
  - 객체가 책임을 수행하는 도중 다른 객체의 도움이 필요한 경우 이를 책임질 적절한 객체 또는 역할을 찾는다.
  - 해당 객체 또는 역할에게 책임을 할당함으로써 두 객체가 협력하게 된다.
  
- 책임 주도 설계의 핵심은 책임을 결정한 후에 책임을 수행할 객체를 결정하는 것
  - 협력에 참여하는 객체들의 책임이 어느 정도 정리될때까지 객체의 내부 상태에 대해 관심을 가지지 않는 것

# 02 책임 할당을 위한 GRASP 패턴
- GRASP은 (일반적인 책임 할당을 위한 소프트웨어 패턴) 의 약자로 객체에게 책임을 할당할 때 지침으로 삼을 수 있는 원칙들의 집합을 패턴 형식으로 정리한 것

## 도메인 개념에서 출발하기
- 설계를 시작하기 전에 도메인에 대한 개략적인 모습을 그려 보는 것이 유용
- 어떤 책임을 할당해야 할 때 가장 먼저 고민해야 하는 유력한 후보는 바로 도메인 개념


![5 1](https://user-images.githubusercontent.com/7076334/107771926-d7dfb000-6d7e-11eb-9325-d1df09a87f18.png)
- 해당 그림을 통해 여러 도메인 개념과 개념 사이의 관계를 대략적으로 알 수 있음

- 설계를 시작하는 단계에서는 개념들의 의미와 관계가 정확하거나 완벽할 필요가 없다. (단지 출발점이 필요할 뿐임)
- 중요한 것은 설계를 시작하는 것이지 도메인 개념들을 완벽하게 정리하는 것이 아니다.
  - 도메인 개념 정리에 너무 많은 시간을 들이지 말고 빠르게 설계와 구현을 진행


### 올바른 도메인 모델이란 존재하지 않는다.
- 많은 사람들이 도메인 모델은 구현과 무관하다고 생각하지만 이것은 오해한 것에 불과하다.
- 도메인 모델은 도메인을 개념적으로 표현한 것 이지만 그 안에 포함된 개념과 관계는 구현의 기반이 돼야 함
- 반대로 코드의 구조가 도메인을 바라보는 관점을 바꾸기도 함
  - 해당 장에 나오는 예제가 이런 케이스
- 이것은 올바른 도메인 모델이란 존재하지 않는다는 사실을 잘 보여줌


## 정보 전문가에게 책임을 할당하라
- 책임 주도 설계 방식의 첫 단계는 애플리케이션이 제공해야 하는 기능을 애플리케이션의 책임으로 생각하는 것
  - 이 책임을 전송된 메시지로 간주하고 이 메시지를 책임질 첫 번째 객체를 선택하는 것으로 설계 시작
  
- 메시지는 메시지를 수신할 객체가 아니라 메시지를 전송할 객체의 의도를 반영해서 결정해야 함
  
- 첫 번째 질문
  - 메시지를 전송할 객체는 무엇을 원하는가?
    - 영화를 예매하는 것

- 두 번째 질문
  - 메시지를 수신할 적합한 객체는 누구인가?
    - 이 질문을 답하기 위해서 객체가 상태와 행동을 통합한 캡슐화의 단위라는 사실에 집중해야 됨
    - 자율적인 존재고 책임을 수행하는데 필요한 상태는 동일한 객체 안에 존재 해야 됨
    
- 객체에게 책임을 할당하는 첫 번째 원칙은 책임을 수행할 정보를 알고 있는 객체에게 책임을 할당
  - GRASP에서는 정보 전문가 패턴이라고 부름


### INFORMATION EXPERT 패턴
```
- 정보 전문가 패턴을 따르면 정보와 행동을 최대한 가까운 곳에 위치시키기 때문에 캡슐화를 유지할 수 있음
- 필요한 정보를 가진 객체들로 책임이 분산되기 때문에 더 응집력 있고, 이해하기 쉬워짐
  - 따라서 높은 응집도가 가능하고 결합도가 낮아져서 간결하고 유지보수하기 쉬운 시스템을 구축 가능
```

- 정보 전문가가 데이터를 반드시 저장하고 있을 필요는 없다
- P140 ~ P142 예제들
  - 메시지 : 예매하라
    - 영화 예매 전문가인 상영(Screening)에게 책임 할당
  - 메시지 : 가격을 계산하라
    - 정보 전문가인 영화(Movie)에게 책임 할당
    - 요금 계산을 위해 할인 정책에 따라 할인 요금을 제외한 금액을 계산
  - 메시지 : 할인 여부를 판단하라
    - 정보 전문가인 할인 조건(DiscountCondition)에게 책임 할당
    - 스스로 할인 여부 판단

- 정보 전문가 패턴은 객체에게 책임을 할당할 때 가장 기본이 되는 책임 할당 원칙임
- 정보 전문가 패턴을 따르는 것만으로 자율성이 높은 객체들로 구성된 협력 공동체를 구축할 가능성이 높아짐


## 높은 응집도와 낮은 결합도
- 설계 중 한가지를 선택해야 하는 경우 정보 전문가 패턴 외에 다른 책임 할당 패턴들을 고려할 필요가 있음

![5 2](https://user-images.githubusercontent.com/7076334/108078736-5f942a00-70b1-11eb-97f0-cae5842f6ebc.png)

- 왜 우리는 위와 같은 설계 대신 Movie가 DiscountCondition과 협력하는 방법을 택한 것일까?
  - 높은 응집도와 낮은 결합도는 객체에 책임을 할당할 때 상상 고려해야 하는 기본 원리
  - 두 협력 패턴 중에 높은 응집도와 낮은 결합도를 얻을 수 있는 설계가 있다면 그 설계를 선택해야 함
  - GRASP에서는 낮은 결합도 패턴과 높은 응집도 패턴이라고 부름


### 낮은 결합도 (LOW COUPLING) 패턴
- 의존성을 낮추고 변화의 영향을 줄이며 재사용성을 증가시키기 위해 설계의 전체적인 결합도가 낮게 유지되도록 책임을 할당
- 낮은 결합도는 모든 설계 결정에서 염두에 둬야 하는 원리

- Movie와 DiscountCondition은 이미 결합돼 있기 때문에 설계 전체적으로 결합도를 추가하지 않고도 협력을 완성할 수 있음
- LOW COUPLING 관점에서 Screening이 DiscountCondition과 협력하는건 결합도가 높다.

### 높은 응집도 (HIGH COHESION) 패턴
- 높은 응집도는 설계 결정을 평가할 떄 적용할 수 있는 평가 원리

- Screening이 DiscountCondition과 협력해야 한다면 예매 요금을 계산하는 방식이 변경될 경우 Screening도 함께 변경해야 됨
  - 서로 다른 이유로 변경되는 책임을 짊어지게 되므로 응집도가 낮아질 수 밖에 없음

- 매순간 낮은 결합도와 높은 응집도의 관점에서 전체적인 설계 품질을 검토하면 단순하면서도 재사용 가능하고 유연한 설계를 얻을 수 있음

## 창조자에게 객체 생성 책임을 할당하라
- GRASP의 CREATOR(창조자) 패턴은 객체를 쌩성할 책임을 어떤 객체에게 할당할지에 대한 지침을 제공

### CREATOR 패턴
- 아래 조건을 최대한 많이 만족하는 B에게 객체 생성 책임을 할당
  - B가 A 객체를 포함하거나 참조한다.
  - B가 A 객체를 기록한다.
  - B가 A 객체를 긴밀하게 사용한다.
  - B가 A 객체를 초기화하는데 필요한 데이터를 가지고 있다(이 경우 B는 A대한 정보 전문가)
- 이미 결합돼 있는 객체에게 생성 책임을 할당하는 것은 설계의 전체적인 결합도에 영향을 미치지 않음
  - 낮은 결합도를 유지할 수 있게 해줌
  
# 03 구현을 통한 검증
- Screening은 예매에 대한 정보 전문가인 동시에 Reservation의 창조자
```
public class Screening {
    public Reservation reserve(Customer customer, int audienceCount) {
    }
}
```

- 책임 결정 후 책임을 수행하는데 필요한 인스턴스 변수를 결정 (상영시간, 상영순번, 가격 계산 메시지를 보내기 위한 Movie)
```
public class Screening {
    private Movie movie;
    private int sequence;
    private LocalDateTime whenScreened;
    
}
```

- 영화를 예매하기 위해서 movie에게 가격을 계산하라 메시지를 전송해서 계산된 영화 요금을 반환
```
public class Screening {
    private Movie movie;
    private int sequence;
    private LocalDateTime whenScreened;

    // movie 에게 가격을 계산하라 메시지 전송
    private Money calculateFee(int audienceCount) {
        return movie.calculateMovieFee(this).times(audienceCount);
    }
}
```

- 구현 과정에서 Movie에 전송하는 메시지의 시그니처를 calculateMovieFee로 선언
  - 수신자인 Movie가 아니라 송신자인 Screening의 의도를 표현
  - Movie의 구현을 고려하지 않고 필요한 메시지를 결정하면 Movie의 내부 구현을 깔끔하게 캡슐화 할 수 있음
- 메시지를 기반으로 협력을 구성하면 Screening과 Movie 사이의 결합도를 느슨하게 유지할 수 있음
  - 이처럼 메시지가 객체를 선택하도록 책임 주도 설계 방식을 따르면 캡슐화와 낮은 결합도라는 목표를 비교적 손쉽게 달성
  


- 할인 정책을 구성하는 할인 금액과 할인 비율을 Movie의 인스턴스 변수로 선언
- 현재의 Movie가 어떤 할인 정책이 적용된 영화인지 나타내기 위한 영화 종류(movieType)를 인스턴스 변수로 포함
- isDiscountable를 통해 조건을 만족하는게 있나 확인하고 만족하는게 있으면 calculateDiscountAmount 로 요금 계산
```
public class Movie {
    private String title;
    private Duration runningTime;
    private Money fee;
    private List<DiscountCondition> discountConditions;

    private MovieType movieType;
    private Money discountAmount;
    private double discountPercent;

    public Money calculateMovieFee(Screening screening) {
        if (isDiscountable(screening)) {
            return fee.minus(calculateDiscountAmount());
        }
        return fee;
    }
    
    private boolean isDiscountable(Screening screening) {
        return discountConditions.stream()
                .anyMatch(condition -> condition.isSatisfiedBy(screening));
    }

    private Money calculateDiscountAmount() {
        switch (movieType) {
            case AMOUNT_DISCOUNT:
                return calculateAmountDiscountAmount();
            case PERCENT_DISCOUNT:
                return calculatePercentDiscountAmount();
            case NONE_DISCOUNT:
                return calculateNoneDiscountAmount();
        }

        throw new IllegalStateException();
    }
}
```

- DiscountCondition은 이 메시지를 처리하기 위해 isSatisfiedBy 메서드를 구현
- 기간 조건을 위한 요일, 시작 시간, 종료 시간
- 순번 조건을 위한 상영 순번 을 인스턴스 변수로 포함


```
public class DiscountCondition {
    private DiscountConditionType type;
    private int sequence;
    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;

    public boolean isSatisfiedBy(Screening screening) {
        if (type == DiscountConditionType.PERIOD) {
            return isSatisfiedByPeriod(screening);
        }
        return isSatisfiedBySequence(screening);
    }

    private boolean isSatisfiedByPeriod(Screening screening) {
        return dayOfWeek.equals(screening.getWhenScreened().getDayOfWeek()) &&
                startTime.compareTo(screening.getWhenScreened().toLocalTime()) <= 0 &&
                endTime.compareTo(screening.getWhenScreened().toLocalTime()) <= 0;
    }

    private boolean isSatisfiedBySequence(Screening screening) {
        return sequence == screening.getSequence();
    }
}
```

## DiscountCondition 개선하기
- 위 코드의 가장 큰 문제점은 변경에 취약할 클래스를 포함하고 있다.
- DiscountCondition은 다음과 같이 서로 다른 세 가지 이유로 변경될 수 있음
  - 새로운 할인 조건 추가
  - 순번 조건을 판단하는 로직 변경
  - 기간 조건을 판단하는 로직이 변경되는 경우
  

- DiscountCondition은 하나 이상의 변경 이유를 가지기 때문에 응집도가 낮음
  - 낮은 응집도가 초래하는 문제를 해결하기 위해서 변경의 이유에 따라 클래스를 분리해야 함
  - 서로 다른 이유로, 서로 다른 시점에 변경될 확률이 높음
  
- 코드를 통해 변경의 이유를 파악할 수 있는 방법
  - 첫번째, 인스턴스 변수가 초기화되는 시점을 살펴보는 것
    - 응집도가 높은 클래스는 인스턴스 생성 시, 모든 속성이 함께 초기화 됨
    - 응집도가 낮은 클래스는 객체의 속성 중 일부만 초기화하고 일부는 초기화되지 않은 상태로 남겨짐
    - DiscountCondition 이 여기에 속하고 따라서 코드를 분리해야 됨
  - 두번째 메서드들이 인스턴스 변수를 사용하는 방식을 살펴보는 것
    - 모든 메서드가 객체의 모든 속성을 사용한다면 클래스의 응집도는 높음
    - 반면 메서드들이 사용하는 속성에 따라 그룹이 나뉜다면 클래스의 응집도는 낮음
    - DiscountCondition 는 응집도를 높이기 위해 속성 그룹과 해당 그룹에 접근하는 메서드 그룹을 기준으로 코드를 분리해야 됨
    
### 클래스 응집도 판단하기
- 다음과 같은 징후면 응집도가 낮음
  - 클래스가 하나 이상의 이유로 변경돼야 한다면 응집도가 낮은 것이다. 변경의 이유를 기준으로 클래스를 분리하라.
  - 클래스의 인스턴스를 초기화하는 시점에 경우에 따라 서로 다른 속성들을 초기화하고 있다면 응집도가 낮은 것이다. 초기화되는 속성의 그룹을 기준으로 클래스를 분리하라.
  - 메서드 그룹이 속성 그룹을 사용하는지 여부로 나뉜다면 응집도가 낮은 것이다. 이들 그룹을 기준으로 클래스를 분리하라.
- DiscountCondition 위 세가지 징후가 모두 포함... 따라서 여러 개의 클래스로 분리
  
## 타입 분리하기
- DiscountCondition 의 가장 큰 문제점 순번 조건과 기간 조건이라는 두 개의 독립적인 타입이 하나의 클래스 안에 공존
- SequenceCondition과 PeriodCondition 으로 분리
```
public class PeriodCondition {
    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;


    public boolean isSatisfiedBy(Screening screening) {
        return dayOfWeek.equals(screening.getWhenScreened().getDayOfWeek()) &&
                startTime.compareTo(screening.getWhenScreened().toLocalTime()) <= 0 &&
                endTime.compareTo(screening.getWhenScreened().toLocalTime()) >= 0;
    }
}

public class SequenceCondition {
    private int sequence;

    public boolean isSatisfiedBy(Screening screening) {
        return sequence == screening.getSequence();
    }
}

```
  
- 클래스를 분리하면 앞에 문제점이 해결됨
- 하지만 새로운 문제점 발생
  - Movie에서 기존에는 DiscountCondition 하나만 참조하고 있었는데 두개 클래스의 인스턴스 모두와 협력해야됨
  
![5 4](https://user-images.githubusercontent.com/7076334/108092642-43e45000-70c0-11eb-995c-3ee0c294bdac.png)

  
- 첫번째 해결 방법 Movie 클래스 안에서 목록을 따로 유지
  
```
public class Movie {
    private List<PeriodCondition> periodConditions;
    private List<SequenceCondition> sequenceConditions;

    private boolean isDiscountable(Screening screening) {
        return checkPeriodConditions(screening) ||
                checkSequenceConditions(screening);
    }

    private boolean checkPeriodConditions(Screening screening) {
        return periodConditions.stream()
                .anyMatch(condition -> condition.isSatisfiedBy(screening));
    }

    private boolean checkSequenceConditions(Screening screening) {
        return sequenceConditions.stream()
                .anyMatch(condition -> condition.isSatisfiedBy(screening));
    }
}
```

- 하지만 문제점 발생
  - 1) Movie 클래스가 PeriodCondition, SequenceCondition 클래스 양쪽 모두에 결합
  - 2) 수정 후에 새로운 할인 조건을 추가하기 더 어려워짐 (List 추가해야됨)
- DiscountCondition 입장에서 보면 응집도가 높아졌지만 변경과 캡슐화 관점에서 전체적으로 설계 품질이 나빠짐


## 다형성을 통해 분리하기
- Movie 입장에서 보면 SequenceCondition, PeriodCondition 은 사실 차이가 없다. 둘다 할인 여부 판단
- 앞에서 배운 역할의 개념이 무대 위로 등장
  - Movie의 입장에서 SequenceCondition, PeriodCondition이 동일한 책임을 수행한다는 것은 동일한 역할을 수행한다는 것을 의미
- 역할을 사용하면 객체의 구체적인 타입을 추상화 할 수 있음 (인터페이스 / 추상 클래스)

- DiscountCondition 인터페이스를 이용해서 역할 구현
```
public interface DiscountCondition {
    boolean isSatisfiedBy(Screening screening);
}


public class PeriodCondition implements DiscountCondition {
}

public class SequenceCondition implements DiscountCondition {
}
```

- 이제 Movie는 협력하는 객체의 구체적인 타입을 몰라도 됨
```
public class Movie {
    private List<DiscountCondition> discountConditions;

    public Money calculateMovieFee(Screening screening) {
        if (isDiscountable(screening)) {
            return fee.minus(calculateDiscountAmount());
        }

        return fee;
    }

    private boolean isDiscountable(Screening screening) {
        return discountConditions.stream()
                .anyMatch(condition -> condition.isSatisfiedBy(screening));
    }
}
```

- 객체의 타입에 따라 변하는 행동이 있다면 타입을 분리하고 변화하는 행동을 각 타입의 책임으로 할당하라
  - GRASP에서는 이를 POLYMORPHISM(다형성) 패턴이라고 부른다.
  
### POLYMORPHISM 패턴
- if ~ else 또는 switch ~ case 등의 사용은 프로그래밍을 수정하기 어렵게 변경에 취약하게함
- POLYMORPHISM 패턴은 다형성을 이용해 새로운 변화를 다루기 쉽게 확장하라고 권고

## 변경으로부터 보호하기
- 새로운할인 조건 추가되면?
  - DiscountCondition 이라는 추상화가 구체적인 타입을 캡슐화 함
  - DiscountCondition 타입이 추가되도 Movie가 영향을 받지 않는다는 것을 의미
- 이처럼 변경을 캡슐화하도록 책임을 할당하는 것을 GRASP 에서는 PROTECTED VARIATIONS(변경 보호) 패턴이라 부른다.

### PROTECTED VARIATIONS 패턴
- 변화가 예상되는 불안정한 지점들을 식별하고 그 주위에 안정된 인터페이스를 형성하도록 책임을 할당
- PROTECTED VARIATIONS 패턴은 책임 할당의 관점에서 캡슐화를 설명한 것

- 결론
  - 하나의 클래스가 여러 타입의 행동을 구현하고 있는 것처럼 보인다면 클래스를 분해하고 다형성 패턴에 따라 책임을 분산
  - 예측 가능한 변경으로 인해 여러 클래스들이 불안정해진다면 변경 보호 패턴에 따라 안정적이 인터페이스 뒤로 변경을 캡슐화
  
## Movie 클래스 개선하기
- Movie도 금액 할인 정책 영화와 비율 할인 정책 영화 두가지 타입(MovieType)을 하나의 클래스에 구현하고 있음
- 앞에서와 마찬가지로 역할의 개념을 도입해서 협력을 다형적으로 만들면 됨

```
public abstract class Movie {
}

// 금액 할인 정책
public class AmountDiscountMovie extends Movie {
    private Money discountAmount;

    @Override
    protected Money calculateDiscountAmount() {
        return discountAmount;
    }
}

// 비율 할인 정책
public class PercentDiscountMovie extends Movie {
    private double percent;

    @Override
    protected Money calculateDiscountAmount() {
        return getFee().times(percent);
    }
}

public class NoneDiscountMovie extends Movie {
    @Override
    protected Money calculateDiscountAmount() {
        return Money.ZERO;
    }
}

```

![5 7](https://user-images.githubusercontent.com/7076334/108098702-0636f580-70c7-11eb-9b39-b8e03cd28c75.png)

- 모든 클래스의 내부 구현은 캡슐화돼 있고 모든 클래스는 변경의 이유를 오직 하나씩만 가진다.
- 각 클래스는 응집도가 높고 다른 클래스와 최대한 느슨하게 결합돼 있다.
- 책임은 적절하게 분배 되어 있다.
  - 책임을 중심으로 협력을 설계할 때 얻을 수 있는 혜택
  
- 결론
  - 데이터가 아닌 책임을 중심으로 설계하라
  

## 변경과 유연성
- 변경에 유연하게 할 수 있는 방법
  - 코드를 이해하고 수정하기 쉽도록 최대한 단순하게 설계
  - 코드를 수정하지 않고도 변경을 수용할 수 있도록 코드를 더 유연하게 만드는 것
    - 유사한 변경이 반복적으로 발생하고 있다면 복잡성이 상승하더라도 유연성을 추가 하는 두 번쨰 방법이 더 좋음

- 영화에 설정된 할인 정책을 실행 중에 변경할 수 있어야 된다면?
  - 현재 구조는 상속이기 때문에 새로운 인스턴스를 생성한 후 필요한 정보를 복사해야 됨
  - 해결 방법은 상속 대신 합성을 사용 (2장에서 본 구조와 동일)
- 유연성의 정도에 따라 결합도를 조절할 수 있는 능력은 객체지향 개발자가 갖춰야 하는 중요한 기술 중 하나다.

### 코드의 구조가 도메인 구조에 대한 새로운 통찰력을 제공한다.
- 도메인 모델은 단순히 도메인의 개념과 관계를 모아 놓은 것이 아니다. 도메인 모델은 구현과 밀접한 관계를 맺어야 한다.

- 책임을 할당하는데 어렵다면 절차형 코드로 실행되는 프로그램을 빠르게 작성한 후 완성된 코드를 객체지향적인 코드로 변경

# 04 책임 주도 설계의 대안
- 아무것도 없는 상태에서 책임과 협력에 관해 고민하기 보다는 일단 실행되는 코드를 얻고 난 후에 코드 상에 명확하게 드러나는 책임을 올바른 위치로 이동
- 코드 수정 후 겉으로 드러나는 동작이 변경되서는 안됨 (리팩터링)


## 메서드 응집도
- ReservationAgency의 reserve 메서드는 길이가 너무 길고 이해하기도 어려움
  - 긴 메서드는 응집도가 낮기 때문에 이해하기 어렵고 재사용하기도 어려우며 변경하기도 어려움 (몬스터 메서드)
- 응집도가 낮은 메서드는 로직의 흐름을 이해하기 위해 주석이 필요한 경우가 대부분
  - 주석 대신 메서드를 작게 분해해서 각 메서드 응집도를 높혀라 

- 객체로 책임을 분배할 때 가장 먼저 할 일은 메서드를 응집도 있는 수준으로 분해하는 것
- 코드를 작은 메서드들로 분해하면 전체적인 흐름을 이해하기도 쉬워짐
- 수정 후의 코드는 변경하기도 더 쉬움

- 하지만 메서드들의 응집도 자체는 높아졌지만 ReservationAgency의 응집도는 여전히 낮음
  - 변경의 이유가 다른 메서드들을 적절한 위치로 분배
  
## 객체를 자율적으로 만들자
- 자신이 소유하고 있는 데이터를 자기 스스로 처리하도록 만드는 것이 자율적인 객체를 만드는 지름길
  - 메서드가 사용하는 데이터를 저장하고 있는 클래스로 메서드를 이동
- 책임 주도 설계 방법이 익숙하지 않다면 일단 데이터 중심으로 구현한 후 이를 리팩터링 하더라도 유사한 결과를 얻을 수 있음

  

