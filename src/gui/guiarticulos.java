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
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class guiarticulos extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTextField txtcodigoarti;
	private JTable tblarticulo;
	private DefaultTableModel modelo;
	private JButton btnBuscar;

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
		btnBuscar.setBounds(411, 27, 89, 23);
		contentPane.add(btnBuscar);
		
		JButton btnAgregar = new JButton("Agregar");
		btnAgregar.setBounds(411, 74, 89, 23);
		contentPane.add(btnAgregar);
		
		JButton btnEditar = new JButton("Editar");
		btnEditar.setBounds(411, 124, 89, 23);
		contentPane.add(btnEditar);
		
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.setBounds(411, 172, 89, 23);
		contentPane.add(btnEliminar);
		
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
		int total=modelo.getRowCount();
		for (int i=0;i<total;i++)
			modelo.removeRow(i);

		
	}
	protected void actionPerformedBtnBuscar(ActionEvent arg0) {
	limpiartabla();
	buscar();	
	}
	private void buscar(){
		try{
			jdbcarticulo obj =new jdbcarticulo();
			String codigo=txtcodigoarti.getText().trim();
			articulo art= obj.buscar (codigo);
          Object[] fila=new Object[4];
          fila[0]=art.getCodigo();
          fila[1]=art.getDescripcion();
          fila[2]=art.getCategoria();
          fila[3]=art.getEmpaque();
          modelo.addRow(fila);
		}catch (Exception ex){
			
			
		}
	}
}
