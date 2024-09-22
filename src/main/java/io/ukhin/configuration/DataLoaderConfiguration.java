package io.ukhin.configuration;

import io.ukhin.models.Holiday;
import io.ukhin.repository.HolidayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
public class DataLoaderConfiguration {

    SimpleDateFormat simpleDateFormat;

    @Autowired
    public DataLoaderConfiguration(SimpleDateFormat simpleDateFormat) {
        this.simpleDateFormat = simpleDateFormat;
    }

    @Bean
    public CommandLineRunner dataLoader(HolidayRepository holidayRepository){
        List<Holiday> holidays = Stream.of("01-01",
                "01-07",
                "03-08",
                "05-01",
                "05-09",
                "06-12",
                "08-24",
                "10-14",
                "12-25").map(date -> {


                }).collect(Collectors.toList());


        return args -> {
            holidayRepository.saveAll(holidays);
        };
    }
}
