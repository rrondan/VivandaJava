package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import entidades.articulo;
import jdbc.jdbcarticulo;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

public class guiarticulos extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTextField txtcodigoarti;
	private JTable tblarticulo;
	private DefaultTableModel modelo;
	private JButton btnBuscar;
	private jdbcarticulo jdbc;

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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 545, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCodigo = new JLabel("Codigo:");
		lblCodigo.setBounds(32, 31, 46, 14);
		contentPane.add(lblCodigo);
		
		txtcodigoarti = new JTextField();
		txtcodigoarti.setBounds(106, 28, 86, 20);
		contentPane.add(txtcodigoarti);
		txtcodigoarti.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(32, 74, 350, 157);
		contentPane.add(scrollPane);
		
		tblarticulo = new JTable();
		scrollPane.setViewportView(tblarticulo);
		
		btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(this);
		btnBuscar.setBounds(202, 27, 89, 23);
		contentPane.add(btnBuscar);
		
		JButton btnAgregar = new JButton("Agregar");
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				guiregarticulo reg = new guiregarticulo();
				reg.setVisible(true);
			}
		});
		btnAgregar.setBounds(406, 71, 113, 23);
		contentPane.add(btnAgregar);
		
		JButton btnEditar = new JButton("Editar");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = tblarticulo.getSelectedRow();
				if(index != -1){
					String codArticulo = (String) tblarticulo.getValueAt(index, 0);
					guiregarticulo reg = new guiregarticulo();
					reg.setCodArticulo(codArticulo);
					reg.setVisible(true);					
				}else{
					JOptionPane.showMessageDialog(null,"Seleccion un articulo");
				}
			}
		});
		btnEditar.setBounds(406, 124, 113, 23);
		contentPane.add(btnEditar);
		
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
		});
		btnEliminar.setBounds(406, 173, 113, 23);
		contentPane.add(btnEliminar);
		
		JButton btnListarTodos = new JButton("Listar Todos");
		btnListarTodos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpiartabla();
				txtcodigoarti.setText("");
				listarTodo();
			}
		});
		btnListarTodos.setBounds(406, 27, 113, 23);
		contentPane.add(btnListarTodos);
		
		tabla();
	}
	private void tabla(){
		modelo= new DefaultTableModel();
		modelo.addColumn("Codigo");
		modelo.addColumn("Descripcion");
		modelo.addColumn("Categoria");
		modelo.addColumn("Empaque");
		tblarticulo.setModel(modelo);
	}
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == btnBuscar) {
			actionPerformedBtnBuscar(arg0);
		}
	}
	private void limpiartabla(){
//		int total = modelo.getRowCount();
//		for (int i=0;i<total;i++){
//			modelo.removeRow(i);
//		}
		if (modelo.getRowCount() > 0) {
		    for (int i = modelo.getRowCount() - 1; i > -1; i--) {
		    	modelo.removeRow(i);
		    }
		}
	}
	protected void actionPerformedBtnBuscar(ActionEvent arg0) {
		limpiartabla();
		buscar();	
	}
	private void buscar(){
		try{			
			String codigo=txtcodigoarti.getText().trim();
			articulo art= jdbc.buscar(codigo);          
			Object[] fila=new Object[4];          
			fila[0]=art.getCodigo();          
			fila[1]=art.getDescripcion();          
			fila[2]=art.getCategoria();          
			fila[3]=art.getEmpaque();          
			modelo.addRow(fila);
		}catch (Exception ex){			
			
		}
	}
	private void listarTodo(){
		try{			
			List<articulo> articulos = jdbc.listarTodo();
			for (articulo art : articulos) {
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
}
