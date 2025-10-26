package racingcar.view;

import racingcar.service.dto.CarStatus;
import racingcar.service.dto.RoundSnapShot;

import java.util.List;

public class ConsoleOutputView implements OutputView {
    private static final String ROUND_RESULT_PROMPT = "실행 결과";
    private static final String WINNERS_RESULT_PROMPT = "최종 우승자 : ";

    private final OutputFormatter formatter;

    public ConsoleOutputView() {
        this.formatter = new OutputFormatter();
    }

    @Override
    public void showRoundSnapShots(final List<RoundSnapShot> snapShots) {
        System.out.println(ROUND_RESULT_PROMPT);

        String formatted = formatter.formatRoundSnapShots(snapShots);
        System.out.println(formatted);
    }

    @Override
    public void showWinners(final List<CarStatus> winners) {
        System.out.println(WINNERS_RESULT_PROMPT + formatter.formatWinners(winners));
    }
}
