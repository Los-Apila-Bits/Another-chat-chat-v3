package comandos;


import java.util.LinkedList;

import servidor.Paquete;
import servidor.Servidor;

public class CrearSala implements Comando {

	private String nombreSala;
	public CrearSala(String nombreSala) {
		this.nombreSala = nombreSala;
	}
	@Override
	public int procesar_comando() {
		Servidor.getSalas().put(nombreSala, new LinkedList<Paquete>());
		return 2;
	}

}
