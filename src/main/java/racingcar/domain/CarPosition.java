package racingcar.domain;

import java.util.Objects;

import static racingcar.error.ErrorMessage.NEGATIVE_POSITION;

public class CarPosition implements Comparable<CarPosition> {
    private static final int INITIAL_POSITION = 0;
    private static final int MINIMUM_POSITION = 0;

    private final int position;

    private CarPosition(final int position) {
        validatePositionRange(position);
        this.position = position;
    }

    public static CarPosition initialState() {
        return new CarPosition(INITIAL_POSITION);
    }

    private void validatePositionRange(final int position) {
        if (position < MINIMUM_POSITION) {
            throw new IllegalStateException(NEGATIVE_POSITION.message());
        }
    }

    public CarPosition moveNext() {
        return new CarPosition(this.position + 1);
    }

    public int getPosition() {
        return position;
    }

    @Override
    public int compareTo(final CarPosition o) {
        return this.position - o.getPosition();
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CarPosition carPosition1 = (CarPosition) o;
        return position == carPosition1.position;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(position);
    }
}
