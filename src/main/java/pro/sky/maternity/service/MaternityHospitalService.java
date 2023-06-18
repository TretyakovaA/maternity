package pro.sky.maternity.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.maternity.dto.MaternityHospitalDto;
import pro.sky.maternity.exception.ReportsNotFoundException;
import pro.sky.maternity.mapper.MaternityHospitalDtoMapper;
import pro.sky.maternity.model.MaternityHospital;
import pro.sky.maternity.repository.MaternityHospitalRepository;
import pro.sky.maternity.exception.MaternityHospitalNotFoundException;

/**
 * Сервис для работы с роддомами
 */
@Service
public class MaternityHospitalService {

    private final MaternityHospitalRepository maternityHospitalRepository;

    Logger logger = LoggerFactory.getLogger(MaternityHospitalService.class);

    private final MaternityHospitalDtoMapper maternityHospitalDtoMapper;

    public MaternityHospitalService(MaternityHospitalRepository maternityHospitalRepository, MaternityHospitalDtoMapper maternityHospitalDtoMapper) {
        this.maternityHospitalRepository = maternityHospitalRepository;
        this.maternityHospitalDtoMapper = maternityHospitalDtoMapper;
    }

    /**
     * метод для добавления обратной роддома
     * использует метод {@link  org.springframework.data.jpa.repository.JpaRepository#save(Object)}
     * @param maternityHospitalDto
     * @return сохраненный роддом
     */
    public MaternityHospitalDto addMaternityHospital(MaternityHospitalDto maternityHospitalDto) {
        MaternityHospital maternityHospital = maternityHospitalDtoMapper.toEntity((maternityHospitalDto));
        logger.info("Роддом добавлен: " + maternityHospital.getNumber());
        return maternityHospitalDtoMapper.toDto(maternityHospitalRepository.save(maternityHospital));
    }

    /**
     * метод для поиска роддома
     * использует метод {@link  org.springframework.data.jpa.repository.JpaRepository#findById(Object)}
     * @param id роддома
     * @throws MaternityHospitalNotFoundException
     * @return найденный роддом
     */
    public MaternityHospitalDto findMaternityHospital(long id) {
        MaternityHospital findMaternityHospital = maternityHospitalRepository.findById(id).orElseThrow(() -> {
            logger.info("Роддом с id " + id + " не найден");
            new MaternityHospitalNotFoundException(id);
            return null;
        });

        logger.info("Роддом с id " + id + " найден");
        System.out.println(findMaternityHospital);
        return maternityHospitalDtoMapper.toDto(findMaternityHospital);
    }

    /**
     * метод для редактирования роддома
     * использует метод {@link  org.springframework.data.jpa.repository.JpaRepository#findById(Object)}
     * использует метод {@link  org.springframework.data.jpa.repository.JpaRepository#save(Object)}
     * @param id роддома
     * @param maternityHospitalDto редактируемая информация
     * @throws MaternityHospitalNotFoundException
     * @return отредактированный роддом
     */
    public MaternityHospitalDto editMaternityHospital(long id, MaternityHospitalDto maternityHospitalDto) {
        MaternityHospital oldMaternityHospital = maternityHospitalRepository.findById(id).orElseThrow(()
                -> new MaternityHospitalNotFoundException(id));
        oldMaternityHospital.setNumber(maternityHospitalDto.getNumber());
        oldMaternityHospital.setAddress(maternityHospitalDto.getAddress());
        oldMaternityHospital.setLocationMap(maternityHospitalDto.getLocationMap());
        logger.info("Роддом с id "+id +" изменен");
        return maternityHospitalDtoMapper.toDto(maternityHospitalRepository.save(oldMaternityHospital));
    }

    /**
     * метод для удаления роддома
     *  использует метод {@link  org.springframework.data.jpa.repository.JpaRepository#findById(Object)}
     * @param id роддом
     * @throws MaternityHospitalNotFoundException
     * @return удаленный роддом
     */
    public MaternityHospitalDto deleteMaternityHospital (long id){
        MaternityHospital maternityHospital = maternityHospitalRepository.findById(id).orElseThrow(()
        -> new MaternityHospitalNotFoundException(id));
        maternityHospitalRepository.delete(maternityHospital);
        logger.info("Роддом с id "+id+" удален");
        return maternityHospitalDtoMapper.toDto(maternityHospital);
    }
}
