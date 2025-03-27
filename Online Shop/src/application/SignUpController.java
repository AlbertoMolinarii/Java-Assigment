package application;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
/**
 * the class {@code SignUpController} manages sign up page.
 */
public class SignUpController {

	@FXML
	private Button btn_finish;

	@FXML
	private TextField txt_address;

	@FXML
	private TextField txt_name;

	@FXML
	private TextField txt_password;

	@FXML
	private TextField txt_surname;

	@FXML
    private Button btn_close;
	
	/**
     * The method finish manages the new account's information and creates a new user;
     * 
     */
	@FXML
	public void finish() throws IOException, ClassNotFoundException 
	{
		m.setContent("new user "+ txt_name.getText()+" "+txt_surname.getText()+" "+txt_address.getText()+" "+txt_password.getText());
		while(true) {
			if(prova) 
			{
				os2.writeObject(m);
				os2.flush();
				os2.reset();
				prova = false;
			}
			Object o = is2.readObject();
			if ((o != null) && (o instanceof Message))
			{
				Message n = (Message) o;
				System.out.format(" Client received: %s from Server\n", n.getContent());
				if (n.getContent().equals("already"))
				{
					already = true;
					continue;
				}
				if (n.getContent().equals("done") && !already)
				{
					prova = true;
					FXMLLoader loader = new FXMLLoader(getClass().getResource("mainscene.fxml"));
			    	Parent root = loader.load();
			    	Stage window = (Stage) btn_finish.getScene().getWindow();
			    	window.setScene(new Scene(root, 600, 400));
			    	
			    	ShopController shopcontroller = loader.getController();
			    	boolean tappo = false;
			    	shopcontroller.getstart(tappo);
			    	shopcontroller.getstream(os2, is2);
					break;
				}
				if (n.getContent().equals("done") && already) 
				{
					prova = true;
					already = false;
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("Look, an Error in the password or in the username");
					alert.setContentText("Ooops, there was an error!");
					alert.showAndWait();
					break;
				}
				System.out.format(" and received: %s from Server\n",
						n.getContent());
				continue;

			}

		}
	}

	Message m = new Message(new User("Agostino", "Poggi", "agostino.poggi@unipr.it", "agostino"), "");
	boolean prova = true;

	ObjectInputStream  is2;
	ObjectOutputStream  os2;
	boolean already = false;
	/**
     * the method getsream set the client input and output.
     * @param server output
     * @param server input
     */
	public void getstream(ObjectOutputStream os1, ObjectInputStream  is1)
	{
		os2 = os1;
		is2 = is1;
	}
	/**
     * The method close close the application.
     */
	@FXML
    void close(ActionEvent event) throws IOException, ClassNotFoundException {
    	m.setContent("close");
    	os2.writeObject(m);
		os2.flush();
		os2.reset();
		Stage stage = (Stage) btn_close.getScene().getWindow();
		stage.close();
    }
}