package pro.sky.maternity.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pro.sky.maternity.controller.ReportsController;
import pro.sky.maternity.dto.ReportsDto;
import pro.sky.maternity.mapper.ReportsDtoMapper;
import pro.sky.maternity.model.Reports;
import pro.sky.maternity.repository.ReportsRepository;
import pro.sky.maternity.service.ReportsService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ReportsController.class)
public class ReportsControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReportsRepository reportsRepository;

    @SpyBean
    private ReportsService reportsService;


    @Autowired
    private ObjectMapper objectMapper;
    @SpyBean
    private ReportsDtoMapper reportsDtoMapper;

    public Reports resultReports() {
        Reports reports = new Reports();
        reports.setId(1L);
        reports.setDate(LocalDateTime.parse("2015-08-25T13:21:05.12345"));
        reports.setText("Text");
        reports.setChatId(123456789);
        return reports;
    }

    @Test
    void getReportsInfo() throws Exception {
        long id = 1L;
        String name = "Valya";
        long chatId = 123456789;
        LocalDateTime date = LocalDateTime.parse("2015-08-25T13:21:05.12345");
        String text = "Text";

        String jsonResult = objectMapper.writeValueAsString(reportsDtoMapper.toDto(resultReports()));

        Reports foundReports = new Reports();
        foundReports.setId(id);
        foundReports.setDate(date);
        foundReports.setText(text);
        foundReports.setChatId(chatId);

        when(reportsRepository.findById(any(long.class))).thenReturn(Optional.of(foundReports));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/reports/" + id)
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResult));
    }


    @Test
    void createReports() throws Exception {
        long id = 1L;
        String name = "Valya";
        long chatId = 123456789;
        LocalDateTime date = LocalDateTime.parse("2015-08-25T13:21:05.12345");
        String text = "Text";

        String jsonResult = objectMapper.writeValueAsString(reportsDtoMapper.toDto(resultReports()));

        Reports addedReports = new Reports();
        addedReports.setId(id);
        addedReports.setDate(date);
        addedReports.setText(text);
        addedReports.setChatId(chatId);

        ReportsDto newReports = new ReportsDto();
        newReports.setDate(date);
        newReports.setText(text);
        newReports.setChatId(chatId);

        when(reportsRepository.save(any(Reports.class))).thenReturn(addedReports);
        when(reportsRepository.findById(any(long.class))).thenReturn(Optional.of(addedReports));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/reports") //посылаем
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newReports))
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResult));
    }

    @Test
    void editReports() throws Exception {
        long id = 1L;
        String name = "Valya";
        long chatId = 123456789;
        LocalDateTime date = LocalDateTime.parse("2015-08-25T13:21:05.12345");
        String text = "Text";

        String jsonResult = objectMapper.writeValueAsString(reportsDtoMapper.toDto(resultReports()));

        Reports changedReports = new Reports();
        changedReports.setId(id);
        changedReports.setDate(date);
        changedReports.setText(text);
        changedReports.setChatId(chatId);

        ReportsDto changesToReports = new ReportsDto();
        changesToReports.setDate(date);
        changesToReports.setText(text);
        changesToReports.setChatId(chatId);

        when(reportsRepository.save(any(Reports.class))).thenReturn(changedReports);
        when(reportsRepository.findById(any(long.class))).thenReturn(Optional.of(changedReports));

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/reports/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(changesToReports))
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResult));
    }

    @Test
    void deleteReports() throws Exception {
        long id = 1L;
        String name = "Valya";
        long chatId = 123456789;
        LocalDateTime date = LocalDateTime.parse("2015-08-25T13:21:05.12345");
        String text = "Text";

        String jsonResult = objectMapper.writeValueAsString(reportsDtoMapper.toDto(resultReports()));

        Reports deletedReports = new Reports();
        deletedReports.setId(id);
        deletedReports.setDate(date);
        deletedReports.setText(text);
        deletedReports.setChatId(chatId);

        when(reportsRepository.findById(any(long.class))).thenReturn(Optional.of(deletedReports));
        doNothing().when(reportsRepository).deleteById(id);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/reports/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResult));
    }
}
