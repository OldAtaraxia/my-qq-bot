package utils;

public class Up {
    @Override
    public String toString() {
        return "Up{" +
                "uid='" + uid + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    private String uid;
    private String name;

    public String getName() {
        return name;
    }

    public String getUid() {
        return uid;
    }

    public Up(String uid, String name) {
        this.uid = uid;
        this.name = name;
    }
}
