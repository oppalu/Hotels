package intergrate;

public class IntergrateData {
	//集成数据库中的hotel表和room表
	//修改项目根目录下的db.txt文件，设置第一行为用户名，第二行为密码
	public static void main(String[] args) {
		new IntergrateHotel().build_table_h();
		new IntergrateRoom().build_table_r();
	}
}
