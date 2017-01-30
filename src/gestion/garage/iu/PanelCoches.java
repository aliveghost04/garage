package gestion.garage.iu;

import gestion.garage.database.Consulta;
import gestion.garage.modelo.ModeloTablaCoche;
import gestion.garage.practicas.Garaje;
import gestion.garage.vehiculo.Coche;
import gestion.garage.vehiculo.Vehiculo;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class PanelCoches extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField txtMarca;
	private JTextField txtKilometraje;
	private JComboBox<String> cbCantidadPuertas;
	private int cantidadRuedas = 4;
	private Consulta consulta;
	private ResultSet rs;

	public PanelCoches() {
		
		Font fuente = new Font("Tahoma", Font.PLAIN, 15);
		
		setBorder(new TitledBorder(null, "Coches", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(null);
		
		JLabel lblMarca = new JLabel("Marca:");
		lblMarca.setBounds(26, 31, 154, 29);
		lblMarca.setFont(fuente);
		add(lblMarca);
		
		JLabel lblKilometraje = new JLabel("Kilometraje:");
		lblKilometraje.setBounds(26, 71, 154, 29);
		lblKilometraje.setFont(fuente);
		add(lblKilometraje);
		
		cbCantidadPuertas = new JComboBox<String>();
		cbCantidadPuertas.setModel(new DefaultComboBoxModel<String>(new String[] {"Seleccione", "2", "4"}));
		cbCantidadPuertas.setBounds(233, 117, 154, 29);
		add(cbCantidadPuertas);
		
		JLabel lblCantidadDePuertas = new JLabel("Cantidad de puertas:");
		lblCantidadDePuertas.setBounds(26, 115, 154, 29);
		lblCantidadDePuertas.setFont(fuente);
		add(lblCantidadDePuertas);
		
		txtMarca = new JTextField();
		txtMarca.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (txtKilometraje.getText().length() == 20) e.consume();
			}
		});
		txtMarca.setBounds(233, 31, 154, 26);
		add(txtMarca);
		txtMarca.setColumns(10);
		
		txtKilometraje = new JTextField();
		txtKilometraje.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() < KeyEvent.VK_0 || e.getKeyChar() > KeyEvent.VK_9) {
					e.consume();
				}
				if (txtKilometraje.getText().length() == 9) e.consume();
			}
		});
		txtKilometraje.setColumns(10);
		txtKilometraje.setBounds(233, 77, 154, 26);
		add(txtKilometraje);
		
		JButton btnAgregarAuto = new JButton("Agregar Coche");
		btnAgregarAuto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String marca = txtMarca.getText();
				double kilometraje = 0;
				int cantidadPuertas = 0;
				
				cantidadPuertas = cbCantidadPuertas.getSelectedIndex() * 2;
				
				if (txtMarca.getText().length() == 0 || txtKilometraje.getText().length() == 0) {
					mensajeError("Debe llenar todos los campos");
					return;
				}
				
				try {
					kilometraje = Double.parseDouble(txtKilometraje.getText());
				} catch (NumberFormatException e) {
					mensajeError("Número inválido: " +e.getMessage());
					return;
				}
				
				if (txtKilometraje.getText().length() > 9) {
					mensajeError("Solo se acepta un limite máximo de 9 dígitos");
					txtKilometraje.setText("");
					txtKilometraje.requestFocus();
					return;
				}
				
				if (cantidadPuertas == 0) {
					mensajeError("Debe seleccionar la cantidad de puertas");
					return;
				}
				
				if (txtMarca.getText().length() > 20) {
					mensajeError("Solo se acepta un limite máximo de 20 carácteres");
					txtMarca.setText("");
					txtMarca.requestFocus();
					return;
				}
				
				Coche coche = new Coche(marca, kilometraje, cantidadRuedas, cantidadPuertas);
				Vehiculo vehiculo = coche;
				if (Garaje.getInstancia().agregarVehiculo(vehiculo) == false) {
					mensajeError("Garaje Lleno !!");
					return;
				}
				
				try {
					consulta = Consulta.getInstancia();
					consulta.agregarCoche(coche);
					rs = consulta.obtenerId();
					if(rs.next()) coche.setId(rs.getInt(1));
					ModeloTablaCoche.getInstancia().agregarCoche(coche);
					limpiarCampos();
				} catch (InstantiationException | IllegalAccessException
						| ClassNotFoundException | SQLException e) {
					mensajeError("Error al insertar en la base de datos: " +e.getMessage());
					return;
				}
				
				JOptionPane.showMessageDialog(PanelCoches.this, "Coche agregado satisfactoriamente !!!", 
						"Información", JOptionPane.INFORMATION_MESSAGE);
				InterfazGaraje.cantidadEspacioLibre(Garaje.getInstancia().getCantidadVehiculos());
			}
		});
		btnAgregarAuto.setBounds(138, 174, 154, 55);
		btnAgregarAuto.setFont(fuente);
		add(btnAgregarAuto);
	}
	
	private void mensajeError(String mensaje) {
		JOptionPane.showMessageDialog(null, mensaje, 
				"Error", JOptionPane.ERROR_MESSAGE);
	}
	
	private void limpiarCampos() {
		txtMarca.setText("");
		txtKilometraje.setText("");
		cbCantidadPuertas.setSelectedIndex(0);
	}
}
