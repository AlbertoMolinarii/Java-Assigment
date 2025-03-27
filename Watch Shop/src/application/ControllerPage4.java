package application;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
/**
 * controls page4 for seeing the watches the are possessed by the user
 */
public class ControllerPage4 {
	@FXML
    private AnchorPane anchor_pane;

    @FXML
    private Button btn_account;

    @FXML
    private Button btn_close;
    
    @FXML
	private Button btn_refill;

    @FXML
    private Button btn_logout;
    
    @FXML
    private Button btn_total;

    @FXML
    private TableColumn<watch, String> column_brand;

    @FXML
    private TableColumn<watch, String> column_model;

    @FXML
    private Label label_account;
    
    @FXML
    private Label label_totalprice;
    
    @FXML
    private Label label_accountbalance;

    @FXML
    private AnchorPane sidePane;
    
    @FXML
    private Button btn_back;

    @FXML
    private TableView<watch> tableView;


    public List<watch> watchselected = new ArrayList<watch>();
    
    public ObservableList<watch> columns = FXCollections.observableArrayList();
    
    public watch watchsel;
    
    public double total = 0.0;
    
    static boolean open_slide = false;
    
    /**
     * if the button btn_close is clicked, it closes the application
     * @param Event
     */
    @FXML
    void close(ActionEvent event) {
    	Stage stage = (Stage) btn_close.getScene().getWindow();
		stage.close();
    }
    /**
     * if the button "Account" is clicked, it creates a side pane on the right
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
     * it creates a table and it does a query for searching the watch that are possessed by the user that is logged in
     * @throws SQLException
     */
    @FXML
    public void createtable() throws SQLException
    {  
    	List<watch> watches = new ArrayList<>();
    	try 
		{ 
			PreparedStatement ps = WatchShopController.conn.prepareStatement("SELECT * FROM watch w JOIN user u ON w.possessed = u.name");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) 
	    	{	
				String brand = rs.getString("brand");
    			String model = rs.getString("model");
    			String movement = rs.getString("movement");
    			Double diameter = rs.getDouble("diameter");
    			String category = rs.getString("category");
    			Double price = rs.getDouble("price");
    			String image = rs.getString("image");
    			String description = rs.getString("description");
    			String possessed = rs.getString("possessed");
    			watch newatch = new watch(brand, model, movement, diameter, category, price, image, description, possessed);
    			watches.add(newatch);
    			total += price;
	    	}
			
			for (int i = 0; i < watches.size(); i++)
	    	{
	    		columns.add(watches.get(i));
	    	}
	    	tableView.setItems(columns);
			
		}
		catch (SQLException ex) 
		{
			ex.printStackTrace();
		}
    	
    }
    /**
     * does the total of the price of the watch that we possesse
     */
    @FXML
    void showtotal()
    {	
    	label_totalprice.setText(Double.toString(total) + " $");
    }
    /**
     * initializes the table and the label with the total
     * @throws SQLException
     */
    @FXML
	public void initialize() throws SQLException
	{
    	column_brand.setCellValueFactory(new PropertyValueFactory<watch, String>("Brand"));
    	column_model.setCellValueFactory(new PropertyValueFactory<watch, String>("Model"));
    	
    	createtable();
    	showtotal();
	}
	
}