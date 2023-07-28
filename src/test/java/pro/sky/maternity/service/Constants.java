package pro.sky.maternity.service;

import org.apache.commons.lang3.ObjectUtils;
import pro.sky.maternity.dto.MaternityHospitalDto;
import pro.sky.maternity.dto.ReportsDto;
import pro.sky.maternity.dto.UserDto;
import pro.sky.maternity.model.MaternityHospital;
import pro.sky.maternity.model.Reports;
import pro.sky.maternity.model.User;

import java.time.LocalDateTime;

public class Constants {
    public static final Boolean TRUE = Boolean.TRUE;
    public static final Boolean FALSE = Boolean.FALSE;
    public static final long ID1 = 1L;
    public static final long ID2 = 2L;

    public static final long CHAT_ID_1 = 1111L;
    public static final long CHAT_ID_2 = 2222L;
    public static final String NAME_1 = "TEST_NAME_1";
    public static final String NAME_2 = "TEST_NAME_2";

    public static final String ADDRESS_1 = "ADDRESS_1";
    public static final String ADDRESS_2 = "ADDRESS_2";

    public static final String LOCATION_MAP_1 = "LOCATION_MAP_1";
    public static final String LOCATION_MAP_2 = "LOCATION_MAP_2";

    public static final LocalDateTime DATE_1 = LocalDateTime.parse("2015-08-25T13:21:05.12345");
    public static final LocalDateTime DATE_2 = LocalDateTime.parse("2016-08-25T13:21:05.12345");

    public static final String TEXT_1 = "TEXT_1";
    public static final String TEXT_2 = "TEXT_2";

    public static final String PHOTO_1 = "PHOTO_1";
    public static final String PHOTO_2 = "PHOTO_2";

    public static final User USER_1 = new User(ID1, CHAT_ID_1, NAME_1);
    public static final User USER_2 = new User(ID2, CHAT_ID_2, NAME_2);

    public static final UserDto USER_DTO_1 = new UserDto(ID1, CHAT_ID_1, NAME_1, null, null, null);
    public static final UserDto USER_DTO_2 = new UserDto(ID2, CHAT_ID_2, NAME_2, null, null, null);


    public static final MaternityHospital MATERNITY_HOSPITAL_1 = new MaternityHospital(ID1, NAME_1, ADDRESS_1, LOCATION_MAP_1);
    public static final MaternityHospital MATERNITY_HOSPITAL_2 = new MaternityHospital(ID2, NAME_2, ADDRESS_2, LOCATION_MAP_2);
    public static final MaternityHospitalDto MATERNITY_HOSPITAL_DTO_1 = new MaternityHospitalDto(ID1, NAME_1, ADDRESS_1, LOCATION_MAP_1);
    public static final MaternityHospitalDto MATERNITY_HOSPITAL_DTO_2 = new MaternityHospitalDto(ID2, NAME_2, ADDRESS_2, LOCATION_MAP_2);

    public static final Reports REPORTS_1 = new Reports(ID1, CHAT_ID_1, DATE_1, TEXT_1, PHOTO_1, USER_1);
    public static final Reports REPORTS_2 = new Reports(ID1, CHAT_ID_2, DATE_2, TEXT_2, PHOTO_2, USER_2);
    public static final ReportsDto REPORTS_DTO_1 = new ReportsDto(ID1, CHAT_ID_1, DATE_1, TEXT_1, PHOTO_1, USER_1);
    public static final ReportsDto REPORTS_DTO_2 = new ReportsDto(ID1, CHAT_ID_2, DATE_2, TEXT_2, PHOTO_2, USER_2);
}