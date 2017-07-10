package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import entidades.Proveedor;
import jdbc.jdbcproveedor;
import libraries.AutocompleteJComboBox;
import libraries.StringSearchable;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class guiReporteCompra extends JFrame {

	private JPanel contentPane;
	
	private AutocompleteJComboBox  cbRzSocial;
	private jdbcproveedor jdbcProveedor;	
	private List<Proveedor> proveedores ;
	private Proveedor proveedorEncontrado;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					guiReporteCompra frame = new guiReporteCompra();
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
	public guiReporteCompra() {
		jdbcProveedor = new jdbcproveedor();
		setTitle("Reporte de Compras por Proveedor por Rango de Fechas");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 565, 348);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblProveedor = new JLabel("Rz Social Proveedor:");
		lblProveedor.setBounds(61, 30, 144, 14);
		contentPane.add(lblProveedor);
		
		List<String> myWords = new ArrayList<String>();
		proveedores = jdbcProveedor.listarTodo();
		for (Proveedor proveedor : proveedores) {
			myWords.add(proveedor.getRazonSocial());
		}
		StringSearchable searchable = new StringSearchable(myWords);		
		cbRzSocial = new AutocompleteJComboBox (searchable);
		cbRzSocial.setBounds(215, 27, 219, 20);
		cbRzSocial.addItemListener(new ItemListener() {			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED){
					buscarProveedor((String) e.getItem());
				}
			}
		});
		contentPane.add(cbRzSocial);
	}

	// Metodos privados
	private void buscarProveedor(String razonSocial){
		boolean encontrado = false;
		for(Proveedor proveedor: proveedores){
				if(proveedor.getRazonSocial().equals(razonSocial)){					
					encontrado = true;
					proveedorEncontrado = proveedor;
				}
		}
		if(!encontrado){
			proveedorEncontrado = null;
		}
	}
	
}
