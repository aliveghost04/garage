package gestion.garage.iu;

import gestion.garage.database.Consulta;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class InterfazPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtCantidadVehiculos;
	private JTextField txtPrecioGomas;
	
	public InterfazPrincipal() {
		
		setTitle("Iniciando el garaje...");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 250);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		Font fuente = new Font("Tahoma", Font.PLAIN, 14);
		
		JLabel lblCantidadDeVehculos = new JLabel("Cantidad m\u00E1xima de veh\u00EDculos: ");
		lblCantidadDeVehculos.setFont(fuente);
		lblCantidadDeVehculos.setBounds(10, 43, 200, 14);
		contentPane.add(lblCantidadDeVehculos);
		
		JLabel lblNewLabel = new JLabel("Precio cambio gomas:");
		lblNewLabel.setFont(fuente);
		lblNewLabel.setBounds(10, 79, 200, 17);
		contentPane.add(lblNewLabel);
		
		txtCantidadVehiculos = new JTextField();
		txtCantidadVehiculos.setHorizontalAlignment(JTextField.RIGHT);
		txtCantidadVehiculos.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() < KeyEvent.VK_0 || e.getKeyChar() > KeyEvent.VK_9) {
					e.consume();
				}
				if (txtCantidadVehiculos.getText().length() >= 7) e.consume();
			}
		});
		txtCantidadVehiculos.setBounds(220, 43, 148, 20);
		contentPane.add(txtCantidadVehiculos);
		txtCantidadVehiculos.setColumns(10);
		
		txtPrecioGomas = new JTextField();
		txtPrecioGomas.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() < KeyEvent.VK_0 || e.getKeyChar() > KeyEvent.VK_9) {
					e.consume();
				}
				if (txtPrecioGomas.getText().length() >= 9) e.consume();
			}
		});
		txtPrecioGomas.setColumns(10);
		txtPrecioGomas.setHorizontalAlignment(JTextField.RIGHT);
		txtPrecioGomas.setBounds(220, 80, 148, 20);
		contentPane.add(txtPrecioGomas);
		
		JButton btnIniciarGaraje = new JButton("Iniciar Garaje");
		btnIniciarGaraje.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int cantidadVehiculos = 0;
				int precioGomas = 0;
				int cantidadVehiculosDB = 0;
				
				if(txtCantidadVehiculos.getText().length() == 0 || txtPrecioGomas.getText().length() == 0) {
					mensajeError("Debe llenar todos los campos !!");
					if (txtCantidadVehiculos.getText().length() == 0) txtCantidadVehiculos.requestFocus();
					else txtPrecioGomas.requestFocus();
					return;
				}
				
				if (txtCantidadVehiculos.getText().length() >= 7) {
					mensajeError("Solo se permite un número máximo de 7 dígitos "
							+ "en la cantidad de vehículos");
					txtCantidadVehiculos.setText("");
					txtCantidadVehiculos.requestFocus();
					return;
				}
				
				try {
					cantidadVehiculos = Integer.parseInt(txtCantidadVehiculos.getText());
					precioGomas = Integer.parseInt(txtPrecioGomas.getText());
				} catch (NumberFormatException e) {
					mensajeError("Números inválidos: " + e.getMessage());
					return;
				}
				
				try {
					cantidadVehiculosDB = Consulta.getInstancia().obtenerCantidadVehiculos();
				} catch (InstantiationException | IllegalAccessException
						| ClassNotFoundException | SQLException e) {
					mensajeError("Error con la base de datos: " + e.getMessage());
					return;
				}
				
				if (precioGomas < 0) {
					mensajeError("Debe de elegir un precio igual o mayor a 0");
					txtPrecioGomas.requestFocus();
					txtPrecioGomas.selectAll();
					return;
				}
				
				if (cantidadVehiculos < cantidadVehiculosDB) {
					mensajeError("Actualmente hay " + cantidadVehiculosDB + " "
							+ "vehiculos en el garaje, debe elegir una capacidad igual o más alta "
							+ "para poder entrar");
					txtCantidadVehiculos.requestFocus();
					txtCantidadVehiculos.selectAll();
					return;
				}
				
				new InterfazGaraje(cantidadVehiculos, precioGomas).setVisible(true);
				dispose();
			}
		});
		btnIniciarGaraje.setFont(fuente);
		btnIniciarGaraje.setBounds(130, 128, 125, 50);
		contentPane.add(btnIniciarGaraje);
	}
	
	private void mensajeError(String mensaje) {
		JOptionPane.showMessageDialog(InterfazPrincipal.this, mensaje, 
				"Error", JOptionPane.ERROR_MESSAGE);
	}
}
