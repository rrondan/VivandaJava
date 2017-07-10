package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import entidades.Articulo;
import entidades.Compra;
import entidades.Proveedor;
import jdbc.jdbcReporte;
import jdbc.jdbcproveedor;
import libraries.AutocompleteJComboBox;
import libraries.DateLabelFormatter;
import libraries.StringSearchable;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTable;

public class guiReporteCompra extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JLabel lblFechaInicioLabel;
	private UtilDateModel pickerFechaInicioModel;
	private JDatePickerImpl pickerFechaInicio;
	private UtilDateModel pickerFechaFinModel;
	private JDatePickerImpl pickerFechaFin;
	private AutocompleteJComboBox  cbRzSocial;
	private JLabel lblFechaFin;
	private JButton btnGenerarReporte;
	private JLabel lblTotal;
	private JTextField txtTotal;
	
	private jdbcproveedor jdbcProveedor;	
	private List<Proveedor> proveedores ;
	private Proveedor proveedorEncontrado;
	private List<Compra> compras;
	private jdbcReporte jdbcReporte;
	private DefaultTableModel modeloTblReporte;
	private JScrollPane scrollPane;
	private JTable tblReporte;

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
		jdbcReporte = new jdbcReporte();
		setTitle("Reporte de Compras por Proveedor por Rango de Fechas");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 565, 447);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblProveedor = new JLabel("Razon Social Proveedor:");
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
		
		lblFechaInicioLabel = new JLabel("Fecha Inicio: ");
		lblFechaInicioLabel.setBounds(32, 71, 93, 14);
		contentPane.add(lblFechaInicioLabel);
		
		pickerFechaInicioModel = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(pickerFechaInicioModel, p);
		pickerFechaInicio = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		pickerFechaInicio.setBounds(107, 65, 129, 26);
		contentPane.add(pickerFechaInicio);
		
		lblFechaFin = new JLabel("Fecha Fin:");
		lblFechaFin.setBounds(278, 71, 68, 14);
		contentPane.add(lblFechaFin);
		pickerFechaFinModel = new UtilDateModel();
		Properties p1 = new Properties();
		p1.put("text.today", "Today");
		p1.put("text.month", "Month");
		p1.put("text.year", "Year");
		JDatePanelImpl datePanel1 = new JDatePanelImpl(pickerFechaFinModel, p);
		pickerFechaFin = new JDatePickerImpl(datePanel1, new DateLabelFormatter());
		pickerFechaFin.setBounds(340, 65, 129, 26);
		contentPane.add(pickerFechaFin);
		
		btnGenerarReporte = new JButton("Generar Reporte");
		btnGenerarReporte.setBounds(179, 101, 184, 23);
		btnGenerarReporte.addActionListener(this);
		contentPane.add(btnGenerarReporte);
		
		lblTotal = new JLabel("Total:");
		lblTotal.setBounds(378, 367, 56, 14);
		contentPane.add(lblTotal);
		
		txtTotal = new JTextField();
		txtTotal.setBounds(453, 364, 86, 20);
		contentPane.add(txtTotal);
		txtTotal.setColumns(10);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 135, 529, 218);
		contentPane.add(scrollPane);
		
		tblReporte = new JTable();
		scrollPane.setViewportView(tblReporte);
		tblReporte.setSelectionMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
		
		tablaReporte();
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == btnGenerarReporte){
			actionPerformedBtnGenerarReporte(e);
		}
	}
	protected void actionPerformedBtnGenerarReporte(ActionEvent e){
		generarReporte();
	}

	// Metodos privados
	private void tablaReporte(){
		modeloTblReporte= new DefaultTableModel(){
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		modeloTblReporte.addColumn("Codigo");
		modeloTblReporte.addColumn("Fecha");
		modeloTblReporte.addColumn("RUC");
		modeloTblReporte.addColumn("Razon Social Proveedor");
		modeloTblReporte.addColumn("Total Compra");
		tblReporte.setModel(modeloTblReporte);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		tblReporte.getColumnModel().getColumn(0).setPreferredWidth(80);
		tblReporte.getColumnModel().getColumn(0).setMaxWidth(80);
		tblReporte.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		tblReporte.getColumnModel().getColumn(1).setPreferredWidth(80);
		tblReporte.getColumnModel().getColumn(1).setMaxWidth(80);
		tblReporte.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
		tblReporte.getColumnModel().getColumn(2).setPreferredWidth(90);
		tblReporte.getColumnModel().getColumn(2).setMaxWidth(90);
		tblReporte.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
		tblReporte.getColumnModel().getColumn(4).setPreferredWidth(100);
		tblReporte.getColumnModel().getColumn(4).setMaxWidth(100);
		tblReporte.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
	}
	
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

	private void generarReporte(){
		String fechaInicio =  pickerFechaInicio.getJFormattedTextField().getText().toString();
		String fechaFin = pickerFechaFin.getJFormattedTextField().getText().toString();
		if(proveedorEncontrado == null){
			JOptionPane.showMessageDialog(null,"Escoja una proveedor.");
		}
		else if(fechaInicio.trim().length() == 0){
			JOptionPane.showMessageDialog(null,"Escoja una fecha de Inicio.");
		}else if(fechaFin.trim().length() == 0){
			JOptionPane.showMessageDialog(null,"Escoja una fecha de Fin.");
		}else{
			compras = jdbcReporte.generarReporte(fechaInicio, fechaFin, proveedorEncontrado.getCodigo());
			limpiarTablaReporte();
			llenarTablaReporte();
			Double TotalReporte = 0.0;
			for (Compra compra : compras) {
				TotalReporte += compra.getPrecioTotal();
			}
			txtTotal.setText(String.valueOf(TotalReporte));
		}		
	}
	private void limpiarTablaReporte(){
		if (modeloTblReporte.getRowCount() > 0) {
		    for (int i = modeloTblReporte.getRowCount() - 1; i > -1; i--) {
		    	modeloTblReporte.removeRow(i);
		    }
		}
	}
	private void llenarTablaReporte(){
		for (Compra compra : compras) {
			Object[] fila=new Object[5];          
			fila[0] = "C-" + String.format("%05d",compra.getId());
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			fila[1] = df.format( compra.getFecha());
			fila[2] = compra.getProveedor().getRUC();          
			fila[3] = compra.getProveedor().getRazonSocial();
			fila[4] = compra.getPrecioTotal();
			modeloTblReporte.addRow(fila);
		}
			
	}
	
}
