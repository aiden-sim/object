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
