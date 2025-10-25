package racingcar.error;

public enum ErrorMessage {

    NEGATIVE_POSITION("위치는 음수일 수 없습니다"),
    NEGATIVE_MOVE_STEP("음의 방향으로 이동할 수 없습니다");

    private final String message;

    ErrorMessage(final String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}
