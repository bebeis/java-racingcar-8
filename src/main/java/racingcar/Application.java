package racingcar;

import racingcar.domain.strategy.RandomMoveStrategy;
import racingcar.model.RacingGameModel;
import racingcar.model.RacingGameModelImpl;
import racingcar.persistence.CarsMemoryRepository;
import racingcar.presenter.ConsoleInputHandler;
import racingcar.presenter.RacingGamePresenter;
import racingcar.view.ConsoleInputReader;
import racingcar.view.ConsoleRacingGameView;
import racingcar.view.InputReader;
import racingcar.view.RacingGameView;

public class Application {
    public static void main(String[] args) {
        InputReader inputReader = new ConsoleInputReader();
        RacingGameView view = new ConsoleRacingGameView(inputReader);
        RacingGameModel model = new RacingGameModelImpl(new RandomMoveStrategy(), new CarsMemoryRepository());
        RacingGamePresenter.InputHandler inputHandler = new ConsoleInputHandler(inputReader);
        RacingGamePresenter presenter = new RacingGamePresenter(view, model, inputHandler);
        presenter.start();
    }
}
