package racingcar.view;

import racingcar.presenter.GamePresenter;

public interface InputView {

    void bindPresenter(GamePresenter presenter);

    void requestCarNames();

    void requestTryCount();
}
