import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

	final static List<ClientThread> clientes = new ArrayList<>();

	public static void main(String args[]) throws IOException {

		ServerSocket server = null;
		Scanner entrada = null;
		final int PORT = 12345;

		try {
			server = new ServerSocket(PORT);
			System.out
					.println("Servidor iniciado no endereço: " + server.getLocalSocketAddress() + " na porta " + PORT);

			while (true) {
				Socket cliente = server.accept();
				entrada = new Scanner(cliente.getInputStream());
				String nome = entrada.nextLine();
				addCliente(new ClientThread(cliente, nome));
			}

		} catch (IOException ex) {
			Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			server.close();
			entrada.close();
		}
	}

	public static boolean addCliente(ClientThread cliente) {
		for (ClientThread clientThread : clientes) {
			if (clientThread.getName().equals(cliente.getName())
					|| clientThread.getIpAddress().equals(cliente.getIpAddress())) {
				System.out.println("Já existe um usuário com este nick ou IP..");
				cliente.close();
				return false;
			}
		}
		clientes.add(cliente);
		cliente.start();
		System.out.println(
				"Seja bem-vindo " + cliente.getName() + "\nVocê esta conectado do IP: " + cliente.getIpAddress());
		return true;
	}

	public static void removeCliente(ClientThread cliente) {
		clientes.remove(cliente);

	}
}
