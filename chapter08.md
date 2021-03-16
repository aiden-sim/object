# 의존성 관리하기
- 잘 설계된 객체지향 애플리케이션은 작고 응집도 높은 객체들로 구성
  - 작고 응집도 높은 객체란 책임의 초점이 명확하고 한 가지 일만 잘하는 객체
  - 단위가 작기 때문에 다른 객체와 협력

- 협력은 객체가 다른 객체에 대해 알것을 강요
  - 객체 사이의 의존성 발생

- 협력을 위해서 의존성이 필요하지만 과도한 의존성은 애플리케이션을 수정하기 어렵게 만듬
  - 객체지향 설계의 핵심은 협력을 위해 필요한 의존성은 유지하면서 변경을 방해하는 의존성을 제거하는 것

# 01 의존성 이해하기
## 변경과 의존성
- 의존성은 실행 시점과 구현 시점에 서로 다른 의미를 가짐
  - 실행 시점 : 의존하는 객체가 정상적으로 동작하기 위해서는 실행 시에 의존 대상 객체가 반드시 존재해야 함
  - 구현 시점 : 의존 대상 객체가 변경될 경우 의존하는 객체도 함께 변경됨

```
public class PeriodCondition implements DiscountCondition {
    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;

    public boolean isSatisfiedBy(Screening screening) {
        return screening.getStartTime().getDayOfWeek().equals(dayOfWeek) &&
                startTime.compareTo(screening.getStartTime().toLocalTime()) <= 0 &&
                endTime.compareTo(screening.getStartTime().toLocalTime()) >= 0;
    }
}
```

- 실행 시점에 PeriodCondition의 인스턴스가 정상적으로 동작하기 위해서 Screening의 인스턴스가 존재해야됨
- 이 처럼 어떤 객체가 예정된 작업을 정상적으로 수행하기 위해 다른 객체를 필요로 하는 경우 두 객체 사이의 의존성이 존재한다고 말함
  - 의존성은 항상 단방향 이다.

- Screening이 변경되면 PeriodCondition이 영향 받지만 그 역은 성립하지 않음
![8 1](https://user-images.githubusercontent.com/7076334/111326808-35358c80-86b0-11eb-86f3-66ff56ef477f.png)

- 설계와 관련된 대부분의 용어들이 변경과 관련이 있음 (의존성 역시 마찬가지)

- 위의 예제에서 PeriodCondition은 DayOfWeek, LocalTime, Screening에 대한 의존성을 가짐
  - 그림 8.2

- 의존성의 종류를 구분 가능하도록 서로 다른 방식으로 표현
<img width="500" alt="8 3" src="https://user-images.githubusercontent.com/7076334/111321360-331cff00-86ab-11eb-8e5d-e0d268dde13c.png">

### UML과 의존성
- 그림 8.3에는 실체화 관계, 연관 관계, 의존 관계가 포함
- 이번장의 '의존성'은 UML의 의존 관계와 다름
  - UML은 두 요소 사이에 존재할 수 있는 다양한 관계의 하나로 '의존 관계'를 정의
  - 의존성은 두 요소 사이에 변경에 의해 영향을 주고받는 힘의 역학관계가 존재한다는 사실에 초점
- 의존성은 UML에서 정의하는 모든 관계가 가지는 공통적인 특성으로 바라봐야 함

## 의존성 전이
- Screening은 Movice, LocalDateTime, Customer에 의존
- 의존성 전이가 의미하는 것은 PeriodCondition이 Screening에 의존할 경우, Screening이 의존하는 대상에 대해서도 자동적으로 의존하게 된다는 것
- PeriodCondition이 간적접으로 Movice, LocalDateTime, Customer에 의존

![8 4](https://user-images.githubusercontent.com/7076334/111326816-3666b980-86b0-11eb-8fd9-651decd75391.png)

- 의존성이 모든 경우 전이되는 것은 아님
  - 의존성이 실제로 전이될지 여부는 변경의 방향과 캡슐화의 정도에 따라 달라짐
  - 의존성 전이는 변경에 의해 영향이 널리 전파될 수도 있따는 경고일 뿐

- 의존성의 종류를 직접 의존성과 간접 의존성으로 나누기도 함
  - 직접 의존성이란 말 그대로 한 요소가 다른 요소에 직접 의존하는 경우 
    - PeriodCondition => Screening (의존)
  - 간접 의존성이란 직접적인 관계는 존재하지 않지만 의존성 전이에 의해 영향이 전파되는 경우
    - PeriodCondition 코드안에 명시적으로 드러나있지 않음

- 변경과 관련이 있는 어떤 것에도 의존성이라는 개념을 적용할 수 있음
  - 의존성의 대상은 객체일 수도 있고 모듈이나 더 큰 규모의 실행 시스템일 수도 있음
  - 하지만 본질은 같음
    - 의존성이란 의존하고 있는 대상의 변경에 영향을 받을 수 있는 가능성이다.

## 런타임 의존성과 컴파일타임 의존성
- 런타임 의존성
  - 애플리케이션이 실행되는 시점을 가리킴
  - 런타임 의존성이 다루는 주제는 객체 사이의 의존성
- 컴파일타임 의존성
  - 작성된 코드를 컴파일하는 시점을 가리키지만 문맥에 따라서는 코드 그 자체를 가리키기도 함
  - 동적 타입 언어의 경우 컴파일타임이 존재하지 않기 때문에 실제로 컴파일 수행되는 시점으로 이해하면 의미가 모호해질 수 있음
  - 컴파일타임 의존성이 다루는 주제는 클래스 사이의 의존성

- 런타임 의존성과 컴파일타임 의존성이 다를 수 있음
  - 유연하고 재사용 가능한 코드를 설계하기 위해서는 두 종류의 의존성을 서로 다르게 만들어야 함

- 영화 예매 시스템 예)

<img width="500" alt="8 5" src="https://user-images.githubusercontent.com/7076334/111326365-d7a14000-86af-11eb-9337-7a9d3256a523.png">


<img width="500" alt="8 6" src="https://user-images.githubusercontent.com/7076334/111326374-d96b0380-86af-11eb-988f-80e044979c2f.png">




