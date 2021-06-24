package comandos;

import java.io.Serializable;

import servidor.Paquete;

public class EnviarMsj implements Comando, Serializable {

	private static final long serialVersionUID = 1L;
	public final String nombreSala;
	public final String msj;
	public final Paquete pcliente;
	
	public EnviarMsj(String nombreSala, String msj, Paquete pcliente) {
		this.nombreSala = nombreSala;
		this.msj = msj;
		this.pcliente = pcliente;
	}
	@Override
	public int procesar_comando() {
		return Comando.ENVIAR_MSJ;
	}

	
}
