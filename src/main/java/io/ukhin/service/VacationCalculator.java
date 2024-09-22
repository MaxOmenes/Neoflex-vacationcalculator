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
        List<Holiday> holidays = holidayCollector.collectHolidays(startDate, endDate);

        List<Date> paidDays = new ArrayList<>();

        while (startCal.before(endCal)) {
            if (startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY ||
                    startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY ||
                    !holidays.contains(new Holiday(startCal.get(Calendar.MONTH), startCal.get(Calendar.DAY_OF_MONTH)))) {
                paidDays.add(startCal.getTime());
            }
            startCal.add(Calendar.DATE, 1);
        }

        return paidDays.size() * yearSalary / 365;
    }
}

