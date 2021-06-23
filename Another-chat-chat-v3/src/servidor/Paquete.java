package servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class Paquete {
	private Socket cliente;
	private String salaActiva;
	private ArrayList<String> salas;
	private String nick;
	private DataInputStream entrada;
	private DataOutputStream salida;
	
	public Paquete(Socket cliente,String nick,DataInputStream entrada,DataOutputStream salida) {
		this.cliente = cliente;
		this.nick = nick;
		this.entrada = entrada;
		this.salida = salida;
		this.salas = new ArrayList<>();
	}
	
	
	public void dejarSala(String sala)
	{
		salas.remove(sala);
	}
	
	public String getSalaActiva() {
		return salaActiva;
	}

	public void setSalaActiva(String salaActiva) {
		this.salaActiva = salaActiva;
	}
	
	public Paquete()
	{
		
	}
	
	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public Socket getCliente() {
		return cliente;
	}
	
	public int cantidadSalas()
	{
		return salas.size(); 
	}
	
	public ArrayList<String> getSala() {
		return salas;
	}

	public void setSala(String sala) {
		salas.add(sala);
	}

	public DataInputStream getEntrada() {
		return entrada;
	}

	public void setEntrada(DataInputStream entrada) {
		this.entrada = entrada;
	}

	public DataOutputStream getSalida() {
		return salida;
	}

	public void setSalida(DataOutputStream salida) {
		this.salida = salida;
	}

	public void setCliente(Socket cliente) {
		this.cliente = cliente;
	}	
	
}
