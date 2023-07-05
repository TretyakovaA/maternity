package pro.sky.maternity.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.maternity.dto.MaternityHospitalDto;
import pro.sky.maternity.dto.UserDto;
import pro.sky.maternity.service.MaternityHospitalService;

import javax.validation.Valid;

@RestController
@RequestMapping("/maternity/hospital")
public class MaternityHospitalController {
    private final MaternityHospitalService maternityHospitalService;

    public MaternityHospitalController(MaternityHospitalService maternityHospitalService) {
        this.maternityHospitalService = maternityHospitalService;
    }

    @Operation(
            summary = "Метод получения информации о роддоме по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Найденный роддом",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    // array = @ArraySchema(schema = @Schema(implementation = UserDto.class))
                                    schema = @Schema(implementation = MaternityHospitalDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Роддом не найден"
                    )
            },
            tags = "MaternityHospital"
    )
    @GetMapping("/{id}")
    public ResponseEntity<MaternityHospitalDto> getMaternityHospitalInfo (@PathVariable long id){
        MaternityHospitalDto maternityHospitalDto = maternityHospitalService.findMaternityHospital(id);
        return ResponseEntity.ok(maternityHospitalDto);
    }
    @Operation(
            summary = "Метод для создания роддома",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Роддом создан",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    // array = @ArraySchema(schema = @Schema(implementation = UserDto.class))
                                    schema = @Schema(implementation = MaternityHospitalDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Роддом не создан"
                    )
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Создаваемый роддом",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            //array = @ArraySchema(schema = @Schema(implementation = MaternityHospitalDto.class))
                            schema = @Schema(implementation = MaternityHospitalDto.class)
                    )
            ),
            tags = "MaternityHospital"
    )
    @PostMapping
    public MaternityHospitalDto createMaternityHospital (@RequestBody @Valid MaternityHospitalDto maternityHospitalDto) {
        return maternityHospitalService.addMaternityHospital(maternityHospitalDto);
    }

    @Operation(
            summary = "Метод для редактирования Роддома",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Роддом отредактирован",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    // array = @ArraySchema(schema = @Schema(implementation = UserDto.class))
                                    schema = @Schema(implementation = MaternityHospitalDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Роддом не отредактирован"
                    )
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Роддом информация",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                           // array = @ArraySchema(schema = @Schema(implementation = MaternityHospitalDto.class))
                            schema = @Schema(implementation = MaternityHospitalDto.class)
                    )
            ),
            tags = "MaternityHospital"
    )
    @PutMapping ("/{id}")
    public MaternityHospitalDto editMaternityHospital (@PathVariable long id, @RequestBody @Valid MaternityHospitalDto maternityHospitalDto){
        return maternityHospitalService.editMaternityHospital(id, maternityHospitalDto);
    }
    @Operation(
            summary = "Метод для удаления роддома",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Роддом удален",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    // array = @ArraySchema(schema = @Schema(implementation = UserDto.class))
                                    schema = @Schema(implementation = MaternityHospitalDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Роддом не удален"
                    )
            },
            tags = "MaternityHospital"
    )
    @DeleteMapping ("/{id}")
    public MaternityHospitalDto deleteMaternityHospital (@PathVariable long id){
        return maternityHospitalService.deleteMaternityHospital(id);
    }
}
