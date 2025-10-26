package racingcar.util;

import java.util.Arrays;
import java.util.List;

import static racingcar.error.ErrorMessage.EMPTY_CSV_VALUE;

public final class CommaSeparator {

    private CommaSeparator() {
    }

    public static List<String> split(final String commaSeperatedValue) {
        validateNotBlank(commaSeperatedValue);
        return Arrays.stream(commaSeperatedValue.split(","))
                .map(String::trim)
                .toList();
    }

    private static void validateNotBlank(final String commaSeperatedValue) {
        if (commaSeperatedValue == null || commaSeperatedValue.isBlank()) {
            throw new IllegalArgumentException(EMPTY_CSV_VALUE.message());
        }
    }
}
