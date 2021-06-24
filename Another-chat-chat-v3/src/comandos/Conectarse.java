package comandos;

import java.io.Serializable;

public class Conectarse implements Comando, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Conectarse() {
		
	}
	@Override
	public int procesar_comando() {
		return 1;
	}

}
