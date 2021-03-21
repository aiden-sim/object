package movie.step03;

import money.Money;

public class Reservation {
    private Customer customer;
    private movie.step03.Screening Screening;
    private Money fee;
    private int audienceCount;

    public Reservation(Customer customer, movie.step03.Screening Screening, Money fee, int audienceCount) {
        this.customer = customer;
        this.Screening = Screening;
        this.fee = fee;
        this.audienceCount = audienceCount;
    }
}
