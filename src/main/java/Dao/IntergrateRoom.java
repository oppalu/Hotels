package Dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import Model.Hotel;
import Model.Room;

public class IntergrateRoom {
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

	public IntergrateRoom() {
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

	public void build_table_r() {
		try {
			Class.forName(JDBC_DRIVER);
			conn = (Connection) DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = (Statement) conn.createStatement();

			ArrayList hotel_list = new ArrayList();
			ArrayList hotel_name = new ArrayList();
			sql = "select * from intergrate_hotel";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				hotel_list.add(rs.getInt("id"));
				hotel_name.add(rs.getString("name"));
			}
			ArrayList<Room> roomlist = new ArrayList<Room>();
			for (int i = 0; i < hotel_list.size(); i++) {
				int id = (Integer) hotel_list.get(i);
				String name = (String) hotel_name.get(i);
				// meituan
				sql = "select id from meituan_hotel where name='" + name + "'";
				rs = stmt.executeQuery(sql);
				if (rs.next()) {
					int mid = rs.getInt("id");
					sql = "select * from meituan_room where hid=" + mid;
					rs = stmt.executeQuery(sql);
					while (rs.next()) {
						Room temp = new Room();
						temp.setDatafrom("meituan");
						temp.setId(id);
						temp.setHid(mid);
						temp.setDescription(rs.getString("description"));
						temp.setPrice(rs.getDouble("price"));
						temp.setRoomservice(rs.getString("roomservice"));
						temp.setRoomtype(rs.getString("roomtype"));
						roomlist.add(temp);
					}
				}
				// qunar
				sql = "select id from qunar_hotel where name='" + name + "'";
				rs = stmt.executeQuery(sql);
				if (rs.next()) {
					int qid = rs.getInt("id");
					sql = "select * from qunar_room where hid=" + qid;
					rs = stmt.executeQuery(sql);
					while (rs.next()) {
						Room temp = new Room();
						temp.setDatafrom("qunar");
						temp.setId(id);
						temp.setHid(qid);
						temp.setDescription(rs.getString("description"));
						temp.setPrice(rs.getDouble("price"));
						temp.setRoomservice(rs.getString("roomservice"));
						temp.setRoomtype(rs.getString("roomtype"));
						roomlist.add(temp);
					}
				}
			}
			// System.out.println(roomlist.size());
			// 建立表格
			sql = "drop table if exists intergrate_room";
			stmt.executeUpdate(sql);
			sql = "CREATE TABLE `intergrate_room` ("+
					  "`id` int(11) NOT NULL ,"+
					  "`hid` int(11) NOT NULL COMMENT '外键：酒店id',"+
					  "`roomtype` varchar(255) DEFAULT 'null' COMMENT '房间类型：大床房，双床房等等',"+
					  "`decription` varchar(255) DEFAULT NULL COMMENT '房型描述：面积，有无窗户等等',"+
					  "`roomservice` varchar(255) DEFAULT NULL COMMENT '房间服务用分号隔开：wifi；早餐供应；空调',"+
					  "`price` double DEFAULT '0' COMMENT '房间价格'"+
					") DEFAULT CHARSET=utf8;";
			stmt.executeUpdate(sql);
			// insert
			sql = "INSERT INTO `intergrate_room` VALUES ";

			for (int i = 0; i < roomlist.size(); i++) {
				Room temp = roomlist.get(i);
				sql = sql + "(" + temp.getId() + ","+temp.getHid()+",'" + temp.getRoomtype() + "','" + temp.getDescription() + "','"
						+ temp.getRoomservice() + "'," + temp.getPrice()+"),";
			}
			sql = sql.substring(0, sql.length() - 1) + ";";
			stmt.executeUpdate(sql);

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

	}

	public static void main(String[] args) {
		new IntergrateRoom().build_table_r();
	}
}
