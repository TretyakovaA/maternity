package pro.sky.maternity.mapper;

import org.springframework.stereotype.Component;
import pro.sky.maternity.dto.MaternityHospitalDto;
import pro.sky.maternity.model.MaternityHospital;

import javax.swing.text.html.parser.Entity;
@Component
public class MaternityHospitalDtoMapper {
    public MaternityHospitalDto toDto (MaternityHospital maternityHospital){
        MaternityHospitalDto maternityHospitalDto = new MaternityHospitalDto();
        maternityHospitalDto.setId(maternityHospital.getId());
        maternityHospitalDto.setAddress(maternityHospital.getAddress());
        maternityHospitalDto.setLocationMap(maternityHospital.getLocationMap());
        maternityHospitalDto.setNumber(maternityHospital.getNumber());
        maternityHospitalDto.setUsers(maternityHospital.getUsers());
        return  maternityHospitalDto;
    }

    public  MaternityHospital toEntity (MaternityHospitalDto maternityHospitalDto){
        MaternityHospital maternityHospital = new MaternityHospital();
        maternityHospital.setId(maternityHospitalDto.getId());
        maternityHospital.setAddress(maternityHospitalDto.getAddress());
        maternityHospital.setLocationMap(maternityHospitalDto.getLocationMap());
        maternityHospital.setNumber(maternityHospitalDto.getNumber());
        maternityHospital.setUsers(maternityHospitalDto.getUsers());
        return maternityHospital;
    }
}
