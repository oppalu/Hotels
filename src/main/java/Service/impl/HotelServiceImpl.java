package Service.impl;

import Model.hotel;
import Service.HotelService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phoebegl on 2017/6/8.
 */
public class HotelServiceImpl implements HotelService {

    private ObjectMapper mapper;

    public HotelServiceImpl() {
        mapper = new ObjectMapper();
    }

    public String getHotelList() {
        List<hotel> list = new ArrayList<hotel>();
        hotel h1 = new hotel();
        h1.setId(1);
        h1.setName("重庆江北机场丽峰酒店");
        h1.setLevel("舒适");
        h1.setLocation("重庆渝北区江北国际机场一碗水后街鹭岭尚品1号楼");
        h1.setScore(4.5);
        h1.setStartprice(199);
        hotel h2 = new hotel();
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
        hotel h = new hotel();
        h.setId(id);
        h.setName("重庆江北机场丽峰酒店");
        h.setLevel("舒适");
        h.setLocation("重庆渝北区江北国际机场一碗水后街鹭岭尚品1号楼");
        h.setScore(4.5);
        h.setStartprice(199);

        String result = "";
        try {
            result = mapper.writeValueAsString(h);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }
}
