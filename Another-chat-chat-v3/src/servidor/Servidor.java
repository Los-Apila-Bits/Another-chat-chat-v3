package servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Servidor {
	
	private ServerSocket server;
	private static Map<String, LinkedList<Paquete>> salas = new HashMap<>();
	private List<Socket> clientes;
	
	public Servidor(int puerto) {
		try {
			server = new ServerSocket(puerto);
			clientes = new ArrayList<Socket>();	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void ejecutar() {
		Socket socket;
		try {
			while(true) {
				socket = server.accept();
				clientes.add(socket);
				new HiloServidor(socket,clientes).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static Map<String, LinkedList<Paquete>> getSalas(){
		return Servidor.salas;
	}
	
	public static void main(String[] args) {
		new Servidor(1200).ejecutar();
	}
}
