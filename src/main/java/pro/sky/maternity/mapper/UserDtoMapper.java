package pro.sky.maternity.mapper;

import org.springframework.stereotype.Component;
import pro.sky.maternity.dto.UserDto;
import pro.sky.maternity.model.User;
@Component
public class UserDtoMapper {

    public UserDto toDto (User user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setChatId(userDto.getChatId());
        userDto.setReports(user.getReports());
        userDto.setChildBirthday(user.getChildBirthday());
        userDto.setMaternityHospital(user.getMaternityHospital());
        return  userDto;
    }

    public User toEntity (UserDto userDto){
        User user = new User ();
        user.setId(userDto.getId());
        user.setChatId(userDto.getChatId());
        user.setName(userDto.getName());
        user.setChildBirthday(userDto.getChildBirthday());
        user.setMaternityHospital(userDto.getMaternityHospital());
        user.setReports(userDto.getReports());
        return user;
    }
}
