package gestion.garage.iu;

import gestion.garage.database.Consulta;
import gestion.garage.modelo.ModeloTablaMotos;
import gestion.garage.practicas.Garaje;
import gestion.garage.vehiculo.Moto;
import gestion.garage.vehiculo.Vehiculo;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class PanelMotos extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField txtMarca;
	private JTextField txtKilometraje;
	private JTextField txtCilindrada;
	private Consulta consulta;
	private int cantidadRuedas = 2;
	private ResultSet rs;

	public PanelMotos() {
		
		Font fuente = new Font("Tahoma", Font.PLAIN, 15);
		
		setBorder(new TitledBorder(null, "Motos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(null);
		
		JLabel lblMarca = new JLabel("Marca:");
		lblMarca.setBounds(26, 31, 154, 29);
		lblMarca.setFont(fuente);
		add(lblMarca);
		
		JLabel lblKilometraje = new JLabel("Kilometraje:");
		lblKilometraje.setBounds(26, 71, 154, 29);
		lblKilometraje.setFont(fuente);
		add(lblKilometraje);
		
		JLabel lblCilindrada = new JLabel("Cilindrada:");
		lblCilindrada.setBounds(26, 115, 154, 29);
		lblCilindrada.setFont(fuente);
		add(lblCilindrada);
		
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
		
		txtCilindrada = new JTextField();
		txtCilindrada.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() < KeyEvent.VK_0 || e.getKeyChar() > KeyEvent.VK_9) {
					e.consume();
				}
				if (txtCilindrada.getText().length() == 9) e.consume();
			}
		});
		txtCilindrada.setColumns(10);
		txtCilindrada.setHorizontalAlignment(JTextField.RIGHT);
		txtCilindrada.setBounds(233, 121, 154, 26);
		add(txtCilindrada);
	
		JButton btnAgregarMoto = new JButton("Agregar Moto");
		btnAgregarMoto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String marca = txtMarca.getText();
				double kilometraje = 0;
				int cilindrada = 0;
				
				if (txtMarca.getText().length() == 0 || txtKilometraje.getText().length() == 0 ||
						txtCilindrada.getText().length() == 0) {
					mensajeError("Debe llenar todos los campos");
					return;
				}
				
				try {
					kilometraje = Double.parseDouble(txtKilometraje.getText());
					cilindrada = Integer.parseInt(txtCilindrada.getText());
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
				
				if (txtCilindrada.getText().length() > 9) {
					mensajeError("Solo se acepta un limite máximo de 9 dígitos");
					txtCilindrada.requestFocus();
					txtCilindrada.setText("");
					return;
				}
				
				if (txtMarca.getText().length() > 20) {
					mensajeError("Solo se acepta un limite máximo de 20 carácteres");
					txtMarca.requestFocus();
					txtMarca.selectAll();
					return;
				}
				
				Moto moto = new Moto(marca, kilometraje, cilindrada, cantidadRuedas);
				Vehiculo vehiculo = moto;
				
				if (Garaje.getInstancia().agregarVehiculo(vehiculo) == false) {
					mensajeError("Garaje Lleno !!");
					return;
				}
				
				try {
					consulta = Consulta.getInstancia();
					consulta.agregarMoto(moto);
					rs = consulta.obtenerId();
					if ( rs.next()) moto.setId(rs.getInt(1));
					ModeloTablaMotos.getInstancia().agregarMoto(moto);
					limpiarCampos();
				} catch (SQLException | InstantiationException |
						IllegalAccessException | ClassNotFoundException e) {
					mensajeError("Error al insertar en la base de datos: " +e.getMessage());
					return;
				}
				
				JOptionPane.showMessageDialog(PanelMotos.this, "Moto agregada satisfactoriamente !!!", 
						"Información", JOptionPane.INFORMATION_MESSAGE);
				InterfazGaraje.cantidadEspacioLibre(Garaje.getInstancia().getCantidadVehiculos());
			}
		});
		btnAgregarMoto.setBounds(139, 176, 154, 55);
		btnAgregarMoto.setFont(fuente);
		add(btnAgregarMoto);
		
	}
	
	private void mensajeError(String mensaje) {
		JOptionPane.showMessageDialog(null, mensaje, 
				"Error", JOptionPane.ERROR_MESSAGE);
	}
	
	private void limpiarCampos() {
		txtMarca.setText("");
		txtKilometraje.setText("");
		txtCilindrada.setText("");
	}

}
