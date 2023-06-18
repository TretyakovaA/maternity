package pro.sky.maternity.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.maternity.dto.ReportsDto;
import pro.sky.maternity.dto.UserDto;
import pro.sky.maternity.service.ReportsService;

import javax.validation.Valid;

@RestController
@RequestMapping("/reports")
public class ReportsController {
    private final ReportsService reportsService;

    public ReportsController(ReportsService reportsService) {
        this.reportsService = reportsService;
    }
    @Operation(
            summary = "Метод получения отчета по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Найденный отчет",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ReportsDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Отчет не найден"
                    )
            },
            tags = "Reports"
    )
  @GetMapping("/{id}")
    public ResponseEntity<ReportsDto> getReports (@PathVariable long id) {
      ReportsDto reportsDto = reportsService.findReports(id);
      return ResponseEntity.ok(reportsDto);
  }

    @Operation(
            summary = "Метод для создания отчета",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Отчет создан",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    // array = @ArraySchema(schema = @Schema(implementation = UserDto.class))
                                    schema = @Schema(implementation = ReportsDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Отчет не создан"
                    )
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Cоздаваемый отчет",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ReportsDto.class))
                    )
            ),
            tags = "Reports"
    )
    @PostMapping
    public ReportsDto createReports (@RequestBody @Valid ReportsDto reportsDto) {
        return reportsService.addReports(reportsDto);
    }

    @Operation(
            summary = "Метод для редактирования отчета",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Отчет отредактирован",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    // array = @ArraySchema(schema = @Schema(implementation = UserDto.class))
                                    schema = @Schema(implementation = ReportsDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Отчет не отредактирован"
                    )
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Редактируемая информация",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ReportsDto.class))
                    )
            ),
            tags = "Reports"
    )

    @PutMapping ("/{id}")
    public ReportsDto editReports (@PathVariable long id, @RequestBody @Valid ReportsDto reportsDto){
        return reportsService.editReports(id, reportsDto);
    }
    @Operation(
            summary = "Метод для удаления отчета",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Отчет удален",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    // array = @ArraySchema(schema = @Schema(implementation = UserDto.class))
                                    schema = @Schema(implementation = UserDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Отчет не удален"
                    )
            },
            tags = "Reports"
    )
    @DeleteMapping ("/{id}")
    public ReportsDto deleteUser (@PathVariable long id){
        return reportsService.deleteReports(id);
    }
}
