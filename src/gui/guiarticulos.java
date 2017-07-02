package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import entidades.Articulo;
import jdbc.jdbcarticulo;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class guiarticulos extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTextField txtcodigoarti;
	private JTable tblarticulo;
	private DefaultTableModel modelo;
	private JButton btnModificar;
	private JButton btnNuevo;
	private JButton btnEliminar;
	private JButton btnListarTodos;
	private jdbcarticulo jdbc;	
	private JPanel articuloPanel;
	private JLabel lblCodigo_1;
	private JLabel lblDescripcion;
	private JLabel lblCategoria;
	private JLabel lblEmpaque;
	private JTextField txtCodigo;
	private JTextField txtDescripcion;
	private JComboBox cboCategoria;
	private JComboBox cboEmpaque;
	private JButton btnGuardar;
	private JButton btnCancelar;
	private List<Integer> categoriasId;
	private List<Integer> empaquesId;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					guiarticulos frame = new guiarticulos();
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
	public guiarticulos() {
		jdbc =new jdbcarticulo();
		setTitle("Mantenimiento de articulos");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 545, 502);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCodigo = new JLabel("Articulo a Buscar:");
		lblCodigo.setBounds(26, 29, 103, 14);
		contentPane.add(lblCodigo);
		
		txtcodigoarti = new JTextField();		
		txtcodigoarti.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				limpiartabla();
				buscar();
			}
		});
		txtcodigoarti.setBounds(141, 26, 229, 20);
		contentPane.add(txtcodigoarti);
		txtcodigoarti.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(26, 57, 364, 172);
		contentPane.add(scrollPane);
		
		tblarticulo = new JTable();
		scrollPane.setViewportView(tblarticulo);
		tblarticulo.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		btnNuevo = new JButton("Nuevo");
		btnNuevo.addActionListener(this);
		btnNuevo.setBounds(400, 77, 113, 42);
		contentPane.add(btnNuevo);
		
		btnModificar = new JButton("Modificar");
		btnModificar.addActionListener(this);
		btnModificar.setBounds(400, 130, 113, 38);
		contentPane.add(btnModificar);
		
		btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(this);
		btnEliminar.setBounds(400, 179, 113, 38);
		contentPane.add(btnEliminar);
		
		btnListarTodos = new JButton("Listar Todos");
		btnListarTodos.addActionListener(this);
		btnListarTodos.setBounds(400, 25, 113, 23);
		contentPane.add(btnListarTodos);
		
		articuloPanel = new JPanel();
		articuloPanel.setBounds(20, 240, 493, 202);
		contentPane.add(articuloPanel);
		articuloPanel.setLayout(null);
		
		lblCodigo_1 = new JLabel("Codigo:");
		lblCodigo_1.setBounds(93, 27, 76, 14);
		articuloPanel.add(lblCodigo_1);
		
		lblDescripcion = new JLabel("Descripci\u00F3n:");
		lblDescripcion.setBounds(93, 63, 76, 14);
		articuloPanel.add(lblDescripcion);
		
		lblCategoria = new JLabel("Categoria: ");
		lblCategoria.setBounds(93, 99, 76, 14);
		articuloPanel.add(lblCategoria);
		
		lblEmpaque = new JLabel("Empaque:");
		lblEmpaque.setBounds(93, 134, 76, 14);
		articuloPanel.add(lblEmpaque);
		
		txtCodigo = new JTextField();
		txtCodigo.setBounds(174, 24, 86, 20);
		articuloPanel.add(txtCodigo);
		txtCodigo.setColumns(10);
		
		txtDescripcion = new JTextField();
		txtDescripcion.setBounds(174, 60, 215, 20);
		articuloPanel.add(txtDescripcion);
		txtDescripcion.setColumns(10);
		
		cboCategoria = new JComboBox();
		cboCategoria.setBounds(174, 96, 215, 20);
		articuloPanel.add(cboCategoria);
		
		cboEmpaque = new JComboBox();
		cboEmpaque.setBounds(174, 131, 215, 20);
		articuloPanel.add(cboEmpaque);
		
		btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(this);
		btnGuardar.setBounds(151, 168, 89, 23);
		articuloPanel.add(btnGuardar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(this);
		btnCancelar.setBounds(276, 168, 89, 23);
		articuloPanel.add(btnCancelar);
		articuloPanel.setVisible(false);
		tabla();
		comboBoxs();
	}
	// action listener implement
	public void actionPerformed(ActionEvent arg0) {
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
	protected void actionPerformedBtnAgregar(ActionEvent arg0) {
			articuloPanel.setVisible(true);			
			limpiarCampos();			
	}
	protected void actionPerformedBtnModificar(ActionEvent arg0) {
			int index = tblarticulo.getSelectedRow();
			if(index != -1){
				String codArticulo = (String) tblarticulo.getValueAt(index, 0);			
				if(!articuloPanel.isVisible()) articuloPanel.setVisible(true);
				limpiarCampos();
				llenarCamposPorCodigo(codArticulo);				
			}else{
				JOptionPane.showMessageDialog(null,"Seleccion un articulo");
			}	
	}
	protected void actionPerformedBtnEliminar(ActionEvent arg0) {
			int index = tblarticulo.getSelectedRow();
			if(index != -1){
				String mensaje = "¿Seguro que desea eliminar el articulo seleccionado?";
				String titulo = "Eliminar Articulo";
			    int respuesta = JOptionPane.showConfirmDialog(null, mensaje, titulo, JOptionPane.YES_NO_OPTION);
			    if(respuesta == JOptionPane.YES_OPTION){
			    	String codArticulo = (String) tblarticulo.getValueAt(index, 0);
			    	if(jdbc.eliminar(codArticulo)){
			    		modelo.removeRow(index);
			    	}
			    }					
			}else{
				JOptionPane.showMessageDialog(null,"Seleccion un articulo para eliminar");
			}	
	}
	protected void actionPerformedBtnListarTodos(ActionEvent arg0) {
		limpiartabla();
		txtcodigoarti.setText("");
		listarTodo();
	}
	protected void actionPerformedBtnGuardar(ActionEvent arg0) {
		grabar();
	}
	protected void actionPerformedBtnCancelar(ActionEvent arg0) {
		articuloPanel.setVisible(false);
	}
	
	// metodos privados
	private void comboBoxs(){
		ResultSet rs = jdbc.listarcategorias();
		categoriasId = new ArrayList<>();
		try {
			while(rs.next()){
				cboCategoria.addItem(rs.getString("descripcion"));
				categoriasId.add(rs.getInt("idcategoria"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	    rs= jdbc.listarempaques();
		empaquesId = new ArrayList<>();
		try {
			while(rs.next()){
				cboEmpaque.addItem(rs.getString("descripcion"));
				empaquesId.add(rs.getInt("idempaque"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	private void tabla(){
		modelo= new DefaultTableModel(){
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		modelo.addColumn("Codigo");
		modelo.addColumn("Descripcion");
		modelo.addColumn("Categoria");
		modelo.addColumn("Empaque");
		tblarticulo.setModel(modelo);
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
			String descripcion=txtcodigoarti.getText().trim();
			ResultSet rs = jdbc.buscar(descripcion);
			while(rs.next()){
				Object[] fila=new Object[4];          
				fila[0] = rs.getString("cod_articulo");          
				fila[1] = rs.getString("des_articulo");          
				fila[2] = rs.getString("categoria");          
				fila[3] = rs.getString("empaque");          
				modelo.addRow(fila);
			}
		}catch (Exception ex){			
			
		}
	}
	private void listarTodo(){
		try{			
			List<Articulo> articulos = jdbc.listarTodo();
			for (Articulo art : articulos) {
				Object[] fila=new Object[4];		          
				fila[0]=art.getCodigo();		         
				fila[1]=art.getDescripcion();				
				fila[2]=art.getCategoria();		        
				fila[3]=art.getEmpaque();		          
				modelo.addRow(fila);
			}
		}catch (Exception ex){			
			
		}
	}
	
	private void limpiarCampos(){
		txtCodigo.setText("");
		txtDescripcion.setText("");
		cboCategoria.setSelectedIndex(0);
		cboEmpaque.setSelectedIndex(0);
	}
	private void llenarCamposPorCodigo(String codArticulo){
		if(!codArticulo.isEmpty()){			
			Articulo articuloEdit = jdbc.buscarPorCodigo(codArticulo);
			txtCodigo.setEnabled(false);
			if(articuloEdit != null){
				txtCodigo.setText(articuloEdit.getCodigo());
				txtDescripcion.setText(articuloEdit.getDescripcion());
				cboCategoria.setSelectedItem(articuloEdit.getCategoria());
				cboEmpaque.setSelectedItem(articuloEdit.getEmpaque());
			}
		}
	}
	private void grabar(){
		if(txtCodigo.isEnabled()){
			Articulo art = jdbc.buscarPorCodigo(txtCodigo.getText());
			if(art != null){
				JOptionPane.showMessageDialog(null, "Ya existe un articulo con ese codigo, porfavor cambie el codigo.");
				return;
			}
		}
		String codigo=txtCodigo.getText();
		String descripcion=txtDescripcion.getText();		
		int categoria=categoriasId.get(cboCategoria.getSelectedIndex());
		int empaque=empaquesId.get(cboEmpaque.getSelectedIndex());		
		jdbc.guardar(codigo, descripcion, categoria, empaque);		
		btnListarTodos.doClick();
		limpiarCampos();
	}
}
