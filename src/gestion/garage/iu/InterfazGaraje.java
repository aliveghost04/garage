package gestion.garage.iu;

import gestion.garage.database.Consulta;
import gestion.garage.modelo.ModeloTablaCoche;
import gestion.garage.modelo.ModeloTablaMotos;
import gestion.garage.practicas.Garaje;
import gestion.garage.vehiculo.Vehiculo;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

public class InterfazGaraje extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Garaje garajeErick;
	private JTable tblAutos;
	private JTable tblMotos;
	private Consulta consulta;
	private static JLabel lblCantidadEspacioLibre;
	private static int cantidadVehiculos;
	private JButton btnChat;

	public InterfazGaraje(int cantidadVehiculos, int precioGomas) {
		
		setTitle("Garaje");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(900, 600);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		InterfazGaraje.cantidadVehiculos = cantidadVehiculos;
		
		Garaje.crearInstancia(cantidadVehiculos, precioGomas);
		
		garajeErick = Garaje.getInstancia();
		Font fuente = new Font("Tahoma", Font.PLAIN, 14);
		
		JPanel panelAutos = new PanelCoches();
		panelAutos.setBounds(10, 10, 430, 265);
		contentPane.add(panelAutos);
		
		JPanel panelMotos = new PanelMotos();
		panelMotos.setBounds(450, 10, 430, 265);
		contentPane.add(panelMotos);
		
		btnChat = new JButton("Chat");
		btnChat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				InterfazPrincipalChat.getInstancia().setLocationRelativeTo(InterfazGaraje.this);
				InterfazPrincipalChat.getInstancia().setVisible(true);
				btnChat.setEnabled(false);
			}
		});
		btnChat.setBounds(795, 538, 89, 23);
		contentPane.add(btnChat);
		
		tblAutos = new JTable(ModeloTablaCoche.getInstancia());
		tblMotos = new JTable(ModeloTablaMotos.getInstancia());
		
		JScrollPane contenedorTablaMotos = new JScrollPane(tblMotos);
		contenedorTablaMotos.setBounds(450, 317, 430, 144);
		contentPane.add(contenedorTablaMotos);
		
		JScrollPane contenedorTablaAutos = new JScrollPane(tblAutos);
		contenedorTablaAutos.setBounds(10, 317, 430, 144);
		contentPane.add(contenedorTablaAutos);
		
		JLabel lblAutos = new JLabel("Lista de Coches");
		lblAutos.setFont(fuente);
		lblAutos.setBounds(147, 286, 103, 20);
		contentPane.add(lblAutos);
		
		JLabel lblMotos = new JLabel("Lista de Motos");
		lblMotos.setFont(fuente);
		lblMotos.setBounds(597, 286, 89, 20);
		contentPane.add(lblMotos);
		
		JButton btnRetirarCoche = new JButton("Retirar Coche");
		btnRetirarCoche.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int fila = tblAutos.getSelectedRow();
				if (fila < 0) { 
					mensajeError("Debe seleccionar un coche");
					return;
				} 
				
				try {
					consulta = Consulta.getInstancia();
					Vehiculo vehiculo = consulta.obtenerCoche(ModeloTablaCoche.getInstancia().getIdCoche(fila));
					if (!garajeErick.eliminarVehiculo(vehiculo)) {
						mensajeError("No se pudo retirar el vehiculo");
						return;
					}
					consulta.eliminarCoche(ModeloTablaCoche.getInstancia().getIdCoche(fila));
					ModeloTablaCoche.getInstancia().retirarCoche(fila);
				} catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
					mensajeError(e.getMessage());
					return;
				}
				
				mensajeInformacion("Coche retirado satisfactoriamente !!!");
				InterfazGaraje.cantidadEspacioLibre(Garaje.getInstancia().getCantidadVehiculos());
			}
		});
		btnRetirarCoche.setFont(fuente);
		btnRetirarCoche.setBounds(10, 478, 155, 43);
		contentPane.add(btnRetirarCoche);
		
		JButton btnRetirarMoto = new JButton("Retirar Moto");
		btnRetirarMoto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int fila = tblMotos.getSelectedRow();
				if (fila < 0) { 
					mensajeError("Debe seleccionar una moto");
					return;
				} 
				
				try {
					consulta = Consulta.getInstancia();
					Vehiculo vehiculo = consulta.obtenerMoto(ModeloTablaMotos.getInstancia().getIdMoto(fila));
					if (!garajeErick.eliminarVehiculo(vehiculo)) {
						mensajeError("No se pudo retirar el vehiculo");
						return;
					}
					consulta.eliminarMoto(ModeloTablaMotos.getInstancia().getIdMoto(fila));
					ModeloTablaMotos.getInstancia().retirarMoto(fila);
				} catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
					mensajeError(e.getMessage());
					return;
				}
				
				mensajeInformacion("Moto retirada satisfactoriamente !!!");
				cantidadEspacioLibre(garajeErick.getCantidadVehiculos());
			}
		});
		btnRetirarMoto.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnRetirarMoto.setBounds(450, 478, 155, 43);
		contentPane.add(btnRetirarMoto);
		
		JButton btnKilometrajeMedio = new JButton("Kilometraje Medio");
		btnKilometrajeMedio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(InterfazGaraje.this, "El kilometraje medio de todos los vehiculos es: " 
						+ garajeErick.getKilometrajeMedio() + " KM", "Kilometraje Medio", JOptionPane.INFORMATION_MESSAGE);
				cantidadEspacioLibre(garajeErick.getCantidadVehiculos());
			}
		});
		btnKilometrajeMedio.setBounds(10, 538, 155, 23);
		contentPane.add(btnKilometrajeMedio);
		
		JButton btnTotalCambioRuedas = new JButton("Total cambio ruedas");
		btnTotalCambioRuedas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(InterfazGaraje.this, "El total del cambio de todas las ruedas es: " 
						+ garajeErick.getTotalCambioGomas(), "Total cambio ruedas", JOptionPane.INFORMATION_MESSAGE);
				cantidadEspacioLibre(garajeErick.getCantidadVehiculos());
			}
		});
		btnTotalCambioRuedas.setBounds(450, 538, 155, 23);
		contentPane.add(btnTotalCambioRuedas);
		
		JLabel lblEspaciosDisponibles = new JLabel("Espacios disponibles:");
		lblEspaciosDisponibles.setBounds(214, 503, 126, 29);
		lblEspaciosDisponibles.setFont(fuente);
		contentPane.add(lblEspaciosDisponibles);
		
		lblCantidadEspacioLibre = new JLabel("0");
		lblCantidadEspacioLibre.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblCantidadEspacioLibre.setBounds(350, 494, 90, 43);
		contentPane.add(lblCantidadEspacioLibre);
		cantidadEspacioLibre(garajeErick.getCantidadVehiculos());
	}
	
	private void mensajeError(String mensaje) {
		JOptionPane.showMessageDialog(InterfazGaraje.this, mensaje, 
				"Error", JOptionPane.ERROR_MESSAGE);
	}
	
	private void mensajeInformacion(String mensaje) {
		JOptionPane.showMessageDialog(InterfazGaraje.this, mensaje, 
				"Información", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void cantidadEspacioLibre(int parametroEspacio) {
		
		int espacio = cantidadVehiculos - parametroEspacio;
		if (espacio == 1) {
			lblCantidadEspacioLibre.setForeground(Color.ORANGE);
		} else if(espacio == 0) {
			lblCantidadEspacioLibre.setForeground(Color.RED);
		} else {
			lblCantidadEspacioLibre.setForeground(Color.GREEN);
		}
		
		lblCantidadEspacioLibre.setText("" + espacio);
	}
}
