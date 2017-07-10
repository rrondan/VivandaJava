package jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import entidades.Articulo;
import entidades.Compra;
import entidades.DetalleCompra;
import entidades.Proveedor;

public class jdbccompra {
	private Conexion c;
	
	public jdbccompra(){
		c = new Conexion();
	}
	
	public List<Compra> listarTodo(){
		List<Compra> compras = new ArrayList<>();
		try {
			Connection cn = c.getConnection();
			String sql="SELECT * FROM vivanda.compras INNER JOIN vivanda.proveedores ON compras.cod_proveedor = proveedores.cod_proveedor ORDER BY fecha;";			
			PreparedStatement ps =(PreparedStatement) cn.prepareStatement(sql);			
			ResultSet rs=ps.executeQuery();
			// llenando la data
			Proveedor pro;
			Compra compra;
			List<DetalleCompra> detalle;
			while(rs.next()){
				pro = new Proveedor(rs.getString("cod_proveedor"), rs.getString("razonsocial"),rs.getString("ruc"),rs.getString("direccion"), 
						rs.getString("telefono"),rs.getString("correo"));
				detalle = listarDetalleCompra(rs.getInt("cod_compra"));
				compra = new Compra(pro,rs.getDouble("precio_total"),detalle,rs.getInt("cod_compra"), rs.getDate("fecha"));				
				compras.add(compra);
			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return compras;
	}
	
	public List<Compra> buscar(String nom_proveedor){
		List<Compra> compras = new ArrayList<>();
		try {					
			Connection cn = c.getConnection();
			String sql="SELECT * FROM vivanda.compras INNER JOIN vivanda.proveedores ON compras.cod_proveedor = proveedores.cod_proveedor where LOWER(proveedores.razonsocial) like ? ORDER BY fecha;";
			PreparedStatement ps =(PreparedStatement) cn.prepareStatement(sql);
			ps.setString(1, "%" + nom_proveedor.toLowerCase() + "%");
			ResultSet rs = ps.executeQuery();
			// llenando la data
			Proveedor pro;
			Compra compra;
			List<DetalleCompra> detalle;
			while(rs.next()){
				pro = new Proveedor(rs.getString("cod_proveedor"), rs.getString("razonsocial"),rs.getString("ruc"),rs.getString("direccion"), 
						rs.getString("telefono"),rs.getString("correo"));
				detalle = listarDetalleCompra(rs.getInt("cod_compra"));
				compra = new Compra(pro,rs.getDouble("precio_total"),detalle,rs.getInt("cod_compra"), rs.getDate("fecha"));
				compras.add(compra);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return compras;
	}
	
	public void guardar(Compra compra){		
		if(compra.getId() != -1){
			editar(compra);
		}else{
			registrar(compra);
		}
	}	
	
	public void editar(Compra compra){
		try {			
			Connection cn = c.getConnection();
			String sql_compra="Update compras set cod_proveedor = ? , precio_total = ?,  fecha = STR_TO_DATE( ?, '%d/%m/%Y')  where cod_compra = ?";
			PreparedStatement ps_compra=(PreparedStatement) cn.prepareStatement(sql_compra);
			ps_compra.setString(1, compra.getProveedor().getCodigo());
			ps_compra.setDouble(2, compra.getPrecioTotal());
			ps_compra.setString(3, compra.getFechaString());
			ps_compra.setInt(4, compra.getId());
			ps_compra.executeUpdate( );
			List<DetalleCompra> detallesAntiguo = listarDetalleCompra(compra.getId());
			for(DetalleCompra detalleAnt : detallesAntiguo){
				eliminarDetalleCompra(detalleAnt);
			}			
			for (DetalleCompra detalle : compra.getDetalleCompra()) {
				detalle.setCod_Compra(compra.getId());
				registrarDetalleCompra(detalle);
			}
			JOptionPane.showMessageDialog(null, "proveedor editado");				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Hubo un problema al editar el proveedor");
		}
	}
	public void registrar(Compra compra){
		try {			
			Connection cn = c.getConnection();
			String sql_compra="INSERT INTO compras (precio_total,cod_proveedor,fecha) VALUES (?,?, STR_TO_DATE( ?, '%d/%m/%Y'));";
			PreparedStatement ps_compra=(PreparedStatement) cn.prepareStatement(sql_compra, Statement.RETURN_GENERATED_KEYS);
			ps_compra.setDouble(1, compra.getPrecioTotal());
			ps_compra.setString(2, compra.getProveedor().getCodigo());
			ps_compra.setString(3, compra.getFechaString());
			ps_compra.executeUpdate( );
			ResultSet rs = ps_compra.getGeneratedKeys();
			int idCompra = -1;
			if (rs.next()){
			    idCompra = rs.getInt(1);
			    System.out.println("IdGenerado: " + idCompra);
			}
			for (DetalleCompra detalle : compra.getDetalleCompra()) {
				detalle.setCod_Compra(idCompra);
				registrarDetalleCompra(detalle);
			}
			JOptionPane.showMessageDialog(null, "Compra registrada");				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Hubo un problema al registrar la compra");
		}
	}
	public void eliminarCompra(Compra compra){
		try {
			Connection cn = c.getConnection();
			for(DetalleCompra detalle: compra.getDetalleCompra()){
				eliminarDetalleCompra(detalle);
			}
			String sql="Delete from compras where cod_compra = ?";
			PreparedStatement ps=(PreparedStatement) cn.prepareStatement(sql);
			ps.setInt(1,compra.getId());			
			ps.executeUpdate();
			JOptionPane.showMessageDialog(null, "La compra se ha eliminado.");			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Hubo un problema al eliminar la compra");
		}
	}
	
	public List<DetalleCompra> listarDetalleCompra(int cod_compra){
		List<DetalleCompra> detalles = new ArrayList<>();
		try {			
			Connection cn = c.getConnection();
			String sql_detalle = "SELECT detalle_compra.cod_detalle_compra, detalle_compra.cod_compra, detalle_compra.precio_unitario," +
													" detalle_compra.cantidad, articulo.cod_articulo, articulo.des_articulo, " +
													" empaque.descripcion as empaque, categoria.descripcion as categoria " +
													" FROM vivanda.detalle_compra"  +
													" INNER JOIN vivanda.articulo ON detalle_compra.cod_articulo = articulo.cod_articulo" +
													" INNER JOIN vivanda.empaque ON articulo.id_empaque = empaque.idempaque" +
													" INNER JOIN vivanda.categoria ON articulo.id_categoria = categoria.idcategoria" + 
													" WHERE detalle_compra.cod_compra = ?";
			PreparedStatement ps_detalle=(PreparedStatement) cn.prepareStatement(sql_detalle);
			ps_detalle.setInt(1, cod_compra);			
			ResultSet rs = ps_detalle.executeQuery();
			DetalleCompra detalle;
			Articulo art;
			while(rs.next()){
				art = new Articulo(rs.getString("cod_articulo"),rs.getString("des_articulo"),rs.getString("categoria"),rs.getString("empaque"));
				detalle = new DetalleCompra(art, rs.getDouble("precio_unitario"), rs.getInt("cantidad"), rs.getInt("cod_detalle_compra"),cod_compra);
				detalles.add(detalle);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Hubo un problema al listar los detalle de Compra");
		}
		return detalles;
	}
	public void registrarDetalleCompra(DetalleCompra detalle){
		try {			
			Connection cn = c.getConnection();
			String sql_detalle = "insert into detalle_compra(cod_compra,cod_articulo,precio_unitario,cantidad) values(?,?,?,?)";
			PreparedStatement ps_detalle=(PreparedStatement) cn.prepareStatement(sql_detalle);
			ps_detalle.setInt(1, detalle.getCod_Compra());
			ps_detalle.setString(2, detalle.getArticulo().getCodigo());
			ps_detalle.setDouble(3, detalle.getPrecioUnitario());
			ps_detalle.setInt(4, detalle.getCantidad());			
			ps_detalle.executeUpdate();					
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Hubo un problema al registrar el detalle de Compra");
		}
	}
	public void editarDetalleCompra(DetalleCompra detalle){
		try {			
			Connection cn = c.getConnection();
			String sql_detalle = "Update detalle_compra set cod_compra = ? , cod_articulo = ? , precio_unitario = ?, cantidad = ? where cod_detalle_compra = ?";
			PreparedStatement ps_detalle=(PreparedStatement) cn.prepareStatement(sql_detalle);
			ps_detalle.setInt(1, detalle.getCod_Compra());
			ps_detalle.setString(2, detalle.getArticulo().getCodigo());
			ps_detalle.setDouble(3, detalle.getPrecioUnitario());
			ps_detalle.setInt(4, detalle.getCantidad());
			ps_detalle.setInt(5, detalle.getId());
			ps_detalle.executeUpdate();	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Hubo un problema al actualizar el detalle de Compra");
		}		
	}
	public void eliminarDetalleCompra(DetalleCompra detalle){
		try {			
			Connection cn = c.getConnection();
			String sql_detalle = "Delete from detalle_compra where cod_detalle_compra = ?";
			PreparedStatement ps_detalle=(PreparedStatement) cn.prepareStatement(sql_detalle);
			ps_detalle.setInt(1, detalle.getId());
			ps_detalle.executeUpdate();	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Hubo un problema al eliminar el detalle de Compra");
		}		
	}
}
