package jdbc;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

public class Conexion {
	private static String url="jdbc:mysql://localhost:3306/vivanda";
	private static String user="root";
	private static String password="mysql";	
	public Conexion() {		
	}
	public Connection getConnection(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection cn= (Connection) DriverManager.getConnection(url, user, password);			
			return cn;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return null;
	}
}
