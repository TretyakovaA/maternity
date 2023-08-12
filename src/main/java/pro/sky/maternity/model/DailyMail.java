package pro.sky.maternity.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "dailymail")
public class DailyMail {
    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "info")
    private String info;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DailyMail)) return false;
        DailyMail dailyMail = (DailyMail) o;
        return getId() == dailyMail.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
