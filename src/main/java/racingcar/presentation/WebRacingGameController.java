package racingcar.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import racingcar.business.RacingGameService;
import racingcar.domain.RacingGame;
import racingcar.presentation.dto.GameResultResponse;
import racingcar.presentation.dto.RacingGameRequest;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public final class WebRacingGameController {

    private final RacingGameService racingGameService;

    @Autowired
    public WebRacingGameController(final RacingGameService racingGameService) {
        this.racingGameService = racingGameService;
    }

    @PostMapping(path = "/plays")
    public ResponseEntity<GameResultResponse> playRacingGame(
            @Valid @RequestBody final RacingGameRequest racingGameRequest
    ) {
        RacingGame racingGame = racingGameService.playRacingGame(racingGameRequest);
        GameResultResponse gameResultResponse = ClientResponseConverter.toGameResultResponse(racingGame);
        return ResponseEntity.ok(gameResultResponse);
    }

    @GetMapping(path = "/plays")
    public ResponseEntity<List<GameResultResponse>> readRecords() {
        List<RacingGame> allGames = racingGameService.readAllGames();
        List<GameResultResponse> gameResultResponses = allGames.stream()
                .map(ClientResponseConverter::toGameResultResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok()
                .body(gameResultResponses);
    }
}
