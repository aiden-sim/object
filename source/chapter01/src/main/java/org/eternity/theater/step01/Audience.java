package org.eternity.theater.step01;

/**
 * 관람객
 */
public class Audience {
    // 소지품을 위한 가방 소지
    private Bag bag;

    public Audience(Bag bag) {
        this.bag = bag;
    }

    public Bag getBag() {
        return bag;
    }
}
