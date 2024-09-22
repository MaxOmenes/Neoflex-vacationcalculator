package io.ukhin.controller;

import io.ukhin.configuration.DataFormaterConfiguration;
import io.ukhin.models.Holiday;
import io.ukhin.repository.HolidayRepository;
import io.ukhin.service.VacationCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/calculate")
public class VacationCalculatorController {

    VacationCalculator vacationCalculator;
    DataFormaterConfiguration dataFormaterConfiguration;

    @Autowired
    public VacationCalculatorController(VacationCalculator vacationCalculator,
                                        DataFormaterConfiguration dataFormaterConfiguration) {
        this.vacationCalculator = vacationCalculator;
        this.dataFormaterConfiguration = dataFormaterConfiguration;

    }

    @GetMapping
    public String calculateVacation(@Param("Year_salary") float yearSalary,
                                    @Param("Start_date") String start,
                                    @Param("End_date") String end
            ) throws ParseException {



    }
}

