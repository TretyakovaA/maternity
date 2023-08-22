package pro.sky.maternity.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.maternity.exception.ReportsNotFoundException;
import pro.sky.maternity.exception.UserNotFoundException;
import pro.sky.maternity.mapper.ReportsDtoMapper;
import pro.sky.maternity.mapper.UserDtoMapper;
import pro.sky.maternity.model.Reports;
import pro.sky.maternity.model.User;
import pro.sky.maternity.repository.ReportsRepository;
import pro.sky.maternity.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

import static pro.sky.maternity.service.Constants.*;

@ExtendWith(MockitoExtension.class)
class ReportsServiceTest {

    @Mock
    private ReportsRepository reportsRepository;

    @InjectMocks
    private ReportsService out;

    @Mock
    private ReportsDtoMapper reportsDtoMapper;


    @Test
    void addReports() {
        when(reportsDtoMapper.toDto(REPORTS_1)).thenReturn(REPORTS_DTO_1);
        when(reportsDtoMapper.toEntity(REPORTS_DTO_1)).thenReturn(REPORTS_1);
        when(reportsRepository.save(any(Reports.class))).thenReturn(REPORTS_1);

        assertEquals(out.addReports(REPORTS_DTO_1), REPORTS_DTO_1);
    }

    @Test
    void findrReports() {
        when(reportsDtoMapper.toDto(REPORTS_1)).thenReturn(REPORTS_DTO_1);
        when(reportsRepository.findById(any(Long.class))).thenReturn(Optional.of(REPORTS_1));

        assertEquals(out.findReports(ID1), REPORTS_DTO_1);
    }

    @Test
    void editReports() {
        when(reportsDtoMapper.toDto(REPORTS_1)).thenReturn(REPORTS_DTO_1);
        when(reportsRepository.findById(any(Long.class))).thenReturn(Optional.of(REPORTS_1));
        when(reportsRepository.save(any(Reports.class))).thenReturn(REPORTS_1);

        assertEquals(out.editReports(ID1, REPORTS_DTO_1), REPORTS_DTO_1);
    }

    @Test
    void deleteReports() {
        when(reportsDtoMapper.toDto(REPORTS_1)).thenReturn(REPORTS_DTO_1);
        when(reportsRepository.findById(any(Long.class))).thenReturn(Optional.of(REPORTS_1));

        assertEquals(out.deleteReports(ID1), REPORTS_DTO_1);
    }

    @Test
    void reportsNotFoundExceptionTestWhenFind() {
        when(reportsRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(ReportsNotFoundException.class, () -> out.findReports(ID1));
    }

    @Test
    void reportsNotFoundExceptionTestWhenDelete() {
        when(reportsRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(ReportsNotFoundException.class, () -> out.deleteReports(ID1));
    }

    @Test
    void reportsNotFoundExceptionTestWhenEdit() {
        when(reportsRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(ReportsNotFoundException.class, () -> out.editReports(ID1, REPORTS_DTO_1 ));
    }

}