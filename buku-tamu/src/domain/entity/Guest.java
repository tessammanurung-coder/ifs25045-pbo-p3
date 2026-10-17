package domain.entity;

/**
 * Entity inti yang merepresentasikan satu tamu pada buku tamu.
 * Bersifat immutable dan tidak mengetahui cara tampil maupun cara penyimpanan.
 */
public class Guest {

    private final int id;
    private final String name;
    private final String purpose;

    public Guest(int id, String name, String purpose) {
        this.id = id;
        this.name = name;
        this.purpose = purpose;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPurpose() {
        return purpose;
    }
}
