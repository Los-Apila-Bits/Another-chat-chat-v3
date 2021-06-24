package comandos;

public interface Comando {
	public static final int CONECTARSE = 0;
	public static final int CREAR_SALA = 1;
	public static final int UNIRSE_SALA = 2;
	public static final int ENVIAR_MSJ = 3;
	public static final int ABANDONAR_SALA = 4;
	public int procesar_comando();
}
