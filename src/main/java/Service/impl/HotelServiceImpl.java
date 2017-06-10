package Service.impl;

import Model.Hotel;
import Model.Room;
import Service.HotelService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by phoebegl on 2017/6/8.
 */
public class HotelServiceImpl implements HotelService {

    private ObjectMapper mapper;

    public HotelServiceImpl() {
        mapper = new ObjectMapper();
    }

    public String getHotelList() {
        List<Hotel> list = new ArrayList<Hotel>();
        Hotel h1 = new Hotel();
        h1.setId(1);
        h1.setName("重庆江北机场丽峰酒店");
        h1.setLevel("舒适");
        h1.setLocation("重庆渝北区江北国际机场一碗水后街鹭岭尚品1号楼");
        h1.setScore(4.5);
        h1.setStartprice(199);
        h1.setImg("http://userimg.qunar.com/imgs/201511/14/JhS1_th1MtiVE-YBJ180.jpg");
        Hotel h2 = new Hotel();
        h2.setId(2);
        h2.setName("南京金陵饭店");
        h2.setLocation("鼓楼区新街口汉中路2号");
        h2.setScore(4.5);
        h2.setStartprice(199);
        list.add(h1);
        list.add(h2);
        String result = "";
        try {
            result = mapper.writeValueAsString(list);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String getHotelList(String keyword) {
        return null;
    }

    public String getHotelInfo(int id) {
        Hotel h = new Hotel();
        h.setId(id);
        h.setName("重庆江北机场丽峰酒店");
        h.setLevel("舒适");
        h.setLocation("重庆渝北区江北国际机场一碗水后街鹭岭尚品1号楼");
        h.setScore(4.5);
        h.setStartprice(199);

        List<Room> rooms = new ArrayList<Room>();
        Room r1 = new Room();
        r1.setId(1);
        r1.setHid(1);
        r1.setRoomtype("静雅商务大床房-预付特惠-含双早");
        r1.setDescription("面积25㎡;位于1-7层;大床;独立卫浴;无窗");
        r1.setRoomservice("宽带上网;免费市内电话;空调;吹风机;24小时热水");
        r1.setPrice(209);
        Room r2 = new Room();
        r2.setId(2);
        r2.setHid(1);
        r2.setRoomtype("静雅商务双床房-预付特惠-含双早");
        r2.setDescription("面积30-40㎡;位于2-4层;双床;独立卫浴;无窗");
        r2.setRoomservice("宽带上网;免费市内电话;空调;吹风机;24小时热水");
        r2.setPrice(239);
        rooms.add(r1) ;
        rooms.add(r2);
        String result = "";

        HashMap<String,Object> map = new HashMap<String, Object>();
        map.put("id",h.getId());
        map.put("name",h.getName());
        map.put("level",h.getLevel());
        map.put("location",h.getLocation());
        map.put("score",h.getScore());
        map.put("startprice",h.getStartprice());
        map.put("room",rooms);
        try {
            result = mapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }

}
