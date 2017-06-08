package Model;

/**
 * Created by phoebegl on 2017/6/8.
 */
public class room {

    private int id;
    private int hid;
    private String roomtype;
    private String description;
    private String roomservice;
    private double price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHid() {
        return hid;
    }

    public void setHid(int hid) {
        this.hid = hid;
    }

    public String getRoomtype() {
        return roomtype;
    }

    public void setRoomtype(String roomtype) {
        this.roomtype = roomtype;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRoomservice() {
        return roomservice;
    }

    public void setRoomservice(String roomservice) {
        this.roomservice = roomservice;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
