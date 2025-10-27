package racingcar.controller;

import racingcar.service.GameService;
import racingcar.service.dto.CarStatus;
import racingcar.service.dto.RoundSnapShot;
import racingcar.ui.UserInterface;
import racingcar.util.CommaSeparator;
import racingcar.view.OutputView;

import java.util.List;

public class GameController {
    private UserInterface userInterface;
    private OutputView outputView;
    private GameService gameService;

    public GameController(final UserInterface userInterface, final OutputView outputView, final GameService gameService) {
        this.userInterface = userInterface;
        this.outputView = outputView;
        this.gameService = gameService;
    }

    public void run() {
        List<String> carNames = CommaSeparator.split(userInterface.readCarNames());
        gameService.prepareCars(carNames);

        int tryCount = userInterface.readTryCount();
        List<RoundSnapShot> roundSnapShots = gameService.playRounds(tryCount);
        outputView.showRoundSnapShots(roundSnapShots);

        List<CarStatus> winners = gameService.getWinners();
        outputView.showWinners(winners);
    }
}
