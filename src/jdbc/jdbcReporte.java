package jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.CallableStatement;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import entidades.Compra;
import entidades.DetalleCompra;
import entidades.Proveedor;

public class jdbcReporte {
	
	private Conexion c;

	public jdbcReporte() {
		c = new Conexion();
	}

	public List<Compra> generarReporte(String fechaInicio, String fechaFin, String codProveedor){
		List<Compra> compras = new ArrayList<>();
		try {
			Connection cn = c.getConnection();
			String sql="{CALL sp_busquedaCompraPorFechas(?,?,?)}";			
			CallableStatement  stmp = (CallableStatement) cn.prepareCall(sql);
			stmp.setString(1, fechaInicio);
			stmp.setString(2, fechaFin);
			stmp.setString(3, codProveedor);
			ResultSet rs = stmp.executeQuery();
			// llenando la data
			Proveedor pro;
			Compra compra;
			List<DetalleCompra> detalle;
			while(rs.next()){
				pro = new Proveedor(rs.getString("cod_proveedor"), rs.getString("razonsocial"),rs.getString("ruc"),rs.getString("direccion"), 
						rs.getString("telefono"),rs.getString("correo"));				
				compra = new Compra(pro,rs.getDouble("precio_total"), null ,rs.getInt("cod_compra"), rs.getDate("fecha"));				
				compras.add(compra);
			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return compras;
	}
	
}
