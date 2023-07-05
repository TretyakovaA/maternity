package pro.sky.maternity.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.maternity.dto.UserDto;
import pro.sky.maternity.service.UserService;


import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "Метод получения информации о пользователе по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Найденный пользователь",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    // array = @ArraySchema(schema = @Schema(implementation = UserDto.class))
                                    schema = @Schema(implementation = UserDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Пользователь не найден"
                    )
            },
            tags = "Users"
    )
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserInfo(@Parameter(description = "id пользователя", example = "123") @PathVariable long id) {
        UserDto userDto = userService.findUser(id);
        return ResponseEntity.ok(userDto);
    }

    @Operation(
            summary = "Метод для создания пользователя",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Пользователь создан",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    // array = @ArraySchema(schema = @Schema(implementation = UserDto.class))
                                    schema = @Schema(implementation = UserDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Пользователь не создан"
                    )
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Cоздаваемый пользователь",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            //array = @ArraySchema(schema = @Schema(implementation = UserDto.class))
                            schema = @Schema(implementation = UserDto.class)
                    )
            ),
            tags = "Users"
    )
    @PostMapping
    public UserDto createUser(@RequestBody @Valid UserDto userDto) {
        return userService.addUser(userDto);
    }

    @Operation(
            summary = "Метод для редактирования пользователя",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Пользователь отредактирован",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    // array = @ArraySchema(schema = @Schema(implementation = UserDto.class))
                                    schema = @Schema(implementation = UserDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Пользователь не отредактирован"
                    )
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Редактируемая информация",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            //array = @ArraySchema(schema = @Schema(implementation = UserDto.class))
                            schema = @Schema(implementation = UserDto.class)
                    )
            ),
            tags = "Users"
    )
    @PutMapping("/{id}")
    public UserDto editUser(@Parameter(description = "id пользователя", example = "123") @PathVariable long id, @RequestBody @Valid UserDto userDto) {
        return userService.editUser(id, userDto);
    }

    @Operation(
            summary = "Метод для удалени пользователя",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Пользователь удален",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    // array = @ArraySchema(schema = @Schema(implementation = UserDto.class))
                                    schema = @Schema(implementation = UserDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Пользователь не удален"
                    )
            },
            tags = "Users"
    )
    @DeleteMapping("/{id}")
    public UserDto deleteUser(@Parameter(description = "id пользователя", example = "123") @PathVariable long id) {
        return userService.deleteUser(id);
    }


}
