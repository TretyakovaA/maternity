package pro.sky.maternity.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.maternity.dto.ReportsDto;
import pro.sky.maternity.exception.ReportsNotFoundException;
import pro.sky.maternity.exception.UserNotFoundException;
import pro.sky.maternity.mapper.ReportsDtoMapper;
import pro.sky.maternity.model.Reports;
import pro.sky.maternity.repository.ReportsRepository;

/**
 * Сервис для работы с обращениями пользователей
 */
@Service
public class ReportsService {
    private final ReportsRepository reportsRepository;

    Logger logger = LoggerFactory.getLogger(ReportsService.class);

    private final ReportsDtoMapper reportsDtoMapper;

    public ReportsService(ReportsRepository reportsRepository, ReportsDtoMapper reportsDtoMapper) {
        this.reportsRepository = reportsRepository;
        this.reportsDtoMapper = reportsDtoMapper;
    }

    /**
     * метод для добавления обратной связи пользователя
     * использует метод {@link  org.springframework.data.jpa.repository.JpaRepository#save(Object)}
     * @param reportsDto
     * @return сохраненное обращение
     */
    public ReportsDto addReports(ReportsDto reportsDto) {
        Reports reports = reportsDtoMapper.toEntity((reportsDto));
        logger.info("Отчет добавлен: " + reports.getId());
        return reportsDtoMapper.toDto(reportsRepository.save(reports));
    }

    /**
     * метод для поиска обращения
     * использует метод {@link  org.springframework.data.jpa.repository.JpaRepository#findById(Object)}
     * @param id обращения
     * @throws ReportsNotFoundException
     * @return найденное обращение
     */
    public ReportsDto findReports(long id) {
        Reports findReports = reportsRepository.findById(id).orElseThrow(() -> {
            logger.info("Отчет с id " + id + " не найден");
            throw new ReportsNotFoundException(id);
        });

        logger.info("Отчет с id " + id + " найден");
        System.out.println(findReports);
        return reportsDtoMapper.toDto(findReports);
    }

    /**
     * метод для редактирования обращения
     * использует метод {@link  org.springframework.data.jpa.repository.JpaRepository#findById(Object)}
     * использует метод {@link  org.springframework.data.jpa.repository.JpaRepository#save(Object)}
     * @param id обращения
     * @param reportsDto редактируемая информация
     * @throws ReportsNotFoundException
     * @return отредактированное обращение
     */
    public ReportsDto editReports(long id, ReportsDto reportsDto) {
        Reports oldReports = reportsRepository.findById(id).orElseThrow(()
                -> { throw new ReportsNotFoundException(id);});
        oldReports.setUser(reportsDto.getUser());
       oldReports.setText(reportsDto.getText());
        oldReports.setDate(reportsDto.getDate());
        oldReports.setChatId(reportsDto.getChatId());
        oldReports.setPhoto(reportsDto.getPhoto());
        logger.info("Отчет с id "+id +" изменен");
        return reportsDtoMapper.toDto(reportsRepository.save(oldReports));
    }

    /**
     * метод для удаления обращения
     *  использует метод {@link  org.springframework.data.jpa.repository.JpaRepository#findById(Object)}
     * @param id обращения
     * @throws ReportsNotFoundException
     * @return удаленное обращения
     */
    public ReportsDto deleteReports (long id){
        Reports reports = reportsRepository.findById(id).orElseThrow(()
                -> { throw new ReportsNotFoundException(id);});
        reportsRepository.delete(reports);
        logger.info("Отчет с id "+id+" удален");
        return reportsDtoMapper.toDto(reports);
    }
}
