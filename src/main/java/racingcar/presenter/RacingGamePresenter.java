package racingcar.presenter;

import racingcar.model.CarStatusData;
import racingcar.model.RacingGameModel;
import racingcar.util.CommaSeparator;
import racingcar.view.RacingGameView;

import java.util.List;

public class RacingGamePresenter implements RacingGameView.ViewEventListener {
    private final RacingGameView view;
    private final RacingGameModel model;
    private final InputHandler inputHandler;

    public RacingGamePresenter(final RacingGameView view, final RacingGameModel model, final InputHandler inputHandler) {
        this.view = view;
        this.model = model;
        this.inputHandler = inputHandler;
        this.view.setPresenter(this);
    }

    public void start() {
        view.start();
    }

    @Override
    public String onCarNamesRequested() {
        view.showCarNamesPrompt();
        String carNamesInput = inputHandler.readCarNames();
        List<String> carNames = CommaSeparator.split(carNamesInput);
        model.prepareCars(carNames);
        return carNamesInput;
    }

    @Override
    public int onTryCountRequested() {
        view.showTryCountPrompt();
        int tryCount = inputHandler.readTryCount();
        playGame(tryCount);
        showWinners();
        return tryCount;
    }

    private void playGame(final int tryCount) {
        view.showRoundResultHeader();

        for (int i = 0; i < tryCount; i++) {
            model.playRound();
            showCurrentRound();
        }
    }

    private void showCurrentRound() {
        List<CarStatusData> carStatuses = model.getCurrentCarStatuses();
        for (CarStatusData status : carStatuses) {
            view.showCarStatus(status.name(), status.position());
        }
        view.showRoundSeparator();
    }

    private void showWinners() {
        List<String> winnerNames = model.getWinnerNames();
        view.showWinnersResult(winnerNames);
    }

    public interface InputHandler {
        String readCarNames();

        int readTryCount();
    }
}
