package pro.sky.maternity.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.maternity.exception.UserNotFoundException;
import pro.sky.maternity.mapper.UserDtoMapper;
import pro.sky.maternity.model.User;
import pro.sky.maternity.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

import static pro.sky.maternity.service.Constants.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService out;

    @Mock
    private UserDtoMapper userDtoMapper;


    @Test
    void addUser() {
        when(userDtoMapper.toDto(USER_1)).thenReturn(USER_DTO_1);
        when(userDtoMapper.toEntity(USER_DTO_1)).thenReturn(USER_1);
        when(userRepository.save(any(User.class))).thenReturn(USER_1);

        assertEquals(out.addUser(USER_DTO_1), USER_DTO_1);
    }

    @Test
    void findUser() {
        when(userDtoMapper.toDto(USER_1)).thenReturn(USER_DTO_1);
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(USER_1));

        assertEquals(out.findUser(ID1), USER_DTO_1);
    }

    @Test
    void editUser() {
        when(userDtoMapper.toDto(USER_1)).thenReturn(USER_DTO_1);
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(USER_1));
        when(userRepository.save(any(User.class))).thenReturn(USER_1);

        assertEquals(out.editUser(ID1, USER_DTO_1), USER_DTO_1);
    }

    @Test
    void deleteUser() {
        when(userDtoMapper.toDto(USER_1)).thenReturn(USER_DTO_1);
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(USER_1));

        assertEquals(out.deleteUser(ID1), USER_DTO_1);
    }

    @Test
    void userNotFoundExceptionTestWhenFind() {
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> out.findUser(ID1));
    }

    @Test
    void userNotFoundExceptionTestWhenDelete() {
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> out.deleteUser(ID1));
    }

    @Test
    void userNotFoundExceptionTestWhenEdit() {
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> out.editUser(ID1, USER_DTO_1 ));
    }

}