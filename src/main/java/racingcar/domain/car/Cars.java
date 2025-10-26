package racingcar.domain.car;

import racingcar.domain.strategy.MoveStrategy;

import java.util.List;

import static java.util.Comparator.naturalOrder;
import static racingcar.error.ErrorMessage.EMPTY_CAR_LIST;

public class Cars {
    private final List<Car> cars;

    public Cars(final List<Car> cars) {
        validateNotEmpty(cars);
        this.cars = List.copyOf(cars);
    }

    private void validateNotEmpty(final List<Car> cars) {
        if (cars == null || cars.isEmpty()) {
            throw new IllegalArgumentException(EMPTY_CAR_LIST.message());
        }
    }

    public void moveAll(final MoveStrategy strategy) {
        cars.forEach(car -> car.moveDeterminedBy(strategy));
    }

    public List<Car> determineWinners() {
        Car winner = cars.stream()
                .max(naturalOrder())
                .orElseThrow(() -> new IllegalStateException(EMPTY_CAR_LIST.message()));

        return cars.stream()
                .filter(winner::isSamePosition)
                .toList();
    }

    public List<Car> getCars() {
        return List.copyOf(cars);
    }
}
