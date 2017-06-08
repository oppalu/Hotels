package Service;

/**
 * Created by phoebegl on 2017/6/8.
 */
public interface HotelService {

    String getHotelList();
    String getHotelList(String keyword);
    String getHotelInfo(int id);
}
