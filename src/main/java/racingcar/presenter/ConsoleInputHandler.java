package racingcar.presenter;

import racingcar.view.InputReader;

public class ConsoleInputHandler implements RacingGamePresenter.InputHandler {
    private final InputReader inputReader;

    public ConsoleInputHandler(final InputReader inputReader) {
        this.inputReader = inputReader;
    }

    @Override
    public String readCarNames() {
        return inputReader.readLine();
    }

    @Override
    public int readTryCount() {
        return Integer.parseInt(inputReader.readLine());
    }
}

