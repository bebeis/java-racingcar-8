package racingcar.presenter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import racingcar.service.dto.RoundSnapShot;
import racingcar.service.dto.WinnerResponse;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class GamePresenterTest {

    @DisplayName("입력 요청을 받고, 서비스를 호출하여 정상적으로 응답을 출력한다.")
    @Test
    void shouldRequestInputAndCallService_thenPrintResult() {
        // given
        StubInputView inputView = new StubInputView("pobi, bebe", 3);
        SpyOutputView outputView = new SpyOutputView();
        StubRacingGame racingGame = new StubRacingGame(
                List.of(new RoundSnapShot("pobi", 2), new RoundSnapShot("bebe", 1)),
                List.of(new WinnerResponse("pobi"))
        );

        // when
        GamePresenter gamePresenter = new GamePresenter(inputView, outputView, racingGame);
        gamePresenter.run();

        // then - input (행위)
        assertThat(inputView.requestedCarNames).isTrue();
        assertThat(inputView.requestedTryCount).isTrue();

        // then - service (상태)
        assertThat(racingGame.carNames).containsExactly("pobi", "bebe");
        assertThat(racingGame.tryCount).isEqualTo(3);

        // then - output (상태)
        assertThat(outputView.snapShots).containsExactly(
                new RoundSnapShot("pobi", 2),
                new RoundSnapShot("bebe", 1)
        );
        assertThat(outputView.winners).containsExactly(
                new WinnerResponse("pobi")
        );
    }
}
