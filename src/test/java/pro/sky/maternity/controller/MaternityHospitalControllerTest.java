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
import pro.sky.maternity.controller.MaternityHospitalController;
import pro.sky.maternity.dto.MaternityHospitalDto;
import pro.sky.maternity.mapper.MaternityHospitalDtoMapper;
import pro.sky.maternity.mapper.UserDtoMapper;
import pro.sky.maternity.model.MaternityHospital;
import pro.sky.maternity.repository.MaternityHospitalRepository;
import pro.sky.maternity.service.MaternityHospitalService;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = MaternityHospitalController.class)
public class MaternityHospitalControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MaternityHospitalRepository maternityHospitalRepository;

    @SpyBean
    private MaternityHospitalService maternityHospitalService;


    @Autowired
    private ObjectMapper objectMapper;
    @SpyBean
    private MaternityHospitalDtoMapper maternityHospitalDtoMapper;

    public MaternityHospital resultMaternityHospital() {
        MaternityHospital maternityHospital = new MaternityHospital();
        maternityHospital.setId(1L);
        maternityHospital.setName("Valya");
        maternityHospital.setAddress("г. Москва, Таймырская ул., д.6");
        return maternityHospital;
    }

    @Test
    void getMaternityHospitalInfo() throws Exception {
        long id = 1L;
        String name = "Valya";
        String address = "г. Москва, Таймырская ул., д.6";

        String jsonResult = objectMapper.writeValueAsString(maternityHospitalDtoMapper.toDto(resultMaternityHospital()));

        MaternityHospital foundMaternityHospital = new MaternityHospital();
        foundMaternityHospital.setId(id);
        foundMaternityHospital.setName(name);
        foundMaternityHospital.setAddress(address);

        when(maternityHospitalRepository.findById(any(long.class))).thenReturn(Optional.of(foundMaternityHospital));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/maternity/hospital/" + id)
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResult));
    }


    @Test
    void createMaternityHospital() throws Exception {
        long id = 1L;
        String name = "Valya";
        String address = "г. Москва, Таймырская ул., д.6";

        String jsonResult = objectMapper.writeValueAsString(maternityHospitalDtoMapper.toDto(resultMaternityHospital()));

        MaternityHospital addedMaternityHospital = new MaternityHospital();
        addedMaternityHospital.setId(id);
        addedMaternityHospital.setName(name);
        addedMaternityHospital.setAddress(address);

        MaternityHospitalDto newMaternityHospital = new MaternityHospitalDto();
        newMaternityHospital.setName(name);
        newMaternityHospital.setAddress(address);

        when(maternityHospitalRepository.save(any(MaternityHospital.class))).thenReturn(addedMaternityHospital);
        when(maternityHospitalRepository.findById(any(long.class))).thenReturn(Optional.of(addedMaternityHospital));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/maternity/hospital") //посылаем
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newMaternityHospital))
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResult));
    }

    @Test
    void editMaternityHospital() throws Exception {
        long id = 1L;
        String name = "Valya";
        String address = "г. Москва, Таймырская ул., д.6";

        String jsonResult = objectMapper.writeValueAsString(maternityHospitalDtoMapper.toDto(resultMaternityHospital()));

        MaternityHospital changedMaternityHospital = new MaternityHospital();
        changedMaternityHospital.setId(id);
        changedMaternityHospital.setName(name);
        changedMaternityHospital.setAddress(address);

        MaternityHospitalDto changesToMaternityHospital = new MaternityHospitalDto();
        changesToMaternityHospital.setName("Valya");
        changesToMaternityHospital.setAddress(address);

        when(maternityHospitalRepository.save(any(MaternityHospital.class))).thenReturn(changedMaternityHospital);
        when(maternityHospitalRepository.findById(any(long.class))).thenReturn(Optional.of(changedMaternityHospital));

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/maternity/hospital/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(changesToMaternityHospital))
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResult));
    }

    @Test
    void deleteMaternityHospital() throws Exception {
        long id = 1L;
        String name = "Valya";
        String address = "г. Москва, Таймырская ул., д.6";

        String jsonResult = objectMapper.writeValueAsString(maternityHospitalDtoMapper.toDto(resultMaternityHospital()));

        MaternityHospital deletedMaternityHospital = new MaternityHospital();
        deletedMaternityHospital.setId(id);
        deletedMaternityHospital.setName(name);
        deletedMaternityHospital.setAddress(address);

        when(maternityHospitalRepository.findById(any(long.class))).thenReturn(Optional.of(deletedMaternityHospital));
        doNothing().when(maternityHospitalRepository).deleteById(id);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/maternity/hospital/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResult));
    }
}
