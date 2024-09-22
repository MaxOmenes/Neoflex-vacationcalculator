package io.ukhin.service;

import io.ukhin.models.Holiday;
import io.ukhin.repository.HolidayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class HolidayCollector {
    HolidayRepository holidayRepository;

    @Autowired
    public HolidayCollector(HolidayRepository holidayRepository) {
        this.holidayRepository = holidayRepository;
    }

    public List<Holiday> collectHolidays(Date startDate, Date endDate) {
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startDate);

        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);

        List<Integer> years = startCal.get(Calendar.YEAR) == endCal.get(Calendar.YEAR) ?
                List.of(startCal.get(Calendar.YEAR)) :
                IntStream.range(startCal.get(Calendar.YEAR),
                        endCal.get(Calendar.YEAR) + 1).boxed().collect(Collectors.toList());

        if (years.size() == 1) {
            return holidayRepository.findBetween(startDate, endDate);
        } else if (years.size() == 2) {
            Date endOfYear = new Date(startCal.get(Calendar.YEAR), Calendar.DECEMBER, 31);
            List<Holiday> holidays = holidayRepository.findBetween(startDate, endOfYear);
            holidays.addAll(holidayRepository.findBetween(new Date(endCal.get(Calendar.YEAR), Calendar.JANUARY, 1), endDate));
            return holidays;
        } else {
            Date endOfYear = new Date(startCal.get(Calendar.YEAR), Calendar.DECEMBER, 31);
            List<Holiday> holidays = holidayRepository.findBetween(startDate, endOfYear);
            holidays.addAll(holidayRepository.findBetween(new Date(endCal.get(Calendar.YEAR), Calendar.JANUARY, 1), endDate));

            // we can find holidays for one year, but if we have several repositories with specific holidays in year we need to find them all
            for (int i = 1; i < years.size() - 1; i++) {
                holidays.addAll(holidayRepository.findBetween(new Date(years.get(i), Calendar.JANUARY, 1),
                        new Date(years.get(i), 11, 31)));
            }
            return holidays;

        }

    }
}
