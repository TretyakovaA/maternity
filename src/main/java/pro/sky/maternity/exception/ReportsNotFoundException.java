package pro.sky.maternity.exception;

public class ReportsNotFoundException extends RuntimeException{
    private final long id;

    public ReportsNotFoundException(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

}
