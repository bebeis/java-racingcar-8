package racingcar.controller;

import racingcar.service.GameService;
import racingcar.service.dto.RoundSnapShot;
import racingcar.service.dto.WinnerResponse;

import java.util.ArrayList;
import java.util.List;

public class StubGameService implements GameService {

    // 검증 대상 - 메서드 호출 여부 및 인자
    boolean setUpCarNamesCalled = false;
    List<String> receivedCarNames = new ArrayList<>();
    
    boolean playRoundsCalled = false;
    int receivedTryCount = 0;
    
    boolean getWinnersCalled = false;

    // 반환할 값
    private final List<RoundSnapShot> roundSnapShots;
    private final List<WinnerResponse> winners;

    public StubGameService(final List<RoundSnapShot> roundSnapShots, final List<WinnerResponse> winners) {
        this.roundSnapShots = roundSnapShots;
        this.winners = winners;
    }

    @Override
    public void setUpCarNames(final List<String> carNames) {
        setUpCarNamesCalled = true;
        receivedCarNames = carNames;
    }

    @Override
    public List<RoundSnapShot> playRounds(final int tryCount) {
        playRoundsCalled = true;
        receivedTryCount = tryCount;
        return roundSnapShots;
    }

    @Override
    public List<WinnerResponse> getWinners() {
        getWinnersCalled = true;
        return winners;
    }
}

