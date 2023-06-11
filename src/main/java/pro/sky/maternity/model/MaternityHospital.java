package pro.sky.maternity.model;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Роддом
 */
@Entity
@Table(name = "maternities")
public class MaternityHospital {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    /**
     * номер роддома
     */
    @Column(name = "number")
    private long number;

    /**
     * адрес роддома
     */
    @Column(name = "address")
    private String address;

    /**
     * схема проезда
     */

    @Column(name = "location_map")
    private String locationMap;

    @JsonIgnore
    @OneToMany(mappedBy = "maternityHospital")
    private List<User> users;

    public MaternityHospital() {
    }

    public MaternityHospital(long number, String address, String locationMap, List<User> users) {
        this.number = number;
        this.address = address;
        this.locationMap = locationMap;
        this.users = users;
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
        MaternityHospital that = (MaternityHospital) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


}
