package racingcar.controller;

import racingcar.view.UserInterface;

public class StubUserInterface implements UserInterface {

    // 검증 대상 - 호출 여부
    boolean requestedCarNames = false;
    boolean requestedTryCount = false;

    // 반환할 값
    private final String carNames;
    private final int tryCount;

    public StubUserInterface(final String carNames, final int tryCount) {
        this.carNames = carNames;
        this.tryCount = tryCount;
    }

    @Override
    public String readCarNames() {
        requestedCarNames = true;
        return carNames;
    }

    @Override
    public int readTryCount() {
        requestedTryCount = true;
        return tryCount;
    }
}

