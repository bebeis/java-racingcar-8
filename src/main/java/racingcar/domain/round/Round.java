package racingcar.domain.round;

import static racingcar.error.ErrorMessage.CANNOT_PROCEED_WHEN_FINISHED;
import static racingcar.error.ErrorMessage.NON_POSITIVE_TRY_COUNT;

public class Round {
    private static final int MINIMUM_TRY_COUNT = 1;
    private static final int MINIMUM_REMAINING_COUNT = 0;

    private final int remainingCount;

    private Round(final int remainingCount) {
        validateRemainingCountRange(remainingCount);
        this.remainingCount = remainingCount;
    }

    private void validateRemainingCountRange(final int remainingCount) {
        if (remainingCount < MINIMUM_REMAINING_COUNT) {
            throw new IllegalStateException(CANNOT_PROCEED_WHEN_FINISHED.message());
        }
    }

    public static Round from(final int tryCount) {
        validateTryCountRange(tryCount);
        return new Round(tryCount);
    }

    private static void validateTryCountRange(final int tryCount) {
        if (tryCount < MINIMUM_TRY_COUNT) {
            throw new IllegalArgumentException(NON_POSITIVE_TRY_COUNT.message());
        }
    }

    public Round next() {
        return new Round(remainingCount - 1);
    }

    public int getValue() {
        return remainingCount;
    }

    public boolean isFinished() {
        return remainingCount == MINIMUM_REMAINING_COUNT;
    }
}
