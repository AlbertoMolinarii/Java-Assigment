package application;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * controls the refill page where the user can insert the informations of his card to complete the payment
 */
public class ControllerRefillPage {

    @FXML
    private AnchorPane anchor_pane;

    @FXML
    private Button btn_account;

    @FXML
    private Button btn_close;

    @FXML
    private Button btn_logout;

    @FXML
    private Button btn_mycollection;

    @FXML
    private Button btn_refill;
    
    @FXML
    private Button btn_confirm;

    @FXML
    private Label label_account;

    @FXML
    private Label label_accountbalance;

    @FXML
    private AnchorPane sidePane;
    
    @FXML
    private TextField txt_balance;
    
    @FXML
    private TextField txt_cardnumber;

    @FXML
    private TextField txt_cvc;

    @FXML
    private TextField txt_owner;
    
    @FXML
    private DatePicker date_expdate;
    
    static boolean open_slide = false;
    
    
    public watch watchsel;
    public ObservableList<String> list = FXCollections.observableArrayList();
    
    /**
     * form page3, it takes the watch that we want to buy
     * @param w
     */
    public void getSelectedwatch(watch w)
	{
		watchsel = w;	
		list.addAll("category: "+w.getCategory(), "Diameter: "+Double.toString(w.getDiameter()), "Movement: "+w.getMovement(), "Price: "+Double.toString(w.getPrice()));
	}
    /**
     * if the button confirm is clicked, we can buy the watch if we have inserted the information of the card. if we do this correctly we go to page4 to see our collection of watches
     * @param Event
     * @throws SQLException
     * @throws IOException
     */
    @FXML
    public void refill(ActionEvent Event) throws SQLException, IOException
    {	
    	if (txt_cardnumber.getText() == "" && txt_cvc.getText() == "" && txt_owner.getText() == "")
    	{
    		Alert a = new Alert(AlertType.INFORMATION);
    		a.setContentText("Insert the card");
    		a.show();
    		return;
    	}
    	else 
    	{
    		String s = "UPDATE watch SET possessed = '";
    		s += WatchShopController.account_name + "'";
    		s += " WHERE category = '";
    		s += watchsel.getCategory() + "'";
    		PreparedStatement ps = WatchShopController.conn.prepareStatement(s);
    		ps.executeUpdate();
		
    		switchtomycollection();
		
    		Alert a = new Alert(AlertType.INFORMATION);
    		a.setContentText("Thank you for your purchase");
    		a.show();
    		return;
    	}
    }
    /**
     * if the botton is clicked, we return to page2
     * @param Event
     * @throws IOException
     */
    @FXML
    public void backpage() throws IOException
    {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("pag2.fxml"));
		Parent root = loader.load();
		Stage window = (Stage) btn_confirm.getScene().getWindow();
		window.setScene(new Scene(root, 600, 400));
    }
    /**
     * if the botton btn_close is clicked, it closes the application
     * @param Event
     */
    @FXML
    void close(ActionEvent event) {
    	Stage stage = (Stage) btn_close.getScene().getWindow();
		stage.close();
    }
    /**
     * if the botton "Account" is clicked, it creates a side pane on the right
     */
    @FXML
    void createsidePane(ActionEvent event) {
		if(open_slide) 
		{
			TranslateTransition slide = new TranslateTransition();
			sidePane.setTranslateX(0);
			slide.setNode(sidePane);
			slide.setToX(1000);
			slide.play();
			open_slide = false;
		}
		else 
		{
			TranslateTransition slide = new TranslateTransition();
			sidePane.setTranslateX(1000);
			slide.setNode(sidePane);
			slide.setToX(-200);
			slide.play();
			open_slide = true;
			if (WatchShopController.account_admin == true)
			{
				label_account.setText(WatchShopController.account_name + " Admin");
			}
			else
			{
				label_account.setText(WatchShopController.account_name);
			}
		}
    }
    /**
     * if the botton btn_logout is clicked, it changes page to pag1 for the login
     * @param Event
     * @throws IOException
     */
    @FXML
    void switchtoLoginPage(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("pag1.fxml"));
		Parent root = loader.load();
		Stage window = (Stage) btn_logout.getScene().getWindow();
		window.setScene(new Scene(root, 600, 400));
    }
    /**
     * if the botton btn_addwatch is clicked, it changes page to pag4 that has the collection of our watches
     * @param Event
     * @throws IOException
     */
    @FXML
    void switchtomycollection() throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("pag4.fxml"));
    	Parent root = loader.load();
    	Stage window = (Stage) btn_mycollection.getScene().getWindow();
    	window.setScene(new Scene(root, 600, 400));
    }
}