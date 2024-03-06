package entities;

public class reservation {
    private int id;
    private int id_user;
    private int event_id;

    public reservation() {
    }

    public reservation(int id, int id_user, int event_id) {
        this.id = id;
        this.id_user = id_user;
        this.event_id = event_id;
    }

    public reservation(int id_user, int event_id) {
        this.id_user = id_user;
        this.event_id = event_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    @Override
    public String toString() {
        return "reservation{" +
                "id=" + id +
                ", id_user=" + id_user +
                ", event_id=" + event_id +
                '}';
    }
}
