package servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

import comandos.Comando;

public class HiloServidor extends Thread {

	private Socket cliente;
	private List<Socket> clientes;
	Comando comando;

	public HiloServidor(Socket cliente, List<Socket> clientes) {
		this.cliente = cliente;
		this.clientes = clientes;
	}

	public void run() {
		ObjectInputStream entrada;
		ObjectOutputStream salida;
		try {
			entrada = new ObjectInputStream(this.cliente.getInputStream());
			while (true) {
				try {
					this.comando = (Comando) entrada.readObject();
					if (comando.procesar_comando()==1) {
						for (Socket socket : clientes) {
							salida = new ObjectOutputStream(socket.getOutputStream());
							salida.writeObject(Servidor.getSalas());
							salida.flush();
							salida.close();
						}			
					}
					if (comando.procesar_comando()==2) {
						for (Socket socket : clientes) {
							salida = new ObjectOutputStream(socket.getOutputStream());
							salida.writeObject(Servidor.getSalas());
						}			
					}
				} catch (ClassNotFoundException | IOException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
