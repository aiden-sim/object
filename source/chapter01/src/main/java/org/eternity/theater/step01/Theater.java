package org.eternity.theater.step01;

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
