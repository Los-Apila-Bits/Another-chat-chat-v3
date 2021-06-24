package comandos;


import java.io.Serializable;
import java.util.LinkedList;

import servidor.Paquete;
import servidor.Servidor;

public class CrearSala implements Comando, Serializable{

	private static final long serialVersionUID = 1L;
	public String nombreSala;
	public CrearSala(String nombreSala) {
		this.nombreSala = nombreSala;
	}
	@Override
	public int procesar_comando() {
		return 2;
	}

}
