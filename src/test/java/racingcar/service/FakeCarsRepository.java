package racingcar.service;

import racingcar.domain.car.Cars;
import racingcar.domain.car.CarsRepository;

public class FakeCarsRepository implements CarsRepository {

    private Cars cars;

    public void save(Cars cars) {
        this.cars = cars;
    }

    public Cars findCars() {
        return cars;
    }
}

