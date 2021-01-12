# 객체, 설계
- 이론이 먼저일까, 실무가 먼저일까?
  - 글래스의 결론은 이론보다 실무가 먼저
  
- 소프트웨어 분야는 아직 걸음마 단계기 때문에 이론보다 실무가 더 앞서 있으며 실무가 더 중요
  - 예를 들어 소프트웨어 설계, 소프트웨어 유지보수
    - 실무는 훌륭한 소프트웨어를 설계하기 위해 필요한 다양한 기법과 도구를 초리부터 성공적으로 적용하고 발전시켜 왔다.

- 결론적으로 소프트웨어 설계와 유지보수에 중점을 두려면 이론이 아닌 실무에 초점을 맞추는 것이 효과적

- 이 책은 훌륭한 객체지향 프로그램을 설계하고 유지보수하는데 필요한 원칙과 기법을 설명하기 위해 쓰여졌다.
  - 객체지향 패러다임을 설명하기 위해 추상적인 개념이나 이론보다는 코드를 이용해 다양한 측면을 설명하려고 노력할 것
  
- 추상적인 개념과 이론은 훌륭한 코드를 작성하는데 필요한 도구일 뿐이다.
  
# 01 티켓 판매 애플리케이션 구현하기
- 소극장에서 추첨을 통해 선정된 관람객에게 무료로 관람할 수 있는 초대장 발송
- 이벤트 당첨된 관람객은 초대장을 티켓으로 교환 후 입장
- 이벤트 미 당첨된 관람객은 티켓을 구매 후 입장

```java
/**
 * 초대장
 */
public class Invitation {
  private LocalDateTime when; // 초대일자
}
```

```java
/**
 * 티켓 (공연을 관람하기 위해 모든 사람들이 티켓을 소지)
 */
public class Ticket {
  private Long fee; // 요금
  
  public Long getFee() {
    reeturn fee;
  }
}
```

- 관람객이 가지고 올 수 있는 소지품은 초대장, 현금, 티켓 세 가지 뿐
- 관람객은 소지품을 보관할 용도로 가방을 들고 올 수 있다고 가정

```java
/**
 * 관람객의 소지품 보관
 */
public class Bag {
    private Long amount; // 현금
    private Invitation invitation; // 초대장
    private Ticket ticket; // 티켓

    // 현금만 있다.
    public Bag(long amount) {
        this(null, amount);
    }

    // 초대장과 현금이 있다.
    public Bag(Invitation invitation, long amount) {
        this.invitation = invitation;
        this.amount = amount;
    }

    // 초대장 소유 여부
    public boolean hashInvitation() {
        return invitation != null;
    }

    // 티켓 소유 여부
    public boolean hasTicket() {
        return ticket != null;
    }

    // 초대장을 티켓으로 교환
    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    // 현금을 증가 시키거나 감소
    public void minusAmount(Long amount) {
        this.amount -= amount;
    }

    public void plusAmount(Long amount) {
        this.amount += amount;
    }
}
```

- 관람객은 소지품을 보관하기 위해 가방을 소지할 수 있음

```java
/**
 * 관람객
 */
public class Audience {
    // 소지품을 위한 가방 소지
    private Bag bag;

    public Audience(Bag bag) {
        this.bag = bag;
    }

    public Bag getBag() {
        return bag;
    }
}
```

- 매표소에는 관람객에게 판매할 티켓과 티켓의 판매 금액이 보관돼 있어야 한다.
```java
/**
 * 매표소 (초대장을 티켓으로 교환하거나 구매)
 */
public class TicketOffice {
    private Long amount; // 판매 금액
    private List<Ticket> tickets = new ArrayList<>(); // 티켓의 목록

    public TicketOffice(Long amount, Ticket... tickets) {
        this.amount = amount;
        this.tickets.addAll(Arrays.asList(tickets));
    }

    // 편의를 위해 첫번째 티켓 반환
    public Ticket getTicket() {
        return tickets.remove(0);
    }

    // 판매 금액을 더하거나 차감
    public void minusAmount(Long amount) {
        this.amount -= amount;
    }

    public void plusAmount(Long amount) {
        this.amount += amount;
    }
}
```

- 판매원은 매표소에서 초대장을 티켓으로 교환해 주거나 티켓을 판매하는 역할을 수행
```java
/**
 * 판매원 (매표소에서 초대장을 티켓으로 교환해 주거나 티켓을 판매 역할)
 */
public class TicketSeller {
    private TicketOffice ticketOffice;

    public TicketSeller(TicketOffice ticketOffice) {
        this.ticketOffice = ticketOffice;
    }

    public TicketOffice getTicketOffice() {
        return ticketOffice;
    }
}
```

- 애플리케이션의 핵심 클래스
![object-1-1](https://user-images.githubusercontent.com/7076334/104099965-552b8700-52e0-11eb-8167-ce4570a7521c.png)

- 관람객의 가방 안에 초대장이 있는지 확인
- 초대장이 있으면 이벤트 당첨된 관람객이기 때문에 티켓을 발급
- 초대장이 없으면 티켓 판매 후 티켓을 발급
  - 이 경우 관람객의 가방에서 티켓 금액만큼 차감하고 매표소 금액은 증가
```java
/**
 * 소극장
 */
public class Theater {
    private TicketSeller ticketSeller;

    public Theater(TicketSeller ticketSeller) {
        this.ticketSeller = ticketSeller;
    }

    /**
     * 1) 초대장이 있으면 이벤트에 당첨된 관람객이므로 티켓을 발급
     * 2) 초대장이 없으면 티켓 판매 후 티켓을 발급
     */
    public void enter(Audience audience) {
        if (audience.getBag().hashInvitation()) {
            Ticket ticket = ticketSeller.getTicketOffice().getTicket();
            audience.getBag().setTicket(ticket);
        } else {
            Ticket ticket = ticketSeller.getTicketOffice().getTicket();
            audience.getBag().minusAmount(ticket.getFee());

            ticketSeller.getTicketOffice().plusAmount(ticket.getFee());
            audience.getBag().setTicket(ticket);
        }
    }
}
```

- 이 작은 프로그램은 몇 가지 문제점을 가지고 있다.

# 02 무엇이 문제인가?
- 클린 소프트웨어에서 모듈(크기와 상관 없이 클래스나 패키지, 라이브러리와 같이 프로그램을 구성하는 임의의 요소를 의미)이 가져야 하는 세 가지 기능
  - 모듈은 제대로 실행돼야 하고, 변경이 용이해야 하며, 이해하기 쉬워야 한다.

- 위의 코드는 변경 용이성과 읽는 사람과의 의사소통이라는 목적을 만족시키지 못했다.

## 예상을 빗나가는 코드
- 문제는 관람객과 판매원이 소극장의 통제를 받는 수동적인 존재라는 점
  - 관람객
    - 소극장이라는 제3자가 초대장을 확인하기 위해 가방을 마음대로 열어봄 ( audience.getBag().hashInvitation() )
  - 판매원 
    - 소극장이 허락도 없이 매표소에 보관중인 티켓과 현금에 마음대로 접근할 수 있다.
    - 티켓을 꺼내 관람객의 가방에 집어넣고 관람객에게서 받은 돈을 매표소에 적립하는 일은 여러분이 아닌 소극장이 수행한다.
  
- 이해 가능한 코드란 그 동작이 우리의 예상에서 크게 벗어나지 않는 코드. (안타깝게도 예제는 우리의 예상을 벗어남)

- 현실에서는? (능동적)
  - 관람객이 직접 자신의 가방에서 초대장을 꺼내 판매원에서 건넨다.
  - 티켓을 구매하는 관람객은 가방 안에서 돈을 직접 꺼내 판매원에게 지불한다.
  - 판매원은 매표소에 있는 티켓을 직접 꺼내 관람객에게 건네고 관람객에게서 직접 돈을 받아 매표소에 보관한다.
  
- 코드를 이해하기 어렵게 만드는 또 다른 이유는?
  - 하나의 클래스나 메서드에서 너무 많은 세부사항을 다루기 때문에 코드를 작성하는 사람뿐만 아니라 코드를 읽고 이해해야 하는 사람 모두에게 큰 부담
  - 가장 심각한 문제는 Audience와 TicketSeller를 변경할 경우 Theater도 함께 변경해야됨
  
## 변경에 취약한 코드
- 더 큰 문제는 변경에 취약하다.
  - 관람객이 가방을 들고 있다는 가정이 바뀐다면?
    - Audience 클래스에서 Bag을 제거해야 할 뿐만 아니라 Audience의 Bag에 직접 접근하는 Theater의 enter 메서드도 수정 필요
    - Theater는 지나치게 세부적인 사실에 의존해서 동작함
    
- 객체 사이의 의존성(dependency)과 관련된 문제다.
  - 의존성이라는 말 속에는 어떤 객체가 변경될 때 그 객체에 의존하는 다른 객체도 함께 변경될 수 있다는 사실이 내포
  
- 객체 사이의 의존성을 완전히 없애는 것이 정답일까?
  - 우리의 목표는 애플리케이션의 기능을 구현하는 데 필요한 최소한의 의존성만 유지하고 불필요한 의존성을 제거
  
- 너무 많은 클래스에 의존하는 Theater
![1-2](https://user-images.githubusercontent.com/7076334/104103858-cae72100-52e7-11eb-9f58-47b8d315594f.jpeg)

- 연관 (다른 객체의 참조를 가지는 필드)
  - TicketSeller
- 의존 (메서드 내에서 해당 클래스 생성, 사용, 리턴)
  - Audience, TicketOffice, Ticket

- 객체 사이의 의존성이 과한 경우를 가리켜 결합도(coupling)가 높다고 한다.
  - 변경하기 어려워 진다.
- 반대로 객체들이 합리적인 수준으로 의존할 경우에는 결합도가 낮다고 말한다.
- 설계의 목표는 객체 사이의 결합도를 낮춰 변경이 용이한 설계를 만드는 것이어야 한다.

# 03 설계 개선하기
- 이 코드는 기능은 제대로 수행하지만 이해하기 어렵고 변경하기 쉽지 않다. (결합도가 높다.)

- 코드를 이해하기 어려운 이유는 Theater가 관람객의 가방과 판매원의 매표소에 직접 접근하기 때문이다.
  - 관람객과 판매원이 수동적이 되었음
  - Audience와 TicketSeller에 결합된다는 것을 의미
  
- 해결법은?
  - Theater가 Audience와 TicketSeller에 관해 너무 세세한 부분까지 알지 못하도록 정보를 차단하면 된다.
  - 관람객이 가방을 가직조 있다는 사실과 판매원이 매표소에서 티켓을 판매 한다는 사실을 Theater가 알 필요 없다.
  - Theater가 알아야 하는 것은 관람객이 소극장에 입장하는 것 뿐이다.
  - 관람객과 판매원을 자율적인 존재(능동적)로 만들자

## 자율성을 높이자
- 첫 번째 단계는 Theater의 enter 메서드에서 TicketOffice에 접근하는 모든 코드를 TicketSeller 내부로 숨긴다.

```java
public class TicketSeller {
    private TicketOffice ticketOffice;

    public TicketSeller(TicketOffice ticketOffice) {
        this.ticketOffice = ticketOffice;
    }

    /* Theater에게 제공해 줄 필요가 없으니 삭제
    public TicketOffice getTicketOffice() {
        return ticketOffice;
    }
    */

    // Theater의 enter 로직을 옮겨옴
    public void sellTo(Audience audience) {
        if (audience.getBag().hashInvitation()) {
            Ticket ticket = ticketOffice.getTicket();
            audience.getBag().setTicket(ticket);
        } else {
            Ticket ticket = ticketOffice.getTicket();
            audience.getBag().minusAmount(ticket.getFee());

            ticketOffice.plusAmount(ticket.getFee());
            audience.getBag().setTicket(ticket);
        }
    }
}
```
- getTicketOffice 메서드가 제거 (외부에서 직접 접근할 수 없음)
- 이처럼 개념적이나 물리적으로 객체 내부의 세부적인 사항을 감추는 것을 캡슐화(encapsulation)라고 부른다.
  - 캡슐화의 목적은 변경하기 쉬운 객체를 만드는 것이다.
  - 객체와 객체 사이의 결합도를 낮출 수 있기 때문에 설계를 좀 더 쉽게 변경 가능
  
```java
public class Theater {
    private TicketSeller ticketSeller;

    public Theater(TicketSeller ticketSeller) {
        this.ticketSeller = ticketSeller;
    }

    /*
    public void enter(Audience audience) {
        if (audience.getBag().hasInvitation()) {
            Ticket ticket = ticketSeller.getTicketOffice().getTicket();
            audience.getBag().setTicket(ticket);
        } else {
            Ticket ticket = ticketSeller.getTicketOffice().getTicket();
            audience.getBag().minusAmount(ticket.getFee());
            ticketSeller.getTicketOffice().plusAmount(ticket.getFee());
            audience.getBag().setTicket(ticket);
        }
    }
    */

    // enter 로직을 TicketSeller로 이동함
    public void enter(Audience audience) {
        ticketSeller.sellTo(audience);
    }
}
```
- Theater 코드가 간단하게 변경됨
- Theater는 ticketOffice에 접근하지 않게됨
- Theater는 오직 TicketSeller의 인터페이스에만 의존한다.
- TicketSeller가 내부에 TicketOffice 인터페이스를 포함하고 있다는 사실은 구현(implementation)의 영역에 속한다.
- 객체를 인터페이스와 구현으로 나누고 인터페이스만을 공개하는 것은 객체 사이의 결합도를 낮추고 변경하기 쉬운 코드로 작성하기 위한 가장 기본적인 설계 원칙
  
- Theater의 결합도를 낮춘 설계
![object-1-4](https://user-images.githubusercontent.com/7076334/104104789-95ddcd00-52ed-11eb-891b-309db67b4021.png)
- Theater에서 TicketOffice, Bag, Ticket의 의존성이 제거됨

- TicketSeller 다음으로 Audience 캡슐화를 개선하자
  - 현재 Audience의 getBag 메서드를 호출해서 Bag 인스턴스에 접근 중
  - Bag 인스턴스에 접근하는 객체가 Theater에서 TicketSeller로 변경되었을 뿐 여전히 수동적인 존재
  
- Bag에 접근하는 모든 로직을 Audience 내부로 감추기 위해 buy메서드 추가 후, TicketSeller의 sellTo 메서드에서 getBag 메서드 접근 하는 부분을 이동
```java
public class Audience {
    // 소지품을 위한 가방 소지
    private Bag bag;

    public Audience(Bag bag) {
        this.bag = bag;
    }

    /* TicketSeller에게 제공해줄 필요가 없으니 삭제
    public Bag getBag() {
        return bag;
    }
    */

    // TicketSeller의 sellTo 로직을 옮겨옴
    public Long buy(Ticket ticket) {
        if (bag.hashInvitation()) {
            bag.setTicket(ticket);
            return 0L;
        } else {
            bag.setTicket(ticket);
            bag.minusAmount(ticket.getFee());
            return ticket.getFee();
        }
    }
}
```
- Audience는 자신의 가방 안에 초대장이 들어있는지 스스로 확인
  - Bag의 존재를 내부로 캡슐화


- TicketSeller가 Audience의 인터페이스에만 의존하도록 수정
```java
public class TicketSeller {
    private TicketOffice ticketOffice;

    public TicketSeller(TicketOffice ticketOffice) {
        this.ticketOffice = ticketOffice;
    }

    public void sellTo(Audience audience) {
        Long buyAmount = audience.buy(ticketOffice.getTicket());
        ticketOffice.plusAmount(buyAmount);
    }
}
```
- TicketSeller와 Audience 사이의 결합도가 낮아졌음
  - 즉 Audience의 구현을 수정하더라도 TicketSeller에는 영향이 없음
  
- 자율적인 Audience와 TicketSeller로 구성된 설계
![object-1-6](https://user-images.githubusercontent.com/7076334/104105357-c5420900-52f0-11eb-99f0-89d09b0416c8.png)
- TicketSeller에서 Bag의 의존성이 제거됨

## 무엇이 개선됐는가
- 모듈의 세가지 기능 (모듈은 제대로 실행돼야 하고, 변경이 용이해야 하며, 이해하기 쉬워야 한다.)
  - 변경 용이성과 의사소통은?
  
- 수정된 Audience와 TicketSeller는 자신이 가지고 있는 소지품을 스스로 관리
  - 코드를 읽는 사람과의 의사소통이라는 관점에서 확실히 개선됨
  
- Audience나 TicketSeller의 내부 구현을 변경하더라도 Theater를 함께 변경할 필요 없어짐

## 어떻게 한 것인가
- 자기 자신의 문제를 스스로 해결하도록 코드를 변경
  - 그 결과 코드는 변경이 용이하고 이해 가능하도록 수정
  
- 객체의 자율성을 높이는 방향으로 설계를 개선
  - 이해하기 쉽고 유연한 설계를 얻을 수 있었다.

## 캡화와 응집도
- 핵심은 객체 내부의 상태를 캡슐화하고 객체 간에 오직 메시지를 통해서만 상호작용하도록 만드는 것이다.
- 밀접하게 연관된 작업만을 수행하고 연관성 없는 작업을 다른 객체에게 위임하는 객체를 응집도가 높다고 말한다.
- 객체는 자신의 데이터를 스스로 처리하는 자율적인 존재여야 하며 그것이 객체의 응집도를 높이는 첫 걸음이다.

## 절차지향과 객체지향
- 기존 Theater 코드
  - 프로세스 : Theater의 enter 메서드
  - 데이터 : Audience, TicketSeller, Bag, TicketOffice
  - 이처럼 프로세스와 데이터를 별도의 모듈에 위치시키는 방식을 **절차적 프로그래밍** 이라고 부름
  
- 일반적으로 절차적 프로그래밍은 우리의 직관에 위배됨
  - 절차적 프로그래밍은 우리의 예상을 쉽게 벗어나기 때문에 코드를 읽는 사람과 원활하게 의사소통하지 못함
  
- 더 큰 문제는 절차적 프로그래밍의 세상에서는 데이터의 변경으로 인한 영향을 지연적으로 고립시키기 어려움
  - 변경은 버그를 부르고 벼그에 대한 두려움은 코드를 변경하기 어렵게 만듬
  
- 변경하기 쉬운 설계는 한 번에 하나의 클래스만 변경할 수 있는 설계
  - 절차적 프로그래밍은 프로세스가 필요한 모든 데이터에 의존해야 한다는 근본적인 문제 때문에 변경에 취약

- 데이터와 프로세스가 동일한 모듈 내부에 위치하도록 프로그래밍하는 방식을 **객체지향 프로그래밍** 이라고 부른다.

- 훌륭한 객체지향 설계의 핵심은 캡슐화를 이용해 의존성을 적절히 관리함으로써 객체 사이의 결합도를 낮추는 것
  - 일반적으로 객체지향이 절차지향에 비해 변경에 좀 더 유연하다고 말하는 이유가 바로 이것임

## 책임의 이동
- 두 방식 사이에 근본적인 차이를 만드는 것은 **책임의 이동** 이다.
- 그림 1.7
  - 책임이 Theater에 집중되어 있다.
- 그림 1.8
  - 제어 흐름이 각 객체에 적절하게 분산돼 있다. 하나의 기능을 완성하는 데 필요한 책임이 여러 객체에 걸쳐 분산돼 있음
  
- 변경 전 절차적 설계에서 Theater의 작업을 변경 후의 객체지향 설계에서는 각 객체가 자신이 맡은 일을 스스로 처리했음
  - 이것이 바로 **책임의 이동**이 의미하는 것
  
- 객체지향 설계에서 각 객체는 **자신을 스스로 책임** 진다.

- 사실 객체지향 설계의 핵심은 적절한 객체에 적절한 책임을 할당하는 것
  - 객체는 다른 객체와의 협력이라는 문맥 안에서 특정한 역할을 수행하는데 필요한 적절한 책임을 수행해야 함
  
- 요 약
  - 설계를 어렵게 만드는 것은 **의존성**
  - 해결 방법은 불필요한 의존성을 제거함으로써 객체 사이의 **결합도**를 낮추는 것
  - 예제에서 결합도를 낮추기 위해 선택한 방법으로 세부사항을 내부로 감춰 **캡슐화**
  - 캡슐화하는 것은 객체의 **자율성**을 높이고 **응집도** 높은 객체들의 공동체를 창조할 수 있게함


## 더 개선할 수 있다
- Audience는 자율적인 존재다. 하지만 Bag은 어떤가?
- Bag을 자율적인 존재로 바꿔보자

```java
public class Bag {
    private Long amount;
    private Ticket ticket;
    private Invitation invitation;

    // Audience buy 로직을 옮겨옴
    public Long hold(Ticket ticket) {
        if (hashInvitation()) {
            setTicket(ticket);
            return 0L;
        } else {
            setTicket(ticket);
            minusAmount(ticket.getFee());
            return ticket.getFee();
        }
    }

    private void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    // 초대장 소유 여부
    private boolean hashInvitation() {
        return invitation != null;
    }

    private void minusAmount(Long amount) {
        this.amount -= amount;
    }
}

public class Audience {
    public Long buy(Ticket ticket) {
        return bag.hold(ticket);
    }
}
```
- public 메서드였던 hasInvitation, minusAmount, setTicket 메서드들이 외부에서 사용되지 않기 때문에 private으로 변경됨
  - 기존 메서드들 유지한 이유는 코드의 중복을 제거하고 표현력을 높이기 위해서


- TicketSeller 역시 TicketOffice의 자유권을 침해한다.
- TicketOffice에 있는 Ticket을 마음대로 꺼내서 Audience에 팔고 받은 돈을 마음대로 TicketOffice에 넣고 있다.
```java
public class TicketOffice {
    private Long amount;
    private List<Ticket> tickets = new ArrayList<>();

    // TicketSeller sellTo 로직을 옮겨옴
    public void sellTicketTo(Audience audience) {
        Long buyAmount = audience.buy(getTicket());
        plusAmount(buyAmount);
    }

    private Ticket getTicket() {
        return tickets.remove(0);
    }

    private void plusAmount(Long amount) {
        this.amount += amount;
    }
}

public class TicketSeller {
    // Theater의 enter 로직을 옮겨옴
    public void sellTo(Audience audience) {
        ticketOffice.sellTicketTo(audience);
    }
}
```
- TicketOffice에 sellTicketTo 메서드를 추가하고 TicketSeller의 sellTo 메서드의 내부 코드를 옮기자
- getTicket, plusAmount 메서드는 TicketOffice 내부에서만 사용하기 때문에 private 으로 변경 가능
- TicketSeller가 TicketOffice의 구현이 아닌 인터페이스에만 의존 

- 만족스러운가?
  - TicketOffice와 Audience 사이에 의존성이 추가되었음
  - TicketOffice의 자율성은 높혔지만 전체 설계의 관점에서 결합도가 상승했음
  - 트레이드 오프 (자율성을 추가 하고 결합도를 높힐 것이냐, 자율성을 포기하고 결합도를 낮출 것이냐)
 
- 이 예제를 통해 깨달은점
  - 1) 어떤 기능을 설계하는 방법은 한 가지 이상일 수 있다.
  - 2) 동일한 긴으을 한 가지 이상의 방법으로 설계할 수 있기 때문에 결국 설계는 트레이드오프의 산물이다.
  - 어떤 경우도 모든 사람들을 만족시킬 수 있는 설계를 만들 수 없다.
  
- 설계는 균형의 예술이다. 훌륭한 설계는 적절한 트레이드오프의 결과물이라는 사실을 명심


## 그래, 거짓말이다!
- 현실에서 관람객과 판매자가 스스로 자신의 일을 처리 하기 때문에 (능동적) 코드에서도 스스로 자신을 책임져야 한다
- 하지만 극장, 가방, 판매소는? 실세계에서 자율적인 존재가 아니였지만 무생물 역시 자율적인 존재로 취급한 것이다.

- 현실에서 수동적인 존재도 객체지향의 세계에 들어오면 모든 것이 능동적이고 자율적인 존재로 바뀐다.
  - 이를 의인화 라고 부른다.
  
- 훌륭한 객체지향 설계란 소프트웨어를 구성하는 모든 객체들이 자율적으로 행동하는 설계를 가리킨다.
  - 이해하기 쉽고 변경하기 쉬운 코드를 작성하고 싶다면 차라리 한 편의 애니메이션을 단든다고 생각하라

- 느낀점 : 모든 필드에 대한 getter를 관행적으로 제공해줄 필요가 없구나

# 04 객체지향 설계
## 설계가 왜 필요한가?
```
설계란 코드를 배치하는 것이다.
```
- 설계는 코드를 작성하는 매 순간 코드를 어떻게 배치할 것인지를 결정하는 과정에서 나온다.
- 설계는 코드 작성의 일부이며 코드를 작성하지 않고서는 검증할 수 없다.

- 좋은 설계란 무엇인가?
  - 좋은 설계란 오늘 요구하는 기능을 온전히 수행하면서 내일의 변경을 매끄럽게 수용할 수 있는 설계다.
  
- 변경을 수용할 수 있는 설계가 중요한 이유는 요구사항이 항상 변경되기 때문
- 또한 코드를 변경할 때 버그가 추가될 가능성이 높기 때문

## 객체지향 설계
- 우리가 진정으로 원하는 것은 변경에 유연하게 대응할 수 있는 코드
- 객체지향 프로그래밍은 의존성을 효율적으로 통제할 수 있는 다양한 방법을 제공함으로써 요구사항 변경에 좀 더 수월하게 대응할 수 있는 가능성을 높혀줌

- 변경 가능한 코드란 이해하기 쉬운 코드

- 객체지향 패러다임은 여러분이 세상을 바라보는 방식대로 코드를 작성할 수 있게 돕는다.

- 훌륭한 객체지향 설계란 협력하는 객체 사이의 의존성을 적절하게 관리하는 설계다.



