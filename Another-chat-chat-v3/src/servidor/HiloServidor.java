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
		int caso;

		try {
			entrada = new ObjectInputStream(this.cliente.getInputStream());
			while (true) {

				comando = (Comando) entrada.readObject();
				caso = comando.procesar_comando();

				switch (caso) {
				case Comando.CONECTARSE: {
					comando_conectarse();
					break;
				}
				case Comando.CREAR_SALA: {
					CrearSala cmd = (CrearSala) comando;
					comando_crear_sala(cmd.nombreSala);
					break;
				}
				default:
					throw new IllegalArgumentException("Unexpected value: " + caso);
				}

			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void comando_conectarse() throws IOException {
		Servidor.getSalidas().get(cliente).writeInt(Comando.CONECTARSE);
		Servidor.getSalidas().get(cliente).flush();
		Servidor.getSalidas().get(cliente).writeObject(new ArrayList<String>(Servidor.getSalas().keySet()));
		Servidor.getSalidas().get(cliente).flush();
	}

	public void comando_crear_sala(String nombreSala) throws IOException {
		Servidor.getSalas().put(nombreSala, new LinkedList<Paquete>());
		for (Socket socket : Servidor.getSalidas().keySet()) {
			Servidor.getSalidas().get(socket).writeInt(Comando.CREAR_SALA);
			Servidor.getSalidas().get(socket).writeObject(new ArrayList<String>(Servidor.getSalas().keySet()));
			Servidor.getSalidas().get(socket).flush();
		}
	}
}
