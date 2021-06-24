package servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;


import comandos.Comando;
import comandos.CrearSala;

public class HiloServidor extends Thread {

	private Socket cliente;
	Comando comando;

	public HiloServidor(Socket cliente) {
		this.cliente = cliente;
	}

	public void run() {
		ObjectInputStream entrada;
		int aux=0;
		try {
			entrada = new ObjectInputStream(this.cliente.getInputStream());
			while (true) {
				try {
					this.comando = (Comando) entrada.readObject();
					aux = this.comando.procesar_comando();
					if (aux==1) {
						for (Socket socket : Servidor.getSalidas().keySet()) {
							Servidor.getSalidas().get(socket).writeObject(new ArrayList<String>(Servidor.getSalas().keySet()));
							Servidor.getSalidas().get(socket).flush();
						}			
					}
					if (aux==2) {
						CrearSala cmd = (CrearSala) comando;
						Servidor.getSalas().put(cmd.nombreSala , new LinkedList<Paquete>());
						for (Socket socket : Servidor.getSalidas().keySet()) {
							Servidor.getSalidas().get(socket).writeObject(new ArrayList<String>(Servidor.getSalas().keySet()));
							Servidor.getSalidas().get(socket).flush();
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
