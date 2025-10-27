package racingcar.ui;

import camp.nextstep.edu.missionutils.Console;

import static racingcar.error.ErrorMessage.TRY_COUNT_NOT_NUMBER;

public class ConsoleUserInterface implements UserInterface {
    private static final String CAR_NAMES_PROMPT = "경주할 자동차 이름을 입력하세요.(이름은 쉼표(,) 기준으로 구분)";
    private static final String TRY_COUNT_PROMPT = "시도할 횟수는 몇 회인가요?";

    @Override
    public String readCarNames() {
        System.out.println(CAR_NAMES_PROMPT);
        return Console.readLine();
    }

    @Override
    public int readTryCount() {
        System.out.println(TRY_COUNT_PROMPT);
        try {
            return Integer.parseInt(Console.readLine());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(TRY_COUNT_NOT_NUMBER.message());
        }
    }
}
