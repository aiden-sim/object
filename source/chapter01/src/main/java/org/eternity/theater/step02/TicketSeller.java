package org.eternity.theater.step02;

/**
 * 판매원 (매표소에서 초대장을 티켓으로 교환해 주거나 티켓을 판매 역할)
 */
public class TicketSeller {
    // 매표소가 아니라 은행에 돈을 보관하도록 만들고 싶다면? TicketSeller 내부만 변경하면 된다.
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
        Long buyAmount = audience.buy(ticketOffice.getTicket());
        ticketOffice.plusAmount(buyAmount);
    }
}
