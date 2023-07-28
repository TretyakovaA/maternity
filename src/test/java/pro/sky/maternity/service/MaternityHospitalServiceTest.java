package pro.sky.maternity.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.maternity.exception.MaternityHospitalNotFoundException;
import pro.sky.maternity.mapper.MaternityHospitalDtoMapper;
import pro.sky.maternity.model.MaternityHospital;
import pro.sky.maternity.repository.MaternityHospitalRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

import static pro.sky.maternity.service.Constants.*;

@ExtendWith(MockitoExtension.class)
class MaternityHospitalServiceTest {

    @Mock
    private MaternityHospitalRepository maternityHospitalRepository;

    @InjectMocks
    private MaternityHospitalService out;

    @Mock
    private MaternityHospitalDtoMapper maternityHospitalDtoMapper;


    @Test
    void addMaternityHospital() {
        when(maternityHospitalDtoMapper.toDto(MATERNITY_HOSPITAL_1)).thenReturn(MATERNITY_HOSPITAL_DTO_1);
        when(maternityHospitalDtoMapper.toEntity(MATERNITY_HOSPITAL_DTO_1)).thenReturn(MATERNITY_HOSPITAL_1);
        when(maternityHospitalRepository.save(any(MaternityHospital.class))).thenReturn(MATERNITY_HOSPITAL_1);

        assertEquals(out.addMaternityHospital(MATERNITY_HOSPITAL_DTO_1), MATERNITY_HOSPITAL_DTO_1);
    }

    @Test
    void findMaternityHospital() {
        when(maternityHospitalDtoMapper.toDto(MATERNITY_HOSPITAL_1)).thenReturn(MATERNITY_HOSPITAL_DTO_2);
        when(maternityHospitalRepository.findById(any(Long.class))).thenReturn(Optional.of(MATERNITY_HOSPITAL_1));

        assertEquals(out.findMaternityHospital(ID1), MATERNITY_HOSPITAL_DTO_2);
    }

    @Test
    void editMaternityHospital() {
        when(maternityHospitalDtoMapper.toDto(MATERNITY_HOSPITAL_1)).thenReturn(MATERNITY_HOSPITAL_DTO_1);
        when(maternityHospitalRepository.findById(any(Long.class))).thenReturn(Optional.of(MATERNITY_HOSPITAL_1));
        when(maternityHospitalRepository.save(any(MaternityHospital.class))).thenReturn(MATERNITY_HOSPITAL_1);


        assertEquals(out.editMaternityHospital(ID1, MATERNITY_HOSPITAL_DTO_1), MATERNITY_HOSPITAL_DTO_1);
    }

    @Test
    void deleteMaternityHospital() {
        when(maternityHospitalDtoMapper.toDto(MATERNITY_HOSPITAL_1)).thenReturn(MATERNITY_HOSPITAL_DTO_1);
        when(maternityHospitalRepository.findById(any(Long.class))).thenReturn(Optional.of(MATERNITY_HOSPITAL_1));

        assertEquals(out.deleteMaternityHospital(ID1), MATERNITY_HOSPITAL_DTO_1);
    }

    @Test
    void userNotFoundExceptionTestWhenFind() {
        when(maternityHospitalRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(MaternityHospitalNotFoundException.class, () -> out.findMaternityHospital(ID1));
    }

    @Test
    void userNotFoundExceptionTestWhenDelete() {
        when(maternityHospitalRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(MaternityHospitalNotFoundException.class, () -> out.deleteMaternityHospital(ID1));
    }

    @Test
    void userNotFoundExceptionTestWhenEdit() {
        when(maternityHospitalRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(MaternityHospitalNotFoundException.class, () -> out.editMaternityHospital(ID1, MATERNITY_HOSPITAL_DTO_1 ));
    }

}