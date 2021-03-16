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
- isSatisfiedBy
