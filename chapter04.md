# 설계 품질과 트레이드오프
- 객체지향 설계의 핵심은 역할, 책임, 협력
- 그 중에서 가장 중요한 것은 '책임'

- 객체지향 설계란 올바른 객체에게 올바른 책임을 할당하면서 낮은 결합도와 높은 응집도를 가진 구조를 창조하는 활동
- 결합도와 응집도를 합리적인 수준으로 유지하려면 객체의 상태가 아니라 객체의 행동에 초점을 맞춘다.

- 객체의 책임에 초점을 맞추면 상태에서 행동으로, 나아가 객체와 객체 사이의 상호작용으로 설계 중심을 이동시키고, 결합도가 낮고 응집도가 높으며 구현을 효과적으로 캡슐화하는 객체들을 창조할 수 있는 기반 제공
- 나쁜 설계에서 통찰력을 얻기 위해 예제

# 01 데이터 중심의 영화 예매 시스템
- 앞의 예제를 데이터 중심으로 보여줌

- 객체지향 설계에서 시스템을 객체로 분할하는 두가지 방법
  - 1) 상태를 분할의 중심 (데이터)
  - 2) 책임을 분할의 중심
  
- 데이터 중심의 관점
  - 객체는 자신이 포함하고 있는 데이터를 조작하는 데 필요한 오퍼레이션을 정의
  - 객체의 상태에 초점을 맞춘다.
  - 객체를 독립된 데이터 덩어리로 본다.

- 책임 중심의 관점
  - 객체는 다른 객체가 요청할 수 있는 오퍼레이션을 위해 필요한 상태를 보관 (협력)
  - 객체의 행동에 초점을 맞춘다.
  - 객체를 협력하는 공동체의 일원으로 본다.
  
- 훌륭한 객체 지향 설계는 데이터가 아닌 책임에 초점을 맞춰야 한다.

- 데이터에 초점을 맞출 시?
  - 객체의 상태는 구현에 속하고 구현은 불안정하기 때문에 변하기 쉽다.
  - 상태를 객체 분할의 중심축으로 삼으면 구현에 관한 세부사항이 객체의 인터페이스에 스며들게 되어 캡슐화의 원칙이 무너짐
  - 상태의 변경은 인터페이스의 변경을 초래하며 의존하는 모든 객체에게 변경의 영향이 퍼짐
  - 변경에 취약함
 
- 책임에 초점을 맞출 시?
  - 책임은 인터페이스에 속함
  - 객체는 책임을 드러내는 안정적인 인터페이스 뒤로 책임을 수행하는 데 필요한 상태를 캡슐화함으로써 구현 변경에 대한 파장이 외부로 나가는 것을 방지
  - 안정적인 설계를 얻을 수 있음
  
- 데이터 기준 영화 예매 시스템 예제를 통해 설명

## 데이터를 준비하자
- 데이터 중심의 설계는 객체가 내부에 저장해야 하는 '데이터가 무엇인가'를 묻는 것으로 시작

- Movie에 저장될 데이터를 결정하는 것으로 설계 시작
```java
public class Movie {
    private String title;
    private Duration runningTime;
    private Money fee;
    private List<DiscountCondition> discountConditions;

    private MovieType movieType;
    private Money discountAmount;
    private double discountPercent;
}
```
- 책임 설계와 동일하게 영화 제목, 상영시간, 기본 요금 등을 인스턴스 변수로 포함 (여기까지만 동일)
- 조건의 목록(discountConditions)이 인스턴스 변수로 Movie 안에 직접 포함
- 금액 할인 정책에 사용하는 할인 금액(discountAmount)과 비율 할인 정책에 사용되는 할인 비율(discountPercent)를 Movie 안에서 직접 정의

- movieType을 통해 할인 정책 구분
```java
public enum MovieType {
    AMOUNT_DISCOUNT,    // 금액 할인 정책
    PERCENT_DISCOUNT,   // 비율 할인 정책
    NONE_DISCOUNT       // 미적용
}
```

- 위의 방식은 말 그대로 데이터 중심의 접근
- 데이터 중심의 설계에서는 객체가 포함해야 하는 데이터에 집중
  - 객체의 책임을 결정하기 전에 이 객체가 포함해야 하는 데이터에 대해 집중 한다면 데이터 중심의 설계에 매몰돼 있을 확률 높음


- 영화 예매 시스템을 위해 필요한 모든 데이터를 클래스로 구현
![4-1](https://user-images.githubusercontent.com/7076334/106617287-d3164180-65b1-11eb-8ac9-f27f31b12887.png)


## 영화를 예매하자
- ReservationAgency는 데이터 클래스들을 조합해서 영화 예매 절차를 구현
```java
public class ReservationAgency {
    public Reservation reserve(Screening screening, Customer customer,
                               int audienceCount) {
        Movie movie = screening.getMovie();

        // 할인 가능 여부 확인
        boolean discountable = false;
        for (DiscountCondition condition : movie.getDiscountConditions()) {
            if (condition.getType() == DiscountConditionType.PERIOD) {
                discountable = screening.getWhenScreened().getDayOfWeek().equals(condition.getDayOfWeek()) &&
                        condition.getStartTime().compareTo(screening.getWhenScreened().toLocalTime()) <= 0 &&
                        condition.getEndTime().compareTo(screening.getWhenScreened().toLocalTime()) >= 0;
            } else {
                discountable = condition.getSequence() == screening.getSequence();
            }

            if (discountable) {
                break;
            }
        }

        // 적절한 할인 정책에 따라 예매 요금 계산
        Money fee;
        if (discountable) {
            Money discountAmount = Money.ZERO;
            switch (movie.getMovieType()) {
                case AMOUNT_DISCOUNT:
                    discountAmount = movie.getDiscountAmount();
                    break;
                case PERCENT_DISCOUNT:
                    discountAmount = movie.getFee().times(movie.getDiscountPercent());
                    break;
                case NONE_DISCOUNT:
                    discountAmount = Money.ZERO;
                    break;
            }

            fee = movie.getFee().minus(discountAmount).times(audienceCount);
        } else {
            fee = movie.getFee().times(audienceCount);
        }

        return new Reservation(customer, screening, fee, audienceCount);
    }
}
```

- reserve 메서드는 크게 두 부분으로 구현
  - 1) DiscountCondition에 대해 루프를 돌면서 할인 가능 여부를 확인하는 for문
  - 2) discountable 변수 값을 체크하고 적절한 할인 정책에 따라 예매 요금을 계산하는 if문
  
# 02 설계 트레이드오프
- 예제에서 데이터 중심 설계와 책임 중심 설계의 장단점을 비교하기 위해 **캡슐화, 응집도, 결합도** 를 사용
- 두 방법을 비교전 세 가지 품질 척도의 의미 확인

## 캡슐화
- 상태와 행동을 하나의 객체 안에 모으는 이유는 객체의 내부 구현을 외부로부터 감추기 위해
- 변경 가능성이 높은 부분은 내부에 숨기고 외부에는 상대적으로 안정적인 부분만 공개함으로써 변경의 여파를 통제

- 변경될 가능성이 높은 부분을 구현
- 상대적으로 안정적인 부분을 인터페이스
- 객체 설계의 가장 기본은 변경의 정도에 따라 구현과 인터페이스를 분리하고 외부에서는 인터페이스에만 의존하도록 관계 조절

- 객체지향에서 가장 중요한 원리는 캡슐화
  - 캡슐화는 외부에서 알 필요가 없는 부분을 감춤으로써 대상을 단순화하는 추상화의 한 종류
- 객체지향 설계의 가장 중요한 원리는 불안정한 구현 세부사항을 안정적인 인터페이스 뒤로 캡슐화하는 것

- 정리
  - 캡슐화란 변경 가능성이 높은 부분을 객체 내부로 숨기는 추상 기법
  - 무엇을 캡슐화 해야 하는가?
    - 변경될 수 있는 어떤 것이라도 캡슐화
    
## 응집도와 결합도
- 응집도는 모듈에 포함된 내부 요소들이 연관돼 있는 정도를 나타냄
  - 모듈 내의 요소들이 하나의 목적을 위해 긴밀하게 협력한다면 그 모듈은 높은 응집도를 가짐
  - 모듈 내의 요소들이 서로 다른 목적을 추구한다면 그 모듈은 낮은 응집도를 가짐
  - 객체지향에서 응집도는 객체 또는 클래스에 얼마나 관련 높은 책임들을 할당했는지 나타냄

- 결합도는 의존성의 정도를 나타내며 다른 모듈에 대해 얼마나 많은 지식을 갖고 있는지를 나타냄
  - 어떤 모듈이 다른 모듈에 대해 너무 자세한 부분까지 알고 있다면 높은 결합도
  - 어떤 모듈이 다른 모듈에 대해 꼭 필요한 지식만 알고 있다면 낮을 결합도
  - 객체지향에서 결합도는 객체 또는 클래스가 협력에 필요한 적절한 수준의 관계만을 유지하고 있는지 나타냄
  
  
- 응집도와 결합도의 의미를 이해하기 위한 첫걸음은 두 개념 모두 설계와 관련 있다는 사실을 이해
- 일반적으로 좋은 설계란 높은 응집도와 낮은 결합도를 가진 모듈로 구성된 설계를 의미
- 좋은 설계란 오늘 기능을 수행하면서 내일의 변경을 수용할 수 있는 설계
  - 좋은 설계를 만들기 위해서 높은 응집도와 낮은 결합도를 추구
  
- 높은 응집도와 낮은 결합도는 설계를 변경하기 쉽게 만듬

- 응집도가 높은 설계에서는 하나의 요구사항 변경을 반영하기 위해 오직 하나만 모듈만 수정하면 됨
- 응집도가 낮은 설계에서 하나의 원인에 의해 변경해야 하는 부분이 다수의 모듈에 분산돼 있기 때문에 여러 모듈을 동시에 수정해야 함
- 응집도가 높을수록 변경의 대상과 범위가 명확해지기 때문에 코드 변경이 쉬워짐
![4-2](https://user-images.githubusercontent.com/7076334/106622992-80d81f00-65b7-11eb-8269-fbc10ba4af3e.jpeg)


- 결합도는 한 모듈이 변경되기 위해서 다른 모듈의 변경을 요구하는 정도로 측정
- 결합도가 높으면 높을수록 함께 변경해야 하는 모듈의 수가 늘어나기 때문에 변경하기 어려워짐
- 내부 구현 변경 시, 다른 모듈에 영향을 미치는 경우 두 모듈 사이의 결합도가 높다
- 퍼블릭 인터페이스를 수정했을 때만 다른 모듈에 영향을 미치는 경우에는 결합도가 낮다
- 클래스의 구현이 아닌 인터페이스에 의존하도록 코드를 작성해야 낮은 결합도를 얻을 수 있음
![4-3](https://user-images.githubusercontent.com/7076334/106622940-7453c680-65b7-11eb-93fd-ea2e548f95ad.jpeg)


- 결합도가 높아도 상관없는 경우
  - 안정적인 

  





  
  
  
  

