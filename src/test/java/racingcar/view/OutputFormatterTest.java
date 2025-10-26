package racingcar.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import racingcar.service.dto.CarStatus;
import racingcar.service.dto.RoundSnapShot;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OutputFormatterTest {

    OutputFormatter formatter;

    @BeforeEach
    void init() {
        formatter = new OutputFormatter();
    }

    @DisplayName("라운드 스냅샷 1개에 대해서 포맷팅한다.")
    @Test
    void formattingRoundSnapShotOne() {
        // given
        RoundSnapShot snapShot = new RoundSnapShot(List.of(
                new CarStatus("bebe", 2),
                new CarStatus("hehe", 3),
                new CarStatus("pobi", 0)
        ));

        // when
        String result = formatter.formatRoundSnapShots(List.of(snapShot));

        // then
        String expected = """
                bebe : --
                hehe : ---
                pobi :\s
                """;
        assertThat(result).isEqualTo(expected);
    }

    @DisplayName("라운드 스냅샷 여러 개를 빈 줄로 구분하여 포맷팅한다")
    @Test
    void formattingMultipleRoundSnapShots() {
        // given
        List<RoundSnapShot> snapShots = List.of(
                new RoundSnapShot(List.of(
                        new CarStatus("bebe", 1),
                        new CarStatus("hehe", 0)
                )),
                new RoundSnapShot(List.of(
                        new CarStatus("bebe", 2),
                        new CarStatus("hehe", 1)
                ))
        );

        // when
        String result = formatter.formatRoundSnapShots(snapShots);

        // then
        String expected = """
                bebe : -
                hehe :\s
                
                bebe : --
                hehe : -
                """;
        assertThat(result).isEqualTo(expected);
    }

}
