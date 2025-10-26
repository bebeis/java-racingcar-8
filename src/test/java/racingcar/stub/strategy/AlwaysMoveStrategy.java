package racingcar.stub.strategy;

import racingcar.domain.strategy.MoveStrategy;

public class AlwaysMoveStrategy implements MoveStrategy {

    @Override
    public boolean canMove() {
        return true;
    }
}
