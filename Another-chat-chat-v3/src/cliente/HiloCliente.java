package cliente;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

import javax.swing.DefaultListModel;
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
		List<String> salas;
		DefaultListModel<String> model = new DefaultListModel<String>();
		while(true) {
			try {
				salas = (List<String>) entrada.readObject();
				model.clear();
				for (String string : salas) {
					model.addElement(string);
				}
				menu.getLista().setModel(model);
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}

		}
	}
}
