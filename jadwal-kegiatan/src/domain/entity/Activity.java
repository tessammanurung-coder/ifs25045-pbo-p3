package domain.entity;

/**
 * Entity inti yang merepresentasikan satu kegiatan terjadwal.
 * ID tidak dapat diubah; perubahan data dilakukan lewat method bernama jelas.
 */
public class Activity {

    private final int id;
    private String title;
    private String day;
    private String time;

    public Activity(int id, String title, String day, String time) {
        this.id = id;
        this.title = title;
        this.day = day;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDay() {
        return day;
    }

    public String getTime() {
        return time;
    }

    public void changeTitle(String title) {
        this.title = title;
    }

    public void changeDay(String day) {
        this.day = day;
    }

    public void changeTime(String time) {
        this.time = time;
    }
}
