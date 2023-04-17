package racingcar.service;

import org.springframework.stereotype.Service;
import racingcar.domain.Car;
import racingcar.domain.RacingGame;
import racingcar.domain.RandomNumberGenerator;
import racingcar.dto.CarData;
import racingcar.dto.GameResultResponse;
import racingcar.dto.RacingGameRequest;
import racingcar.repository.RacingGameRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public final class RacingGameService {

    private final RacingGameRepository racingGameRepository;

    public RacingGameService(final RacingGameRepository racingGameRepository) {
        this.racingGameRepository = racingGameRepository;
    }

    public GameResultResponse playRacingGame(final RacingGameRequest racingGameRequest) {
        RacingGame racingGame = createRacingGame(racingGameRequest);
        play(racingGame);
        GameResultResponse gameResultResponse = new GameResultResponse(
                mapWinnerNamesTextFrom(racingGame),
                mapCarDtosFrom(racingGame)
        );
        racingGameRepository.saveGameRecord(gameResultResponse, racingGameRequest.getCount());
        return gameResultResponse;
    }

    private RacingGame createRacingGame(final RacingGameRequest racingGameRequest) {
        return new RacingGame(
                List.of(racingGameRequest.getNames().split(",")),
                racingGameRequest.getCount(),
                new RandomNumberGenerator()
        );
    }

    private void play(final RacingGame racingGame) {
        // TODO: 검증 로직 도메인 내로 이동 (미션 요구사항에 따라 2단계에서 수정 예정)
        while (racingGame.isGameOnGoing()) {
            racingGame.start();
        }
    }

    private List<CarData> mapCarDtosFrom(final RacingGame racingGame) {
        return racingGame.getCars().stream()
                .sorted(Comparator.comparingInt(Car::getPosition).reversed())
                .map(car -> new CarData(car.getCarName(), car.getPosition()))
                .collect(Collectors.toList());
    }

    private String mapWinnerNamesTextFrom(final RacingGame racingGame) {
        return racingGame.getWinners().stream()
                .map(Car::getCarName)
                .collect(Collectors.joining(","));
    }

    public List<GameResultResponse> makeGameRecords() {
        return racingGameRepository.makeGameRecords();
    }
}
