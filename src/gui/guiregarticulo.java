package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import entidades.Articulo;
import jdbc.jdbcarticulo;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class guiregarticulo extends JFrame implements ActionListener {
	
private JComboBox cboCategoria ;
private JComboBox cboEmpaque;
	private JPanel contentPane;
	private JTextField txtCodigo;
	private JTextField txtDescripcion;
	private List<Integer> categoriasId;
	private List<Integer> empaquesId;
	private JButton btnGrabar;	
	private JButton btnCancelar;
	private Articulo articuloEdit;
	private jdbcarticulo jdbc;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					guiregarticulo frame = new guiregarticulo();					
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
	public guiregarticulo() {		
		jdbc =new jdbcarticulo();		
		setTitle("Registro de Articulo");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCodigo = new JLabel("codigo");
		lblCodigo.setBounds(38, 46, 46, 14);
		contentPane.add(lblCodigo);
		
		txtCodigo = new JTextField();
		txtCodigo.setBounds(116, 43, 65, 20);
		contentPane.add(txtCodigo);
		txtCodigo.setColumns(10);
		
		JLabel lblDescripcion = new JLabel("Descripcion");
		lblDescripcion.setBounds(38, 89, 65, 14);
		contentPane.add(lblDescripcion);
		
		txtDescripcion = new JTextField();
		txtDescripcion.setBounds(116, 86, 149, 20);
		contentPane.add(txtDescripcion);
		txtDescripcion.setColumns(10);
		
		cboCategoria = new JComboBox();
		cboCategoria.setBounds(116, 135, 130, 20);
		contentPane.add(cboCategoria);
		
		cboEmpaque = new JComboBox();
		cboEmpaque.setBounds(116, 182, 130, 20);
		contentPane.add(cboEmpaque);
		
		JLabel lblCategoria = new JLabel("Categoria");
		lblCategoria.setBounds(38, 138, 65, 14);
		contentPane.add(lblCategoria);
		
		JLabel lblEmpaque = new JLabel("Empaque");
		lblEmpaque.setBounds(38, 185, 46, 14);
		contentPane.add(lblEmpaque);
		
		btnGrabar = new JButton("Grabar");
		btnGrabar.addActionListener(this);
		btnGrabar.setBounds(280, 134, 89, 23);
		contentPane.add(btnGrabar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(this);		
		btnCancelar.setBounds(280, 182, 89, 23);
		contentPane.add(btnCancelar);
		
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
	
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == btnGrabar) {
			actionPerformedBtnGrabar(arg0);
		}
		if (arg0.getSource() == btnCancelar) {
			actionPerformedBtnCancelar(arg0);
		}
	}
	protected void actionPerformedBtnGrabar(ActionEvent arg0) {
		grabar();
	}

	protected void actionPerformedBtnCancelar(ActionEvent arg0) {
		setVisible(false);
	}
	
	// funciones privadas
	public void setCodArticulo(String codArticulo){
		if(!codArticulo.isEmpty()){			
			articuloEdit = jdbc.buscar(codArticulo);
			txtCodigo.setEnabled(false);
			if(articuloEdit != null){
				txtCodigo.setText(articuloEdit.getCodigo());
				txtDescripcion.setText(articuloEdit.getDescripcion());
				cboCategoria.setSelectedItem(articuloEdit.getCategoria());
				cboEmpaque.setSelectedItem(articuloEdit.getEmpaque());
			}
		}
	}
	
	public void limpiarGui(){
			txtCodigo.setText("");
			txtDescripcion.setText("");
			cboCategoria.setSelectedIndex(0);
			cboEmpaque.setSelectedIndex(0);
	}
	
	private void grabar(){
		String codigo=txtCodigo.getText();
		String descripcion=txtDescripcion.getText();		
		int categoria=categoriasId.get(cboCategoria.getSelectedIndex());
		int empaque=empaquesId.get(cboEmpaque.getSelectedIndex());		
		jdbc.guardar(codigo, descripcion, categoria, empaque);
		
	}
	
	
}
	
	

