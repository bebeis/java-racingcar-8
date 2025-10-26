package racingcar.presenter;

import racingcar.service.RacingGame;
import racingcar.view.InputView;
import racingcar.view.OutputView;

public class GamePresenter {
    private InputView inputView;
    private OutputView outputView;
    private RacingGame racingGame;

    public GamePresenter(final InputView inputView, final OutputView outputView, final RacingGame racingGame) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.racingGame = racingGame;
    }

    public void run() {
        
    }

    public void onCarNamesEntered(final String delimitedCarNames) {

    }

    public void onTryCountEntered(final int tryCount) {

    }
}
