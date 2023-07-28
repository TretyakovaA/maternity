package pro.sky.maternity.model;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;


/**
 * Reports обратная связь от роженицы
 */
@Entity
@Table(name = "reports")
public class Reports {

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
     * дата отправки пользователем обратной связи
     */
    @Column(name = "date")
    private LocalDateTime date;

    /**
     * текст обратной связи
     */
    @Column(name = "text")
    private String text;

    /**
     * фото прикрепленное к сообщению
     * необязательное поле
     */
    @Column(name = "photo")
    private String photo;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Reports() {
    }

    public Reports(long chatId, LocalDateTime date, String text, String photo, User user) {
        this.chatId = chatId;
        this.date = date;
        this.text = text;
        this.photo = photo;
        this.user = user;
    }
    public Reports(long id, long chatId, LocalDateTime date, String text, String photo, User user) {
        this.id = id;
        this.chatId = chatId;
        this.date = date;
        this.text = text;
        this.photo = photo;
        this.user = user;
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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
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
        if (o == null || getClass() != o.getClass()) return false;
        Reports reports = (Reports) o;
        return id == reports.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
