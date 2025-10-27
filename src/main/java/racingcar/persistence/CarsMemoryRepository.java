package racingcar.persistence;

import racingcar.domain.car.Cars;
import racingcar.domain.car.CarsRepository;

public class CarsMemoryRepository implements CarsRepository {
    private Cars cars;

    @Override
    public void save(final Cars cars) {
        this.cars = cars;
    }

    @Override
    public Cars findCars() {
        return cars;
    }
}
