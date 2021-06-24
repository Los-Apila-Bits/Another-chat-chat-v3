package cliente;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import comandos.Comando;
import ventanas.JLobby;

public class Cliente {
	
	private Socket socket;
	private int puerto;
	private String ip;
	private ObjectOutputStream salida;
	private ObjectInputStream entrada;
	
	public Cliente(int puerto, String ip) {
		this.puerto = puerto;
		this.ip = ip;

		try {
			socket = new Socket(this.ip, this.puerto);
			salida = new ObjectOutputStream(socket.getOutputStream());
			entrada = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void ejecutarComando(Comando comando) {
		try {
			salida.writeObject(comando);
			salida.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void inicializarHiloCliente(JLobby menu) {
			new HiloCliente(entrada, menu).start();
	}
}
