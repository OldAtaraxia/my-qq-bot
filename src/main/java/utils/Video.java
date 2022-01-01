package utils;

public class Video {
    private String url;
    private String title;
    private String upId;

    public Video(String url, String title, String upId) {
        this.url = url;
        this.title = title;
        this.upId = upId;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getUpId() {
        return upId;
    }
}
