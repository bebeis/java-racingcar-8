package racingcar;

import racingcar.controller.GameController;
import racingcar.domain.strategy.RandomMoveStrategy;
import racingcar.persistence.CarsMemoryRepository;
import racingcar.service.GameService;
import racingcar.service.GameServiceImpl;
import racingcar.ui.ConsoleUserInterface;
import racingcar.ui.UserInterface;
import racingcar.view.ConsoleOutputView;
import racingcar.view.OutputView;

public class Application {
    public static void main(String[] args) {
        UserInterface userInterface = new ConsoleUserInterface();
        OutputView outputView = new ConsoleOutputView();
        GameService gameService = new GameServiceImpl(new RandomMoveStrategy(), new CarsMemoryRepository());
        GameController gameController = new GameController(userInterface, outputView, gameService);
        gameController.run();
    }
}
