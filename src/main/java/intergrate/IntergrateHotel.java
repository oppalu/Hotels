package intergrate;

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

public class IntergrateHotel {
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

	public IntergrateHotel() {
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

	public void build_table_h() {
		try {
			Class.forName(JDBC_DRIVER);
			conn = (Connection) DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = (Statement) conn.createStatement();

			
			//get Information from both meituan,qunar
			ArrayList<Hotel> hotel1=new ArrayList<Hotel>();
			ArrayList mlist=new ArrayList();
			ArrayList qlist=new ArrayList();
			sql="select meituan_hotel.id as mid,qunar_hotel.id as qid from meituan_hotel,qunar_hotel where meituan_hotel.name=qunar_hotel.name";
			rs=stmt.executeQuery(sql);
			while (rs.next()) {
				mlist.add(rs.getInt("mid"));
				qlist.add(rs.getInt("qid"));
			}
			for(int i=0;i<mlist.size();i++){
				sql="select * from meituan_hotel where meituan_hotel.id="+mlist.get(i);
				rs=stmt.executeQuery(sql);
				rs.next();
				//score
				double m_score=rs.getDouble("score");
				//location as the longer one
				String m_location=rs.getString("location");
				//type
				String m_type=rs.getString("type");
				
				sql="select * from qunar_hotel where qunar_hotel.id="+qlist.get(i);
				rs2=stmt.executeQuery(sql);
				rs2.next();
				
				Hotel temp=new Hotel();
				temp.setId(i+1);
				temp.setName(rs2.getString("name"));
				//img
				temp.setImg(rs2.getString("img"));
				//score
				double q_score=rs2.getDouble("score");
				//startprice
				double q_startprice=rs2.getDouble("startprice");
				//location as the longer one
				String q_location=rs2.getString("location");
				//type
				String q_type=rs2.getString("type");
				
				//startprice
				sql="select min(price) as min_price from meituan_room where meituan_room.hid="+mlist.get(i);
				rs=stmt.executeQuery(sql);
				rs.next();
				double m_startprice=rs.getDouble("min_price");
				temp.setStartprice((m_startprice>q_startprice)?q_startprice:m_startprice);
				//score
				temp.setScore((m_score+q_score)/2);
				//type as the score bigger
				temp.setLevel((m_score>q_score)?m_type:q_type);
//				if(m_score>q_score){
//					temp.setLevel(rs.getString("type"));
//				}else{
//					temp.setLevel(rs2.getString("type"));
//				}
				//location as the longer one
				temp.setLocation((m_location.length()>q_location.length())?m_location:q_location);
				
				hotel1.add(temp);
			}
			
			
//			for(int i=0;i<hotel1.size();i++){
//				System.out.println(hotel1.get(i).getId());
//				System.out.println(hotel1.get(i).getName());
//				System.out.println(hotel1.get(i).getLevel());
//				System.out.println(hotel1.get(i).getLocation());
//				System.out.println(hotel1.get(i).getScore());
//				System.out.println(hotel1.get(i).getStartprice());
//			}
			
			
			//get Information only meituan
			ArrayList<Hotel> hotel2=new ArrayList<Hotel>();
			int count=hotel1.size();
			sql="select * from meituan_hotel where meituan_hotel.name not in (select name from qunar_hotel)";
			rs=stmt.executeQuery(sql);
			while(rs.next()){
				Hotel temp=new Hotel();
				temp.setId(rs.getInt("id"));
				temp.setName(rs.getString("name"));
				temp.setLevel(rs.getString("type"));
				temp.setLocation(rs.getString("location"));
				temp.setImg(rs.getString("img"));
				temp.setScore(rs.getDouble("score"));
				hotel2.add(temp);
			}
			for(int i=0;i<hotel2.size();i++){
				sql="select min(price) as min_price from meituan_room where meituan_room.hid="+hotel2.get(i).getId();
				rs=stmt.executeQuery(sql);
				rs.next();
				hotel2.get(i).setStartprice(rs.getDouble("min_price"));
				hotel2.get(i).setId(count+i+1);
//				System.out.println(i+" "+hotel2.get(i).getId()+" "+hotel2.get(i).getStartprice());
			}
			
			//get Information only qunar
			ArrayList<Hotel> hotel3=new ArrayList<Hotel>();
			count=hotel1.size()+hotel2.size();
			sql="select * from qunar_hotel where qunar_hotel.name not in (select name from meituan_hotel)";
			rs=stmt.executeQuery(sql);
			while(rs.next()){
				count++;
				Hotel temp=new Hotel();
				temp.setId(count);
				temp.setName(rs.getString("name"));
				temp.setLevel(rs.getString("type"));
				temp.setLocation(rs.getString("location"));
				temp.setImg(rs.getString("img"));
				temp.setScore(rs.getDouble("score"));
				temp.setStartprice(rs.getDouble("startprice"));
				hotel3.add(temp);
			}
//			for(int i=0;i<hotel3.size();i++){
//				System.out.println(hotel3.get(i).getId());
//				System.out.println(hotel3.get(i).getName());
//				System.out.println(hotel3.get(i).getLevel());
//				System.out.println(hotel3.get(i).getLocation());
//				System.out.println(hotel3.get(i).getScore());
//				System.out.println(hotel3.get(i).getStartprice());
//			}
			hotel1.addAll(hotel2);
			hotel1.addAll(hotel3);
			
			// 建立表格
			sql = "drop table if exists intergrate_hotel";
			stmt.executeUpdate(sql);
			sql = "CREATE TABLE `intergrate_hotel` (" + "`id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',"
					+ "`name` varchar(45) NOT NULL COMMENT '酒店名称',"
					+ "`level` varchar(5) DEFAULT '经济型' COMMENT '酒店级别',"
					+ "`location` varchar(255) DEFAULT 'null' COMMENT '地理位置',"
					+ "`score` double DEFAULT '0' COMMENT '酒店评分，5分制'," + "`startprice` double DEFAULT '0' COMMENT '起价',"
					+ "`img` varchar(255) DEFAULT NULL," + "PRIMARY KEY (`id`)"
					+ ") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;";
			stmt.executeUpdate(sql);
			//insert
			sql="INSERT INTO `intergrate_hotel` VALUES ";
			
			
			for(int i=0;i<hotel1.size();i++){
				Hotel temp=hotel1.get(i);
				sql=sql+"("+temp.getId()+",'"+temp.getName()+"','"+temp.getLevel()+"','"+temp.getLocation()+"',"+temp.getScore()+","+temp.getStartprice()+",'"+temp.getImg()+"'),";
			}
			sql=sql.substring(0, sql.length()-1)+";";
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

//	public static void main(String[] args) {
//		new IntergrateHotel().build_table_h();
//	}
}
