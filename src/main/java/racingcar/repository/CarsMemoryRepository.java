package racingcar.repository;

import racingcar.domain.car.Cars;

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
