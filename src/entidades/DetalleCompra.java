package entidades;

public class DetalleCompra {
		private int id;
		private Articulo articulo;
		private double precioUnitario;
		private int cantidad;
		private int cod_compra;
		
		
		public DetalleCompra() {
			super();		
			this.id = -1;
		}
		
		public DetalleCompra(Articulo articulo, double precioUnitario, int cantidad, int id,int cod_compra) {
			super();
			this.articulo = articulo;
			this.precioUnitario = precioUnitario;
			this.cantidad = cantidad;
			this.id = id;
			this.cod_compra = cod_compra;
		}

		public Articulo getArticulo() {
			return articulo;
		}

		public void setArticulo(Articulo articulo) {
			this.articulo = articulo;
		}

		public double getPrecioUnitario() {
			return precioUnitario;
		}

		public void setPrecioUnitario(double precioUnitario) {
			this.precioUnitario = precioUnitario;
		}

		public int getCantidad() {
			return cantidad;
		}

		public void setCantidad(int cantidad) {
			this.cantidad = cantidad;
		}

		public int getCod_Compra() {
			return cod_compra;
		}

		public void setCod_Compra(int cod_Compra) {
			this.cod_compra = cod_Compra;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}
		
}
