package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import entidades.Articulo;
import entidades.Compra;
import entidades.DetalleCompra;
import entidades.Proveedor;
import jdbc.jdbcarticulo;
import jdbc.jdbccompra;
import jdbc.jdbcproveedor;
import libraries.AutocompleteJComboBox;
import libraries.DateLabelFormatter;
import libraries.StringSearchable;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class guicompras extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTextField txtRuc;
	private JTextField txtDireccion;
	private JTable tblDetalleCompra;
	private JTextField txtTotal;
	private JTextField txtRzSocialBuscar;
	private JTable tblCompras;
	private JButton btnAgregarDetalle;
	private JButton btnEliminarDetalle;
	private JButton btnBuscar;
	private JButton btnListarTodos;
	private JButton btnNuevo;
	private JButton btnModificar;
	private JButton btnEliminarCompra;
	private JButton btnGuardar;
	private JButton btnCancelar;
	private JPanel regCompraPanel ;
	private JScrollPane scrollPane;
	private JScrollPane scrollPaneCompras;
	private JPanel regDetallePanel;
	private JTextField txtCodArticulo;
	private JTextField txtDesArticulo;
	private JTextField txtCategoria;
	private JTextField txtEmpaque;
	private JLabel lblPrecioUnitario;
	private JTextField txtPrecioUnitario;
	private JLabel lblCantidad;
	private JTextField txtCantidad;
	private JButton btnBuscarCodArticulo;
	private JButton btnBAvanzada;
	private JButton btnAgregar;
	private JButton btnCancelarDetalle;
	private JPanel busAvanzadaPanel;
	private JLabel lblNombreArticulo;
	private JTextField txtNomArticulo;
	private JButton btnSeleccionar;
	private JButton btnCancelarAvanzada;
	private JTable tblArticulos;
	
	
	private AutocompleteJComboBox  cbRzSocial;	
	private DefaultTableModel modeloTblCompras;
	private DefaultTableModel modeloTblDetalle;
	private DefaultTableModel modeloTblArticulo;
	private jdbcproveedor jdbcProveedor;	
	private List<Proveedor> proveedores ;
	private jdbccompra jdbcCompra;
	private List<Compra> compras;
	private jdbcarticulo jdbcArticulo;
	private List<Articulo> articulos;
	private Compra compraSeleccionado;
	private Articulo articuloSeleccionado;
	private JLabel lblFecha;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					guicompras frame = new guicompras();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public guicompras() {
		setTitle("Mantenimiento de Compras");
		jdbcProveedor = new jdbcproveedor();
		jdbcCompra = new jdbccompra();
		jdbcArticulo = new jdbcarticulo();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(40, 40, 843, 658);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		UtilDateModel model = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		
		regCompraPanel = new JPanel();
		regCompraPanel.setBounds(10, 255, 471, 353);		
		contentPane.add(regCompraPanel);
		regCompraPanel.setLayout(null);
		
		JLabel lblRuc = new JLabel("RUC:");
		lblRuc.setBounds(23, 30, 46, 14);
		regCompraPanel.add(lblRuc);
		
		txtRuc = new JTextField();
		txtRuc.setBounds(114, 27, 119, 20);
		regCompraPanel.add(txtRuc);
		txtRuc.setEditable(false);
		txtRuc.setColumns(10);
		
		JLabel lblRazonSocial = new JLabel("Razon Social:");
		lblRazonSocial.setBounds(23, 58, 87, 14);
		regCompraPanel.add(lblRazonSocial);		

		List<String> myWords = new ArrayList<String>();
		proveedores = jdbcProveedor.listarTodo();
		for (Proveedor proveedor : proveedores) {
			myWords.add(proveedor.getRazonSocial());
		}
		StringSearchable searchable = new StringSearchable(myWords);		
		cbRzSocial = new AutocompleteJComboBox (searchable);
		cbRzSocial.setBounds(114, 55, 296, 20);
		cbRzSocial.addItemListener(new ItemListener() {			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED){
					buscarProveedor((String) e.getItem());
				}
			}
		});
		regCompraPanel.add(cbRzSocial);
		
		JLabel lblDireccin = new JLabel("Direcci\u00F3n:");
		lblDireccin.setBounds(23, 83, 87, 14);
		regCompraPanel.add(lblDireccin);
		
		txtDireccion = new JTextField();
		txtDireccion.setBounds(114, 80, 296, 20);
		regCompraPanel.add(txtDireccion);
		txtDireccion.setEditable(false);
		txtDireccion.setColumns(10);
		
		JLabel lblDetalleDeCompra = new JLabel("Detalle de Compra");
		lblDetalleDeCompra.setBounds(23, 118, 119, 14);
		regCompraPanel.add(lblDetalleDeCompra);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(23, 132, 338, 136);
		regCompraPanel.add(scrollPane);
		
		tblDetalleCompra = new JTable();
		scrollPane.setViewportView(tblDetalleCompra);
		tblDetalleCompra.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		btnAgregarDetalle = new JButton("Agregar");
		btnAgregarDetalle.setBounds(369, 130, 92, 44);
		btnAgregarDetalle.addActionListener(this);
		regCompraPanel.add(btnAgregarDetalle);
		
		btnEliminarDetalle = new JButton("Eliminar");
		btnEliminarDetalle.setBounds(369, 185, 92, 44);
		btnEliminarDetalle.addActionListener(this);
		regCompraPanel.add(btnEliminarDetalle);
		
		JLabel lblTotal = new JLabel("Total:");
		lblTotal.setBounds(224, 274, 55, 14);
		regCompraPanel.add(lblTotal);
		
		txtTotal = new JTextField();
		txtTotal.setBounds(275, 271, 86, 20);
		regCompraPanel.add(txtTotal);
		txtTotal.setEditable(false);
		txtTotal.setColumns(10);
		
		btnGuardar = new JButton("Guardar");
		btnGuardar.setBounds(101, 302, 100, 40);
		btnGuardar.addActionListener(this);
		regCompraPanel.add(btnGuardar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(222, 302, 100, 40);
		btnCancelar.addActionListener(this);
		regCompraPanel.add(btnCancelar);
		
		JLabel lblRegistroDeCompras = new JLabel("Registro de Compras");
		lblRegistroDeCompras.setBounds(23, 5, 134, 14);
		regCompraPanel.add(lblRegistroDeCompras);
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		datePicker.setBounds(290, 24, 120, 26);
		regCompraPanel.add(datePicker);
		
		lblFecha = new JLabel("Fecha:");
		lblFecha.setBounds(245, 30, 55, 14);
		regCompraPanel.add(lblFecha);
		
		JLabel lblBuscarPorRazon = new JLabel("Buscar Compras por Razon Social de Proveedor");
		lblBuscarPorRazon.setBounds(120, 11, 348, 14);
		contentPane.add(lblBuscarPorRazon);
		
		JLabel lblRazonSocial_1 = new JLabel("Razon Social: ");
		lblRazonSocial_1.setBounds(120, 36, 81, 14);
		contentPane.add(lblRazonSocial_1);
		
		txtRzSocialBuscar = new JTextField();
		txtRzSocialBuscar.setBounds(211, 33, 197, 20);
		contentPane.add(txtRzSocialBuscar);
		txtRzSocialBuscar.setColumns(10);
		
		btnBuscar = new JButton("Buscar");
		btnBuscar.setBounds(418, 32, 89, 23);
		btnBuscar.addActionListener(this);
		contentPane.add(btnBuscar);
		
		scrollPaneCompras = new JScrollPane();
		scrollPaneCompras.setBounds(120, 61, 387, 183);
		contentPane.add(scrollPaneCompras);
		
		tblCompras = new JTable();
		scrollPaneCompras.setViewportView(tblCompras);		
		tblCompras.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				
		btnListarTodos = new JButton("Listar Todos");
		btnListarTodos.setBounds(517, 61, 116, 43);
		btnListarTodos.addActionListener(this);
		contentPane.add(btnListarTodos);
		
		btnNuevo = new JButton("Nuevo");
		btnNuevo.setBounds(517, 108, 116, 43);
		btnNuevo.addActionListener(this);
		contentPane.add(btnNuevo);
		
		btnModificar = new JButton("Modificar");
		btnModificar.setBounds(517, 155, 116, 43);
		btnModificar.addActionListener(this);
		contentPane.add(btnModificar);
		
		btnEliminarCompra = new JButton("Eliminar");
		btnEliminarCompra.setBounds(517, 202, 116, 43);
		btnEliminarCompra.addActionListener(this);
		contentPane.add(btnEliminarCompra);
		
		regDetallePanel = new JPanel();
		regDetallePanel.setBounds(491, 255, 331, 212);
		contentPane.add(regDetallePanel);
		regDetallePanel.setLayout(null);
		
		JLabel lblRegistroDeDetalle = new JLabel("Registro de Detalle");
		lblRegistroDeDetalle.setBounds(10, 11, 116, 14);
		regDetallePanel.add(lblRegistroDeDetalle);
		
		JLabel lblCodArticulo = new JLabel("Cod. Articulo:");
		lblCodArticulo.setBounds(10, 36, 85, 14);
		regDetallePanel.add(lblCodArticulo);
		
		txtCodArticulo = new JTextField();
		txtCodArticulo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {				
				txtDesArticulo.setText("");
				txtCategoria.setText("");
				txtEmpaque.setText("");
				articuloSeleccionado = null;
			}
		});
		txtCodArticulo.setBounds(99, 33, 86, 20);
		regDetallePanel.add(txtCodArticulo);
		txtCodArticulo.setColumns(10);
		
		JLabel lblDescripcin = new JLabel("Descripci\u00F3n:");
		lblDescripcin.setBounds(10, 60, 72, 14);
		regDetallePanel.add(lblDescripcin);
		
		txtDesArticulo = new JTextField();
		txtDesArticulo.setEditable(false);
		txtDesArticulo.setBounds(99, 57, 222, 20);
		regDetallePanel.add(txtDesArticulo);
		txtDesArticulo.setColumns(10);
		
		JLabel lblCategoria = new JLabel("Categoria:");
		lblCategoria.setBounds(10, 84, 72, 14);
		regDetallePanel.add(lblCategoria);
		
		txtCategoria = new JTextField();
		txtCategoria.setEditable(false);
		txtCategoria.setBounds(99, 81, 86, 20);
		regDetallePanel.add(txtCategoria);
		txtCategoria.setColumns(10);
		
		JLabel lblEmpaque = new JLabel("Empaque: ");
		lblEmpaque.setBounds(10, 108, 85, 14);
		regDetallePanel.add(lblEmpaque);
		
		txtEmpaque = new JTextField();
		txtEmpaque.setEditable(false);
		txtEmpaque.setBounds(99, 105, 86, 20);
		regDetallePanel.add(txtEmpaque);
		txtEmpaque.setColumns(10);
		
		lblPrecioUnitario = new JLabel("Precio Unitario:");
		lblPrecioUnitario.setBounds(10, 132, 103, 14);
		regDetallePanel.add(lblPrecioUnitario);
		
		txtPrecioUnitario = new JTextField();
		txtPrecioUnitario.setBounds(99, 129, 86, 20);
		regDetallePanel.add(txtPrecioUnitario);
		txtPrecioUnitario.setColumns(10);
		
		
		lblCantidad = new JLabel("Cantidad:");
		lblCantidad.setBounds(10, 156, 85, 14);
		regDetallePanel.add(lblCantidad);
		
		txtCantidad = new JTextField();
		txtCantidad.setBounds(99, 153, 86, 20);
		regDetallePanel.add(txtCantidad);
		txtCantidad.setColumns(10);
		
		btnBuscarCodArticulo = new JButton("Buscar");
		btnBuscarCodArticulo.setBounds(195, 32, 92, 23);
		btnBuscarCodArticulo.addActionListener(this);
		regDetallePanel.add(btnBuscarCodArticulo);
		
		btnBAvanzada = new JButton("B. Avanzada");
		btnBAvanzada.setBounds(195, 80, 126, 23);
		btnBAvanzada.addActionListener(this);
		regDetallePanel.add(btnBAvanzada);
		
		btnAgregar = new JButton("Agregar");
		btnAgregar.setBounds(52, 181, 89, 23);
		btnAgregar.addActionListener(this);
		regDetallePanel.add(btnAgregar);
		
		btnCancelarDetalle = new JButton("Cancelar");
		btnCancelarDetalle.setBounds(177, 181, 89, 23);
		btnCancelarDetalle.addActionListener(this);
		regDetallePanel.add(btnCancelarDetalle);
		
		busAvanzadaPanel = new JPanel();
		busAvanzadaPanel.setBounds(491, 472, 331, 136);
		contentPane.add(busAvanzadaPanel);
		busAvanzadaPanel.setLayout(null);
		
		lblNombreArticulo = new JLabel("Nombre Articulo: ");
		lblNombreArticulo.setBounds(10, 11, 105, 14);
		busAvanzadaPanel.add(lblNombreArticulo);
		
		txtNomArticulo = new JTextField();
		txtNomArticulo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {				
				limpiarTablaArticulos();
				buscarArticulos();
			}
		});
		txtNomArticulo.setBounds(117, 8, 204, 20);		
		busAvanzadaPanel.add(txtNomArticulo);
		txtNomArticulo.setColumns(10);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 36, 311, 73);
		busAvanzadaPanel.add(scrollPane_1);
		
		tblArticulos = new JTable();
		scrollPane_1.setViewportView(tblArticulos);
		tblArticulos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		btnSeleccionar = new JButton("Seleccionar");
		btnSeleccionar.setBounds(56, 113, 122, 23);
		btnSeleccionar.addActionListener(this);
		busAvanzadaPanel.add(btnSeleccionar);
		
		btnCancelarAvanzada = new JButton("Cancelar");
		btnCancelarAvanzada.setBounds(188, 113, 100, 23);
		btnCancelarAvanzada.addActionListener(this);
		busAvanzadaPanel.add(btnCancelarAvanzada);
		regCompraPanel.setVisible(false);
		regDetallePanel.setVisible(false);
		busAvanzadaPanel.setVisible(false);
		tablaCompras();
		tablaDetalleCompra();
		tablaArticulos();
	}

	// Eventos de Action Listener
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnBuscar) {
			actionPerformedBtnBuscar(e);
		}
		if (e.getSource() == btnNuevo) {
			actionPerformedBtnNuevo(e);
		}
		if (e.getSource() == btnModificar) {
			actionPerformedBtnModificar(e);
		}
		if (e.getSource() == btnEliminarCompra) {
			actionPerformedBtnEliminarCompra(e);
		}
		if (e.getSource() == btnListarTodos) {
			actionPerformedBtnListarTodos(e);
		}
		if (e.getSource() == btnAgregarDetalle) {
			actionPerformedBtnAgregarDetalle(e);
		}
		if (e.getSource() == btnEliminarDetalle) {
			actionPerformedBtnEliminarDetalle(e);
		}		
		if (e.getSource() == btnGuardar) {
			actionPerformedBtnGuardar(e);
		}				
		if (e.getSource() == btnCancelar) {
			actionPerformedBtnCancelar(e);
		}
		if(e.getSource() == btnBuscarCodArticulo){
			actionPerformedBtnBuscarCodArticulo(e);
		}
		if(e.getSource() == btnBAvanzada){
			actionPerformedBtnBAvanzada(e);
		}
		if(e.getSource() == btnAgregar){
			actionPerformedBtnAgregar(e);
		}
		if(e.getSource() == btnCancelarDetalle){
			actionPerformedBtnCancelarDetalle(e);
		}
		if(e.getSource() == btnSeleccionar){
			actionPerformedBtnSeleccionar(e);
		}
		if(e.getSource() == btnCancelarAvanzada){
			actionPerformedBtnCancelarAvanzada(e);
		}
	}
	protected void actionPerformedBtnBuscar(ActionEvent e) {
		buscarCompra();		
	}
	protected void actionPerformedBtnNuevo(ActionEvent e) {
		compraSeleccionado = new Compra();
		txtTotal.setText("0");
		regCompraPanel.setVisible(true);
	}
	protected void actionPerformedBtnModificar(ActionEvent e) {
		llenarDataParaModificar();
	}
	protected void actionPerformedBtnEliminarCompra(ActionEvent e) {
		
	}
	protected void actionPerformedBtnListarTodos(ActionEvent e) {
		listarTodoCompras();
	}
	protected void actionPerformedBtnAgregarDetalle(ActionEvent e) {		
		regDetallePanel.setVisible(true);
	}
	protected void actionPerformedBtnEliminarDetalle(ActionEvent e) {
		
	}
	protected void actionPerformedBtnGuardar(ActionEvent e) {
		
	}
	protected void actionPerformedBtnCancelar(ActionEvent e) {
		regCompraPanel.setVisible(false);
		regDetallePanel.setVisible(false);
		busAvanzadaPanel.setVisible(false);
	}
	protected void actionPerformedBtnBuscarCodArticulo(ActionEvent e) {
		BuscarArticuloPorCodigo();
	}	
	protected void actionPerformedBtnBAvanzada(ActionEvent e) {
		limpiarCamposBusquedaAvanzada();
		busAvanzadaPanel.setVisible(true);
	}	
	protected void actionPerformedBtnAgregar(ActionEvent e) {
		AgregarDetalleDeCompra();
	}	
	protected void actionPerformedBtnCancelarDetalle(ActionEvent e) {
		regDetallePanel.setVisible(false);
		busAvanzadaPanel.setVisible(false);
		limpiarCamposRegistroDetalle();
	}	
	protected void actionPerformedBtnSeleccionar(ActionEvent e) {
		SeleccionarArticulo();
	}	
	protected void actionPerformedBtnCancelarAvanzada(ActionEvent e) {
		limpiarCamposBusquedaAvanzada();
		busAvanzadaPanel.setVisible(false);
	}
	
	// metodos privados
	private void tablaCompras(){
		modeloTblCompras= new DefaultTableModel(){
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		modeloTblCompras.addColumn("#");
		modeloTblCompras.addColumn("Razon Social Proveedor");
		modeloTblCompras.addColumn("Total Compra");		
		tblCompras.setModel(modeloTblCompras);
		tblCompras.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );		
		tblCompras.getColumnModel().getColumn(0).setPreferredWidth(50);
		tblCompras.getColumnModel().getColumn(0).setMaxWidth(50);
		tblCompras.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		tblCompras.getColumnModel().getColumn(2).setPreferredWidth(100);
		tblCompras.getColumnModel().getColumn(2).setMaxWidth(100);
		tblCompras.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
	}	
	private void tablaDetalleCompra(){
		modeloTblDetalle = new DefaultTableModel(){
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		modeloTblDetalle.addColumn("#");
		modeloTblDetalle.addColumn("Articulo");
		modeloTblDetalle.addColumn("P/U");
		modeloTblDetalle.addColumn("Cantidad");
		modeloTblDetalle.addColumn("Total");		
		tblDetalleCompra.setModel(modeloTblDetalle);		
		tblDetalleCompra.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		tblDetalleCompra.getColumnModel().getColumn(0).setPreferredWidth(30);
		tblDetalleCompra.getColumnModel().getColumn(0).setMaxWidth(30);
		tblDetalleCompra.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		tblDetalleCompra.getColumnModel().getColumn(2).setPreferredWidth(50);
		tblDetalleCompra.getColumnModel().getColumn(2).setMaxWidth(50);
		tblDetalleCompra.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
		tblDetalleCompra.getColumnModel().getColumn(3).setPreferredWidth(60);
		tblDetalleCompra.getColumnModel().getColumn(3).setMaxWidth(60);
		tblDetalleCompra.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
		tblDetalleCompra.getColumnModel().getColumn(4).setPreferredWidth(65);
		tblDetalleCompra.getColumnModel().getColumn(4).setMaxWidth(65);
		tblDetalleCompra.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
	}
	private void limpiartablaCompras(){
		if (modeloTblCompras.getRowCount() > 0) {
		    for (int i = modeloTblCompras.getRowCount() - 1; i > -1; i--) {
		    	modeloTblCompras.removeRow(i);
		    }
		}
	}	
	private void llenarTablaCompras(){
		int index = 1;
		for(Compra com : compras){
			Object[] fila=new Object[3];		          
			fila[0] = index;			
			fila[1] = com.getProveedor().getRazonSocial();
			fila[2] = com.getPrecioTotal();							
			modeloTblCompras.addRow(fila);
			index++;
		}
	}
	private void buscarProveedor(String razonSocial){
		boolean encontrado = false;
		for(Proveedor proveedor: proveedores){
				if(proveedor.getRazonSocial().equals(razonSocial)){
					txtRuc.setText(proveedor.getRUC());
					txtDireccion.setText(proveedor.getDireccion());
					encontrado = true;
				}
		}
		if(!encontrado){
			txtRuc.setText("");
			txtDireccion.setText("");
		}
	}
	
	private void buscarCompra(){
		limpiartablaCompras();
		compras = jdbcCompra.buscar(txtRzSocialBuscar.getText());
		llenarTablaCompras();
	}
	private void listarTodoCompras(){
		limpiartablaCompras();
		compras = jdbcCompra.listarTodo();
		llenarTablaCompras();
	}
	private void llenarDataParaModificar(){
		int index = tblCompras.getSelectedRow();
		if(index != -1){						
			if(!regCompraPanel.isVisible()) regCompraPanel.setVisible(true);
			limpiarCampos();
			compraSeleccionado = compras.get(index);
			cbRzSocial.setSelectedItem(compraSeleccionado.getProveedor().getRazonSocial());
			llenarTablaDetalle(compraSeleccionado.getDetalleCompra());
			txtTotal.setText(String.valueOf(compraSeleccionado.getPrecioTotal()));
		}else{
			JOptionPane.showMessageDialog(null,"Seleccione un proveedor");
		}	
	}
	
	private void limpiarCampos(){
		txtRuc.setText("");
		txtDireccion.setText("");
		txtTotal.setText("");
		cbRzSocial.setSelectedItem("");
		limpiarTablaDetalle();
	}
	private void limpiarTablaDetalle(){
		if (modeloTblDetalle.getRowCount() > 0) {
		    for (int i = modeloTblDetalle.getRowCount() - 1; i > -1; i--) {
		    	modeloTblDetalle.removeRow(i);
		    }
		}
	}
	private void llenarTablaDetalle(List<DetalleCompra> detalle){
		int index = 1;
		for(DetalleCompra det : detalle){
			Object[] fila=new Object[5];		          
			fila[0] = index;		         
			fila[1] = det.getArticulo().getDescripcion();
			fila[2] = det.getPrecioUnitario();
			fila[3] = det.getCantidad();
			fila[4] = (det.getCantidad() * det.getPrecioUnitario());
			modeloTblDetalle.addRow(fila);
			index++;
		}
	}
	private void tablaArticulos(){
		modeloTblArticulo = new DefaultTableModel(){
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		modeloTblArticulo.addColumn("Codigo");
		modeloTblArticulo.addColumn("Descripcion");
		modeloTblArticulo.addColumn("Categoria");
		modeloTblArticulo.addColumn("Empaque");		
		tblArticulos.setModel(modeloTblArticulo);		
		tblArticulos.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		tblArticulos.getColumnModel().getColumn(0).setPreferredWidth(50);
		tblArticulos.getColumnModel().getColumn(0).setMaxWidth(50);
		tblArticulos.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		tblArticulos.getColumnModel().getColumn(2).setPreferredWidth(60);
		tblArticulos.getColumnModel().getColumn(2).setMaxWidth(60);
		tblArticulos.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
		tblArticulos.getColumnModel().getColumn(3).setPreferredWidth(60);
		tblArticulos.getColumnModel().getColumn(3).setMaxWidth(60);
		tblArticulos.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);		
	}
	private void limpiarTablaArticulos(){
		if (modeloTblArticulo.getRowCount() > 0) {
		    for (int i = modeloTblArticulo.getRowCount() - 1; i > -1; i--) {
		    	modeloTblArticulo.removeRow(i);
		    }
		}
	}	
	private void buscarArticulos(){
		try{			
			String descripcion=txtNomArticulo.getText().trim();			
			articulos = jdbcArticulo.buscarXDes(descripcion);
			for (Articulo articulo : articulos) {
				Object[] fila=new Object[4];          
				fila[0] = articulo.getCodigo();          
				fila[1] = articulo.getDescripcion();          
				fila[2] = articulo.getCategoria();          
				fila[3] = articulo.getEmpaque();          
				modeloTblArticulo.addRow(fila);
			}			
		}catch (Exception ex){
			System.out.println(ex.getMessage());
		}
	}
	private void SeleccionarArticulo(){
		int index = tblArticulos.getSelectedRow();
		if(index != -1){
			articuloSeleccionado = articulos.get(index);
			txtCodArticulo.setText(articuloSeleccionado.getCodigo());
			LlenarDatosArticulo();
			busAvanzadaPanel.setVisible(false);
			limpiarCamposBusquedaAvanzada();
		}else{
			JOptionPane.showMessageDialog(null,"Seleccion un articulo");
		}	
	}
	private void LlenarDatosArticulo(){
		txtDesArticulo.setText(articuloSeleccionado.getDescripcion());
		txtCategoria.setText(articuloSeleccionado.getCategoria());
		txtEmpaque.setText(articuloSeleccionado.getEmpaque());
	}
	private void BuscarArticuloPorCodigo(){
		String codigoArt = txtCodArticulo.getText().trim(); 
		if( codigoArt.length() >  0){
			Articulo art = jdbcArticulo.buscarPorCodigo(codigoArt);
			if(art != null){
				articuloSeleccionado = art;
				LlenarDatosArticulo();
			}else{
				JOptionPane.showMessageDialog(null,"No existe un artículo con ese código.");
			}
		}else{
			JOptionPane.showMessageDialog(null,"Escriba un codigo de artículo.");
		}
	}
	private void AgregarDetalleDeCompra(){
		if(articuloSeleccionado == null){
			JOptionPane.showMessageDialog(null,"Escoja un articulo.");
		}		
		if(txtPrecioUnitario.getText().trim().length() == 0){
			JOptionPane.showMessageDialog(null,"Escriba un precio unitario.");
		}
		if(txtCantidad.getText().trim().length() == 0){
			JOptionPane.showMessageDialog(null,"Escriba una cantidad.");
		}
		double precioUnitario = Double.parseDouble(txtPrecioUnitario.getText().trim());
		int cantidad = Integer.parseInt(txtCantidad.getText().trim());
		double precioTotal = precioUnitario * cantidad;
		DetalleCompra detalleCompra = new DetalleCompra();
		detalleCompra.setArticulo(articuloSeleccionado);
		detalleCompra.setPrecioUnitario(precioUnitario);
		detalleCompra.setCantidad(cantidad);
		compraSeleccionado.addDetalleCompra(detalleCompra);
		compraSeleccionado.sumarAlTotal(precioTotal);
		limpiarTablaDetalle();
		llenarTablaDetalle(compraSeleccionado.getDetalleCompra());
		txtTotal.setText(String.valueOf(compraSeleccionado.getPrecioTotal()));
		limpiarCamposRegistroDetalle();
	}
	private void limpiarCamposRegistroDetalle(){
		txtCodArticulo.setText("");
		txtDesArticulo.setText("");
		txtCategoria.setText("");
		txtEmpaque.setText("");
		txtPrecioUnitario.setText("");
		txtCantidad.setText("");
	}
	private void limpiarCamposBusquedaAvanzada(){
		txtNomArticulo.setText("");
		limpiarTablaArticulos();
	}
}
