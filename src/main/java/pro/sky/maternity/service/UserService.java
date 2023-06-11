package pro.sky.maternity.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.maternity.dto.UserDto;
import pro.sky.maternity.exception.UserNotFoundException;
import pro.sky.maternity.mapper.UserDtoMapper;
import pro.sky.maternity.model.User;
import pro.sky.maternity.repository.UserRepository;

/**
 * Сервис для работы с пользователем
 */
@Service
public class UserService {
    private final UserRepository userRepository;
    Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserDtoMapper userDtoMapper;

    public UserService(UserRepository userRepository, UserDtoMapper userDtoMapper) {
        this.userRepository = userRepository;
        this.userDtoMapper = userDtoMapper;
    }

    /**
     * метод для добавления пользователя
     * использует метод {@link  org.springframework.data.jpa.repository.JpaRepository#save(Object)}
     * @param userDto
     * @return сохраненного пользователя
     */
    public UserDto addUser(UserDto userDto) {
        User user = userDtoMapper.toEntity((userDto));
        logger.info("Пользователь добавлен: " + user.getId());
        return userDtoMapper.toDto(userRepository.save(user));
    }

    /**
     * метод для поиска пользователя
     * использует метод {@link  org.springframework.data.jpa.repository.JpaRepository#findById(Object)}
     * @param id пользователя
     * @throws UserNotFoundException
     * @return найденный пользователь
     */
    public UserDto findUser(long id) {
        User findUser = userRepository.findById(id).orElseThrow(() -> {
            logger.info("Пользователь с id " + id + " не найден");
            new UserNotFoundException(id);
            return null;
        });

        logger.info("Пользователь с id " + id + " найден");
        System.out.println(findUser);
        return userDtoMapper.toDto(findUser);
    }

    /**
     * метод для редактирования данных пользователя
     * использует метод {@link  org.springframework.data.jpa.repository.JpaRepository#findById(Object)}
     * использует метод {@link  org.springframework.data.jpa.repository.JpaRepository#save(Object)}
     * @param id пользователя
     * @param userDto редактируемая информация
     * @throws UserNotFoundException
     * @return отредактированный пользователь
     */
    public UserDto editUser(long id, UserDto userDto) {
        User oldUser = userRepository.findById(id).orElseThrow(()
                -> new UserNotFoundException(id));
        oldUser.setName(userDto.getName());
        oldUser.setReports(userDto.getReports());
        oldUser.setMaternityHospital(userDto.getMaternityHospital());
        oldUser.setChatId(userDto.getChatId());
        oldUser.setChildBirthday(userDto.getChildBirthday());
        logger.info("Пользователь с id "+id +" изменен");
        return userDtoMapper.toDto(userRepository.save(oldUser));
    }

    /**
     * метод для удаления пользователя
     *  использует метод {@link  org.springframework.data.jpa.repository.JpaRepository#findById(Object)}
     * @param id пользователя
     * @throws UserNotFoundException
     * @return удаленный пользователь
     */
    public UserDto deleteUser (long id){
        User user = userRepository.findById(id).orElseThrow(()
                -> new UserNotFoundException(id));
        userRepository.delete(user);
        logger.info("Пользователь с id "+id+" удален");
        return userDtoMapper.toDto(user);
    }
}
