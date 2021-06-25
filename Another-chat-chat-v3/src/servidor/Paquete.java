package servidor;

//import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import cliente.Cliente;
import ventanas.JChatCliente;

public class Paquete implements Serializable {

	private static final long serialVersionUID = 1L;
	private Socket cliente;
	private String nombre;
	private int salasActivas = 0;
	private List<String> salas;
	private ObjectOutputStream salida;
	
	public Paquete(Cliente cliente) {
		this.nombre = cliente.getNombre();
		salas = new LinkedList<String>();
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
	
	public void conectarSala(String nombreSala) {
		salas.add(nombreSala);
		salasActivas++;
	}
	
	public void desconectarSala(String nombreSala) {
		for (Iterator<String> iterator = salas.iterator(); iterator.hasNext();) {
			String sala = (String) iterator.next();
			if(sala.equals(nombreSala)) {
				iterator.remove();
			}
		}
		salasActivas--;
	}
	
	public int getSalasActivas() {
		return this.salasActivas;
	}
	
	public List<String> getSalas(){
		return this.salas;
	}
	
	
	public void setSalida(ObjectOutputStream salida) {
		this.salida = salida;
	}

	public ObjectOutputStream getSalida() {
		return salida;
	}
}
