package racingcar.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import racingcar.service.dto.CarStatus;
import racingcar.service.dto.RoundSnapShot;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("GameController 테스트")
class GameControllerTest {

    @Nested
    @DisplayName("입력 읽기 테스트")
    class InputReadTest {
        StubUserInterface inputView;
        SpyOutputView outputView;
        StubGameService gameService;
        GameController controller;

        @BeforeEach
        void init() {
            inputView = new StubUserInterface("pobi,bebe", 5);
            outputView = new SpyOutputView();
            gameService = new StubGameService(List.of(), List.of());
            controller = new GameController(inputView, outputView, gameService);
        }

        @Test
        @DisplayName("Controller는 InputView를 통해 자동차 이름을 읽는다")
        void shouldReadCarNames() {
            // when
            controller.run();

            // then
            assertThat(inputView.requestedCarNames).isTrue();
        }

        @Test
        @DisplayName("Controller는 InputView를 통해 시도 횟수를 읽는다")
        void shouldReadTryCount() {
            // when
            controller.run();

            // then
            assertThat(inputView.requestedTryCount).isTrue();
        }
    }

    @Nested
    @DisplayName("GameService 호출 테스트")
    class GameServiceCallTest {
        StubUserInterface inputView;
        SpyOutputView outputView;
        StubGameService gameService;
        GameController controller;

        @BeforeEach
        void init() {
            inputView = new StubUserInterface("pobi,bebe", 5);
            outputView = new SpyOutputView();
            gameService = new StubGameService(List.of(), List.of());
            controller = new GameController(inputView, outputView, gameService);
        }

        @Test
        @DisplayName("Controller는 입력받은 자동차 이름으로 GameService.setUpCarNames()를 호출한다")
        void shouldCallPrepareCars() {
            // when
            controller.run();

            // then
            assertThat(gameService.setUpCarNamesCalled).isTrue();
            assertThat(gameService.receivedCarNames).containsExactly("pobi", "bebe");
        }

        @Test
        @DisplayName("Controller는 입력받은 시도 횟수로 GameService.playRounds()를 호출한다")
        void shouldCallPlayRounds() {
            // when
            controller.run();

            // then
            assertThat(gameService.playRoundsCalled).isTrue();
            assertThat(gameService.receivedTryCount).isEqualTo(5);
        }

        @Test
        @DisplayName("Controller는 GameService.getWinners()를 호출한다")
        void shouldCallGetWinners() {
            // when
            controller.run();

            // then
            assertThat(gameService.getWinnersCalled).isTrue();
        }
    }

    @Nested
    @DisplayName("OutputView 호출 테스트")
    class OutputViewCallTest {
        StubUserInterface inputView;
        SpyOutputView outputView;

        @BeforeEach
        void init() {
            inputView = new StubUserInterface("pobi,bebe", 5);
            outputView = new SpyOutputView();
        }

        @Test
        @DisplayName("Controller는 GameService로부터 받은 라운드 스냅샷을 OutputView로 전달한다")
        void shouldShowRoundSnapShots() {
            // given
            List<RoundSnapShot> expectedSnapShots = List.of(
                    new RoundSnapShot(List.of(
                            new CarStatus("pobi", 1),
                            new CarStatus("bebe", 1)
                    )),
                    new RoundSnapShot(List.of(
                            new CarStatus("pobi", 2),
                            new CarStatus("bebe", 2)
                    ))
            );
            StubGameService gameService = new StubGameService(expectedSnapShots, List.of());
            GameController controller = new GameController(inputView, outputView, gameService);

            // when
            controller.run();

            // then
            assertThat(outputView.snapShots).isEqualTo(expectedSnapShots);
        }

        @Test
        @DisplayName("Controller는 GameService로부터 받은 우승자 목록을 OutputView로 전달한다")
        void shouldShowWinners() {
            // given
            List<CarStatus> expectedWinners = List.of(
                    new CarStatus("pobi", 3),
                    new CarStatus("bebe", 3)
            );
            StubGameService gameService = new StubGameService(List.of(), expectedWinners);
            GameController controller = new GameController(inputView, outputView, gameService);

            // when
            controller.run();

            // then
            assertThat(outputView.winners).isEqualTo(expectedWinners);
        }
    }
}

