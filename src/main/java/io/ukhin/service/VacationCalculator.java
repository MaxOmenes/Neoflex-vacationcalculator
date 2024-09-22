package io.ukhin.service;

import io.ukhin.configuration.DataFormaterConfiguration;
import io.ukhin.models.Holiday;
import io.ukhin.repository.HolidayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class VacationCalculator {

    HolidayRepository holidayRepository;
    DataFormaterConfiguration dataFormaterConfiguration;

    @Autowired
    public VacationCalculator(HolidayRepository holidayRepository,
                                        DataFormaterConfiguration dataFormaterConfiguration) {
        this.holidayRepository = holidayRepository;
        this.dataFormaterConfiguration = dataFormaterConfiguration;
    }

    List<Holiday> holidays = (List<Holiday>) holidayRepository.findAll();

    Date startDate = dataFormaterConfiguration.getDataFormatter().parse(start);
    Date endDate = dataFormaterConfiguration.getDataFormatter().parse(end);

        if (startDate.after(endDate)) {
        return "Start date should be before end date";
    }

    Calendar startCal = Calendar.getInstance();
        startCal.setTime(startDate);
    Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);

        if (startCal.get(Calendar.YEAR) != endCal.get(Calendar.YEAR)) {

    }
}
