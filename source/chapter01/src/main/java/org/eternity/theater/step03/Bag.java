package org.eternity.theater.step03;

/**
 * 관람객의 소지품 보관
 */
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
