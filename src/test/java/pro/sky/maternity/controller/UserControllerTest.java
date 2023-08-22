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
import pro.sky.maternity.controller.UserController;
import pro.sky.maternity.dto.UserDto;
import pro.sky.maternity.mapper.UserDtoMapper;
import pro.sky.maternity.model.User;
import pro.sky.maternity.repository.UserRepository;
import pro.sky.maternity.service.UserService;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @SpyBean
    private UserService userService;


    @Autowired
    private ObjectMapper objectMapper;
    @SpyBean
    private UserDtoMapper userDtoMapper;

    public User resultUser() {
        User user = new User();
        user.setId(1L);
        user.setName("Valya");
        user.setChatId(123456789);
        return user;
    }

    @Test
    void getUserInfo() throws Exception {
        long id = 1L;
        String name = "Valya";
        long chatId = 123456789;

        String jsonResult = objectMapper.writeValueAsString(userDtoMapper.toDto(resultUser()));

        User foundUser = new User();
        foundUser.setId(id);
        foundUser.setName(name);
        foundUser.setChatId(chatId);

        when(userRepository.findById(any(long.class))).thenReturn(Optional.of(foundUser));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/user/" + id)
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResult));
    }


    @Test
    void createUser() throws Exception {
        long id = 1L;
        String name = "Valya";
        long chatId = 123456789;

        String jsonResult = objectMapper.writeValueAsString(userDtoMapper.toDto(resultUser()));

        User addedUser = new User();
        addedUser.setId(id);
        addedUser.setName(name);
        addedUser.setChatId(chatId);

        UserDto newUser = new UserDto();
        newUser.setName(name);
        newUser.setChatId(chatId);

        when(userRepository.save(any(User.class))).thenReturn(addedUser);
        when(userRepository.findById(any(long.class))).thenReturn(Optional.of(addedUser));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/user") //посылаем
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUser))
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResult));
    }

    @Test
    void editUser() throws Exception {
        long id = 1L;
        String name = "Valya";
        long chatId = 123456789;

        String jsonResult = objectMapper.writeValueAsString(userDtoMapper.toDto(resultUser()));

        User changedUser = new User();
        changedUser.setId(id);
        changedUser.setName(name);
        changedUser.setChatId(chatId);

        UserDto changesToUser = new UserDto();
        changesToUser.setName("Valya");
        changesToUser.setChatId(chatId);

        when(userRepository.save(any(User.class))).thenReturn(changedUser);
        when(userRepository.findById(any(long.class))).thenReturn(Optional.of(changedUser));

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/user/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(changesToUser))
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResult));
    }

    @Test
    void deleteUser() throws Exception {
        long id = 1L;
        String name = "Valya";
        long chatId = 123456789;

        String jsonResult = objectMapper.writeValueAsString(userDtoMapper.toDto(resultUser()));

        User deletedUser = new User();
        deletedUser.setId(id);
        deletedUser.setName(name);
        deletedUser.setChatId(chatId);

        when(userRepository.findById(any(long.class))).thenReturn(Optional.of(deletedUser));
        doNothing().when(userRepository).deleteById(id);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/user/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResult));
    }
}
