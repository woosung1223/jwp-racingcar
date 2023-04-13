package racingcar.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import racingcar.dto.CarDto;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class JdbcTemplateRacingGameDao implements RacingGameDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcTemplateRacingGameDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Number saveGameResult(final String winners, final int trialCount) {
        final String sql = "INSERT INTO GAME_RESULT (winners, trial_count) values (?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setString(1, winners);
            preparedStatement.setInt(2, trialCount);
            return preparedStatement;
        }, keyHolder);
        return keyHolder.getKey();
    }

    public void savePlayerResults(final List<CarDto> racingCars, final Number gameResultKey) {
        for (CarDto carDto : racingCars) {
            String sql = "INSERT INTO PLAYER_RESULT (name, position, game_result_id) values (?, ?, ?)";
            jdbcTemplate.update(sql, carDto.getName(), carDto.getPosition(), gameResultKey);
        }
    }
}
