package entidades;

public class articulo {
String codigo,descripcion,categoria,empaque;

public articulo() {
	super();	
}

public articulo(String codigo, String descripcion, String categoria, String empaque) {
	super();
	this.codigo = codigo;
	this.descripcion = descripcion;
	this.categoria = categoria;
	this.empaque = empaque;
}

public String getCodigo() {
	return codigo;
}

public void setCodigo(String codigo) {
	this.codigo = codigo;
}

public String getDescripcion() {
	return descripcion;
}

public void setDescripcion(String descripcion) {
	this.descripcion = descripcion;
}

public String getCategoria() {
	return categoria;
}

public void setCategoria(String categoria) {
	this.categoria = categoria;
}

public String getEmpaque() {
	return empaque;
}

public void setEmpaque(String empaque) {
	this.empaque = empaque;
}

}
