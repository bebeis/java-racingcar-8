package racingcar.service.dto;

import racingcar.domain.car.Car;

public record CarStatus(String name, int position) {

    public static CarStatus from(Car car) {
        return new CarStatus(car.nameValue(), car.positionValue());
    }
}

