package racingcar.domain.car;

import racingcar.domain.strategy.MoveStrategy;

public class Car implements Comparable<Car> {

    private final CarName name;
    private CarPosition position;

    public Car(final CarName name) {
        this.name = name;
        position = CarPosition.initialState();
    }

    public void moveDeterminedBy(MoveStrategy strategy) {
        if (strategy.canMove()) {
            position = position.moveNext();
        }
    }

    public boolean isSamePosition(final Car other) {
        return this.position.equals(other.position);
    }

    public int positionValue() {
        return position.value();
    }

    public String nameValue() {
        return name.value();
    }

    @Override
    public int compareTo(final Car other) {
        return this.position.compareTo(other.position);
    }
}
