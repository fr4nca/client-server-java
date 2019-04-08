import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {

	public static void main(String[] args) throws IOException {

		final int PORT = 12345;
		final String HOST = "127.0.2.1";

		Socket cliente = new Socket(HOST, PORT);

		try (Scanner teclado = new Scanner(System.in); PrintStream saida = new PrintStream(cliente.getOutputStream())) {
			System.out.println("Digite 'Exit' para finalizar a conexão.");
			System.out.print("Digite seu nome: ");
			String linha = null;
			while (teclado.hasNextLine()) {
				linha = teclado.nextLine();
				if (linha.equals("Exit")) {
					System.out.println("Deixando o servidor..");
					cliente.close();
					System.exit(0);
				}
				saida.println(linha);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			cliente.close();
			System.out.println("Conexão finalizada");
		}
	}
}
