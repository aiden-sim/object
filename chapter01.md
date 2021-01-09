# 객체, 설계
- 이론이 먼저일까, 실무가 먼저일까?
  - 글래스의 결론은 이론보다 실무가 먼저
  
- 소프트웨어 분야는 아직 걸음마 단계기 때문에 이론보다 실무가 더 앞서 있으며 실무가 더 중요
  - 예를 들어 소프트웨어 설계, 소프트웨어 유지보수
    - 실무는 훌륭한 소프트웨어를 설계하기 위해 필요한 다양한 기법과 도구를 초리부터 성공적으로 적용하고 발전시켜 왔다.

- 결론적으로 소프트웨어 설계와 유지보수에 중점을 두려면 이론이 아닌 실무에 초점을 맞추는 것이 효과적

- 이 책은 훌륭한 객체지향 프로그램을 설계하고 유지보수하는데 필요한 원칙과 기법을 설명하기 위해 쓰여졌다.
  - 객체지향 패러다임을 설명하기 위해 추상적인 개념이나 이론보다는 코드를 이용해 다양한 측면을 설명하려고 노력할 것
  
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

```java
/**
 * 관람객의 소지품 보관
 */
public class Bag {
    private Long amount;
    private Invitation invitation;
    private Ticket ticket;

    // 현금만 있다.
    public Bag(long amount) {
        this(null, amount);
    }

    // 초대장과 현금이 있다.
    public Bag(Invitation invitation, long amount) {
        this.invitation = invitation;
        this.amount = amount;
    }

    // 초장 소유 여부
    public boolean hashInvitation() {
        return invitation != null;
    }

    // 티켓 소유 여부
    public boolean hasTicket() {
        return ticket != null;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public void minusAmount(Long amount) {
        this.amount -= amount;
    }

    public void plusAmount(Long amount) {
        this.amount += amount;
    }
}
```


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

```java
/**
 * 매표소 (초대장을 티켓으로 교환하거나 구매)
 */
public class TicketOffice {
    private Long amount;
    private List<Ticket> tickets = new ArrayList<>();

    public TicketOffice(Long amount, Ticket... tickets) {
        this.amount = amount;
        this.tickets.addAll(Arrays.asList(tickets));
    }

    // 편의를 위해 첫번째 티켓 반환
    public Ticket getTicket() {
        return tickets.remove(0);
    }

    public void minusAmount(Long amount) {
        this.amount -= amount;
    }

    public void plusAmount(Long amount) {
        this.amount += amount;
    }
}
```

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
  - 하나의 콜래스나 메서드에서 너무 많은 세부사항을 다루기 때문에 코드를 작성하는 사람뿐만 아니라 코드를 읽고 이해해야 하는 사람 모두에게 큰 부담
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

- 객체 사이의 의존성이 과한 경우를 가리켜 결합도(coupling)가 높다고 한다.
  - 변경하기 어려워 진다.
- 반대로 객체들이 합리적인 수준으로 의존할 경우에는 결합도가 낮다고 말한다.





  
  


  
