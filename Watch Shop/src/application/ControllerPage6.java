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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
/**
 * controls page6 where ONLY admin can access and he can visualize the attributes of the watch selected in page2 and he can decide to modify
 */
public class ControllerPage6 {
	
	@FXML
    private ImageView image;
	
	@FXML
	private Button btn_close;
	
	@FXML
	private Button btn_logout;
	
	@FXML
	private AnchorPane sidePane;
    
    @FXML
    private Button btn_account;
    
    @FXML
    private Button btn_buy;
    
    @FXML
    private Label label_account;
	
	@FXML
    private Label label;
	
	@FXML
    private ListView<String> listView;
	
	@FXML
    private Button btn_addwatch;
	
	@FXML
    private Button btn_back;
	
	@FXML
	private Button btn_modify;
	
    @FXML
    private Label label_accountbalance;
	
	@FXML
	private Button btn_mycollection;
	
	public watch watchsel;
	static boolean open_slide = false;
	public ObservableList<String> list = FXCollections.observableArrayList();
	
	/**
	 * /**
	 * it takes the watch that has been selected in the page2 and saves it in this page
	 * @param w
	 */
	public void getSelectedwatch(watch w)
	{
		watchsel = w;	
		list.addAll("category: "+w.getCategory(), "Diameter: "+Double.toString(w.getDiameter()), "Movement: "+w.getMovement(), "Price: "+Double.toString(w.getPrice()));
		Image myimage = new Image(getClass().getResourceAsStream(watchsel.getImage()));
		image.setImage(myimage);
		label.setText(watchsel.getDescription());
	}
	/**
	 * initializes the list view with the attributes of the watch selected
	 */
	@FXML
	public void initialize()
	{
		listView.setItems(list);
	}
	/**
     * if the botton btn_logout is clicked, it changes page to pag1 for the login
     * @param Event
     * @throws IOException
     */
	@FXML
    public void switchtoLoginPage(ActionEvent Event) throws IOException
    {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("pag1.fxml"));
		Parent root = loader.load();
		Stage window = (Stage) btn_logout.getScene().getWindow();
		window.setScene(new Scene(root, 600, 400));
    }
	/**
     * if the botton btn_addwatch is clicked, it changes page to addwatchpage. this botton is present only if the account is admin
     * @param Event
     * @throws IOException
     */
	@FXML
    public void switchtoAddWatchPage(ActionEvent Event) throws IOException
    {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("addwatchpage.fxml"));
		Parent root = loader.load();
		Stage window = (Stage) btn_addwatch.getScene().getWindow();
		window.setScene(new Scene(root, 600, 400));
    }
	/**
	 * if the botton "modify" is clicked, we go to the pag5 where we can modify the attributes of the watch selected
	 * @param Event
	 * @throws IOException
	 */
	@FXML
	public void switchtomodify(ActionEvent Event) throws IOException
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("pag5.fxml"));
		Parent root = loader.load();
		Stage window = (Stage) btn_addwatch.getScene().getWindow();
		window.setScene(new Scene(root, 600, 400));
		ControllerPage5 controllerpage5 = loader.getController();
		controllerpage5.getSelectedwatch(watchsel);
	}
	/**
     * if the button btn_close is clicked, it closes the application
     * @param Event
     */
    @FXML
    public void close(ActionEvent Event)
    {
    	Stage stage = (Stage) btn_close.getScene().getWindow();
		stage.close();
    }
    /**
     * if the botton is clicked, we return to page2
     * @param Event
     * @throws IOException
     */
    @FXML
    public void backpage(ActionEvent Event) throws IOException
    {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("pag2.fxml"));
		Parent root = loader.load();
		Stage window = (Stage) btn_back.getScene().getWindow();
		window.setScene(new Scene(root, 600, 400));
    }
    /**
     * if the botton "Account" is clicked, it creates a side pane on the right
     */
    public void createsidePane() {
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
				btn_addwatch.setDisable(true);
				btn_addwatch.setVisible(false);
			}
		}
	}
}