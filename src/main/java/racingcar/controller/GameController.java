package racingcar.controller;

import racingcar.service.GameService;
import racingcar.service.dto.CarStatus;
import racingcar.service.dto.RoundSnapShot;
import racingcar.util.CommaSeparator;
import racingcar.view.InputView;
import racingcar.view.OutputView;

import java.util.List;

public class GameController {
    private InputView inputView;
    private OutputView outputView;
    private GameService gameService;

    public GameController(final InputView inputView, final OutputView outputView, final GameService gameService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.gameService = gameService;
    }

    public void run() {
        List<String> carNames = CommaSeparator.split(inputView.readCarNames());
        gameService.prepareCars(carNames);

        int tryCount = inputView.readTryCount();
        List<RoundSnapShot> roundSnapShots = gameService.playRounds(tryCount);
        outputView.showRoundSnapShots(roundSnapShots);

        List<CarStatus> winners = gameService.getWinners();
        outputView.showWinners(winners);
    }
}
