package io.ukhin.service;

import io.ukhin.models.Holiday;
import io.ukhin.repository.HolidayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            return holidayRepository.findBeetween(startDate, endDate);
        }
    }
}
