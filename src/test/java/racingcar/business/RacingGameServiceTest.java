package racingcar.business;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import racingcar.domain.Car;
import racingcar.domain.RacingGame;
import racingcar.domain.RandomNumberGenerator;
import racingcar.persistence.repository.RacingGameRepository;
import racingcar.presentation.dto.RacingGameRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

class RacingGameServiceTest {

    @MockBean
    private RacingGameService racingGameService;

    @Test
    @DisplayName("플레이어가 한명일 때 GameResultDto가 잘 반환된다")
    void shouldReturnCorrectlyWhenInputOnePlayer() {
        RacingGameRepository racingGameRepository = Mockito.mock(RacingGameRepository.class);
        this.racingGameService = new RacingGameService(racingGameRepository);

        RacingGameRequest racingGameRequest = new RacingGameRequest(
                "브리",
                3
        );

        RacingGame racingGame = racingGameService.playRacingGame(racingGameRequest);

        assertThat(racingGame.getCars()).hasSize(1);
        assertThat(racingGame.getWinners()).hasSize(1);
    }

    @Test
    @DisplayName("플레이어가 여려 명일 때 GameResultDto가 잘 반환된다")
    void shouldReturnCorrectlyWhenInputManyPlayer() {
        List<RacingGame> allRacingGames = List.of(
                new RacingGame(
                        List.of(
                                new Car("브리", 0),
                                new Car("토미", 0),
                                new Car("브라운", 0)
                        ),
                        3,
                        new RandomNumberGenerator()
                ),
                new RacingGame(
                        List.of(
                                new Car("제이슨", 0),
                                new Car("구구", 0),
                                new Car("네오", 0)
                        ),
                        3,
                        new RandomNumberGenerator()
                )
        );

        RacingGameRepository racingGameRepository = Mockito.mock(RacingGameRepository.class);
        given(racingGameRepository.selectAllGames())
                .willReturn(allRacingGames);

        this.racingGameService = new RacingGameService(racingGameRepository);

        RacingGameRequest firstRacingGameRequest = new RacingGameRequest(
                "브리,토미,브라운",
                3
        );
        RacingGameRequest secondRacingGameRequest = new RacingGameRequest(
                "제이슨,구구,네오",
                3
        );

        racingGameService.playRacingGame(firstRacingGameRequest);
        racingGameService.playRacingGame(secondRacingGameRequest);

        List<RacingGame> allRacingGames1 = racingGameService.readAllGames();
        assertThat(allRacingGames1.size()).isEqualTo(2);
    }
}
