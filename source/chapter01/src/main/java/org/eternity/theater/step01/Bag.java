package org.eternity.theater.step01;

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

    // 초대장 소유 여부
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
