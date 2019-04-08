import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

	public static void main(String args[]) throws IOException {

		ServerSocket server = null;
		Scanner entrada = null;
		final int PORT = 12345;
		final List<ClientThread> clientes = new ArrayList<>();

		try {
			server = new ServerSocket(PORT);
			System.out.println("Servidor iniciado no endereço: " + server.getLocalSocketAddress() + " na porta " + PORT);

			while (true) {
				if (clientes.isEmpty()) {
					Socket cliente = server.accept();
					entrada = new Scanner(cliente.getInputStream());
					String nome = entrada.nextLine();
					ClientThread clienteThread = new ClientThread(cliente, nome);
					clientes.add(clienteThread);
					clienteThread.start();
					System.out.println("Seja bem-vindo " + clienteThread.getName() + "\nVocê esta conectado do IP: "
							+ cliente.getInetAddress().getHostAddress());
				} else {
					Socket cliente = server.accept();
					entrada = new Scanner(cliente.getInputStream());
					String nome = entrada.nextLine();
					for (ClientThread clientThread : clientes) {
						if (clientThread.getName().equals(nome)
								|| clientThread.getIpAddress().equals(cliente.getInetAddress().getHostAddress())) {
							System.out.println("Já existe um usuário com este nick ou IP..");
							cliente.close();
						}
					}
					ClientThread clienteThread = new ClientThread(cliente, nome);
					clientes.add(clienteThread);
					clienteThread.start();
					System.out.println("Seja bem-vindo " + clienteThread.getName() + "\nVocê esta conectado do IP: "
							+ cliente.getInetAddress().getHostAddress());
				}
			}

		} catch (IOException ex) {
			Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			server.close();
			entrada.close();
		}
	}
}
