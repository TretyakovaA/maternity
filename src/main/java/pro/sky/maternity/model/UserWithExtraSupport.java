package pro.sky.maternity.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "extra_support")
public class UserWithExtraSupport {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public UserWithExtraSupport() {
    }

    public UserWithExtraSupport(User user) {
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserWithExtraSupport)) return false;
        UserWithExtraSupport that = (UserWithExtraSupport) o;
        return getId() == that.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
