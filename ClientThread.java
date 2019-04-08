import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

class ClientThread extends Thread {

	String linha = null;
	Socket cliente = null;
	Scanner entrada = null;

	public ClientThread(Socket cliente, String nome) {
		this.cliente = cliente;
		this.setName(nome);
	}
	
	public String getIpAddress() {
		return this.cliente.getInetAddress().getHostAddress();
	}
	
	public int getPortNumber() {
		return this.cliente.getPort();
	}

	public void run() {
		try {
			entrada = new Scanner(this.cliente.getInputStream());
			while (entrada.hasNextLine()) {
				linha = entrada.nextLine();
				System.out.println(this.getName() + " diz: " + linha);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				System.out.println("Cliente fechou a conexão");
				cliente.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}