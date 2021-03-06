package jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import entidades.Articulo;

public class jdbcarticulo{
  //declaracion de variables
	private Articulo art;
	private Conexion c;
	
	public jdbcarticulo(){		
		c = new Conexion();		
	}
	
	public ArrayList<Articulo> listarTodo(){
		ArrayList<Articulo> articulos = new ArrayList<>();
		try {
			Connection cn = c.getConnection();

			String sql="select articulo.cod_articulo, articulo.des_articulo,categoria.descripcion as categoria,empaque.descripcion as empaque from articulo inner join categoria on articulo.id_categoria=categoria.idcategoria inner join empaque on articulo.id_empaque=empaque.idempaque where articulo.activo = 1";			
			PreparedStatement ps =(PreparedStatement) cn.prepareStatement(sql);
			
			ResultSet rs=ps.executeQuery();
			// llenando la data
			Articulo art;
			while(rs.next()){
				art = new Articulo(rs.getString("cod_articulo"),rs.getString("des_articulo"),
						rs.getString("categoria"),rs.getString("empaque"));
				articulos.add(art);
			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return articulos;
	}
	
	public Articulo buscarPorCodigo(String codigo){
		art = null;
		try {					
			Connection cn = c.getConnection();
			String sql="select a.cod_articulo, a.des_articulo,c.descripcion as categoria,e.descripcion as empaque from articulo a inner join categoria c on a.id_categoria=c.idcategoria inner join empaque e on a.id_empaque=e.idempaque where a.cod_articulo = ?";
			PreparedStatement ps =(PreparedStatement) cn.prepareStatement(sql);
			ps.setString(1, codigo);
			ResultSet rs=ps.executeQuery();
			// llenando la data
			while(rs.next()){
				art =new Articulo(rs.getString("cod_articulo"),rs.getString("des_articulo"),
						rs.getString("categoria"),rs.getString("empaque"));				
			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return art;
	}
	
	public Articulo buscarPorCodigoActivo(String codigo){
		art = null;
		try {					
			Connection cn = c.getConnection();
			String sql="select a.cod_articulo, a.des_articulo,c.descripcion as categoria,e.descripcion as empaque from articulo a inner join categoria c on a.id_categoria=c.idcategoria inner join empaque e on a.id_empaque=e.idempaque where a.cod_articulo = ? and a.activo = 1";
			PreparedStatement ps =(PreparedStatement) cn.prepareStatement(sql);
			ps.setString(1, codigo);
			ResultSet rs=ps.executeQuery();
			// llenando la data
			while(rs.next()){
				art =new Articulo(rs.getString("cod_articulo"),rs.getString("des_articulo"),
						rs.getString("categoria"),rs.getString("empaque"));				
			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return art;
	}
	
	
	public ResultSet buscar(String descripcion){
		ResultSet rs = null;
		try {					
			Connection cn = c.getConnection();
			String sql= "select a.cod_articulo, a.des_articulo,c.descripcion as categoria,e.descripcion as empaque from articulo a inner join categoria c on a.id_categoria=c.idcategoria inner join empaque e on a.id_empaque=e.idempaque where LOWER(a.des_articulo) like ? and a.activo = 1";
			PreparedStatement ps =(PreparedStatement) cn.prepareStatement(sql);
			ps.setString(1, "%" + descripcion.toLowerCase() + "%");
			rs=ps.executeQuery();						
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	
	public ArrayList<Articulo> buscarXDes(String descripcion){
		ArrayList<Articulo> articulos = new ArrayList<>();
		try {					
			Connection cn = c.getConnection();
			String sql= "select a.cod_articulo, a.des_articulo,c.descripcion as categoria,e.descripcion as empaque from articulo a inner join categoria c on a.id_categoria=c.idcategoria inner join empaque e on a.id_empaque=e.idempaque where LOWER(a.des_articulo) like ? and a.activo = 1";
			PreparedStatement ps =(PreparedStatement) cn.prepareStatement(sql);
			ps.setString(1, "%" + descripcion.toLowerCase() + "%");
			ResultSet rs=ps.executeQuery();
			Articulo art;
			while(rs.next()){
				art = new Articulo(rs.getString("cod_articulo"),rs.getString("des_articulo"),rs.getString("categoria"),rs.getString("empaque"));
				articulos.add(art);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return articulos;
	}
	
	public void guardar(String cod,String des,int cat,int emp){
		Articulo art = buscarPorCodigo(cod);
		if(art != null){
			editar(cod,des,cat,emp);
		}else{
			registrar(cod,des,cat,emp);
		}
	}
	
	public void registrar(String cod,String des,int cat,int emp){		
		try {			
			Connection cn = c.getConnection();
			String sql="insert into articulo values(?,?,?,?,?)";
			PreparedStatement ps=(PreparedStatement) cn.prepareStatement(sql);
			ps.setString(1,cod);
			ps.setString(2,des);
			ps.setInt(3,cat);
			ps.setInt(4, emp);
			ps.setBoolean(5, true);
			ps.executeUpdate();
			JOptionPane.showMessageDialog(null, "articulo registrado");				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Hubo un problema al registrar su articulo");
		}
	}
	public void editar(String cod,String des,int cat,int emp){		
		try {		
			Connection cn = c.getConnection();
			String sql="Update articulo set cod_articulo = ? , des_articulo = ? , id_categoria = ?, id_empaque = ? where cod_articulo = ?";
			PreparedStatement ps=(PreparedStatement) cn.prepareStatement(sql);
			ps.setString(1,cod);
			ps.setString(2,des);
			ps.setInt(3,cat);
			ps.setInt(4, emp);
			ps.setString(5,cod);
			ps.executeUpdate();
			JOptionPane.showMessageDialog(null, "articulo actualizado");			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Hubo un problema al actualizar su articulo");
		}
	}
	
	public ResultSet listarcategorias(){
		ResultSet rs=null;
		try {			
			Connection cn = c.getConnection();
			String sql="select * from categoria";
			PreparedStatement ps=(PreparedStatement) cn.prepareStatement(sql);
			rs= ps.executeQuery();			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rs;
	}
	
	public boolean eliminar(String cod){
		boolean respuesta = false;
		try {
			Connection cn = c.getConnection();
			String sql="Update articulo set activo = ? where cod_articulo = ?";
			PreparedStatement ps=(PreparedStatement) cn.prepareStatement(sql);
			ps.setBoolean(1, false);
			ps.setString(2,cod);			
			ps.executeUpdate();
			JOptionPane.showMessageDialog(null, "El articulo se ha eliminado.");
			respuesta = true;			
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
			Connection cn = c.getConnection();
			String sql="select * from empaque";
			PreparedStatement ps=(PreparedStatement) cn.prepareStatement(sql);
			rs= ps.executeQuery();			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}

}
