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






