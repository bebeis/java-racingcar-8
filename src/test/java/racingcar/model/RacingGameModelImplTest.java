package racingcar.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import racingcar.domain.car.Car;
import racingcar.domain.car.CarName;
import racingcar.domain.car.Cars;
import racingcar.domain.car.CarsRepository;
import racingcar.domain.strategy.MoveStrategy;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class RacingGameModelImplTest {

    @Nested
    @DisplayName("prepareCars 메서드는")
    class PrepareCarTest {
        private FakeCarsRepository repository;
        private RacingGameModel model;

        @BeforeEach
        void setUp() {
            repository = new FakeCarsRepository();
            model = new RacingGameModelImpl(() -> true, repository);
        }

        @Test
        @DisplayName("차량 이름 리스트로 차량들을 준비한다")
        void prepareCars() {
            List<String> carNames = Arrays.asList("pobi", "woni", "jun");

            model.prepareCars(carNames);

            Cars cars = repository.findCars();
            assertThat(cars.getCars()).hasSize(3);
        }

        @Test
        @DisplayName("준비된 차량들의 이름이 올바르다")
        void prepareCars_WithCorrectNames() {
            List<String> carNames = Arrays.asList("pobi", "woni");

            model.prepareCars(carNames);

            Cars cars = repository.findCars();
            List<String> savedNames = cars.getCars().stream()
                    .map(Car::nameValue)
                    .toList();
            assertThat(savedNames).containsExactly("pobi", "woni");
        }
    }

    @Nested
    @DisplayName("playRound 메서드는")
    class PlayRoundTest {
        private FakeCarsRepository repository;
        private StubMoveStrategy strategy;
        private RacingGameModel model;

        @BeforeEach
        void setUp() {
            repository = new FakeCarsRepository();
            strategy = new StubMoveStrategy();
            model = new RacingGameModelImpl(strategy, repository);
        }

        @Test
        @DisplayName("모든 차량을 이동시킨다")
        void playRound_MovesAllCars() {
            strategy.setCanMove(true);
            List<String> carNames = Arrays.asList("pobi", "woni");
            model.prepareCars(carNames);

            model.playRound();

            List<CarStatusData> statuses = model.getCurrentCarStatuses();
            assertThat(statuses).allMatch(status -> status.position() == 1);
        }

        @Test
        @DisplayName("이동 조건이 false면 차량이 이동하지 않는다")
        void playRound_DoesNotMoveWhenCannotMove() {
            strategy.setCanMove(false);
            List<String> carNames = Arrays.asList("pobi", "woni");
            model.prepareCars(carNames);

            model.playRound();

            List<CarStatusData> statuses = model.getCurrentCarStatuses();
            assertThat(statuses).allMatch(status -> status.position() == 0);
        }

        @Test
        @DisplayName("여러 라운드를 진행할 수 있다")
        void playRound_Multiple() {
            strategy.setCanMove(true);
            List<String> carNames = Arrays.asList("pobi");
            model.prepareCars(carNames);

            model.playRound();
            model.playRound();
            model.playRound();

            List<CarStatusData> statuses = model.getCurrentCarStatuses();
            assertThat(statuses.get(0).position()).isEqualTo(3);
        }
    }

    @Nested
    @DisplayName("getCurrentCarStatuses 메서드는")
    class GetCurrentCarStatusesTest {
        private FakeCarsRepository repository;
        private RacingGameModel model;

        @BeforeEach
        void setUp() {
            repository = new FakeCarsRepository();
            model = new RacingGameModelImpl(() -> true, repository);
        }

        @Test
        @DisplayName("현재 차량 상태 리스트를 반환한다")
        void getCurrentCarStatuses() {
            List<String> carNames = Arrays.asList("pobi", "woni", "jun");
            model.prepareCars(carNames);

            List<CarStatusData> statuses = model.getCurrentCarStatuses();

            assertThat(statuses).hasSize(3);
        }

        @Test
        @DisplayName("차량 상태에 이름과 위치가 포함된다")
        void getCurrentCarStatuses_ContainsNameAndPosition() {
            List<String> carNames = Arrays.asList("pobi");
            model.prepareCars(carNames);
            model.playRound();

            List<CarStatusData> statuses = model.getCurrentCarStatuses();

            assertThat(statuses.get(0).name()).isEqualTo("pobi");
            assertThat(statuses.get(0).position()).isEqualTo(1);
        }
    }

    @Nested
    @DisplayName("getWinnerNames 메서드는")
    class GetWinnerNamesTest {
        private FakeCarsRepository repository;
        private AlternatingMoveStrategy strategy;
        private RacingGameModel model;

        @BeforeEach
        void setUp() {
            repository = new FakeCarsRepository();
            strategy = new AlternatingMoveStrategy();
            model = new RacingGameModelImpl(strategy, repository);
        }

        @Test
        @DisplayName("우승자 이름 리스트를 반환한다")
        void getWinnerNames() {
            List<String> carNames = Arrays.asList("pobi", "woni");
            model.prepareCars(carNames);
            model.playRound();

            List<String> winners = model.getWinnerNames();

            assertThat(winners).hasSize(1);
        }

        @Test
        @DisplayName("가장 멀리 이동한 차량을 우승자로 선정한다")
        void getWinnerNames_SelectsFarthestCar() {
            List<String> carNames = Arrays.asList("pobi", "woni", "jun");
            model.prepareCars(carNames);
            model.playRound();

            List<String> winners = model.getWinnerNames();

            assertThat(winners).containsExactly("pobi");
        }

        @Test
        @DisplayName("동일한 위치의 여러 차량을 우승자로 선정한다")
        void getWinnerNames_SelectsMultipleWinners() {
            StubMoveStrategy samePositionStrategy = new StubMoveStrategy();
            samePositionStrategy.setCanMove(true);
            model = new RacingGameModelImpl(samePositionStrategy, repository);

            List<String> carNames = Arrays.asList("pobi", "woni", "jun");
            model.prepareCars(carNames);
            model.playRound();

            List<String> winners = model.getWinnerNames();

            assertThat(winners).hasSize(3);
            assertThat(winners).containsExactlyInAnyOrder("pobi", "woni", "jun");
        }
    }

    static class FakeCarsRepository implements CarsRepository {
        private Cars cars;

        @Override
        public void save(Cars cars) {
            this.cars = cars;
        }

        @Override
        public Cars findCars() {
            return cars;
        }
    }

    static class StubMoveStrategy implements MoveStrategy {
        private boolean canMove;

        public void setCanMove(boolean canMove) {
            this.canMove = canMove;
        }

        @Override
        public boolean canMove() {
            return canMove;
        }
    }

    static class AlternatingMoveStrategy implements MoveStrategy {
        private int callCount = 0;

        @Override
        public boolean canMove() {
            return callCount++ % 3 == 0;
        }
    }
}

