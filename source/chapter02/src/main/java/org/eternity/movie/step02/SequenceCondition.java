package org.eternity.movie.step02;

public class SequenceCondition implements DiscountCondition {
    private int sequence; // 할인 여부를 판단하기 위해 사용할 순번

    public SequenceCondition(int sequence) {
        this.sequence = sequence;
    }

    /**
     * screening의 상영 순번과 일치할 경우 할인 가능
     */
    @Override
    public boolean isSatisfiedBy(Screening screening) {
        return screening.isSequence(sequence);
    }
}
