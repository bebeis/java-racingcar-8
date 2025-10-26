package racingcar.service.dto;

import racingcar.domain.car.Cars;

import java.util.List;

public record RoundSnapShot(List<CarStatus> cars) {

    public static RoundSnapShot from(Cars cars) {
        List<CarStatus> statuses = cars.getCars().stream()
                .map(CarStatus::from)
                .toList();
        return new RoundSnapShot(statuses);
    }
}
