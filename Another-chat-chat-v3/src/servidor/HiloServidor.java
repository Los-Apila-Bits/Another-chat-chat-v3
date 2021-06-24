package servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import comandos.AbandonarSala;
import comandos.Comando;
import comandos.Conectarse;
import comandos.CrearSala;
import comandos.EnviarMsj;
import comandos.UnirseSala;

public class HiloServidor extends Thread {

	private Socket cliente;
	private Comando comando;
	private Paquete pcliente;
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
					Conectarse cmd = (Conectarse) comando;
					comando_conectarse(cmd.pcliente);
					break;
				}
				case Comando.CREAR_SALA: {
					CrearSala cmd = (CrearSala) comando;
					comando_crear_sala(cmd.nombreSala);
					break;
				}
				case Comando.UNIRSE_SALA: {
					UnirseSala cmd = (UnirseSala) comando;
					comando_unise_sala(cmd.nombreSala);
					break;
				}
				case Comando.ENVIAR_MSJ: {
					EnviarMsj cmd = (EnviarMsj) comando;
					comando_enviar_msj(cmd.nombreSala,cmd.msj);
					break;
				}
				case Comando.ABANDONAR_SALA: {
					AbandonarSala cmd = (AbandonarSala) comando;
					comando_abandonar_sala(cmd.sala);
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
	
	public void comando_actualizar_salas() throws IOException {
		Servidor.getSalidas().get(cliente).flush();
		Set<String> keySalas = Servidor.getSalas().keySet();
		List<String> salas = new ArrayList<String>();
		for (String keySala : keySalas) {
			salas.add(keySala+" ("+Servidor.getSalas().get(keySala).size()+")");
		}
		for (Socket socket : Servidor.getSalidas().keySet()) {
			Servidor.getSalidas().get(socket).writeInt(Comando.ACTUALIZAR_SALAS);
			Servidor.getSalidas().get(socket).writeObject(salas);
			Servidor.getSalidas().get(socket).flush();
		}
	}

	public void comando_conectarse(Paquete pcliente) throws IOException {
		this.pcliente = pcliente;
		this.pcliente.setSocket(cliente);
		this.pcliente.setSalida(Servidor.getSalidas().get(cliente));
		Set<String> keySalas = Servidor.getSalas().keySet();
		List<String> salas = new ArrayList<String>();
		for (String keySala : keySalas) {
			salas.add(keySala+" ("+Servidor.getSalas().get(keySala).size()+")");
		}
		Servidor.getSalidas().get(cliente).flush();
		Servidor.getSalidas().get(cliente).writeInt(Comando.CONECTARSE);
		Servidor.getSalidas().get(cliente).flush();
		Servidor.getSalidas().get(cliente).writeObject(salas);
		Servidor.getSalidas().get(cliente).flush();
		
	}

	public void comando_crear_sala(String nombreSala) throws IOException {
		Servidor.getSalas().put(nombreSala, new LinkedList<Paquete>());
		Set<String> keySalas = Servidor.getSalas().keySet();
		List<String> salas = new ArrayList<String>();
		for (String keySala : keySalas) {
			salas.add(keySala+" ("+Servidor.getSalas().get(keySala).size()+")");
		}
		for (Socket socket : Servidor.getSalidas().keySet()) {
			Servidor.getSalidas().get(cliente).flush();
			Servidor.getSalidas().get(socket).writeInt(Comando.CREAR_SALA);
			Servidor.getSalidas().get(socket).writeObject(salas);
			Servidor.getSalidas().get(socket).flush();
		}
	}
	
	public void comando_unise_sala(String nombreSala) throws IOException {
		if(pcliente.getSalasActivas()<3) {
			Servidor.getSalas().get(nombreSala).add(pcliente);
			pcliente.conectarSala();
			Servidor.getSalidas().get(cliente).flush();
			Servidor.getSalidas().get(cliente).writeInt(Comando.UNIRSE_SALA);
			Servidor.getSalidas().get(cliente).flush();
			Servidor.getSalidas().get(cliente).writeUTF(nombreSala);
			Servidor.getSalidas().get(cliente).flush();
			comando_actualizar_salas();
		}
	}
	
	public void comando_abandonar_sala(String nombreSala) throws IOException {
		Servidor.getSalas().get(nombreSala).remove(pcliente);
		pcliente.desconectarSala();
		comando_actualizar_salas();
		Servidor.getSalidas().get(cliente).flush();
		Servidor.getSalidas().get(cliente).writeInt(Comando.ABANDONAR_SALA);
		Servidor.getSalidas().get(cliente).flush();
		Servidor.getSalidas().get(cliente).writeUTF(nombreSala);
		Servidor.getSalidas().get(cliente).flush();
	}
	
	public void comando_enviar_msj(String nombreSala,String msj) throws IOException{
		List<Paquete> receptores = Servidor.getSalas().get(nombreSala);
		for (Paquete receptor : receptores) {
			receptor.getSalida().flush();
			receptor.getSalida().writeInt(Comando.ENVIAR_MSJ);
			receptor.getSalida().flush();
			receptor.getSalida().writeUTF(nombreSala);
			receptor.getSalida().flush();
			receptor.getSalida().writeUTF(msj);
			receptor.getSalida().flush();
		}
	}
}
