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
![dependency](https://user-images.githubusercontent.com/7076334/111320395-4380aa00-86aa-11eb-89b0-ef20dd7427cd.jpg)

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
- 

![dependency-transfer](https://user-images.githubusercontent.com/7076334/111322292-1e8d3680-86ac-11eb-8ced-57896a23aa1a.jpg)

