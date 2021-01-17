package org.eternity.theater.step01;

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
