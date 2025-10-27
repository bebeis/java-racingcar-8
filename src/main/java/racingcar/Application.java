package racingcar;

import racingcar.controller.GameController;
import racingcar.domain.strategy.RandomMoveStrategy;
import racingcar.repository.CarsMemoryRepository;
import racingcar.service.GameService;
import racingcar.service.GameServiceImpl;
import racingcar.view.ConsoleOutputView;
import racingcar.view.ConsoleUserInterface;
import racingcar.view.OutputView;
import racingcar.view.UserInterface;

public class Application {
    public static void main(String[] args) {
        UserInterface userInterface = new ConsoleUserInterface();
        OutputView outputView = new ConsoleOutputView();
        GameService gameService = new GameServiceImpl(new RandomMoveStrategy(), new CarsMemoryRepository());
        GameController gameController = new GameController(userInterface, outputView, gameService);
        gameController.run();
    }
}
