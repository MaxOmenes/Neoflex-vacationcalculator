package io.ukhin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class VacationCalculatorTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void test_one_year() throws Exception {
        this.mockMvc.perform(get("/calculate")
                        .param("Year_salary", "365")
                        .param("Start_date", "01-01-2022")
                        .param("End_date", "31-12-2022"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("256.0"))); //in 2022 - 105 weekend days
    }

    @Test
    public void test_holidays() throws Exception {
        ResultActions resultActions1 = this.mockMvc.perform(get("/calculate")
                .param("Year_salary", "365")
                .param("Start_date", "01-01-2022")
                .param("End_date", "2-10-2022"));

        ResultActions resultActions2 = this.mockMvc.perform(get("/calculate")
                .param("Year_salary", "365")
                .param("Start_date", "01-01-2022")
                .param("End_date", "3-10-2022"));

        assertThat(resultActions1.andReturn().getResponse().getContentAsString())
                .isEqualTo(resultActions2.andReturn().getResponse().getContentAsString());
    }

    @Test
    public void test_two_years() throws Exception {
        this.mockMvc.perform(get("/calculate")
                .param("Year_salary", "365")
                .param("Start_date", "01-12-2022")
                .param("End_date", "31-1-2023"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("44.0")));

    }

    @Test
    public void test_multiple_years() throws Exception {
        this.mockMvc.perform(get("/calculate")
                .param("Year_salary", "365")
                .param("Start_date", "01-12-2021")
                .param("End_date", "31-1-2023"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("300.0")));
    }

    @Test
    public void test_start_after_end() throws Exception {
        this.mockMvc.perform(get("/calculate")
                .param("Year_salary", "365")
                .param("Start_date", "01-12-2023")
                .param("End_date", "01-12-2021"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Start date should be before end date")));
    }

    @Test
    public void test_negative_salary() throws Exception {
        this.mockMvc.perform(get("/calculate")
                        .param("Year_salary", "-365")
                        .param("Start_date", "01-12-2021")
                        .param("End_date", "31-1-2023"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Year salary should be positive")));
    }

    @Test
    public void test_parse() throws Exception {
        this.mockMvc.perform(get("/calculate")
                        .param("Year_salary", "365")
                        .param("Start_date", "2021.01.12")
                        .param("End_date", "2023.01.31"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Invalid date format")));
    }


}
