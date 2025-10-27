package racingcar.domain.car;

public interface CarsRepository {

    void save(Cars cars);

    Cars findCars();
}
