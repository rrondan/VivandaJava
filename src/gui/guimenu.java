package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class guimenu extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JLabel lblMantenimientos;
	private JButton btnArticulos ;
	private JButton btnProveedores;
	private JButton btnCompras;
	private guiarticulos articulos;
	private guiproveedores proveedores;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					guimenu frame = new guimenu();
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
	public guimenu() {
		articulos = new guiarticulos();
		proveedores = new guiproveedores();
		setTitle("Menu");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblMantenimientos = new JLabel("O p c i o n e s");
		lblMantenimientos.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblMantenimientos.setBounds(144, 11, 156, 41);
		contentPane.add(lblMantenimientos);
		
		btnArticulos = new JButton("Articulos");
		btnArticulos.addActionListener(this);
		btnArticulos.setBounds(34, 59, 118, 49);
		contentPane.add(btnArticulos);
		
		btnProveedores = new JButton("Proveedores");
		btnProveedores.addActionListener(this);
		btnProveedores.setBounds(288, 59, 113, 48);
		contentPane.add(btnProveedores);
		
		btnCompras = new JButton("Compras");
		btnCompras.addActionListener(this);
		btnCompras.setBounds(162, 59, 113, 48);
		contentPane.add(btnCompras);
	}

	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == btnArticulos) {
			actionPerformedBtnArticulos(arg0);
		}
		if (arg0.getSource() == btnProveedores) {
			actionPerformedBtnProveedores(arg0);
		}
		if (arg0.getSource() == btnCompras) {
			actionPerformedBtnCompras(arg0);
		}	
	}
	protected void actionPerformedBtnArticulos(ActionEvent arg0) {		
		articulos.setVisible(true);		
	}
	protected void actionPerformedBtnProveedores(ActionEvent arg0) {
		proveedores.setVisible(true);
	}
	protected void actionPerformedBtnCompras(ActionEvent arg0) {
		JOptionPane.showMessageDialog(null, "Muy pronto estara implementado esta funcionalidad");
	}
	
}
