package org.eternity.theater.step02;

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
