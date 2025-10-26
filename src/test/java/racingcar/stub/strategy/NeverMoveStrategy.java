package racingcar.stub.strategy;

import racingcar.domain.strategy.MoveStrategy;

public class NeverMoveStrategy implements MoveStrategy {

    @Override
    public boolean canMove() {
        return false;
    }
}
