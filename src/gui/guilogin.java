package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class guilogin extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JTextField txtUsuario;
	private JTextField txtContrasena;
	private JButton btnIngresar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					guilogin frame = new guilogin();
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
	public guilogin() {
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 475, 322);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblImage = new JLabel(new ImageIcon("assets/img/vivanda-logo.png"));
		lblImage.setBounds(23, 87, 190, 149);
		contentPane.add(lblImage);
		
		JLabel lblTitulo = new JLabel("Super Mercado Vivanda");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblTitulo.setBounds(101, 22, 285, 33);
		contentPane.add(lblTitulo);
		
		JLabel lblUsuario = new JLabel("Usuario: ");
		lblUsuario.setBounds(236, 98, 70, 14);
		contentPane.add(lblUsuario);
		
		JLabel lblContrasea = new JLabel("Contrase\u00F1a:");
		lblContrasea.setBounds(236, 154, 84, 14);
		contentPane.add(lblContrasea);
		
		txtUsuario = new JTextField();
		txtUsuario.setBounds(234, 123, 203, 20);
		contentPane.add(txtUsuario);
		txtUsuario.setColumns(10);
		
		txtContrasena = new JPasswordField();
		txtContrasena.setBounds(234, 179, 203, 20);
		contentPane.add(txtContrasena);
		txtContrasena.setColumns(10);
		
		btnIngresar = new JButton("Ingresar");		
		btnIngresar.addActionListener(this);
		btnIngresar.setBounds(284, 224, 113, 23);
		contentPane.add(btnIngresar);
	}
	
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == btnIngresar) {
			actionPerformedBtnIngresar(arg0);
		}
	}
	protected void actionPerformedBtnIngresar(ActionEvent arg0) {		
		Login();
	}
	private void Login(){
		String usuario = txtUsuario.getText();
		String contrasena = txtContrasena.getText();
		if(usuario.equals("admin") && contrasena.equals("admin")){
			guimenu gui = new guimenu();
			gui.setVisible(true);
			setVisible(false);
		}else{
			JOptionPane.showMessageDialog(null, "Ingrese un usuario valido");
		}
	}
	
}
