# 서브클래싱과 서브타이핑
- 상속의 용도
  - 1) 타입 계층을 구현하는 것 : 부모 클래스는 자식 클래스의 일반화 이고 자식 클래스는 부모 클래스의 특수화  
  - 2) 코드 재사용 : 간단하게 코드 재사용할 수 있으나 강하게 결합되기 때문에 변경하기 어려운 코드를 얻게 될 수 있음

- 상속을 사용하는 일차적인 목표는 타입 계층 구현을 하는 것 (유연한 설계)
- 타입 사이의 관계를 고려하지 않은 채 단순히 코드를 재사용하기 위해 상속을 사용해서는 안됨

- 타입 계층이란 무엇인가?

### 객체지향 프로그래밍과 객체기반 프로그래밍
```
객체기반 프로그래밍 : 상태와 행동을 캡슐화한 객체를 조합해서 프로그램을 구성하는 방식
객체지향 프로그래밍 : 객체기반 프로그래밍의 한 종류. 객체기반 프로그래밍과 차이점은 상속과 다형성을 지원한다는 점
```

# 01 타입
- 개념 관점의 타입과 프로그래밍 언어 관점의 타입

## 개념 관점의 타입
- 개념 관점에서 타입이란 우리가 인지하는 세상의 사물의 종류를 의미
- 어떤 대상의 타입으로 분류될 때 그 대상을 타입의 인스턴스라고 부름
  - 일반적으로 타입의 인스턴스를 객체라고 부름

- 타입의 구성 요소
  - 심볼 : 타입에 이름을 붙인 것이다. 앞에서 '프로그래밍 언어'가 타입의 심볼에 해당
  - 내연 : 타입의 정의로서 타입에 속하는 객체들이 가지는 공통적인 속성이나 행동을 가리킨다.
  - 외연 : 타입에 속하는 객체들의 집합. '프로그래밍 언어' 타입의 경우에는 자바, 루비, 자바스크립트, C가 속한 집합이 외연을 구성한다. 

- 기타) 클래스, 객체, 인스턴스 차이
  - https://gmlwjd9405.github.io/2018/09/17/class-object-instance.html 

## 프로그래밍 언어 관점의 타입
- 프로그래밍 언어 관점에서 타입은 연속적인 비트에 의미와 제약을 부여하기 위해 사용

- 두 가지 목적
  - 1) 타입에 수행될 수 있는 유효한 오퍼레이션의 집합을 정의한다 
    - ex) 자바에서 '+' 연산자는 원시형 숫자 타입이나 문자열 타입의 객체에서 사용할 수 있지만 다른 클래스의 인스턴스에 대해서는 사용할 수 없음
    - 모든 객체지향 언어들은 객체의 타입에 따라 적용 가능한 연산자의 종류를 제한함으로써 프로그램저의 실수를 막아줌
  - 2) 타입에 수행되는 오퍼레이션에 대해 미리 약속된 문맥을 제공한다
    - ex) a +b 라는 연산에서 타입이 int라면 두 수를 더함. String 이라면 두 문자열을 하나로 합침
    - 객체를 생성하는 방법에 대한 문맥을 결정하는 것은 바로 객체의 타입

- 정리
  - 타입은 적용 가능한 오퍼레이션의 종류와 의미를 정의함으로써 코드의 의미를 명확하게 전달하고 개발자의 실수를 방자히가 위해 사용 

## 객체지향 패러다임 관점의 타입
- 타입의 두 가지 관점
  - 1) 개념 관점에서 타입이란 공통의 특징을 공유하는 대상들의 분류
  - 2) 프로그래밍 언어 관점에서 타입이란 동일한 오퍼레이션을 적용할 수 있는 인스턴스들이 집합

- 객체의 타입이란 객체가 수신할 수 있는 메시지의 종류를 정의하하는 것
- 객체지향 프로그래밍에서 타입을 정의하는 것은 객체의 퍼블릭 인터페이스를 정의하는 것과 동일

- 객체지향 프로그래밍 관점에서 타입정의
```
객체의 퍼블릭 인터페이스가 객체의 타입을 결정한다. 따라서 동일한 퍼블릭 인터페이스를 제공하는 객체들은 동일한 타입으로 분류된다.
```

- 객체를 바라볼 때는 항상 객체가 외부에 제공하는 행동에 초점을 맞춰야 함. 객체의 타입을 결정하는 것은 내부 속성이 아니라 객체가 외부에 제공하는 행동이라는 사실

# 02 타입 계층
## 타입 사이의 포함관계
- 타입 안에 포함된 객체들을 좀 더 상세한 기준으로 묶어 새로운 타입을 정의하면 이 새로운 타입은 자연스럽게 기존 타입의 부분집합이 됨

- '프로그래밍 언어' 타입의 인스턴스 집합
  - ![스크린샷 2021-04-17 오후 10 35 19](https://user-images.githubusercontent.com/7076334/115115045-36b6e500-9fcd-11eb-9dd7-6bd3ef7f9fc6.png)

- 위의 집합을 좀 더 상세한 기준에 따라 분류
- 타입은 집합의 관점에서 더 세분화된 타입의 집합을 부분집합으로 포함
  - ![스크린샷 2021-04-17 오후 10 33 29](https://user-images.githubusercontent.com/7076334/115114983-f5bed080-9fcc-11eb-93b8-3b11d704f7ca.png)

- 타입의 특성
  - 동일한 인스턴스가 하나 이상의 타입으로 분류되는 것도 가능
  - 다른 타입을 포함하는 타입은 포함되는 타입보다 좀 더 일반화된 의미를 표현할 수 있음
  - 다른 타입을 포함하는 타입은 포함되는 타입보다 더 많은 인스터를 가짐 
    - 다시 말해 포함하는 타입은 외연 관점에서는 더 크고 내연 관점에서는 더 **일반적임**
    - 포함되는 타입은 외연 관점에서 더 작고 내연 관점에서는 더 **특수함**

- 일반화/특수화 관계로 연결된 타입 계층
  - ![스크린샷 2021-04-17 오후 10 44 53](https://user-images.githubusercontent.com/7076334/115115279-8ba72b00-9fce-11eb-9b01-941f5cbdeeff.png)
  - 타입 계층 표현 시, 더 일반적인 타입을 위쪽에, 더 특수한 타입을 아래쪽에 배치
  - 더 일반적인 타입을 **슈퍼타입**
  - 더 특수한 타입을 **서브타입**

- 일반화와 특수화의 정의
```
일반화는 다른 타입을 오나전히 포함하거나 내포하는 타입을 식별하는 행위 또는 그 행위의 결과를 가리킨다.
특수화는 다른 타입 안에 전체적으로 포함되거나 완전히 내포되는 타입을 식별하는 행위 또는 그 행위의 결과를 가리킨다.
```

- 슈퍼타입과 서브타입 특징
  - 슈퍼타입
    - 집합이 다른 집합의 모든 멤버를 포함
    - 타입 정의가 다른 타입보다 좀 더 일반적 
  - 서브타입 
    - 집합에 포함되는 인스턴스들이 더 큰 집합에 포함됨
    - 타입 정의가 다른 타입보다 좀 더 구체적

## 객체지향 프로그래밍과 타입 계층
- 객체의 타입을 결정하는 것은 퍼블릭 인터페이스
- 퍼블릭 인터페이스 관점의 슈퍼타입/서브타입
  - 슈퍼타입이란 서브타입이 정의한 퍼블릭 인터페이스를 일반화시켜 상대적으로 범용적이고 넓은 의미로 정의한 것
  - 서브타입이란 슈퍼타입이 정의한 퍼블릭 인터페이스를 특수화시켜 상대적으로 구체적이고 좁은 의미로 정의한 것

- 강조
  - 서브타입의 인스턴스는 슈퍼타입의 인스턴스로 간주될 수 있음
  - 이 사실이 이번 장의 핵심이자 상속과 다형성의 관계를 이해하기 위한 출발점 

# 03 서브클래싱과 서브타이핑
- 객체지향 프로그래밍 언어에서 **타입**을 구현하는 일반적인 방법은 클래스를 이용하는 것
- **타입 계층**을 구현하는 일반적인 방법은 상속을 이용하는 것
  - 부모 클래스가 슈퍼타입 역할, 자식 클래스가 서브타입 역할

- 서브 타입의 퍼블릭 인터페이스가 특수하다는 것은 어떤 의미일까?

## 언제 상속을 사용해야 하는가?
- 상속의 올바른 용도는 타입 계층을 구현하는 것

- 두 질문을 해보고 '예'라 답할 수 있을 경우에만 상속을 사용 (마틴 오더스키)
  - 1) 상속 관계가 is-a 관계를 모델링하는가?
    - 일반적으로 '자식 클래스'는 '부모 클래스'다 라고 말해도 이상하지 않다면 상속을 사용할 후보로 간주
    - 두 클래스에 대해 기대하는 행동이 다르면 is-a 관계라도 상속을 사용해서는 안됨
  - 2) 클라이언트 입장에서 부모 클래스의 타입으로 자식 클래스를 사용해도 무방한가?
    - 상속 계층을 사용하는 클라이언트의 입장에서 부모 클래스와 자식 클래스의 차이점을 몰라야함 (행동 호환성)
    - 상속을 결정하기 위해 이 질문에 초점을 맞추는 것이 중요

## is-a 관계
- 마틴 오더스키 : 두 클래스가 어휘적으로 is-a 관계를 모델링할 경우에만 상속을 사용해야 함
  - 참고) http://www.yes24.com/Product/Goods/96640057
- 스콧 마이어스 : 새와 펭귄의 예를 들어 is-a 관계가 직관을 쉽게 배신할 수 있다는 사실을 보여줌

- 새와 펭귄
  - 펭귄은 새다
  - 새는 날 수 있다 

```
public class Bird {
    public void fly() {

    }
}

public class Penguin extends Bird {
}
```
- 펭귄은 날 수 없지만, 코드는 분명 '펭귄은 새고, 따라서 날 수 있다'라고 주장하고 있음
- 위 예는 어휘적인 정의가 아니라 기대되는 행동에 따라 타입 계층을 구성해야 한다는 사실을 잘 보여줌
  - 어휘적으로 펭귄은 새지만 새에 날 수 있다는 행동이 포함된다면 펭귄은 새의 서브타입이 못됨

- 타입 계층의 의미는 행동이라는 문맥에 따라 달라질 수 있음. 그에 따라 올바른 타입 계층이라는 의미 역시 문맥에 따라 달라질 수 있음
  - 새는 울음 소리를 낼 수 있다는 행동을 기대한다면? 새와 펭귄을 타입 계층으로 묶을 수 있음

- 결론
  - 슈퍼타입과 서브타입 관계에서 is-a 보다 행동 호환성이 더 중요
  - 성급하게 상속을 적용하려 하지 말고, is-a를 만족한다면 상속을 사용할 예비 후보 정도로만 생각하자

## 행동 호환성
- 행동이 호환된다는 것은 무슨 의미일까?
  - 중요한 것은 행동의 호환 여부를 판단하는 기준은 **클라이언트의 관점** 이라는 것
  - 클라이언트가 두 타입이 동일하게 행동할 것이라고 기대한다면 두 타입을 타입 계층으로 묶을 수 있음
  - 타입 계층을 이해하기 위해서는 그 타입 계층이 사용될 문맥을 이해하는 것이 중요

- 펭귄이 새다라는 상속 계층을 유지할 수 있는 3가지 방법
  - 1) Penguin의 fly 메서드 오버라이딩을 구현을 비워 두는 것 
      ```
      public class Penguin extends Bird {
          @Override
          public void fly() {

          }
      }
      ```
      - 어떤 행동도 수행하지 않기 때문에 모든 새가 날 수 있다는 클라이언트의 기대를 만족시키지 못함 (올바른 설계가 아님)


  - 2) Penguin의 fly 메서드 오버라이딩한 후 예외를 던지게 하는 것
      ```
      public class Penguin extends Bird {
          @Override
          public void fly() {
            throw new UnsupportedOperationException();
          }
      }
      ```
      - 역시 클라이언트 관점에서 Bird와 Penguin의 행동이 호환되지 못함


  - 3) flyBird 메서드를 수정해서 Penguin이 아닌 경우에만 fly 메시지를 전송
      ```
      public void flyBird(Bird bird) {
          if (!(bird instanceof Penguin)) {
              bird.fly();
          }
      }
      ```
      - 날 수 없는 새가 계속 추가될 때마다 타입 체크 코드도 추가.
        - 코드를 수정을 요구하기 때문에 개방-폐쇄 원칙을 위반 
      - 구체적인 클래스에 대한 결합도를 높인다.

## 클라이언트의 기대에 따라 계층 분리하기
- 문제를 해결할 수 있는 방법은 클라이언트의 기대에 맞게 상속 계층을 분리하는 것
  - 날 수 있는 새와 날 수 없는 새를 명확하게 구분할 수 있게 상속 계층을 분리하면 서로 다른 요구 사항을 가진 클라이언트를 만족시킬 수 있을 것

1. 날 수 없는 새와 날 수 있는 새를 분리
```
public class Bird {}

public class FlyingBird extends Bird {
    public void fly() {

    }
}

public class Penguin extends Bird {}

public class Client {
    public void flyBird(FlyingBird bird) {
        bird.fly();
    }
}
```
- Bird가 아닌 FlyingBird에 fly 행동을 구현

- 클라이언트의 기대에 따라 상속 계층을 분리

- ![스크린샷 2021-04-18 오전 12 03 08](https://user-images.githubusercontent.com/7076334/115117437-813e5e80-9fd9-11eb-9fbd-4d088c3e9b18.png)
- 이제 FlyingBird 타입의 인스턴스만이 fly 메시지를 수신할 수 있음
  - 날 수 엇는 Penguin의 인스턴스에게 fly 메시지를 보낼 방법은 없음
  - 즉 객체와 협력해서 기대했던 행동이 수행되지 않거나 예외가 던져지는 일은 일어나지 않을 것



2. 다른 방법으로 클라이언트에 따라 인터페이스를 분리
- Bird : fly, walk
- Penguin : walk

![스크린샷 2021-04-18 오전 12 03 17](https://user-images.githubusercontent.com/7076334/115117439-826f8b80-9fd9-11eb-95ad-f7c339eca612.png)
- 가장 좋은 방법은 fly 오퍼레이션을 가진 Flyer 인터페이스와 walk 오퍼레이션을 가진 Walker 인터페이스로 분리하는 것
- Bird와 Penguin은 자신이 수행할 수 있는 인터페이스만 구현 하면 됨

```
public interface Flyer {
    void fly();
}

public interface Walker {
    void walk();
}

public class Bird implements Flyer, Walker {
    @Override
    public void fly() {}

    @Override
    public void walk() {}
}

public class Penguin implements Walker {
    @Override
    public void walk() {}
}
```

- Penguin이 Bird의 코드를 재사용해야 한다면?
  - Penguin이 Bird를 상속 받는다면? 퍼블릭 인터페이스에 fly 오퍼레이션이 추가되기 때문에 하면 안됨. 또한 재사용을 위한 상속은 위험
  - 합성을 사용하자 (P.450)
 
- 클라이언트에 따라 인터페이스를 분리하면 변경에 대한 영향을 더 세밀하게 제어할 수 있게 됨
- 각 클라이언트의 요구가 바뀌더라도 영향의 파급 효과를 효과적으로 제어할 수 있게 됨

- 인터페이스 분리 원칙(ISP)
  - 인터페이스를 클라이언트의 기대에 따라 분리함으로써 변경에 의해 영향을 제어하는 설계 원칙을 말함

```
비대한 인터페이스를 가지는 클래스는 응집성이 없는 인터페이스를 가지는 클래스다.
비대한 클래스는 그 클라이언트 사이에 이상하고 해로운 결합이 생기게 만든다.
이 비대한 클래스의 인터페이스를 여러 개의 클라이언트에 특화된 인터페이스로 분리함으로써 성취될 수 있다.
이렇게 하면 호출하지 않는 메서드에 대한 클라이언트의 의존성을 끊고, 클라이언트가 서로에 대해 독립적이 되게 만들 수 있다.
```

- 주의할 부분은 설계가 꼭 현실 세계를 반영할 필요는 없음
- 중요한 것은 설계가 반영할 도메인의 요구사항이고 그 안에서 클라이언트가 객체에게 요구하는 행동임

- 스콧 마이어스의 조언
```
최고의 설계는 제작하려는 소프트웨어 시스템이 기대하는 바에 따라 달라진다.
애플리케이션이 비행에 대한 지식을 전혀 쓰지 않으며 나중에도 쓸일 없을 것이라면, 날 수 있는 새와 날지 않는 새를 구분하지 않는 것이 탁월한 선택일 수 있다.
실제로 이런 것들을 잘 구분해서 설계하는 쪽이 바람직하다.
```

- 결론
  - 자연어에 현혹되지 말고 요구사항 속에서 클라이언트가 기대 하는 행동에 집중하라

## 서브클래싱과 서브타이핑
- 사람들은 상속을 사용하는 두 가지 목적(코드 재사용, 타입 계층 구성)에 특별한 이름을 붙였는데 **서브클래싱**과 **서브타이핑**
  - 서브클래싱
    - 다른 클래스의 코드를 재사용할 목적으로 상속을 사용하는 경우. 
    - 자식과 부모 클래스의 행동이 호환되지 않기 때문에 자식 클래스의 인스턴스가 부모 클래스의 인스턴스를 대체할 수 없음
    - 서브클래싱을 구현 상속 또는 클래스 상속이라고 부름
  - 서브타이핑
    - 타입 계층을 구성하기 위해 상속을 사용하는 경우
    - 자식 클래스와 부모 클래스 행동이 호환되기 때문에 자식 클래스의 인스턴스가 부모 클래스의 인스턴스를 재체할 수 있음
    - 서브타이핑을 인터페이스 상속이라고 부름

- 서브클래싱과 서브타이핑을 나누는 기준은 상속을 사용하는 목적
  - 서브클래싱 : 자식 클래스가 부모 클래스의 코드를 재사용할 목적으로 상속
  - 서브타이핑 : 부모 클래스의 인스턴스 대신 자식 클래스의 인스턴스를 사용할 목적으로 상속을 사용했다면 그것은 서브타이핑


```
클래스 상속은 객체의 구현을 정의할 때 이미 객체의 구현을 바탕으로 한다.
이에 비해 인터페이스 상속(서브타이핑)은 객체가 다른 곳에서 사용될 수 있음을 의미한다. (런타임에 서브타입의 객체로 대체할 수 있다.)
```

- 서브타이핑 관계가 유지되기 위해서는 서브타입이 슈퍼타입이 하는 모든 행동을 동일하게 할 수 있어야 함
- 즉 어떤 타입이 다른 타입의 서브타입이 되기 위해서는 행동 호환성을 만족시켜야 함
- 자식 클래스와 부모 클래스 사이의 행동 호환성은 부모 클래스에 대한 자식 클래스의 대체 가능성을 포함함

# 04 리스코프 치환 원칙
- 리스코프 치환 원칙을 한마디로 정리하면 '서브타입은 그것의 기반 타입에 대해 대체 가능해야 한다'는 것으로 클라이언트가 '차이점을 인식하지 못한 채 기반 클래스의 인터페이스를 통해 서브클래스를 사용할 수 있어야 한다'는 것

- 리스코프 치환 원칙은 앞에서 논의한 행동 호환성을 설계 원칙으로 정리한 것

- 리스코프 치환 위반 사례 (정사각형과 직사각형)
  - 개념적으로 정사각형은 직사각형의 특수한 경우이고 직사각형은 정사각형의 일반적인 경우이기 때문에 is-a 관계 성립


- Rectangle과 협력하는 클라이언트는 직사각형의 너비와 높이가 다르다면?
  ```
  public class Client {
    public static void main(String[] args) {
        Square square = new Square(10, 10, 10);
        resize(square, 50, 100);
    }

    public static void resize(Rectangle rectangle, int width, int height) {
        rectangle.setWidth(width);
        rectangle.setHeight(height);
        assert rectangle.getWidth() == width && rectangle.getHeight() == height; // 에러발생
    }
  }
  ```
  - resize 메서드 구현은 Rectangle이 세운 가정에 기반하기 때문에 직사각형의 너비와 높이를 독립적으로 변경할 수 있음
  - 정사각형은 항상 너비와 높이를 같게 설정하기 때문에 실패할 것이다.
  - 두 클래스는 서브타이핑 관계가 아니라 서브클래싱 관계
  - 중요한것은 클라이언트 관점에서 행동이 호환되는지 여부

## 클라이언트와 대체 가능성
- 클라이언트 입장에서 정사각형을 추상화한 Square는 직사각형을 추상화한 Rectangle과 동일하지 않기 때문에 대체할 수 없음

- 리스코프 치환 원칙은 자식 클래스가 부모 클래스를 대체하기 위해서는 부모 클래스에 대한 클라이언트의 가정을 준수해야 한다는 것을 강조

- Stack과 Vector
- ![스크린샷 2021-04-18 오전 2 01 21](https://user-images.githubusercontent.com/7076334/115120748-07fb3780-9fea-11eb-8233-195dfe5afa19.png)
  - 리스코프 치환 원칙을 위반하는 가장 큰 이유는 상속으로 인해 Stack에 포함돼서는 안 되는 Vector의 퍼블릭 인터페이스가 포함됐기 때문 
  - Vector의 클라이언트는 임의의 위치에 요소 추가 가능
  - Stack의 클라이언트는 Stack이 임의의 위치에서의 조회나 추가 금지
  - 클라이언트 입장에서 Vector 대신 Stack으로 변환하거나 Stack 대신 Vector로 변환 했을 경우 기대했던 대로 동작하지 않는다.


- 리스코프 치환 원칙
  - 클라이언트와 격리한 채로 본 모델은 의미 있게 검증하는 것이 불가능하다는 중요한 결론을 이끔
  - 상속 관계에 있는 두 클래스 사이의 관계를 클라이언트와 떨어트려 놓고 판단하지 말라

- 결론
  - 상속 관계는 클라이언트의 관점에서 자식 클래스가 부모 클래스를 대체할 수 있을 때만 올바름
  - 대체 가능성을 결정하는 것은 클라이언트 


## is-a 관계 다시 살펴보기
- is-a는 클라이언트 관점에서 is-a 일 때만 참이다.
- 클라이언트에서 기대하는 행동이 있고 그 행동을 만족하는 경우 올바른 상속이라 볼 수 있음

- 중요
  - 오더스키가 설명한 is-a 관계를 행동이 호환되는 타입에 어떤 이름을 붙여야 하는지 설명하는 가이드라 생각하라
  - 슈퍼타입과 서브타입이 클라이언트 입장에서 행동이 호환된다면 두 타입을 is-a로 연결해 문장을 만들어도 어색하지 않은 단어로 타입의 이름을 정리하라는 것
  - 이름이 아니라 행동이 먼저다.

## 리스코프 치환 원칙은 유연한 설계의 기반이다
- 새로운 자식 클래스를 추가하더라도 클라이언트 입장에서 동일하게 행동하기만 한다면 클라이언트를 수정ㅈ하지 않고도 상속 계층을 확장할 수 있음
- 리스코프 치환 원칙을 따르는 설계는 유연할뿐만 아니라 확장성이 높음

- 8장의 중복 할인 정책 (의존성 역전 원칙, 개방-폐쇄원칙, 리스코프 치환 원칙)
- ![스크린샷 2021-04-18 오전 2 32 50](https://user-images.githubusercontent.com/7076334/115121575-688c7380-9fee-11eb-8ce3-09cf6d07d32d.png)
  - 의존성 역전 원칙(DIP) : 상위 수준의 모듈인 Movie와 하위 수준의 모듈인 OverlappedDiscountPolicy는 모두 추상 클래스 DiscountPolicy에 의존 
  - 리스코프 치환 원칙(LSP) : Movie 관점에서 DiscountPolicy 대신 OverlappedDiscountPolicy와 협력하더라도 문제 없음 (행동이 같음)
  - 개방-폐쇄 원칙(OCP) : 새로운 기능을 추가하기 위해 OverlappedDiscountPolicy를 추가하더라도 Movie에 영향을 끼치지 않음

- 리스코프 치환 원칙은 개방-폐쇄 원칙을 만족하는 설계를 위한 전제 조건
- 일반적으로 리스코프 치환 원칙 위반은 잠재적인 개방-폐쇄 원칙 위반

## 타입 계층과 리스코프 치환 원칙
- 구현 방법은 중요하지 않음 (자바의 인터페이스나 스칼라의 트레이트가 되었던)
- 구현 방법과 무관하게 클라이언트의 관점에서 슈퍼타입에 대해 기대하는 모든 것이 서브타입에게도 적용돼야 서브타이핑이라 할 수 있음

# 05 계약에 의한 설계와 서브타이핑

- 계약에 의한 설계 (P.540)
  - 사전조건 : 메서드가 호출되기 위해 만족돼야 하는 조건. 사전조건을 만족시키는 것은 메서드를 실행하는 클라이언트의 의무다.
  - 사후조건 : 메서드가 실행된 후에 클라이언트에게 보장해야 하는 조건. 사전조건을 만족시켰는데도 사후조건을 만족시키지 못한 경우에는 클라이언트에게 예외를 던져야 한다. 사후조건을 만족시키는 것은 서버의 의무다.
  - 불변식 : 항상 참이라고 보장되는 서버의 조건. 메서드를 실행하기 전이나 종료된 후에 불변식은 항상 참이어야 한다.

- 리스코프 치환 원칙과 계약에 의한 설계 사이의 관계를 다음과 같은 한 문장으로 요약할 수 있음
  - 서브타입이 리스코프 치환 원칙을 만족시키기 위해서는 클라이언트와 슈퍼타입 간에 체결된 '계약'을 준수해야 한다.

```
// 클라이언트
public class Movie {
    
    public Money calculateMovieFee(Screening screening) {
        return fee.minus(discountPolicy.calculateDiscountAmount(screening));
    }
}

// 서버
public abstract class DiscountPolicy {
    public Money calculateDiscountAmount(Screening screening) {
        for (DiscountCondition each : conditions) {
            if (each.isSatisfiedBy(screening)) {
                return getDiscountAmount(screening);
            }
        }

        return screening.getMovieFee();
    }


    abstract protected Money getDiscountAmount(Screening Screening);
}

```
- Movie는 DiscountPolicy의 인스턴스에게 calculateDiscountAmount 메시지를 전송하는 클라이언트

- 계약에 의한 설계에 따르면 협력하는 클라이언트와 슈퍼타입의 인스턴스 사이에는 어떤 계약이 맺어져 있음
- 서브타입이 슈퍼타입처럼 보일 수 있는 유일한 방법은 클라이언트가 슈퍼타입과 맺은 계약을 서브타입이 준수하는 것뿐

- 사전 조건
  - calculateDiscountAmount 메서드 인자로 screening이 null인지 여부 확인 (NPE 발생)
  - 영화 시작 시간이 아직 지나지 않았다고 가정

- 사후 조건
  - calculateDiscountAmount 메서드 반환값은 항상 null이 아니어야 함
  - 반환되는 값은 청구되는 요금이기 때문에 최소한 0원보다는 커야 함

```
   public Money calculateDiscountAmount(Screening screening) {
        checkPrecondition(screening); // 사전 조건

        Money amount = Money.ZERO;
        for (DiscountCondition each : conditions) {
            if (each.isSatisfiedBy(screening)) {
                amount = getDiscountAmount(screening);
                checkPostcondition(amount);
                return amount;
            }
        }

        amount = screening.getMovieFee();
        checkPostcondition(amount); // 사후 조건
        return amount;
    }

    protected void checkPrecondition(Screening screening) {
        assert screening != null &&
                screening.getStartTime().isAfter(LocalDateTime.now());
    }

    protected void checkPostcondition(Money amount) {
        assert amount != null && amount.isGreaterThanOrEqual(Money.ZERO);
    }
```



- 사전조건을 만족시키기 위해 Movie는 사전조건을 위반하는 screening을 전달해서는 안됨
```
    public Money calculateMovieFee(Screening screening) {
        if (screening == null ||
                screening.getStartTime().isBefore(LocalDateTime.now())) {
            throw new InvalidScreeningException();
        }

        return fee.minus(discountPolicy.calculateDiscountAmount(screening));
    }
```

- DiscountPolicy의 자식 클래스인 AmountDiscountPolicy, PercentDiscountPolicy, OverlappedDiscountPolicy는 Movie와 DiscountPolicy 사이에 체결된 계약을 만족시키는가?
  - DiscountPolicy의 calculateDiscountAmount를 그대로 상속 받았기 때문에 계약을 변경하지 않음 (서브타이핑 관계)

## 서브타입과 계약
- 자식 클래스가 부모 클래스의 메서드를 오버라이딩 해서 더 강력한 사전조건을 정의했다면?

```
    @Override
    public Money calculateDiscountAmount(Screening screening) {
        checkPrecondition(screening);                 // 기존의 사전조건
        checkStrongerPrecondition(screening);         // 더 강력한 사전조건

        Money amount = screening.getMovieFee();
        checkPostcondition(amount);                   // 기존의 사후조건
        return amount;
    }

    private void checkStrongerPrecondition(Screening screening) {
        assert screening.getEndTime().toLocalTime()
                .isBefore(LocalTime.MIDNIGHT);
    }
```
- Movie가 알고 있는 DiscountPolicy의 사전조건은 screening이 null이 아니면서 시작시간이 현재 시간 이후인 것
- BrokenDiscountPolicy의 사전조건은 종료 시간이 자정을 넘는 영화를 예매할 수 없다
  - BrokenDiscountPolicy과 협력은 실패하고 만다. DiscountPolicy의 서브타입이 아니다.
```
서브타입에 더 강력한 사전조건을 정의할 수 없다.
```

- 사전조건을 제거해서 약화 시킨다면?
```
    @Override
    public Money calculateDiscountAmount(Screening screening) {
        // checkPrecondition(screening);                 // 기존의 사전조건

        Money amount = screening.getMovieFee();
        checkPostcondition(amount);                   // 기존의 사후조건
        return amount;
    }
```
- 클라이언트(Movie)에서 자신의 의무를 충실히 수행하고 있기 때문에 이 조건을 체크하지 않는 것이 기존 협력에 어떤 영향도 미치지 않음 (문제 발생하지 않음)
```
서브타입에 슈퍼타입과 같거나 더 약한 사전조건을 정의할 수 있다.
```

- 사후조건을 강화한다면?
```
    @Override
    public Money calculateDiscountAmount(Screening screening) {
        checkPrecondition(screening);                 // 기존의 사전조건

        Money amount = screening.getMovieFee();
        checkPostcondition(amount);                   // 기존의 사후조건
        checkStrongerPostcondition(amount);           // 더 강력한 사후조건
        return amount;
    }

    private void checkStrongerPostcondition(Money amount) {
        assert amount.isGreaterThanOrEqual(Money.wons(1000));
    }
```
- 최소 1000원 이상 돼야 한다는 새로운 사후조건 추가
- Movie는 최소한 0원보다 큰 금액을 반환받기만 하면 협력이 정상적으로 수행됐다고 가정한다. (계약을 위반하지 않음)
```
서브타입에 슈퍼타입과 같거나 더 강한 사후조건을 정의할 수 있다.
```

- 사후조건을 약하게 정의한다면?
```
    @Override
    public Money calculateDiscountAmount(Screening screening) {
        checkPrecondition(screening);                 // 기존의 사전조건

        Money amount = screening.getMovieFee();
        
        // checkPostcondition(amount);                // 기존의 사후조건 제거
        checkWeakerPostcondition(amount);             // 더 약한 사후조건
        return amount;
    }

    private void checkWeakerPostcondition(Money amount) {
        assert amount != null;
    }
```
- null만 체크하고 금액에 대한 체크는 제거
- Movie는 자기와 협력하는 객체가 반환된 금액이 0원보다는 크다고 믿고 있기 때문에 마이너스 금액이 넘어올 경우 위반
```
서브타입에 더 약한 사후조건을 정의할 수 없다.
```

- 계약에 의한 설계는 클라이언트 관점에서의 대체 가능성을 계약으로 설명할 수 있다는 사실을 잘 보여 준다.
- 서브타이핑을 위해 상속을 사용하고 있다면 부모 클래스가 클라이언트와 맺고 있는 계약에 깊이 있게 고민해야 됨


- 참고) https://songii00.github.io/2020/04/25/2020-04-25-OBJECTS%20Item%2013/











