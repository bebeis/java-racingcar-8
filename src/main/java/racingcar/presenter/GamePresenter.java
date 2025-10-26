package racingcar.presenter;

import racingcar.service.RacingGame;
import racingcar.service.dto.RoundSnapShot;
import racingcar.service.dto.WinnerResponse;
import racingcar.util.CommaSeparator;
import racingcar.view.InputView;
import racingcar.view.OutputView;

import java.util.List;

public class GamePresenter {
    private InputView inputView;
    private OutputView outputView;
    private RacingGame racingGame;

    public GamePresenter(final InputView inputView, final OutputView outputView, final RacingGame racingGame) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.racingGame = racingGame;
        this.inputView.bindPresenter(this);
    }

    public void run() {
        inputView.requestCarNames();
    }

    public void onCarNamesEntered(final String delimitedCarNames) {
        List<String> carNames = CommaSeparator.split(delimitedCarNames);
        racingGame.setUpCarNames(carNames);
    }

    public void onTryCountEntered(final int tryCount) {
        List<RoundSnapShot> roundSnapShots = racingGame.playRounds(tryCount);
        outputView.showRoundSnapShots(roundSnapShots);

        List<WinnerResponse> winners = racingGame.getWinners();
        outputView.showWinners(winners);
    }
}
