package racingcar;

import racingcar.controller.GameController;
import racingcar.domain.strategy.RandomMoveStrategy;
import racingcar.repository.CarsMemoryRepository;
import racingcar.service.GameService;
import racingcar.service.GameServiceImpl;
import racingcar.view.ConsoleOutputView;
import racingcar.view.ConsoleUserInterface;
import racingcar.view.InputView;
import racingcar.view.OutputView;

public class Application {
    public static void main(String[] args) {
        InputView inputView = new ConsoleUserInterface();
        OutputView outputView = new ConsoleOutputView();
        GameService gameService = new GameServiceImpl(new RandomMoveStrategy(), new CarsMemoryRepository());
        GameController gameController = new GameController(inputView, outputView, gameService);
        gameController.run();
    }
}
