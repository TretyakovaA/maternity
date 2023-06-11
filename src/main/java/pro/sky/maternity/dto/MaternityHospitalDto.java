package pro.sky.maternity.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import pro.sky.maternity.model.User;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Objects;

public class MaternityHospitalDto {

    private long id;

    private long number;

    private String address;


    private String locationMap;

    private List<User> users;

    public MaternityHospitalDto(long id, long number, String address, String locationMap, List<User> users) {
        this.id = id;
        this.number = number;
        this.address = address;
        this.locationMap = locationMap;
        this.users = users;
    }

    public MaternityHospitalDto() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocationMap() {
        return locationMap;
    }

    public void setLocationMap(String locationMap) {
        this.locationMap = locationMap;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MaternityHospitalDto that = (MaternityHospitalDto) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
