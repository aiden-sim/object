package org.eternity.theater.step03;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    // TicketSeller sellTo 로직을 옮겨옴
    public void sellTicketTo(Audience audience) {
        Long buyAmount = audience.buy(getTicket());
        plusAmount(buyAmount);
    }

    // 편의를 위해 첫번째 티켓 반환
    private Ticket getTicket() {
        return tickets.remove(0);
    }

    private void plusAmount(Long amount) {
        this.amount += amount;
    }

    public void minusAmount(Long amount) {
        this.amount -= amount;
    }

}
