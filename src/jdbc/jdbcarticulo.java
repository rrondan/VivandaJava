package jdbc;

import java.io.Console;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import entidades.articulo;

public class jdbcarticulo {
  //declaracion de variables
	private articulo art;
	private static String url="jdbc:mysql://localhost:3306/vivanda";
	private static String user="root";
	private static String password="mysql";
	private Connection cn;
	public jdbcarticulo(){		
	}
	
	public List<articulo> listarTodo(){
		List<articulo> articulos = new ArrayList();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			cn=(Connection) DriverManager.getConnection(url, user, password);
			String sql="select a.cod_articulo, a.des_articulo,c.descripcion as categoria,e.descripcion as empaque from articulo a inner join categoria c on a.id_categoria=c.idcategoria inner join empaque e on a.id_empaque=e.idempaque";			
			PreparedStatement ps =(PreparedStatement) cn.prepareStatement(sql);
			
			ResultSet rs=ps.executeQuery();
			// llenando la data
			articulo art;
			while(rs.next()){
				art = new articulo(rs.getString("cod_articulo"),rs.getString("des_articulo"),
						rs.getString("categoria"),rs.getString("empaque"));
				articulos.add(art);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return articulos;
	}
	
	public articulo buscar(String codigo){
		art = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			cn=(Connection) DriverManager.getConnection(url, user, password);
			String sql="select a.cod_articulo, a.des_articulo,c.descripcion as categoria,e.descripcion as empaque from articulo a inner join categoria c on a.id_categoria=c.idcategoria inner join empaque e on a.id_empaque=e.idempaque where a.cod_articulo = ?";
			
			PreparedStatement ps =(PreparedStatement) cn.prepareStatement(sql);
			ps.setString(1, codigo);
			ResultSet rs=ps.executeQuery();
			// llenando la data
			while(rs.next()){
				art =new articulo(rs.getString("cod_articulo"),rs.getString("des_articulo"),
						rs.getString("categoria"),rs.getString("empaque"));				
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return art;
	}
	
	public void guardar(String cod,String des,int cat,int emp){
		articulo art = buscar(cod);
		if(art != null){
			editar(cod,des,cat,emp);
		}else{
			registrar(cod,des,cat,emp);
		}
	}
	
	public void registrar(String cod,String des,int cat,int emp){		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			cn=(Connection) DriverManager.getConnection(url, user, password);
			String sql="insert into articulo values(?,?,?,?)";
			PreparedStatement ps=(PreparedStatement) cn.prepareStatement(sql);
			ps.setString(1,cod);
			ps.setString(2,des);
			ps.setInt(3,cat);
			ps.setInt(4, emp);
			ps.executeUpdate();
			JOptionPane.showMessageDialog(null, "articulo registrado");			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Hubo un problema al registrar su articulo");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Hubo un problema al registrar su articulo");
		}
	}
	public void editar(String cod,String des,int cat,int emp){		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			cn=(Connection) DriverManager.getConnection(url, user, password);
			String sql="Update articulo set cod_articulo = ? , des_articulo = ? , id_categoria = ?, id_empaque = ? where cod_articulo = ?";
			PreparedStatement ps=(PreparedStatement) cn.prepareStatement(sql);
			ps.setString(1,cod);
			ps.setString(2,des);
			ps.setInt(3,cat);
			ps.setInt(4, emp);
			ps.setString(5,cod);
			ps.executeUpdate();
			JOptionPane.showMessageDialog(null, "articulo actualizado");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Hubo un problema al actualizar su articulo");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Hubo un problema al actualizar su articulo");
		}
	}
	
	public ResultSet listarcategorias(){
		ResultSet rs=null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			cn=(Connection) DriverManager.getConnection(url, user, password);
			String sql="select * from categoria";
			PreparedStatement ps=(PreparedStatement) cn.prepareStatement(sql);
			rs= ps.executeQuery();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rs;
	}
	
	public boolean eliminar(String cod){
		boolean respuesta = false;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			cn=(Connection) DriverManager.getConnection(url, user, password);
			String sql="Delete from articulo where cod_articulo = ?";
			PreparedStatement ps=(PreparedStatement) cn.prepareStatement(sql);
			ps.setString(1,cod);			
			ps.executeUpdate();
			JOptionPane.showMessageDialog(null, "El articulo se ha eliminado.");
			respuesta = true;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Hubo un problema al eliminar su articulo");			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Hubo un problema al eliminar su articulo");
		}
		return respuesta;
	}
	
	public ResultSet listarempaques(){
		ResultSet rs=null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			cn=(Connection) DriverManager.getConnection(url, user, password);
			String sql="select * from empaque";
			PreparedStatement ps=(PreparedStatement) cn.prepareStatement(sql);
			rs= ps.executeQuery();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}

}
