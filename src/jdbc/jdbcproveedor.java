package jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import entidades.Proveedor;

public class jdbcproveedor {
	private Conexion c;

	public jdbcproveedor() {
		c = new Conexion();
	}
	
	public List<Proveedor> listarTodo(){
		List<Proveedor> proveedores = new ArrayList<>();
		try {
			Connection cn = c.getConnection();
			String sql="Select * from proveedores";			
			PreparedStatement ps =(PreparedStatement) cn.prepareStatement(sql);			
			ResultSet rs=ps.executeQuery();
			// llenando la data
			Proveedor pro;
			while(rs.next()){
				pro = new Proveedor(rs.getString("cod_proveedor"), rs.getString("razonsocial"),rs.getString("ruc"),rs.getString("direccion"), 
						rs.getString("telefono"),rs.getString("correo"));
				proveedores.add(pro);
			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return proveedores;
	}
	
	public Proveedor buscarPorCodigo(String codigo){		
		try {					
			Connection cn = c.getConnection();
			String sql="select * from proveedores where cod_proveedor = ?";
			PreparedStatement ps =(PreparedStatement) cn.prepareStatement(sql);
			ps.setString(1, codigo);
			ResultSet rs=ps.executeQuery();
			// llenando la data
			if(rs.next())
			{
				Proveedor pro =  new Proveedor(rs.getString("cod_proveedor"), rs.getString("razonsocial"),rs.getString("ruc"),rs.getString("direccion"), 
									rs.getString("telefono"),rs.getString("correo"));
				return pro;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public ResultSet buscar(String nom_proveedor){
		ResultSet rs = null;
		try {					
			Connection cn = c.getConnection();
			String sql="select * from proveedores where LOWER(razonsocial) like ?";
			PreparedStatement ps =(PreparedStatement) cn.prepareStatement(sql);
			ps.setString(1, "%" + nom_proveedor.toLowerCase() + "%");
			rs = ps.executeQuery();			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	public void guardar(String cod, String raz, String ruc, String dir, String tel, String cor){
		Proveedor pro = buscarPorCodigo(cod);
		if(pro != null){
			editar(cod,raz,ruc,dir,tel,cor);
		}else{
			registrar(cod,raz,ruc,dir,tel,cor);
		}
	}
	
	public void registrar(String cod, String raz, String ruc, String dir, String tel, String cor){		
		try {			
			Connection cn = c.getConnection();
			String sql="insert into proveedores values(?,?,?,?,?,?)";
			PreparedStatement ps=(PreparedStatement) cn.prepareStatement(sql);
			ps.setString(1,cod);
			ps.setString(2,raz);
			ps.setString(3,ruc);
			ps.setString(4,dir);
			ps.setString(5,tel);
			ps.setString(6,cor);			
			ps.executeUpdate();
			JOptionPane.showMessageDialog(null, "proveedor registrado");				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Hubo un problema al registrar el proveedor");
		}
	}
	public void editar(String cod, String raz, String ruc, String dir, String tel, String cor){		
		try {		
			Connection cn = c.getConnection();
			String sql="Update proveedores set cod_proveedor = ? , razonsocial = ? , ruc = ?, direccion = ?, telefono = ?, correo = ? where cod_proveedor = ?";
			PreparedStatement ps=(PreparedStatement) cn.prepareStatement(sql);
			ps.setString(1,cod);
			ps.setString(2, raz);
			ps.setString(3, ruc);
			ps.setString(4, dir);
			ps.setString(5, tel);
			ps.setString(6, cor);
			ps.setString(7, cod);
			ps.executeUpdate();
			JOptionPane.showMessageDialog(null, "proveedor actualizado");			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Hubo un problema al actualizar el proveedor");
		}
	}
	
	public boolean eliminar(String cod){
		boolean respuesta = false;
		try {
			Connection cn = c.getConnection();
			String sql="Delete from proveedores where cod_proveedor = ?";
			PreparedStatement ps=(PreparedStatement) cn.prepareStatement(sql);
			ps.setString(1,cod);			
			ps.executeUpdate();
			JOptionPane.showMessageDialog(null, "El proveedor se ha eliminado.");
			respuesta = true;			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Hubo un problema al eliminar el proveedor");
		}
		return respuesta;
	}
	
}
