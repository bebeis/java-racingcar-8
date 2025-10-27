package racingcar.model;

import racingcar.domain.car.Car;
import racingcar.domain.car.CarName;
import racingcar.domain.car.Cars;
import racingcar.domain.car.CarsRepository;
import racingcar.domain.strategy.MoveStrategy;

import java.util.List;

public class RacingGameModelImpl implements RacingGameModel {
    private final MoveStrategy moveStrategy;
    private final CarsRepository carsRepository;

    public RacingGameModelImpl(final MoveStrategy moveStrategy, final CarsRepository carsRepository) {
        this.moveStrategy = moveStrategy;
        this.carsRepository = carsRepository;
    }

    @Override
    public void prepareCars(final List<String> carNames) {
        List<Car> carList = carNames.stream()
                .map(CarName::new)
                .map(Car::new)
                .toList();

        Cars cars = new Cars(carList);
        carsRepository.save(cars);
    }

    @Override
    public void playRound() {
        Cars cars = carsRepository.findCars();
        cars.moveAll(moveStrategy);
    }

    @Override
    public List<CarStatusData> getCurrentCarStatuses() {
        Cars cars = carsRepository.findCars();
        return cars.getCars().stream()
                .map(car -> new CarStatusData(car.nameValue(), car.positionValue()))
                .toList();
    }

    @Override
    public List<String> getWinnerNames() {
        Cars cars = carsRepository.findCars();
        List<Car> winners = cars.determineWinners();
        return winners.stream()
                .map(Car::nameValue)
                .toList();
    }
}

