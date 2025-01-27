package io.ukhin.controller;

import io.ukhin.configuration.DataFormaterConfiguration;
import io.ukhin.models.Holiday;
import io.ukhin.repository.HolidayRepository;
import io.ukhin.service.VacationCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/calculate")
public class VacationCalculatorController {

    VacationCalculator vacationCalculator;
    SimpleDateFormat dataFormatter;

    @Autowired
    public VacationCalculatorController(VacationCalculator vacationCalculator,
                                        SimpleDateFormat dataFormatter) {
        this.vacationCalculator = vacationCalculator;
        this.dataFormatter = dataFormatter;

    }

    @GetMapping
    public String calculateVacation(@RequestParam("Year_salary") Float yearSalary,
                                    @RequestParam("Start_date") String start,
                                    @RequestParam("End_date") String end) {
        Calendar startCal;
        Calendar endCal;

        try {
            startCal = Calendar.getInstance();
            startCal.setTime(dataFormatter.parse(start));
            endCal = Calendar.getInstance();
            endCal.setTime(dataFormatter.parse(end));



        } catch (ParseException e) {
            return "Invalid date format: Should be dd-MM-yyyy";
        }

        if (startCal.after(endCal)) {
            return "Start date should be before end date";
        }

        if (yearSalary < 0) {
            return "Year salary should be positive";
        }

        return "Vacation cost: " + vacationCalculator.calculate(startCal.getTime(), endCal.getTime(), yearSalary);

    }
}

