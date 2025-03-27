package application;

import java.io.IOException;
import java.sql.SQLException;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class WatchShopView {
	
	@FXML
	private Button btn_login;
    
    @FXML
    private TextField txt_name;

	@FXML
    private TextField txt_password;
    
    /**
     * da qua iniziano le var di new user
     */
    @FXML
    private Button btn_adduser;

    @FXML
    private TextField txt_newuaddress;

    @FXML
    private TextField txt_newuname;

    @FXML
    private TextField txt_newupassword;

    @FXML
    private TextField txt_newusurname;
    
    @FXML
    private Button btn_account;
    
    @FXML
    private Button btn_back;

    @FXML
    private Button btn_page2;

    @FXML
    private Label label_account;

    @FXML
    private AnchorPane sidePane;

    @FXML
    private TextField txt_brand;

    @FXML
    private TextField txt_category;

    @FXML
    private TextArea txt_description;

    @FXML
    private TextField txt_diameter;

    @FXML
    private TextField txt_image;

    @FXML
    private TextField txt_model;

    @FXML
    private TextField txt_movement;

    @FXML
    private TextField txt_price;
    
    static boolean open_slide = false;
    
    /**
     * this function called the login function in WatchShopController to do the login
     * @param Event
     * @throws SQLException
     * @throws IOException
     */
    @FXML
    void login(ActionEvent Event) throws SQLException, IOException 
    {
    	if (WatchShopController.login(txt_name.getText(), txt_password.getText())) 
    	{
    		switch_page("pag2.fxml", Event);
    	}
    }
    /**
     * if the button is clicked it changes page
     * @param page
     * @param Event
     * @throws IOException
     */
    void switch_page(String page, ActionEvent Event) throws IOException 
    {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource(page));
    	Parent root = loader.load();
    	Stage stage = (Stage)((Node)Event.getSource()).getScene().getWindow();
    	Scene scene = new Scene(root);
    	stage.setScene(scene);
    	stage.show();
    }
    /**
     * if you are a new user, you have to insert your information and then you go to page1 for the login if all the information are correct. it called the function newuser in WatchShopController
     * @param Event
     * @throws SQLException
     * @throws IOException
     */
    @FXML
    void newuser(ActionEvent Event) throws SQLException, IOException 
    {
    	if (WatchShopController.newuser(txt_newuname.getText(), txt_newusurname.getText(),  txt_newuaddress.getText(),  txt_newupassword.getText())) 
    	{
    		switch_page("pag1.fxml", Event);
    	}
    }
    /**
     * if the admin wants to insert a new watch. it called the function in WatchSopController and then we go to page2 to see the watches in the shop
     * @param Event
     * @throws SQLException
     * @throws IOException
     */
    @FXML
    void newwatch(ActionEvent Event) throws SQLException, IOException
    {
    	if (WatchShopController.newwatch(txt_brand.getText(), txt_model.getText(), txt_movement.getText(), txt_diameter.getText(), txt_category.getText(), txt_price.getText(), txt_image.getText(), txt_description.getText()))
    	{
    		switch_page("pag2.fxml", Event);
    	}
    }
    /**
     * if the button is clicked we go to newuserpage to create a new user
     * @param Event
     * @throws IOException
     */
    @FXML
    public void newuserpage(ActionEvent Event) throws IOException
    {
    	switch_page("newuserpage.fxml", Event);
    }
    /**
     * if we click the arrow, we return to page2
     * @param Event
     * @throws IOException
     * @throws SQLException
     */
    @FXML
    public void back(ActionEvent Event) throws IOException, SQLException
    {
    	switch_page("pag2.fxml", Event);
    }
    
}