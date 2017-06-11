package Dao.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import Dao.HotelDao;
import Model.Hotel;
import Model.Room;

public class HotelDaoImpl implements HotelDao{
	private static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static String DB_URL = "jdbc:mysql://localhost:3306/hotel?characterEncoding=utf8&useSSL=true";

	// Database credentials
	private static String USER = "";// "root";
	private static String PASS = "";// "141250199";

	private static Connection conn = null;
	private static Statement stmt = null;
	private static String sql = null;
	private static ResultSet rs = null;
	private static ResultSet rs2 = null;
	
	public HotelDaoImpl(){
		try {
			String encoding = "utf8";
			File file = new File("db.txt");
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				if ((lineTxt = bufferedReader.readLine()) != null) {
					USER = lineTxt;
				}
				if ((lineTxt = bufferedReader.readLine()) != null) {
					PASS = lineTxt;
				}
				read.close();
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
	}
	
	
	public List<Hotel> getHotelList() {
		// TODO Auto-generated method stub
		ArrayList<Hotel> hotel_list = new ArrayList<Hotel>();
		try {
			Class.forName(JDBC_DRIVER);
			conn = (Connection) DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = (Statement) conn.createStatement();

			sql = "select * from intergrate_hotel";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Hotel temp = new Hotel();
				temp.setId(rs.getInt("id"));
				temp.setName(rs.getString("name"));
				temp.setLevel(rs.getString("level"));
				temp.setLocation(rs.getString("location"));
				temp.setScore(rs.getDouble("score"));
				temp.setStartprice(rs.getDouble("startprice"));
				temp.setImg(rs.getString("img"));
				hotel_list.add(temp);
			}
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					conn.close();
			} catch (SQLException se) {
			} // do nothing
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		}
//		System.out.println(hotel_list.size());
		return hotel_list;
	}

	public List<Hotel> searchHotels(String keyword) {
		// TODO Auto-generated method stub
		return null;
	}

	public Hotel getHotelInfo(int id) {
		// TODO Auto-generated method stub
		Hotel temp = new Hotel(); 
		try {
			Class.forName(JDBC_DRIVER);
			conn = (Connection) DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = (Statement) conn.createStatement();

			sql = "select * from intergrate_hotel where id="+id;
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				temp.setId(rs.getInt("id"));
				temp.setName(rs.getString("name"));
				temp.setLevel(rs.getString("level"));
				temp.setLocation(rs.getString("location"));
				temp.setScore(rs.getDouble("score"));
				temp.setStartprice(rs.getDouble("startprice"));
				temp.setImg(rs.getString("img"));
			}
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					conn.close();
			} catch (SQLException se) {
			} // do nothing
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		}
		System.out.println(temp.getName());
		return temp;
	}

	public List<Room> getRooms(int hotelid) {
		// TODO Auto-generated method stub
		ArrayList<Room> roomlist = new ArrayList<Room>(); 
		try {
			Class.forName(JDBC_DRIVER);
			conn = (Connection) DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = (Statement) conn.createStatement();

			sql = "select * from intergrate_room where id=" + hotelid;
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Room temp = new Room();
				temp.setDatafrom(rs.getString("datafrom"));
				temp.setId(rs.getInt("id"));
				temp.setHid(rs.getInt("hid"));
				temp.setDescription(rs.getString("description"));
				temp.setPrice(rs.getDouble("price"));
				temp.setRoomservice(rs.getString("roomservice"));
				temp.setRoomtype(rs.getString("roomtype"));
				roomlist.add(temp);
			}
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					conn.close();
			} catch (SQLException se) {
			} // do nothing
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		}
//		System.out.println(roomlist.get(0).getRoomtype());
//		System.out.println(roomlist.get(1).getRoomtype());
		return roomlist;
	}
	
	public static void main(String[] args) {
//		new HotelDaoImpl().getHotelList();
//		new HotelDaoImpl().getHotelInfo(32);
//		new HotelDaoImpl().getRooms(1257);
		
	}

}
