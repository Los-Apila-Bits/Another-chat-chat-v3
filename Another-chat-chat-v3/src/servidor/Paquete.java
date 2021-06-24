package servidor;

//import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

import cliente.Cliente;

public class Paquete implements Serializable {

	private static final long serialVersionUID = 1L;
	private Socket cliente;
	private String nombre;
	private int salasActivas = 0;
//	private ObjectInputStream entrada;
	private ObjectOutputStream salida;
	
	public Paquete(Cliente cliente) {
		this.nombre = cliente.getNombre();
	}
	

	public String getNombre() {
		return this.nombre;
	}
	
	public Socket getSocket() {
		return this.cliente;
	}
	
	public void setSocket(Socket cliente) {
		this.cliente = cliente;
	}
	
	public void conectarSala() {
		salasActivas++;
	}
	
	public void desconectarSala() {
		salasActivas--;
	}
	
	public int getSalasActivas() {
		return this.salasActivas;
	}
	
	public void setSalida(ObjectOutputStream salida) {
		this.salida = salida;
	}

	public ObjectOutputStream getSalida() {
		return salida;
	}
}
