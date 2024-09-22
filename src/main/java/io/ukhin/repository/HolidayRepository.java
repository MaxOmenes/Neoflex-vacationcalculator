package io.ukhin.repository;

import io.ukhin.models.Holiday;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Repository
public class HolidayRepository {

    JdbcTemplate jdbcTemplate;
    Connection connection;

    @Autowired
    public HolidayRepository(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        try {
            this.connection = dataSource.getConnection();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public List<Holiday> findBetween(Date startDate, Date endDate) {
        Calendar start = Calendar.getInstance();
        start.setTime(startDate);
        Calendar end = Calendar.getInstance();
        end.setTime(endDate);

        int startDay = start.get(Calendar.DAY_OF_YEAR);
        int endDay = end.get(Calendar.DAY_OF_YEAR);

        int startMonth = start.get(Calendar.MONTH);
        int endMonth = end.get(Calendar.MONTH);

        PreparedStatementCreator psc = connection -> {
            PreparedStatement statement =
            connection.prepareStatement("SELECT * FROM holidays WHERE month between ?1 AND ?2 AND " +
                "(CASE WHEN ?1 = ?2 THEN day between ?3 AND ?4 " +
                "WHEN month = ?1 THEN day >= ?3 " +
                "WHEN month = ?2 THEN day <= ?4 END)");

            statement.setInt(1, startMonth);
            statement.setInt(2, endMonth);
            statement.setInt(3, startDay);
            statement.setInt(4, endDay);
            return statement;
        };

        return jdbcTemplate.query(psc, new BeanPropertyRowMapper<>(Holiday.class));
    }

    public List<Holiday> findAll() {
        return jdbcTemplate.query("SELECT * FROM holidays", new BeanPropertyRowMapper<>(Holiday.class));
    }
}
