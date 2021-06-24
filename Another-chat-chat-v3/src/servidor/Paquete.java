package servidor;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

import cliente.Cliente;

public class Paquete implements Serializable {

	private static final long serialVersionUID = 1L;
	private Socket cliente;
	private String nombre;
	private ObjectInputStream entrada;
	private ObjectOutputStream salida;
	
	public Paquete(Cliente cliente) {
		//this.cliente = cliente.getSocket();
		this.nombre = cliente.getNombre();
		//this.salida = cliente.getSalida();
		//this.entrada = cliente.getEntrada();
	}
	

	public String getNombre() {
		return this.nombre;
	}

//	public Socket getCliente() {
//		return cliente;
//	}

//	public ObjectInputStream getEntrada() {
//		return entrada;
//	}
//
//	public ObjectOutputStream getSalida() {
//		return salida;
//	}
}
