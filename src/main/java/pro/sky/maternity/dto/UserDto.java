package pro.sky.maternity.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import pro.sky.maternity.model.MaternityHospital;
import pro.sky.maternity.model.Reports;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;


public class UserDto {

    private long id;

    private long chatId;

    private String name;

    private LocalDateTime childBirthday;


    private MaternityHospital maternityHospital;

    private List<Reports> reports;

    public UserDto(long id, long chatId, String name, LocalDateTime childBirthday, MaternityHospital maternityHospital, List<Reports> reports) {
        this.id = id;
        this.chatId = chatId;
        this.name = name;
        this.childBirthday = childBirthday;
        this.maternityHospital = maternityHospital;
        this.reports = reports;
    }

    public UserDto() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getChildBirthday() {
        return childBirthday;
    }

    public void setChildBirthday(LocalDateTime childBirthday) {
        this.childBirthday = childBirthday;
    }

    public MaternityHospital getMaternityHospital() {
        return maternityHospital;
    }

    public void setMaternityHospital(MaternityHospital maternityHospital) {
        this.maternityHospital = maternityHospital;
    }

    public List<Reports> getReports() {
        return reports;
    }

    public void setReports(List<Reports> reports) {
        this.reports = reports;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return id == userDto.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
