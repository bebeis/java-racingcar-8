package racingcar.domain;

import racingcar.strategy.MoveStrategy;

import java.util.List;
import java.util.TreeMap;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
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

    public List<Car> getWinners() {
        return cars.stream()
                .collect(groupingBy(Car::positionValue, TreeMap::new, toList()))
                .lastEntry()
                .getValue();
    }

    public List<Car> getCars() {
        return List.copyOf(cars);
    }
}
