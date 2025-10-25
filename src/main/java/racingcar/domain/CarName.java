package racingcar.domain;

import racingcar.error.ErrorMessage;

public class CarName {
    private static final int MAX_CAR_NAME_LENGTH = 5;

    private final String name;

    public CarName(final String name) {
        validateName(name);
        this.name = name;
    }

    private void validateName(final String name) {
        validateNotBlank(name);
        validateLength(name);
    }

    private void validateNotBlank(final String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(ErrorMessage.EMPTY_CAR_NAME.message());
        }
    }

    private void validateLength(final String name) {
        if (name.length() > MAX_CAR_NAME_LENGTH) {
            throw new IllegalArgumentException(ErrorMessage.EXCEED_CAR_NAME_MAX_LENGTH.message());
        }
    }

    public String value() {
        return name;
    }
}
