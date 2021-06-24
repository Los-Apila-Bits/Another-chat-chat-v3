package ventanas;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import cliente.*;
import comandos.AbandonarSala;

public class JChatCliente extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Cliente cliente;
	private String nombreSala;
	private JTextField textField;
	private JButton btnEnviar;
	private JTextArea textArea;
	private JScrollPane scrollPane;

	public JChatCliente(Cliente cliente, String nombreSala) {
		this.cliente = cliente;
		this.nombreSala = nombreSala;
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				cliente.ejecutarComando(new AbandonarSala(nombreSala));
			}
		});
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		textField = new JTextField();
		textField.setBounds(10, 219, 312, 31);
		contentPane.add(textField);
		textField.setColumns(10);
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				enviarMsj();
				textField.setText("");
			}
		});

		btnEnviar = new JButton("Enviar");
		btnEnviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				enviarMsj();
				textField.setText("");
			}
		});
		btnEnviar.setBounds(335, 219, 89, 31);
		contentPane.add(btnEnviar);

		scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(10, 33, 414, 175);
		contentPane.add(scrollPane);

		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		textArea.setEditable(false);
	}

	public JChatCliente iniciar() {
		return this;
	}

	public void escribirMensajeEnTextArea(String mensaje) {
		textArea.append(mensaje);
		try {
			sonidoMsj();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		}
	}

	public void enviarMsj() {
		String message = textField.getText();
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		String formatDateTime = now.format(format);
		if (!message.isEmpty())
//			cliente.ejecutarComando();
		return;
	}

	public void sonidoMsj() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		String soundName = "sonidos/sonido_msn.wav";
		AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
		Clip clip = AudioSystem.getClip();
		clip.open(audioInputStream);
		clip.start();
	}
	
	public String getSala() {
		return this.nombreSala;
	}
	
	public void run() {
			this.setTitle("Chat");
			this.setVisible(true);
	}

}
