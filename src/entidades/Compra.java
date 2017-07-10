package entidades;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class Compra {
	
	private int id;
	private Proveedor proveedor;
	private double precioTotal;
	private List<DetalleCompra> detalleCompra;
	private Date fecha;
	private String fechaString;
	
	public Compra() {
		super();
		this.id = -1;
		this.detalleCompra = new ArrayList<>();
		this.precioTotal = 0;
	}
	public Compra(Proveedor proveedor, double precioTotal, List<DetalleCompra> detalleCompra,int id, Date fecha) {
		super();
		this.proveedor = proveedor;
		this.precioTotal = precioTotal;
		this.detalleCompra = detalleCompra;
		if(detalleCompra != null){
			if(this.detalleCompra.isEmpty()) 
				this.precioTotal = 0;
		}
		this.id = id;
		this.fecha = fecha;
	}
	public Proveedor getProveedor() {
		return proveedor;
	}
	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}
	public double getPrecioTotal() {
		return precioTotal;
	}
	public void setPrecioTotal(double precioTotal) {
		this.precioTotal = precioTotal;
	}
	public List<DetalleCompra> getDetalleCompra() {
		return detalleCompra;
	}
	public void setDetalleCompra(List<DetalleCompra> detalleCompra) {
		this.detalleCompra = detalleCompra;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}	
	public void addDetalleCompra(DetalleCompra detalle){
		if(detalleCompra == null){
			detalleCompra = new ArrayList<>();
		}
		detalleCompra.add(detalle);
	}
	public void updateDetallecompra(DetalleCompra detalle, int posicion){
		detalleCompra.set(posicion,detalle);
	}
	public void removeDetalleCompra(int posicion){
		if(detalleCompra.get(posicion) == null){
			JOptionPane.showMessageDialog(null, "Hubo un eliminar su detalle");
			return;
		}
		detalleCompra.remove(posicion);
	}
	public void CalcularTotal(){
		this.precioTotal = 0;
		for (DetalleCompra detalle : this. detalleCompra) {
			this.precioTotal += (detalle.getPrecioUnitario() * detalle.getCantidad());
		}
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getFechaString() {
		return fechaString;
	}
	public void setFechaString(String fechaString) {
		this.fechaString = fechaString;
	}
	
	
}
