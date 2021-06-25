package cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import comandos.Comando;
import ventanas.JChatCliente;
import ventanas.JLobby;

public class HiloCliente extends Thread {

	private ObjectInputStream entrada;
	private JLobby menu;
	private List<JChatCliente> chats;
	private int salasConectadas = 0;

	public HiloCliente(ObjectInputStream entrada, JLobby menu) {
		this.entrada = entrada;
		this.menu = menu;
		this.chats = new ArrayList<JChatCliente>();
	}

	public void run() {
		int caso;
		try {
			while (true) {
				caso = entrada.readInt();
				switch (caso) {
				case Comando.CONECTARSE: {
					conectarse();
					break;
				}
				case Comando.CREAR_SALA: {
					crear_sala();
					break;
				}
				case Comando.UNIRSE_SALA: {
					unirse_sala();
					break;
				}
				case Comando.ABANDONAR_SALA: {
					abandonar_sala();
					break;
				}
				case Comando.ACTUALIZAR_SALAS: {
					actualizar_sala();
					break;
				}
				case Comando.ENVIAR_MSJ: {
					enviar_msj();
					break;
				}
				case Comando.DESCONECTAR: {
					desconectar();
					break;
				}
				default:
					break;
				}
			}
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}

	}
	
	private void actualizar_sala() throws ClassNotFoundException, IOException {
		@SuppressWarnings("unchecked")
		List<String> salas = (List<String>) entrada.readObject();
		menu.actualizar_salas(salas);
	}
	
	private void conectarse() throws ClassNotFoundException, IOException {
		@SuppressWarnings("unchecked")
		List<String> salas = (List<String>) entrada.readObject();
		menu.actualizar_salas(salas);
	}
	
	private void crear_sala() throws ClassNotFoundException, IOException {
		@SuppressWarnings("unchecked")
		List<String> salas = (List<String>) entrada.readObject();
		menu.actualizar_salas(salas);
	}
	
	private void unirse_sala() throws IOException {
		if(salasConectadas < 3) {
			String sala = entrada.readUTF();
			chats.add(new JChatCliente(menu.getCliente(), sala));
			chats.get(salasConectadas).run();
			salasConectadas++;
		}
	}
	
	private void enviar_msj() throws IOException {
		String nombreSala = entrada.readUTF();
		String mensaje = entrada.readUTF();
		for (JChatCliente chat : chats) {
			if(chat.getSala().equals(nombreSala))
				chat.escribirMensajeEnTextArea(mensaje);
		}
	}
	
	private void abandonar_sala() throws IOException {
		String sala = entrada.readUTF();
		salasConectadas--;
		for (Iterator<JChatCliente> iterator = chats.iterator(); iterator.hasNext();) {
			JChatCliente chat = (JChatCliente) iterator.next();
			if(chat.getSala().equals(sala)) {
				iterator.remove();
				return;
			}
		}
	}
	private void desconectar() throws IOException, ClassNotFoundException {
		menu.getCliente().getSalida().close();
		menu.getCliente().getSocket().close();
	}
}
