# 합성과 유연한 설계
- 합성은 전체를 표현하는 객체가 부분을 표현하는 객체를 포함해서 부분 객체의 코들들 재사용함
- 상속 관계 : is-a (정적관계)
- 합성 관계 : has-a (동적관계)

- 합성은 내부에 포함되는 객체의 구현이 아닌 퍼블릭 인터페이스에 의존
  - 내부 구현이 변경되더라도 영향을 최소화 할 수 있음 (안정적인 코드)

```
코드 재사용을 위해서는 객체 합성이 클래스 상속보다 더 좋은 방법이다.
```

- 상속은 부모 클래스 안에 구현된 코드 자체를 재사용
- 합성은 포함되는 객체의 퍼블릭 인터페이스를 재사용
  - 클래스 사이의 높은 결합도를 객체 사이의 낮은 결합도로 대체

```
상속 : 화이트박스 재사용
합성 : 블랙박스 재사용
```

# 01 상속을 합성으로 변경하기
- 상속 문제점
  - 불필요한 인터페이스 상속 문제
  - 메서드 오버라이딩 오작용 문제
  - 부모 클래스와 자식 클래스의 동시 수정 문제

## 불필요한 인터페이스 상속 문제:java.util.Properties와 java.util.Stack

- Properties를 상속 관계에서 합성으로 변경
```
public class Properties {
    private Hashtable<String, String> properties = new Hashtable<>();

    public String getPropertiy(String key, String value) {
        return properties.put(key, value);
    }

    public String getProperty(String key) {
        return properties.get(key);
    }
}
```
- 클라이언트는 오직 Proeprties에서 정의한 오퍼레이션만 사용 가능
- Properties는 Hashtable의 내부 구현에 관해 알지 못함

- Stack을 상속 관계에서 합성으로 변경
```
public class Stack<E> {
    private Vector<E> elements = new Vector<>();

    public E push(E item) {
        elements.addElement(item);
        return item;
    }

    public E pop() {
        if (elements.isEmpty()) {
            throw new EmptyStackException();
        }
        return elements.remove(elements.size() - 1);
    }
}
```
- Stack의 퍼블릭 인터페이스에 불필요한 Vector의 오퍼레이션들 미포함

## 메서드 오버라이딩의 오작용 문제: InstrumentedHashSet
- InstrumentedHashSet도 합성으로 변경
```
public class InstrumentedHashSet<E> {
    private int addCount = 0;
    private Set<E> set;

    public InstrumentedHashSet(Set<E> set) {
        this.set = set;
    }


    public boolean add(E e) {
        addCount++;
        return set.add(e);
    }

    public boolean addAll(Collection<? extends E> c) {
        addCount += c.size();
        return set.addAll(c);
    }

    public int getAddCount() {
        return addCount;
    }
}
```
- HashSet에서 제공하는 퍼블릭 인터페이스를 그대로 제공해야 함.


- 구현 결합도는 제거하면서 퍼블릭 인터페이스는 그대로 상속받을 수 있는 방법은 없을까?
  - 자바의 인터페이스 사용
```
public class InstrumentedHashSet<E> implements Set<E> {
    private int addCount = 0;
    private Set<E> set;

    public InstrumentedHashSet(Set<E> set) {
        this.set = set;
    }

    @Override
    public boolean add(E e) {
        addCount++;
        return set.add(e);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        addCount += c.size();
        return set.addAll(c);
    }

    public int getAddCount() {
        return addCount;
    }

    @Override
    public boolean remove(Object o) {
        return set.remove(o);
    }

    @Override
    public void clear() {
        set.clear();
    }
    // 생략 
}
```
- 동일한 메서드를 호출하기 위해 추가된 메서드를 **포워딩 메서드** 라 부름
- 포워딩은 기존 클래스의 인터페이스를 그대로 외부에 제공하면서 구현에 대한 결합 없이 일부 작동 방식을 변경하고 싶은 경우 유용한 기법

## 부모 클래스와 자식 클래스의 동시 수정 문제: PersonalPlaylist

- Playlist의 경우 합성으로 사용해도 가수별 노래 목록을 유지하기 위해 Playlist와 PersonalPlaylist를 함께 수정해야 하는 문제가 해결되지 않음
```
public class PersonalPlaylist {
    private Playlist playlist = new Playlist();

    public void append(Song song) {
        playlist.append(song);
    }

    public void remove(Song song) {
        playlist.getTracks().remove(song);
        playlist.getSingers().remove(song.getSinger());
    }
}
```
- 그래도 상속보다 합성을 사용하는게 좋은데, 향후에 Playlist의 내부 구현을 변경하더라도 파급효과를 최대한 PersonalPlaylist 내부로 캡슐화 할 수 있음

### 몽키 패치
- 몽키 패치란 현재 실행 중인 환경에만 영향을 미치도록 지역적으로 코드를 수정하거나 확장하는 것을 가리킴
- 자바는 언어 차원에서 몽키 패치를 미지원. 때문에 바이트코드를 직접 변환하거나 AOP를 이용해 몽키 패치를 구현하고 있음

# 02 상속으로 인한 조합의 폭발적인 증가
- 상속에서 작은 기능들을 조합해서 더 큰 기능을 수행하는 객체를 만들때 발생하는 문제점
  - 하나의 기능을 추가하거나 수정하기 위해 불필요하게 많은 수의 클래스를 추가하거나 수정해야 한다.
  - 단일 상속만 지원하는 언어에서는 상속으로 인해 오히려 중복 코드의 양이 늘어날 수 있다.

## 기본 정책과 부가 정책 조합하기
- 기본 정책과 부가 정책의 종류
![11_1](https://user-images.githubusercontent.com/7076334/113480309-b5405c80-94ce-11eb-8661-6b1ab9f876d4.png)

- 기본 정책은 가입자의 통화 정보를 기반
- 부가 정책은 통화량과 무관하게 기본 정책에 선택적으로 추가할 수 있는 요금 방식 (10장에서 taxRate)
 
- 부가 정책 특성
  - 기본 정책의 계싼 결과에 적용된다
  - 선택적으로 적용할 수 있다
  - 조합 가능하다
  - 부가 정책은 임의의 순서로 적용 가능하다

- 조합 가능한 모든 요금 계산 순서

![11_2](https://user-images.githubusercontent.com/7076334/113480311-b83b4d00-94ce-11eb-9e36-420d2bc8c8ee.png)


## 상속을 이용해서 기본 정책 구현하기
- P.355 (10장에서 사용한 추상 클래스 상속 구조)
  - 부가 정책 없이 오직 기본 정책만으로 요금을 계산

## 기본 정책에 세금 정책 조합하기
- P.357

- 세금 정책을 조합하기 위해 가장 간단한 방법은 RegularPhone 클래스를 상속받은 TaxableRegularPhone 클래스 추가

```
public class TaxableRegularPhone extends RegularPhone {
    private double taxRate;

    public TaxableRegularPhone(Money amount, Duration seconds, double taxRate) {
        super(amount, seconds);
        this.taxRate = taxRate;
    }

    @Override
    public Money calculateFee() {
        Money fee = super.calculateFee(); // 자식 클래스와 부모 클래스 사이의 결합도가 높아진다.
        return fee.plus(fee.times(taxRate));
    }
}
```
- 부모 클래스의 메서드를 재사용하기 위해 super 호출을 사용하면 원하는 결과를 쉽게 얻을 수 있지만 자식 클래스와 부모 클래스 사이의 결합도가 높아짐

- 부모 클래스에서 정의한 추상 메서드를 호출하고 자식 클래스가 이 메서드를 오버라이딩 하도록 제공하면 결합도를 느슨하게 만들 수 있음
```
public abstract class Phone {
    // 생략
        
    abstract protected Money afterCalculated(Money fee);
}

public class RegularPhone extends Phone {
    // 생략
    
    @Override
    protected Money afterCalculated(Money fee) {
        return fee;
    }
}

public class NightlyDiscountPhone extends Phone {
    // 생략
    
    @Override
    protected Money afterCalculated(Money fee) {
        return fee;
    }
}
```
- 부모 클래스에 추상 메서드를 추가하면 모든 자식 클래스들이 추상 메서드를 오버라이딩해야 하는 문제점이 발생

- 유연성을 유지하면서 중복 코드를 제거할 수 있는 방법은 Phone에서 afterCalculated에 대한 기본 구현을 함께 제공

### 추상 메서드와 훅 메서드
- 추상 메서드와 동일하게 자식 클래스에서 오버라이딩할 의도로 메서드를 추가했지만 편의를 위해 기본 구현을 제공하는 메서드를 훅 메서드(hook method)라 부름

- P.360
  - TaxableRegularPhone, TaxableNightlyDiscountPhone 세금을 부과하기 위해 클래스 추가

![11_3](https://user-images.githubusercontent.com/7076334/113481432-7e6d4500-94d4-11eb-98a6-0fc6e74e2aad.png)
- RegularPhone :  일반 요금제
- TaxableRegularPhone : 일반 요금제에 세금 정책 조합
- NightlyDiscountPhone : 심야 할인 요금제
- TaxableNightlyDiscountPhone : 심야 할인 요금제 세금 정책 조합

- 문제점
  - TaxableRegularPhone, TaxableNightlyDiscountPhone 사이에 중복 코드 발생
  - 객체지향 언어는 단일 상속만 지원하기 때문에 상속으로 인해 발생하는 중복을 해결하기 어려움


## 기본 정책에 기본 요금 할인 정책 조합하기
- P.362
  - RateDiscountableRegularPhone, RateDiscountableNightlyDiscountPhone 요금 할인 정책을 위해 클래스 추가

![11_4](https://user-images.githubusercontent.com/7076334/113481436-8200cc00-94d4-11eb-86ea-840928e7b9d2.png)

- 문제점
  - 이번에도 RateDiscountableRegularPhone, RateDiscountableNightlyDiscountPhone 사이에 중복 코드 발생

## 중복 코드의 덫에 걸리다
- 만약 일반 요금제의 계산 결과에 세금 정책을 조합한 후 기본 요금 할인 정책을 추가 하고 싶다면?
  - TaxableNightlyDiscountPhone를 상속 받는 TaxableAndDiscountableNightlyDiscountPhone 을 추가해야됨

- 현재까지 구현된 상속 계층 구조

![11_5](https://user-images.githubusercontent.com/7076334/113481783-8d54f700-94d6-11eb-84ab-e2fc5dc43e07.png)

- 문제점
  - 복잡함
  - 바로 새로운 정책을 추가하기 어려움
  - 새로운 정책을 추가하기 위해서 불필요하게 많은 수의 클래스를 상속 계층 안에 추가해야됨 

- 고정 요금제 (FixedRatePhone)을 추가해야 된다면?

![11_6](https://user-images.githubusercontent.com/7076334/113481846-ddcc5480-94d6-11eb-8250-12c0d566548e.jpeg)
- 고정 요금제 하나를 추가하기 위해 5가지 새로운 클래스를 추가함

- 상속의 남용으로 하나의 기능을 추가하기 위해 필요 이상으로 많은 수의 클래스를 추가 해야 하는 경우를 가리켜 **클래스 폭발** 이라고 부름
- 클래스 폭발 문제는 자식 클래스가 부모 클래스의 구현에 강하게 결합되도록 강요하는 상속의 근본적인 한계 때문에 발생하는 문제
- 클래스 폭발은 새로운 기능 추가 뿐만 아니라 수정할 때도 문제가 됨
  - 하나라도 누락된다면 버그 발생

# 03 합성 관계로 변경하기
- 합성은 컴파일타임 관계를 런타임 관계로 변경함으로써 클래스 폭발 해결
- 합성을 사용하면 구현이 아닌 퍼블릭 인터페이스에 대해서만 의존할 수 있기 때문에 런타임에 객체의 관계를 변경할 수 있음

- 합성을 사용하면 구현 시점에 정책들의 관계를 고정시킬 필요가 없으며 실ㄹ행 시점에 정책들의 관계를 유연하게 변경할 수 있음
- 상속이 조합의 결과를 개별 클래스 안으로 밀어 넣는 방법이라면 합성은 조합을 구성하는 요소들을 개별 클래스로 구현한 후 실행 시점에 인스턴스를 조립하는 방법을 사용하는 것

## 기본 정책 합성하기
- 각 정책별을 별도의 클래스로 구현
  - 핸드폰과 요금 계산 방법 개념 분리

- P.369
- 합성 관계를 사용한 기본 정책의 정체적인 구조

![11_7](https://user-images.githubusercontent.com/7076334/113483392-29cec780-94de-11eb-9992-b27e3a24ae0f.png)

```
// 일반 요금제 규칙
Phone phone = new Phone(new RegularPolicy(Money.wons(10), Duration.ofSeconds(10)));

// 심야 할인 요금제
Phone phone2 = new Phone(new NightlyDiscountPolicy(Money.wons(5), Money.wons(10), Duration.ofSeconds(10)));
```
- 일반 요금제 사용하고 싶으면 Phone과 RegularPolicy의 인스턴스 합성
- 심야 할인 요금제 사용하고 싶으면 Phone과 NightlyDiscountPolicy의 인스턴스 합성
- 합성을 사용하면 Phone과 연결된 RatePolicy 인터페이스의 구현 클래스가 어떤 타입인지에 따라 요금을 계산 하는 방식이 달라짐

## 부가 정책 적용하기
- 컴파일 시점의 Phone 클래스와 RatePolicy 인터페이스 사이의 관계가 런타임에 Pone 인스턴스와 RegularPolicy 인스턴스 사이의 관계로 대체
![11_8](https://user-images.githubusercontent.com/7076334/113483396-2d624e80-94de-11eb-9b7b-2f113643aac2.png)


- 부가 정책을 추가
  - 부가 정책은 기본 정책에 대한 계산이 끝난 후 적용
  - RegularPolicy와 Phone 사이에 세금 정책을 구현하는 TaxablePolicy 인스턴스를 연결
![11_9](https://user-images.githubusercontent.com/7076334/113483400-2dfae500-94de-11eb-8412-bbf35ccff16e.png)

- 일반 요금제에 기본 요금 할인 정책을 적용한 후에 세금 정책을 적용한다면? (아래와 같은 순서로 연결)
![11_10](https://user-images.githubusercontent.com/7076334/113483403-2e937b80-94de-11eb-95f1-cb68b948738c.png)

- 그림 11.9와 그림 11.10은 두 가지 제약에 따라 부가 정책을 구현해야 함
  -  부가 정책은 기본 정책이나 다른 부가 정책의 인스턴스를 참조할 수 있어야 한다. 다시 말해서 부가 정책의 인스턴스는 어떤 종류의 정책과도 합성될 수 있어야 한다.
  -  Phone의 입장에서는 자신이 기본 정책ㅊ의 인스턴스에게 메시지를 전송하고 있는지, 부가 정책의 신스턴스에게 메시지를 전송하고 있는지를 몰라야 한다.
  -  다시 말해서 기본 정책과 부가 정책은 협력 안에서 동일한 '역할'을 수행해야 한다. 이것은 부가 정책이 기본 정책과 동일한 RatePolicy 인터페이스를 구현해야 한다는 것을 의미한다.

- P.374
- 모든 요금 계산과 관련된 모든 클래스 사이의 관계를 다이어그램으로 표현
![11_11](https://user-images.githubusercontent.com/7076334/113484228-22112200-94e2-11eb-8bd2-b515a615e290.png)

## 기본 정책과 부가 정책 합성하기
- 원하는 정책의 인스턴스를 생성한 후 의존성 주입을 통해 다른 정책의 인스턴스에 전달

```
// 일반 요금제에 세금 정책을 조합
phone = new Phone(new TaxablePolicy(0.05, new RegularPolicy(Money.wons(10), Duration.ofSeconds(10))));

// 일반 요금제에 기본 요금 할인 정책을 조합한 결과에 세금 정책을 조합
phone = new Phone(new TaxablePolicy(0.05, new RateDiscountablePolicy(Money.wons(1000),
                                                                     new RegularPolicy(Money.wons(10),
                                                                                               Duration.ofSeconds(10)))));

// 세금 정책과 기본 요금 할인 정책 적용 순서 변경
phone = new Phone(new RateDiscountablePolicy(Money.wons(1000), new TaxablePolicy(0.05,new RegularPolicy(
                                                                                 Money.wons(10),Duration.ofSeconds(10)))));
                                                                                 
// 동일한 정책으로 심야 할인 요금제도 적용
phone = new Phone(new RateDiscountablePolicy(Money.wons(1000), new TaxablePolicy(0.05, 
                                                               new NightlyDiscountPolicy(Money.wons(5), Money.wons(10), Duration.ofSeconds(10)))));

```

- 객체를 조합하고 사용하는 방식이 상속을 사용한 방식보다 더 예측 가능하고 일관성이 있따는 사실을 알게 될 것이다.

## 새로운 정책 추가하기
- 고정 요금제가 필요하다면 고정 요금제를 구현한 클래스 '하나' 만 추가 후 원하는 방식으로 조합
![11_12](https://user-images.githubusercontent.com/7076334/113484231-250c1280-94e2-11eb-9842-31ae72dec54a.png)

- 약정 할인 정책이라는 새로운 부가 정책이 필요한 경우? 클래스 '하나'만 추가 하면 됨
![11_13](https://user-images.githubusercontent.com/7076334/113484235-25a4a900-94e2-11eb-840f-2f8b72cde432.png)

- 오직 하나의 클래스만 추가하고 런타임에 필요한 정책들을 조합해서 원하는 기능을 얻을 수 있음
- 요구사항을 변경할 때 오직 하나의 클래스만 수정해도 됨 (단일 책임 원칙을 준수)

## 객체 합성이 클래스 상속보다 더 좋은 방법이다
- 상속은 부모 클래스의 세부적인 구현에 자식 클래스를 강하게 결합 시키기 때문에 코드의 진화를 방해
- 코드를 재사용하면서 건전한 결합도를 유지할 수 있는 더 좋은 방법은 합성을 이용

- 상속을 사용해야 되는 경우는?
  - 구현 상속과 인터페이스 상속 두 가지로 나눠야 함
  - 이번 장에서 살펴본 상속에 대한 모든 단점들은 구현 상속에 국한된다는 점  

# 04 믹스인
- 우리가 원하는 것은 코드를 재사용하면서도 납득할 만한 결합도를 유지하는 것

- **믹스인**은 객체를 생성할 때 코드 일부를 클래스 안에 섞어 넣어 재사용하는 기법을 가리키는 용어
- 합성이 실행 시점에 객체를 조합하는 재사용 방법이라면 믹스인은 컴파일 시점에 필요한 코드 조각을 조합하는 재사용 방법

## 기본 정책 구현하기
- P.380 부터 예제들
  - 스칼라는 trait 를 통해서 런타임 시점에 믹스인 시킬 수 있음
  - 스칼라는 trait 를 클래스나 다른 trait에 믹스인할 수 있도록 extends와 with 키워드 제공
  
## 쌓을 수 있는 변경
- 믹스인을 추상 서브클래스(abstract subclass)라고 부르기도 함
- 믹스인을 사용하면 특정 클래스에 대한 변경 또는 확장을 독립적으로 구현한 후 필요한 시점에 차례대로 추가할 수 있음
  - 이러한 특징을 **쌓을 수 있는 변경** 이라고 부름










