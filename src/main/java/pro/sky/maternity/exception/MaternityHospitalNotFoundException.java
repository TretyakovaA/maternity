package pro.sky.maternity.exception;

public class MaternityHospitalNotFoundException extends RuntimeException{

private final long id;

    public MaternityHospitalNotFoundException(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

}
