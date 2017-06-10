package Dao;

import Model.Hotel;
import Model.Room;

import java.util.List;

/**
 * Created by phoebegl on 2017/6/10.
 */
public interface HotelDao {

    List<Hotel> getHotelList();

    List<Hotel> searchHotels(String keyword);

    Hotel getHotelInfo(int id);

    List<Room> getRooms(int hotelid);
}
