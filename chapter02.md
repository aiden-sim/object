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

- 인스턴스 변수의 가시성은 private 이고 메서드의 가시성은 public
- 
