package racingcar.error;

public enum ErrorMessage {

    NEGATIVE_POSITION("위치는 음수일 수 없습니다."),
    NEGATIVE_MOVE_STEP("음의 방향으로 이동할 수 없습니다."),
    EXCEED_CAR_NAME_MAX_LENGTH("자동차 이름의 최대 길이를 초과하였습니다."),
    EMPTY_CAR_NAME("자동차 이름은 비어있을 수 없습니다."),
    EMPTY_CAR_LIST("자동차 목록은 비어있을 수 없습니다."),
    NON_POSITIVE_TRY_COUNT("시도 횟수는 0 이하일 수 없습니다."),
    CANNOT_PROCEED_WHEN_FINISHED("종료된 이후에 라운드를 진행할 수 없습니다"),
    EMPTY_CSV_VALUE("목록이 비어있습니다"),
    TRY_COUNT_NOT_NUMBER("시도 횟수는 숫자여야 합니다.");

    private final String message;

    ErrorMessage(final String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}
