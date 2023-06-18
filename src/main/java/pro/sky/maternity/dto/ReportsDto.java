package pro.sky.maternity.dto;

import pro.sky.maternity.model.User;

import java.time.LocalDateTime;
import java.util.Objects;

public class ReportsDto {

    private long id;

    private long chatId;

    private LocalDateTime date;

    private String text;


    private String photo;


    private User user;

    public ReportsDto() {
    }

    public ReportsDto(long id, long chatId, LocalDateTime date, String text, String photo, User user) {
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
        ReportsDto reportDto = (ReportsDto) o;
        return id == reportDto.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
