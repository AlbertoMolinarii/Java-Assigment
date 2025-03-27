package application;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * The class {@code ObjectClient} defines a client that sends
 * an object message and then waits for an answer.
 *
 **/
public class ObjectClient
{
	private static final int SPORT = 4444;
	private static final String SHOST = "localhost";
	private boolean prova = true;

	/**
	 * Sends a e message.
	 *
	 **/

	public User login(String name, String surname, String address, String password) {
		User U = new User(name, surname, address, password);
		return U;

	}

	public void send()
	{
		try (Scanner scanner = new Scanner(System.in)) {
			String message = scanner.nextLine();
			try
			{

				Socket  client = new Socket(SHOST, SPORT);

				Message m      = new Message(new User(
						"Agostino", "Poggi", "agostino.poggi@unipr.it", "agostino"), message);

				ObjectOutputStream os = new ObjectOutputStream(client.getOutputStream());
				ObjectInputStream  is = new ObjectInputStream(client.getInputStream());
				while (true)
				{
					// Sends messages until it receives an ï¿½endï¿½ message
					m.setContent(message);

					System.out.format(" Client sends: %s to Server", m.getContent());
					if(prova) {
						os.writeObject(m);
						os.flush();
						os.reset();
						prova = false;
					}
					Object o = is.readObject();
					if ((o != null) && (o instanceof Message))
					{
						Message n = (Message) o;
						
						if (n.getContent().equals("end"))
						{
							break;
						}
						System.out.format(" and received: %s from Server\n",
								n.getContent());
						if (n.getContent().equals("done"))
						{
							prova = true;
							message = scanner.nextLine();
						}
						
						continue;

					}

				}
				client.close();
			}
			catch (IOException | ClassNotFoundException e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * Starts the demo.
	 *
	 * @param args  the method does not requires arguments.
	 *
	 **/
	public static void main(final String[] args)
	{
		System.out.println("ti devi loggare prima di fare qualsiasi operazione, digita login o new user");
		new ObjectClient().send();
	}
}
