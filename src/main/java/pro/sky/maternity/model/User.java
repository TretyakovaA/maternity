package pro.sky.maternity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * User это роженица
 */
@Entity
@Table(name = "users")

public class User {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    /**
     * chatId в телеграме
     */
    @Column(name = "chat_id")
    private long chatId;

    /**
     * ник в телеграме
     */
    @Column(name = "name")
    private String name;

    /**
     * день рождения ребенка
     */
    @Column(name = "child_birthday")
    private LocalDateTime childBirthday;

    /**
     * роддом
     */
    @ManyToOne
    @JoinColumn(name = "maternity_hospital_id")
    private MaternityHospital maternityHospital;

    /**
     * ежедневная обратная связь от пользователя
     */
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Reports> reports;

    public User() {
    }

    public User(long chatId, String name, LocalDateTime childBirthday, MaternityHospital maternityHospital) {
        this.chatId = chatId;
        this.name = name;
        this.childBirthday = childBirthday;
        this.maternityHospital = maternityHospital;
    }

    public User(long id, long chatId, String name) {
        this.id = id;
        this.chatId = chatId;
        this.name = name;
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
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
