package org.eternity.theater.step02;

/**
 * 관람객
 */
public class Audience {
    // 소지품을 위한 가방 소지
    // 만약 가방이 아닌 지갑으로 변경한다고 하면? Audience 내부만 변경하면 된다.
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
