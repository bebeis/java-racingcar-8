package racingcar.view;

import java.util.List;
import java.util.stream.Collectors;

public class ConsoleRacingGameView implements RacingGameView {
    private static final String CAR_NAMES_PROMPT = "경주할 자동차 이름을 입력하세요.(이름은 쉼표(,) 기준으로 구분)";
    private static final String TRY_COUNT_PROMPT = "시도할 횟수는 몇 회인가요?";
    private static final String ROUND_RESULT_HEADER = "실행 결과";
    private static final String NAME_POSITION_DELIMITER = " : ";
    private static final String POSITION_MARKER = "-";
    private static final String WINNERS_RESULT_PREFIX = "최종 우승자 : ";
    private static final String NAME_SEPARATOR = ", ";

    private ViewEventListener presenter;
    private final InputReader inputReader;

    public ConsoleRacingGameView(final InputReader inputReader) {
        this.inputReader = inputReader;
    }

    @Override
    public void setPresenter(final ViewEventListener presenter) {
        this.presenter = presenter;
    }

    @Override
    public void start() {
        presenter.onCarNamesRequested();
        presenter.onTryCountRequested();
    }

    @Override
    public void showCarNamesPrompt() {
        System.out.println(CAR_NAMES_PROMPT);
    }

    @Override
    public void showTryCountPrompt() {
        System.out.println(TRY_COUNT_PROMPT);
    }

    @Override
    public void showRoundResultHeader() {
        System.out.println(ROUND_RESULT_HEADER);
    }

    @Override
    public void showCarStatus(final String name, final int position) {
        System.out.println(name + NAME_POSITION_DELIMITER + POSITION_MARKER.repeat(position));
    }

    @Override
    public void showRoundSeparator() {
        System.out.println();
    }

    @Override
    public void showWinnersResult(final List<String> winnerNames) {
        String names = winnerNames.stream()
                .collect(Collectors.joining(NAME_SEPARATOR));
        System.out.println(WINNERS_RESULT_PREFIX + names);
    }
}
