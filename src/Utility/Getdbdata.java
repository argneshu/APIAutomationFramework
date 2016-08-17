package Utility;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Getdbdata {
	public static String dbDriver="com.mysql.jdbc.Driver";
	public static String dbURL="139.162.24.27:3306";
	public static String tableName="merchant_test";
	public static String userName="r42user";
	public static String password="r42passmysql";
	static java.sql.Connection con=null;
	static java.sql.Statement stmt=null;
	
	static int count = 0;

	public static int dbGetData(String query) throws ClassNotFoundException,
	SQLException {

		try {
			Class.forName("com.mysql.jdbc.Driver");
		
			java.sql.Connection con = DriverManager.getConnection(
					"jdbc:mysql://139.162.24.27:3306/merchant", "qateam",
					"o1Fs@Ihg2Mu^cIrSCuJw");
		
			java.sql.Statement stmt = con.createStatement();
		
			java.sql.ResultSet rs = stmt
					.executeQuery(query);
			//count = rs.getInt(1);
			//System.out.println(rs.getInt(1));
		
			//
			while (rs.next()){
				count = rs.getInt(1);
			}
			// System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));
		
			con.close();
		
		} catch (Exception e) {
			System.out.println(e);
		}
		return count;
		
}

	public static void connectDB() throws ClassNotFoundException, SQLException {
	try {
		Class.forName(dbDriver);

		con = DriverManager.getConnection("jdbc:mysql://"+dbURL+"/"+tableName,userName,password);

		stmt = con.createStatement();
	} catch (Exception e) {
		System.out.println(e.toString());
	}
}
	
	public static void connectDB(String DBUrl,String tName,String uName,String psword) throws ClassNotFoundException, SQLException {
		try {
			Class.forName(dbDriver);

			con = DriverManager.getConnection("jdbc:mysql://"+DBUrl+"/"+tName,uName,psword);

			stmt = con.createStatement();
			System.out.println("Connected to DB");
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	
	public static String executeQuery(String query) throws ClassNotFoundException, SQLException {
		String outPut="";
		try {
			java.sql.ResultSet rs = stmt
					.executeQuery(query);

			while (rs.next()) {
				outPut= outPut+" "+rs.getString(1);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return outPut;
	}
	
	public static void closeDBConnection() throws ClassNotFoundException, SQLException {
		try {
			con.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

}
