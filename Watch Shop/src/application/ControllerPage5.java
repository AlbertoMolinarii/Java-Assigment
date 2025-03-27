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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
/**
 * controls page5 where ONLY the admin can access and he can modify the attributes of the watch selected
 */
public class ControllerPage5 {

    @FXML
    private Button btn_account;

    @FXML
    private Button btn_addwatch;

    @FXML
    private Button btn_close;

    @FXML
    private Button btn_logout;

    @FXML
    private Button btn_mycollection;

    @FXML
    private Button btn_confirm;
    
    @FXML
    private Button btn_save;

    @FXML
    private ImageView image;

    @FXML
    private Label label_account;

    @FXML
    private Label label_accountbalance;

    @FXML
    private ListView<?> listView;

    @FXML
    private AnchorPane sidePane;

    @FXML
    private TextArea txt_description;
    
    @FXML
    private TextField txt_movement;
    @FXML
    private TextField txt_diameter;
    @FXML
    private TextField txt_category;
    @FXML
    private TextField txt_price;
    
    public watch watchsel;
	static boolean open_slide = false;
	public ObservableList<String> list = FXCollections.observableArrayList();
    
	/**
	 * it takes the watch that we want to modify from page6 
	 * @param w
	 */
    public void getSelectedwatch(watch w)
	{
		watchsel = w;	
		list.addAll("category: "+w.getCategory(), "Diameter: "+Double.toString(w.getDiameter()), "Movement: "+w.getMovement(), "Price: "+Double.toString(w.getPrice()));
		Image myimage = new Image(getClass().getResourceAsStream(watchsel.getImage()));
		image.setImage(myimage);
		txt_description.setText(watchsel.getDescription());
		txt_category.setText(watchsel.getCategory());
		txt_diameter.setText(watchsel.getDiameter().toString());
		txt_movement.setText(watchsel.getMovement());
		txt_price.setText(watchsel.getPrice().toString());
	}
    /**
     * does a query that modify the watch selected. in page5 we select what we want to modify
     * @param event
     * @throws SQLException
     * @throws IOException
     */
    @FXML
    void modify(ActionEvent event) throws SQLException, IOException {
    	
    	String s = "UPDATE watch SET description = '" + txt_description.getText() + "' WHERE category = '" + watchsel.getCategory() + "' AND model = '" + watchsel.getModel() + "'";
    	PreparedStatement ps = WatchShopController.conn.prepareStatement(s);
    	ps.executeUpdate();
    	
    	s = "UPDATE watch SET category = '" + txt_category.getText() + "' WHERE category = '" + watchsel.getCategory() + "' AND model = '" + watchsel.getModel() + "'";
    	ps = WatchShopController.conn.prepareStatement(s);
    	ps.executeUpdate();
    	
    	s = "UPDATE watch SET diameter = '" + txt_diameter.getText() + "' WHERE category = '" + watchsel.getCategory() + "' AND model = '" + watchsel.getModel() + "'";
    	ps = WatchShopController.conn.prepareStatement(s);
    	ps.executeUpdate();
    	
    	s = "UPDATE watch SET movement = '" + txt_movement.getText() + "' WHERE category = '" + watchsel.getCategory() + "' AND model = '" + watchsel.getModel() + "'";
    	ps = WatchShopController.conn.prepareStatement(s);
    	ps.executeUpdate();
    	
    	s = "UPDATE watch SET price = '" + txt_price.getText() + "' WHERE category = '" + watchsel.getCategory() + "' AND model = '" + watchsel.getModel() + "'";
    	ps = WatchShopController.conn.prepareStatement(s);
    	ps.executeUpdate();
    	
    	returntopage3();
    }
    /**
     * if we click btn_save, we go back to page2 and the changes are made
     * @throws IOException
     */
    @FXML
    public void returntopage3() throws IOException
    {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("pag2.fxml"));
		Parent root = loader.load();
		Stage window = (Stage) btn_save.getScene().getWindow();
		window.setScene(new Scene(root, 600, 400));
    }

}