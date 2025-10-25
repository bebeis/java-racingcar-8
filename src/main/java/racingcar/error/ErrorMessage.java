package racingcar.error;

public enum ErrorMessage {

    NEGATIVE_POSITION("위치는 음수일 수 없습니다."),
    NEGATIVE_MOVE_STEP("음의 방향으로 이동할 수 없습니다."),
    EXCEED_CAR_NAME_MAX_LENGTH("자동차 이름의 최대 길이를 초과하였습니다."),
    EMPTY_CAR_NAME("자동차 이름은 비어있을 수 없습니다.");

    private final String message;

    ErrorMessage(final String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}
