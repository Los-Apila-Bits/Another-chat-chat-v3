package cliente;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import javax.swing.DefaultListModel;

import servidor.Paquete;
import ventanas.JLobby;

public class HiloCliente extends Thread{
	
	private ObjectInputStream entrada;
	private JLobby menu;
	//private JChatCliente ventana;
	
	public HiloCliente(ObjectInputStream entrada, JLobby menu) {
		this.entrada = entrada;
		this.menu = menu;
	}
	
	@SuppressWarnings("unchecked")
	public void run() {
		Map<String, LinkedList<Paquete>> salas;
		Set<String> nombSalas;
		DefaultListModel<String> model = new DefaultListModel<String>();
		while(true) {
			try {
				salas = (Map<String, LinkedList<Paquete>>) entrada.readObject();
				nombSalas = salas.keySet();
				for (String string : nombSalas) {
					model.addElement(string);
				}
				menu.getLista().setModel(model);
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}

		}
	}
}
