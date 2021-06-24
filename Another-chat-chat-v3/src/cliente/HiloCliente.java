package cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
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
		chats = new ArrayList<JChatCliente>();
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
				default:
					throw new IllegalArgumentException("Unexpected value: " + caso);
				}
			}
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}

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
		String sala = entrada.readUTF();
		if(salasConectadas < 3) {
			chats.add(new JChatCliente(menu.getCliente(), sala));
			chats.get(salasConectadas).run();
			salasConectadas++;
		}
	}
	
	private void abandonar_sala() throws IOException {
		String sala = entrada.readUTF();
		for (JChatCliente chat : chats) {
			if(chat.getSala().equals(sala)) {
				chats.remove(chat);
				salasConectadas--;
			}
		}
	}
}
