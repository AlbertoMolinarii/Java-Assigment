package application;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;


/**
 *
 * The class {@code ObjectServer} defines a server that waits
 * for an object message and then sends an answer.
 *
 **/

public class ObjectServer
{
	private static final int SPORT  = 4444;
	public product milk = new product("milk", 2.0, 0, 10);
	product apple = new product("apple", 1.0, 1, 10);
	product[] p_list = new product[] {milk, apple};
	User luca = new User("luca","luca","luca","luca");
	User albi = new User("albi","albi","albi","albi");
	User[] u_list = new User[] {luca,albi};
	boolean logged = false;
	boolean already_write = false;
	boolean u_already_in = false;
	boolean close = false;
	User new_user = null;
	
	
	public void show(String s, ObjectOutputStream os, Object o, Message m) throws IOException
	{	
		if(s.equals("user")) {
			for(User i:u_list) {
				os.writeObject(new Message(m.getUser(), i.getAddress()));
				os.flush();
			}
			
		}
		if(s.equals("product")) {
		for(product i:p_list) {
			if (i.getQuantity() != 0) {
				os.writeObject(new Message(new_user, i.ToString()));
				os.flush();
				}
			}
		}
	}
	public void buy(String[] L)
	{
		if(L.length==3) {
			for(product i: p_list) {
				if(L[1].equals(Integer.toString(i.getId()))){
					if(i.getQuantity()>= Integer.parseInt(L[2]) && i.getQuantity()!=0) {
						i.Buy(Integer.parseInt(L[2]));
					}
				}
			}
		}
	}
	public void refund(String[] L)
	{
		if(L.length==3) {
			for(product i: p_list) {
				if(L[1].equals(Integer.toString(i.getId()))){
					i.Refund(Integer.parseInt(L[2]));
				}
			}
		}
	}
	public void new1(String[] L)
	{
		if (L.length==6) {
			if(L[1].equals("product")) {

				product newarr[] = new product[p_list.length + 1];
				product new_product = new product(L[2], Double.parseDouble(L[3]), Integer.parseInt(L[4]), Integer.parseInt(L[5]));
				for(int i = 0; i<p_list.length;i++) {
					if(p_list[i].getId()==new_product.getId())
					{
						break;
					}
					newarr[i]=p_list[i];
				}
				newarr[p_list.length]=new_product;
				p_list = newarr;
				}
			}
	}
	public void login(String[] L)
	{
		for(int i=0; i<u_list.length;i++) {
			if(u_list[i].getAddress().equals(L[1]) && u_list[i].getPassword().equals(L[2])) {
				logged = true;
				new_user = u_list[i];
			}
		}
	}
	public void new2(String[] L)
	{
		User newarr[] = new User[u_list.length + 1];
		if(L.length==6) {
			User new_user1 = new User(L[2], L[3], L[4], L[5]);
			for(int i = 0; i<u_list.length;i++) {
				if(u_list[i].getAddress().equals(new_user1.getAddress()))
				{
					u_already_in = true;
				}
				newarr[i]=u_list[i];
			}
			if(!u_already_in) {
				newarr[u_list.length]=new_user1;
				u_list = newarr;
				logged = true;
			}}
	}

	/**
	 * Sends the answer.
	 *
	 **/
	public void reply()
	{
		try
		{
			ServerSocket server = new ServerSocket(SPORT);

			Socket client = server.accept();

			ObjectInputStream  is = new ObjectInputStream(client.getInputStream());
			ObjectOutputStream os = new ObjectOutputStream(client.getOutputStream());
			while (true)
			{
				// Reads until there are messages or
				// until it sends an ï¿½endï¿½ message

				Object o = is.readObject();
				if ((o != null) && (o instanceof Message))
				{
					Message m = (Message) o;

					System.out.format(" Server received: %s from Client\n", m.getContent());
					if(logged) {
						String[] request = m.getContent().split(" ");

						switch (request[0]){
							case ("show"): 
								show(request[1], os, o, m);
								break;
						
							case ("buy"):
								buy(request);
								break;
						
							case ("refund"):
								refund(request);
								break;
						
							case ("new"):
								new1(request);
								break;
							case ("close"):
								close = true;
						}
					}
					if (close) {break;}
						String[] login_message = m.getContent().split(" ");
						if(login_message[0].equals("close")) {close=true;break;}
						switch(login_message[0]) {
							case("login"):
								login(login_message);
								if(logged) {
									os.writeObject(new Message(new_user, "login eseguito"));
								os.flush();
								break;}
								os.writeObject(new Message(m.getUser(), "non eseguito"));
								os.flush();
						
							case("new"):
								new2(login_message);
								if(u_already_in) 
								{
									os.writeObject(new Message(m.getUser(), "already"));
									os.flush();
								}
								u_already_in = false;
							}
						
					os.writeObject(new Message(new_user, "done"));
					os.flush();
					continue;
				}
			
			
		}
			client.close();
			server.close();
			}
		catch (IOException | ClassNotFoundException e)
		{
			e.printStackTrace();
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
		new ObjectServer().reply();
	}
}
