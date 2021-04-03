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
  - 





