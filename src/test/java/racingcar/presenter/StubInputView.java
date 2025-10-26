package racingcar.presenter;

import racingcar.view.InputView;

public class StubInputView implements InputView {

    private final String delimitedCarNames;
    private final int tryCount;
    GamePresenter presenter;
    boolean requestedCarNames;
    boolean requestedTryCount;

    public StubInputView(final String delimitedCarNames, final int tryCount) {
        this.delimitedCarNames = delimitedCarNames;
        this.tryCount = tryCount;
    }

    @Override
    public void bindPresenter(final GamePresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void requestCarNames() {
        requestedCarNames = true;
        presenter.onCarNamesEntered(delimitedCarNames);
    }

    @Override
    public void requestTryCount() {
        requestedTryCount = true;
        presenter.onTryCountEntered(tryCount);
    }
}
