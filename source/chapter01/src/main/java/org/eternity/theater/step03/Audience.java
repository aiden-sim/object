package org.eternity.theater.step03;

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

    public Long buy(Ticket ticket) {
        return bag.hold(ticket);
    }
}
