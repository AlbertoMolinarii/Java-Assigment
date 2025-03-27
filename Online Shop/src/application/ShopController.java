package application;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
/**
 * the class {@code ShopController} manages the login page.
 */
public class ShopController {

    
    @FXML
    private Button btn_login;

    @FXML
    private Button btn_signup;
   
    @FXML
    private AnchorPane scenaprincipale;

    @FXML
    private TextField txt_Password;

    @FXML
    private TextField txt_Username;
    
    @FXML
    private Button btn_close;

    private static final int SPORT = 4444;
	private static final String SHOST = "localhost";
	private boolean prova = true;
	private boolean var_login = false;
	User user = new User("Agostino", "Poggi", "agostino.poggi@unipr.it", "agostino");
	Message m = new Message(user, "");
	Socket  client = null;
	ObjectOutputStream os = null;
	ObjectInputStream  is = null;
	boolean start = true;
	/**
     * The method getstart set a boolean variable;
     * @param boolean.
     */
	public void getstart(boolean not_start) {start = not_start;};
	/**
     * the method getsream set the client input and output.
     * @param server output
     * @param server input
     */
	public void getstream(ObjectOutputStream os1, ObjectInputStream  is1)
	{
		os = os1;
		is = is1;
	}
	/**
     * The method login manages the access to the application;
     * 
     */
    @FXML
    public void login() throws IOException, ClassNotFoundException {
    	if(start) 
		{
			try  {
				Socket client = new Socket(SHOST, SPORT);
				os = new ObjectOutputStream(client.getOutputStream());
				is = new ObjectInputStream(client.getInputStream());
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			start = false;
		}
    	m.setContent("login "+txt_Username.getText()+" "+txt_Password.getText());
    	System.out.format(" Client sends: %s to Server", m.getContent());
    	while(true) {
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
				System.out.println(n.getContent());
				if (n.getContent().equals("end"))
				{
					break;
				}
				if (n.getContent().equals("login eseguito"))
				{
					user = n.getUser();
					var_login = true;
				}
				if (n.getContent().equals("non eseguito")) 
				{
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("Look, an Error in the password or in the username");
					alert.setContentText("Ooops, there was an error!");

					alert.showAndWait();
				}
				if (n.getContent().equals("done"))
				{
					prova = true;
					if(var_login) {
						switchToLoggedIn();
					}
					break;
				}
				System.out.format(" and received: %s from Server\n",
						n.getContent());
				continue;

			}
        	
    	}
    	
    	
    }
    /**
     * The method switchToLoggedIn opens the logged in page;
     * 
     */
	@FXML
    public void switchToLoggedIn() throws IOException
    {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("logged_in.fxml"));
    	Parent root = loader.load();
    	Stage window = (Stage) btn_login.getScene().getWindow();
    	window.setScene(new Scene(root, 600, 400));
    	
    	LoggedInController loggedincontroller = loader.getController();
    	loggedincontroller.getstream(os, is);
    	loggedincontroller.get_user(user);
    }
	/**
     * The method signup call the switch to sign up method;
     * 
     */
	@FXML
    public void signup() throws IOException, ClassNotFoundException 
	{
		if(start) 
		{
			try  {
				Socket client = new Socket(SHOST, SPORT);
				os = new ObjectOutputStream(client.getOutputStream());
				is = new ObjectInputStream(client.getInputStream());
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			start = false;
		}
		switchToSignup();
		
	}
	/**
     * The method switchToSignup opens the sign up page;
     * 
     */
    @FXML
    public void switchToSignup() throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("sign_up.fxml"));
    	Parent root = loader.load();
    	Stage window = (Stage) btn_signup.getScene().getWindow();
    	window.setScene(new Scene(root, 600, 400));
    	
    	SignUpController signupcontroller = loader.getController();
    	signupcontroller.getstream(os, is);
    }
    /**
     * The method close close the application.
     */
    @FXML
    void close(ActionEvent event) throws IOException, ClassNotFoundException {
    	if(!start) 
    	{
    		m.setContent("close");
        	os.writeObject(m);
    		os.flush();
    		os.reset();
    		Stage stage = (Stage) btn_close.getScene().getWindow();
    		stage.close();
    	}
    	else 
    	{
    		Stage stage = (Stage) btn_close.getScene().getWindow();
    		stage.close();
    	}
    }
   
    
    
}