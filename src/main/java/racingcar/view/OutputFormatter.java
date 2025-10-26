package racingcar.view;

import racingcar.service.dto.CarStatus;
import racingcar.service.dto.RoundSnapShot;

import java.util.List;
import java.util.stream.Collectors;

public class OutputFormatter {

    private static final String NAME_POSITION_DELIMITER = " : ";
    private static final String POSITION_MARKER = "-";
    private static final String CAR_SEPARATOR = "\n";
    private static final String ROUND_SEPARATOR = "\n\n";
    private static final String END_LINE = "\n";

    public String formatRoundSnapShots(final List<RoundSnapShot> snapShots) {
        return snapShots.stream()
                .map(this::formatRoundSnapShot)
                .collect(Collectors.joining(ROUND_SEPARATOR)) + END_LINE;
    }

    private String formatRoundSnapShot(final RoundSnapShot snapShot) {
        return snapShot.cars()
                .stream()
                .map(this::formatCarStatus)
                .collect(Collectors.joining(CAR_SEPARATOR));
    }

    private String formatCarStatus(final CarStatus status) {
        return status.name() + NAME_POSITION_DELIMITER + POSITION_MARKER.repeat(status.position());
    }
}
