package comandos;

import java.io.Serializable;

import servidor.Paquete;

public class UnirseSala implements Comando, Serializable {
	
	private static final long serialVersionUID = 1L;
	public final String nombreSala;
	public final Paquete pcliente;
	
	public UnirseSala(String nombreSala, Paquete pcliente) {
		this.nombreSala = nombreSala;
		this.pcliente = pcliente;
	}
	
	public int procesar_comando() {
		return Comando.UNIRSE_SALA;
	}

}
