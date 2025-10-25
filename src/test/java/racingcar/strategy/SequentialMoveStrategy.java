package racingcar.strategy;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class SequentialMoveStrategy implements MoveStrategy {
    private final Queue<Boolean> moveSequence;

    public SequentialMoveStrategy(Boolean... sequence) {
        this.moveSequence = new LinkedList<>(Arrays.asList(sequence));
    }

    @Override
    public boolean canMove() {
        if (moveSequence.isEmpty()) {
            throw new IllegalStateException("더 이상 정의된 이동 시퀀스가 없습니다.");
        }
        return moveSequence.poll();
    }
}
