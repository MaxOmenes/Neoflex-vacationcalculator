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

        int startDay = start.get(Calendar.DAY_OF_MONTH);
        int endDay = end.get(Calendar.DAY_OF_MONTH);

        int startMonth = start.get(Calendar.MONTH)+1;
        int endMonth = end.get(Calendar.MONTH)+1;

        PreparedStatementCreator psc = connection -> {
            PreparedStatement statement =
            connection.prepareStatement("SELECT * FROM holidays " +
                    "WHERE (holiday_month >= ?1 AND holiday_month <= ?2) " +
                    "OR (holiday_month = ?1 AND holiday_day >= ?3) " +
                    "OR (holiday_month = ?2 AND holiday_day <= ?4) " +
                    "OR (?1 = ?2 AND HOLIDAYS.HOLIDAY_MONTH = ?1 AND holiday_day BETWEEN ?3 AND ?4);");

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
