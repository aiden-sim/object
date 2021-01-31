package org.eternity.movie.step01;

import org.eternity.money.Money;

public class Reservation {

    private Customer customer;
    private Screening screening;
    private Money free;
    private int audienceCount;

    public Reservation(Customer customer, Screening screening, Money free, int audienceCount) {
        this.customer = customer;
        this.screening = screening;
        this.free = free;
        this.audienceCount = audienceCount;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Screening getScreening() {
        return screening;
    }

    public void setScreening(Screening screening) {
        this.screening = screening;
    }

    public Money getFree() {
        return free;
    }

    public void setFree(Money free) {
        this.free = free;
    }

    public int getAudienceCount() {
        return audienceCount;
    }

    public void setAudienceCount(int audienceCount) {
        this.audienceCount = audienceCount;
    }
}
