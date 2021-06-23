package ventanas;

import javax.swing.JFrame;
import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;

import cliente.Cliente;
import comandos.*;

import javax.swing.JLabel;
import java.awt.Font;

public class JLobby extends JFrame {	

	private static final long serialVersionUID = 1L;
	private JList<String> list;
	private JScrollPane listScroller;
	private static DefaultListModel<String> model;
	private JButton crearSalaButton;
	private JButton unirseSalaButton;
	private Cliente cliente;
	
	static {
		model = new DefaultListModel<String>();	
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JLobby frame = new JLobby();
					frame.setTitle("Chat");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public JLobby() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		setBounds(50, 50, 358, 308);
		crearSalaButton = new JButton("Crear Sala");
		iniButtonCrearSala();
		list = new JList<String>();
		listScroller = new JScrollPane(list);
		listScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		listScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		listScroller.setBounds(10, 50, 319, 175);
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setBounds(29, 46, 300, 300);
		getContentPane().add(listScroller);
		list.setModel(model);
		unirseSalaButton = new JButton("Unirse");
		iniButtonUnirse();
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 342, 22);
		getContentPane().add(menuBar);
		JMenu mnNewMenu = new JMenu("Opciones");
		menuBar.add(mnNewMenu);
		JMenuItem MenuConectar = new JMenuItem("Conectar");
		mnNewMenu.add(MenuConectar);
		MenuConectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//String nombre = JOptionPane.showInputDialog("Ingrese nombre de usuario");
				cliente = new Cliente(1200,"localhost");
				cliente.inicializarHiloCliente(getLobby());
				cliente.ejecutarComando(new Conectarse());
			}
		});
	}

	public void iniButtonCrearSala() {
		crearSalaButton.setBounds(229, 230, 100, 23);
		getContentPane().add(crearSalaButton);
		crearSalaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nombre = JOptionPane.showInputDialog("Ingrese el nombre de la sala");
				cliente.ejecutarComando(new CrearSala(nombre));
			}
		});
	}

	public void iniButtonUnirse() {
		unirseSalaButton.setBounds(130, 230, 89, 23);
		getContentPane().add(unirseSalaButton);
		JLabel lblSalas = new JLabel("Salas");
		lblSalas.setFont(new Font("Arial", Font.PLAIN, 12));
		lblSalas.setBounds(10, 33, 46, 17);
		getContentPane().add(lblSalas);
		unirseSalaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//run();
			}
		});
	}

	public JList<String> getLista() {
		return this.list;
	}
	
	public JLobby getLobby() {
		return this;
	}
}
