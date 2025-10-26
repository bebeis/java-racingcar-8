package racingcar.domain.strategy;

import static camp.nextstep.edu.missionutils.Randoms.pickNumberInRange;

public class RandomMoveStrategy implements MoveStrategy {
    private static final int MIN_VALUE = 0;
    private static final int MAX_VALUE = 9;
    private static final int MOVE_THRESHOLD = 4;

    @Override
    public boolean canMove() {
        int randomNumber = pickNumberInRange(MIN_VALUE, MAX_VALUE);
        return randomNumber >= MOVE_THRESHOLD;
    }
}
