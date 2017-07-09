package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import entidades.Articulo;
import entidades.Proveedor;
import jdbc.jdbcarticulo;
import jdbc.jdbcproveedor;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.List;
import java.awt.event.ActionEvent;

public class guiproveedores extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTextField txtCodigoBuscar;
	private JTable tblProveedores;
	private JButton btnBuscar;
	private JButton btnListarTodos;
	private JButton btnNuevo;
	private JButton btnModificar;
	private JButton btnEliminar;
	private JPanel proveedorPanel;
	private JLabel lblRuc;
	private JLabel lblRazonSocial;
	private JTextField txtRuc;
	private JLabel lblCodigo;
	private JTextField txtCodigo;
	private JTextField txtRazonSocial;
	private JLabel lblDireccion;
	private JLabel lblTelfono;
	private JLabel lblCorreo;
	private JTextField txtDireccion;
	private JTextField txtTelefono;
	private JTextField txtCorreo;
	private JButton btnGuardar;
	private JButton btnCancelar;
	
	private jdbcproveedor jdbc;	
	private DefaultTableModel modelo;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					guiproveedores frame = new guiproveedores();
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
	public guiproveedores() {
		jdbc = new jdbcproveedor();
		setTitle("Mantenimiento de Proveedores");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 612, 585);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCodigoABuscar = new JLabel("Proveedor a Buscar: ");
		lblCodigoABuscar.setBounds(29, 32, 127, 14);
		contentPane.add(lblCodigoABuscar);
		
		txtCodigoBuscar = new JTextField();
		txtCodigoBuscar.setBounds(150, 29, 199, 20);
		contentPane.add(txtCodigoBuscar);
		txtCodigoBuscar.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(29, 70, 435, 185);
		contentPane.add(scrollPane);
		
		tblProveedores = new JTable();
		scrollPane.setViewportView(tblProveedores);
		tblProveedores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(this);
		btnBuscar.setBounds(359, 28, 105, 23);
		contentPane.add(btnBuscar);
		
		btnListarTodos = new JButton("Listar Todos");
		btnListarTodos.addActionListener(this);
		btnListarTodos.setBounds(474, 70, 105, 39);
		contentPane.add(btnListarTodos);
		
		btnNuevo = new JButton("Nuevo");
		btnNuevo.addActionListener(this);
		btnNuevo.setBounds(474, 116, 105, 39);
		contentPane.add(btnNuevo);
		
		btnModificar = new JButton("Modificar");
		btnModificar.addActionListener(this);
		btnModificar.setBounds(474, 166, 105, 39);
		contentPane.add(btnModificar);
		
		btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(this);
		btnEliminar.setBounds(474, 216, 105, 39);
		contentPane.add(btnEliminar);
		
		proveedorPanel = new JPanel();
		proveedorPanel.setBounds(29, 282, 557, 237);
		contentPane.add(proveedorPanel);
		proveedorPanel.setLayout(null);
		
		lblRuc = new JLabel("RUC: ");
		lblRuc.setBounds(75, 39, 71, 14);
		proveedorPanel.add(lblRuc);
		
		lblRazonSocial = new JLabel("Razon Social:");
		lblRazonSocial.setBounds(75, 64, 82, 14);
		proveedorPanel.add(lblRazonSocial);
		
		txtRuc = new JTextField();
		txtRuc.setBounds(162, 36, 285, 20);
		proveedorPanel.add(txtRuc);
		txtRuc.setColumns(10);
		
		lblCodigo = new JLabel("Codigo:");
		lblCodigo.setBounds(75, 11, 71, 14);
		proveedorPanel.add(lblCodigo);
		
		txtCodigo = new JTextField();
		txtCodigo.setBounds(162, 8, 86, 20);
		proveedorPanel.add(txtCodigo);
		txtCodigo.setColumns(10);
		
		txtRazonSocial = new JTextField();
		txtRazonSocial.setBounds(162, 61, 285, 20);
		proveedorPanel.add(txtRazonSocial);
		txtRazonSocial.setColumns(10);
		
		lblDireccion = new JLabel("Direcci\u00F3n: ");
		lblDireccion.setBounds(75, 92, 82, 14);
		proveedorPanel.add(lblDireccion);
		
		lblTelfono = new JLabel("Tel\u00E9fono:");
		lblTelfono.setBounds(75, 117, 82, 14);
		proveedorPanel.add(lblTelfono);
		
		lblCorreo = new JLabel("Correo:");
		lblCorreo.setBounds(75, 142, 82, 14);
		proveedorPanel.add(lblCorreo);
		
		txtDireccion = new JTextField();
		txtDireccion.setBounds(162, 89, 285, 20);
		proveedorPanel.add(txtDireccion);
		txtDireccion.setColumns(10);
		
		txtTelefono = new JTextField();
		txtTelefono.setBounds(162, 114, 285, 20);
		proveedorPanel.add(txtTelefono);
		txtTelefono.setColumns(10);
		
		txtCorreo = new JTextField();
		txtCorreo.setBounds(162, 139, 285, 20);
		proveedorPanel.add(txtCorreo);
		txtCorreo.setColumns(10);
		
		btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(this);
		btnGuardar.setBounds(125, 185, 145, 40);
		proveedorPanel.add(btnGuardar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(this);
		btnCancelar.setBounds(301, 185, 146, 40);
		proveedorPanel.add(btnCancelar);
		
		proveedorPanel.setVisible(false);
		tabla();
	}
	//Action Listeners
	
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == btnBuscar) {
			actionPerformedBtnBuscar(arg0);
		}
		if (arg0.getSource() == btnNuevo) {
			actionPerformedBtnAgregar(arg0);
		}
		if (arg0.getSource() == btnModificar) {
			actionPerformedBtnModificar(arg0);
		}
		if (arg0.getSource() == btnEliminar) {
			actionPerformedBtnEliminar(arg0);
		}
		if (arg0.getSource() == btnListarTodos) {
			actionPerformedBtnListarTodos(arg0);
		}
		if (arg0.getSource() == btnGuardar) {
			actionPerformedBtnGuardar(arg0);
		}
		if (arg0.getSource() == btnCancelar) {
			actionPerformedBtnCancelar(arg0);
		}		
	}
	
	protected void actionPerformedBtnBuscar(ActionEvent arg0) {
			limpiartabla();
			buscar();	
	}
	protected void actionPerformedBtnAgregar(ActionEvent arg0) {
			limpiarCampos();
			txtCodigo.setEditable(true);
			if(!proveedorPanel.isVisible()) proveedorPanel.setVisible(true);
	}
	protected void actionPerformedBtnModificar(ActionEvent arg0) {
		modificar();
	}
	protected void actionPerformedBtnEliminar(ActionEvent arg0) {
			eliminar();
	}
	protected void actionPerformedBtnListarTodos(ActionEvent arg0) {
			limpiartabla();
			txtCodigoBuscar.setText("");
			listarTodos();
	}
	protected void actionPerformedBtnGuardar(ActionEvent arg0) {
			guardar();
	}
	protected void actionPerformedBtnCancelar(ActionEvent arg0) {
			cancelar();
	}
	
	// metodos privados
	private void tabla(){
		modelo= new DefaultTableModel(){
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		modelo.addColumn("Codigo");
		modelo.addColumn("RUC");
		modelo.addColumn("Razon Social");
		modelo.addColumn("Direccion");
		modelo.addColumn("Telefono");
		modelo.addColumn("Correo");
		tblProveedores.setModel(modelo);
	}	
	private void limpiartabla(){
		if (modelo.getRowCount() > 0) {
		    for (int i = modelo.getRowCount() - 1; i > -1; i--) {
		    	modelo.removeRow(i);
		    }
		}
	}	
	
	private void buscar(){
		try{			
			String nom_proveedor= txtCodigoBuscar.getText().trim();
			ResultSet rs = jdbc.buscar(nom_proveedor);          
			while(rs.next()){
				Object[] fila=new Object[6];          
				fila[0] = rs.getString("cod_proveedor");          
				fila[1] = rs.getString("ruc");
				fila[2] = rs.getString("razonsocial");
				fila[3] = rs.getString("direccion");
				fila[4] = rs.getString("telefono");
				fila[5] = rs.getString("correo");
				modelo.addRow(fila);
			}
		}catch (Exception ex){			
			
		}
	}
	
	private void modificar(){
			int index = tblProveedores.getSelectedRow();
			if(index != -1){
				String codProveedor = (String) tblProveedores.getValueAt(index, 0);			
				if(!proveedorPanel.isVisible()) proveedorPanel.setVisible(true);
				limpiarCampos();
				llenarCamposPorCodigo(codProveedor);
				proveedorPanel.setVisible(true);		
			}else{
				JOptionPane.showMessageDialog(null,"Seleccion un proveedor");
			}	
	}
	private void eliminar(){
		int index = tblProveedores.getSelectedRow();
		if(index != -1){
			String mensaje = "¿Seguro que desea eliminar el proveedor seleccionado?";
			String titulo = "Eliminar Proveedor";
		    int respuesta = JOptionPane.showConfirmDialog(null, mensaje, titulo, JOptionPane.YES_NO_OPTION);
		    if(respuesta == JOptionPane.YES_OPTION){
		    	String codProveedor = (String) tblProveedores.getValueAt(index, 0);
		    	if(jdbc.eliminar(codProveedor)){
		    		modelo.removeRow(index);
		    	}
		    }					
		}else{
			JOptionPane.showMessageDialog(null,"Seleccion un proveedor para eliminar");
		}	
	}
	
	private void listarTodos(){
		try{			
			List<Proveedor> proveedores = jdbc.listarTodo();
			for (Proveedor pro : proveedores) {
				Object[] fila=new Object[6];		          
				fila[0]=pro.getCodigo();		         
				fila[1]=pro.getRUC();
				fila[2]=pro.getRazonSocial();
				fila[3]=pro.getDireccion();
				fila[4]=pro.getTelefono();
				fila[5]=pro.getCorreo();				
				modelo.addRow(fila);
			}
		}catch (Exception ex){			
			
		}
	}
	
	private void guardar(){
		if(txtCodigo.isEditable()){
			Proveedor pro = jdbc.buscarPorCodigo(txtCodigo.getText());
			if(pro != null){
				JOptionPane.showMessageDialog(null, "Ya existe un proveedor con ese codigo, porfavor cambie el codigo.");
				return;
			}
		}
		String codigo=txtCodigo.getText();
		String razonSocial = txtRazonSocial.getText();
		String ruc = txtRuc.getText();
		String direccion = txtDireccion.getText();
		String telefono = txtTelefono.getText();
		String correo = txtCorreo.getText();
		jdbc.guardar(codigo, razonSocial, ruc, direccion, telefono, correo);		
		btnListarTodos.doClick();
		limpiarCampos();
	}
	private void cancelar(){
		proveedorPanel.setVisible(false);
	}
	
	private void limpiarCampos(){
		txtCodigo.setText("");
		txtRuc.setText("");
		txtRazonSocial.setText("");
		txtDireccion.setText("");
		txtTelefono.setText("");
		txtCorreo.setText("");
	}
	private void llenarCamposPorCodigo(String codProveedor){
		if(!codProveedor.isEmpty()){			
			Proveedor proveedorEditar = jdbc.buscarPorCodigo(codProveedor);
			txtCodigo.setEditable(false);
			if(proveedorEditar != null){
				txtCodigo.setText(proveedorEditar.getCodigo());
				txtRuc.setText(proveedorEditar.getRUC());
				txtRazonSocial.setText(proveedorEditar.getRazonSocial());
				txtDireccion.setText(proveedorEditar.getDireccion());
				txtTelefono.setText(proveedorEditar.getTelefono());
				txtCorreo.setText(proveedorEditar.getCorreo());
			}
		}
	}
	
}
