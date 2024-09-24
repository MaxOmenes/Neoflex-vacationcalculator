package io.ukhin.service;

import io.ukhin.configuration.DataFormaterConfiguration;
import io.ukhin.models.Holiday;
import io.ukhin.repository.HolidayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VacationCalculator {

    HolidayCollector holidayCollector;
    DataFormaterConfiguration dataFormaterConfiguration;

    @Autowired
    public VacationCalculator(HolidayCollector holidayCollector,
                              DataFormaterConfiguration dataFormaterConfiguration) {
        this.holidayCollector = holidayCollector;
        this.dataFormaterConfiguration = dataFormaterConfiguration;
    }

    public float calculate(Date startDate, Date endDate, float yearSalary) {
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startDate);
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);

        endCal.add(Calendar.DATE, 1);
        List<Holiday> holidays = holidayCollector.collectHolidays(startDate, endDate);


        List<Date> paidDays = new ArrayList<>();

        while (startCal.before(endCal)) {
            if (startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY &&
                    startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
                paidDays.add(startCal.getTime());
            }
            startCal.add(Calendar.DATE, 1);
        }

        Calendar filterCalendar = Calendar.getInstance();

        paidDays = paidDays.stream().filter(day -> {
            filterCalendar.setTime(day);
            for (Holiday holiday : holidays) {
                if (filterCalendar.get(Calendar.DAY_OF_MONTH) == holiday.getHoliday_day() &&
                        filterCalendar.get(Calendar.MONTH) + 1 == holiday.getHoliday_month()) {
                    return false;
                }
            }
            return true;
        }).collect(Collectors.toList());

        return (paidDays.size()) * yearSalary / 365;
    }
}

