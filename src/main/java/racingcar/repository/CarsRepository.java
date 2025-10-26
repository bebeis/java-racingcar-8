package racingcar.repository;

import racingcar.domain.car.Cars;

public interface CarsRepository {

    void save(Cars cars);

    Cars findCars();
}
