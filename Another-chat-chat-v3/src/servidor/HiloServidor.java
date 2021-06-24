package servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import comandos.Comando;
import comandos.CrearSala;
import comandos.UnirseSala;

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
				case Comando.UNIRSE_SALA: {
					UnirseSala cmd = (UnirseSala) comando;
					comando_unise_sala(cmd.nombreSala, cmd.pcliente);
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
		Servidor.getSalidas().get(cliente).flush();
		Set<String> keySalas = Servidor.getSalas().keySet();
		List<String> salas = new ArrayList<String>();
		for (String keySala : keySalas) {
			salas.add(keySala+" ("+Servidor.getSalas().get(keySala).size()+")");
		}
		for (Socket socket : Servidor.getSalidas().keySet()) {
			Servidor.getSalidas().get(socket).writeInt(Comando.CONECTARSE);
			Servidor.getSalidas().get(socket).writeObject(salas);
			Servidor.getSalidas().get(socket).flush();
		}
	}

	public void comando_crear_sala(String nombreSala) throws IOException {
		Servidor.getSalas().put(nombreSala, new LinkedList<Paquete>());
		Set<String> keySalas = Servidor.getSalas().keySet();
		List<String> salas = new ArrayList<String>();
		for (String keySala : keySalas) {
			salas.add(keySala+" ("+Servidor.getSalas().get(keySala).size()+")");
		}
		for (Socket socket : Servidor.getSalidas().keySet()) {
			Servidor.getSalidas().get(socket).writeInt(Comando.CREAR_SALA);
			Servidor.getSalidas().get(socket).writeObject(salas);
			Servidor.getSalidas().get(socket).flush();
		}
	}
	
	public void comando_unise_sala(String nombreSala, Paquete pcliente) throws IOException {
		Servidor.getSalas().get(nombreSala).add(pcliente);
		Servidor.getSalidas().get(cliente).writeInt(Comando.UNIRSE_SALA);
		Servidor.getSalidas().get(cliente).flush();
		Servidor.getSalidas().get(cliente).writeUTF(nombreSala);
		Servidor.getSalidas().get(cliente).flush();
		comando_conectarse();
	}
}
