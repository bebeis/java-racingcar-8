package racingcar.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import racingcar.service.dto.CarStatus;
import racingcar.service.dto.RoundSnapShot;
import racingcar.stub.strategy.AlwaysMoveStrategy;
import racingcar.stub.strategy.SequentialMoveStrategy;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class GameServiceImplTest {

    @Nested
    @DisplayName("자동차 이름 설정 테스트")
    class SetUpCarNamesTest {
        FakeCarsRepository repository;
        GameService gameService;

        @BeforeEach
        void setUp() {
            repository = new FakeCarsRepository();
            gameService = new GameServiceImpl(new AlwaysMoveStrategy(), repository);
        }

        @Test
        @DisplayName("자동차 이름 목록으로 Cars를 저장한다")
        void shouldSaveCarsWithGivenNames() {
            // given
            List<String> carNames = List.of("pobi", "bebe");

            // when
            gameService.prepareCars(carNames);

            // then
            assertThat(repository.findCars()).isNotNull();
            assertThat(repository.findCars().getCars()).hasSize(2);
        }

        @Test
        @DisplayName("자동차 한대로도 게임을 설정할 수 있다")
        void shouldSetUpSingleCar() {
            // given
            List<String> carNames = List.of("pobi");

            // when
            gameService.prepareCars(carNames);

            // then
            assertThat(repository.findCars().getCars()).hasSize(1);
        }
    }

    @Nested
    @DisplayName("라운드 진행 테스트")
    class PlayRoundsTest {
        FakeCarsRepository repository;

        @BeforeEach
        void setUp() {
            repository = new FakeCarsRepository();
        }

        @Test
        @DisplayName("라운드를 1번 진행하고 결과를 반환한다.")
        void shouldPlayOneRound() {
            // given
            GameService gameService = new GameServiceImpl(new AlwaysMoveStrategy(), repository);
            gameService.prepareCars(List.of("pobi"));

            // when
            List<RoundSnapShot> snapShots = gameService.playRounds(1);

            // then
            assertThat(snapShots).hasSize(1);
            assertThat(snapShots.getFirst().cars()).hasSize(1);
            assertThat(snapShots.getFirst().cars().getFirst().position()).isEqualTo(1);
        }

        @Test
        @DisplayName("각 라운드마다 모든 자동차의 이름과 위치 정보가 포함된다")
        void shouldIncludeAllCarsInEachRound() {
            // given
            GameService gameService = new GameServiceImpl(new AlwaysMoveStrategy(), repository);
            gameService.prepareCars(List.of("pobi", "bebe"));

            // when
            List<RoundSnapShot> snapShots = gameService.playRounds(2);

            // then
            assertThat(snapShots).hasSize(2);
            assertThat(snapShots).allSatisfy(roundSnapShot ->
                    assertThat(roundSnapShot.cars()).hasSize(2)
            );

            RoundSnapShot lastRound = snapShots.get(1);
            assertThat(lastRound.cars()).extracting(CarStatus::name)
                    .containsExactlyInAnyOrder("pobi", "bebe");
            assertThat(lastRound.cars()).allSatisfy(car ->
                    assertThat(car.position()).isEqualTo(2)
            );
        }

        @Test
        @DisplayName("여러 라운드의 진행 상황을 모두 기록한다")
        void shouldRecordAllRounds() {
            // given
            GameService gameService = new GameServiceImpl(new AlwaysMoveStrategy(), repository);
            gameService.prepareCars(List.of("pobi"));

            // when
            List<RoundSnapShot> snapShots = gameService.playRounds(3);

            // then
            assertThat(snapShots).hasSize(3);

            assertThat(snapShots.get(0).cars().getFirst().position()).isEqualTo(1);
            assertThat(snapShots.get(1).cars().getFirst().position()).isEqualTo(2);
            assertThat(snapShots.get(2).cars().getFirst().position()).isEqualTo(3);
        }
    }

    @Nested
    @DisplayName("우승자 결정 테스트")
    class GetWinnersTest {

        FakeCarsRepository repository;

        @BeforeEach
        void setUp() {
            repository = new FakeCarsRepository();
        }

        @Test
        @DisplayName("모든 자동차가 같은 위치에 있으면 모두 우승자다")
        void shouldAllWinIfSamePosition() {
            // given
            GameService gameService = new GameServiceImpl(new AlwaysMoveStrategy(), repository);
            gameService.prepareCars(List.of("pobi", "bebe", "hehe"));
            gameService.playRounds(3);

            // when
            List<CarStatus> winners = gameService.getWinners();

            // then
            assertThat(winners).hasSize(3);
            assertThat(winners).extracting(CarStatus::name)
                    .containsExactlyInAnyOrder("pobi", "bebe", "hehe");
        }

        @Test
        @DisplayName("서로 다른 위치에 있을 때 가장 앞선 자동차를 우승자로 선정한다")
        void shouldSelectFarthestCarAsWinner() {
            // given
            GameService gameService = new GameServiceImpl(
                    new SequentialMoveStrategy(
                            true, false,
                            true, false,
                            true, true),
                    repository
            );
            gameService.prepareCars(List.of("pobi", "bebe"));
            gameService.playRounds(3);

            // when
            List<CarStatus> winners = gameService.getWinners();

            // then
            assertThat(winners).hasSize(1);
            assertThat(winners.getFirst().name()).isEqualTo("pobi");
        }

        @Test
        @DisplayName("자동차가 한 대뿐이면 그 자동차가 우승한다.")
        void shouldSelectOnlyCarAsWinner() {
            // given
            GameService gameService = new GameServiceImpl(new AlwaysMoveStrategy(), repository);
            gameService.prepareCars(List.of("pobi"));
            gameService.playRounds(5);

            // when
            List<CarStatus> winners = gameService.getWinners();

            // then
            assertThat(winners).hasSize(1);
            assertThat(winners.getFirst().name()).isEqualTo("pobi");
        }

        @Test
        @DisplayName("최대 위치에 여러 자동차들이 있으면, 모두 공동 우승자로 선정한다")
        void shouldSelectMultipleWinnersAtSamePosition() {
            // given
            GameService gameService = new GameServiceImpl(
                    new SequentialMoveStrategy(
                            true, true, false,
                            false, true, true,
                            true, false, false
                    ),
                    repository
            );
            gameService.prepareCars(List.of("pobi", "bebe", "hehe"));
            gameService.playRounds(3);

            // when
            List<CarStatus> winners = gameService.getWinners();

            // then
            assertThat(winners).hasSize(2);
            assertThat(winners).extracting(CarStatus::name)
                    .containsExactlyInAnyOrder("pobi", "bebe");
        }
    }
}

