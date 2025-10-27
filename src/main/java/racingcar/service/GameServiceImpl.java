package racingcar.service;

import racingcar.domain.car.Car;
import racingcar.domain.car.CarName;
import racingcar.domain.car.Cars;
import racingcar.domain.car.CarsRepository;
import racingcar.domain.round.Round;
import racingcar.domain.strategy.MoveStrategy;
import racingcar.service.dto.CarStatus;
import racingcar.service.dto.RoundSnapShot;

import java.util.ArrayList;
import java.util.List;

public class GameServiceImpl implements GameService {
    private MoveStrategy strategy;
    private CarsRepository repository;

    public GameServiceImpl(final MoveStrategy strategy, final CarsRepository repository) {
        this.strategy = strategy;
        this.repository = repository;
    }

    @Override
    public void prepareCars(final List<String> carNames) {
        List<Car> carList = carNames.stream()
                .map(CarName::new)
                .map(Car::new)
                .toList();

        Cars cars = new Cars(carList);
        repository.save(cars);
    }

    @Override
    public List<RoundSnapShot> playRounds(final int tryCount) {
        Cars cars = repository.findCars();

        List<RoundSnapShot> snapShots = new ArrayList<>();
        Round currentRound = Round.from(tryCount);
        while (!currentRound.isFinished()) {
            cars.moveAll(strategy);
            snapShots.add(RoundSnapShot.from(cars));
            currentRound = currentRound.next();
        }

        return snapShots;
    }

    @Override
    public List<CarStatus> getWinners() {
        List<Car> cars = repository.findCars().determineWinners();
        return cars.stream()
                .map(CarStatus::from)
                .toList();
    }
}
