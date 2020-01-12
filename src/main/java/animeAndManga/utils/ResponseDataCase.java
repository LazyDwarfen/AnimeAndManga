package animeAndManga.utils;

public class ResponseDataCase {
    private Object data;
    private String status;
    public ResponseDataCase(Object data, String status){
        this.data = data;
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public String getStatus() {
        return status;
    }
}
