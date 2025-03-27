package application;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
/**
 * ControllerPage2 controls the second page that creates a table for the watchshop
 */
public class ControllerPage2 {

    @FXML
	private AnchorPane sidePane;
    
    @FXML
    private Button btn_account;
    
    @FXML
    private Label label_account;
	
	@FXML
    private Button btn_switchpage3;
	
	@FXML
    private Button btn_addwatch;

	@FXML
    private TableView<watch> tableView;
    
    @FXML
    private TableColumn<watch, String> column_brand;

    @FXML
    private TableColumn<watch, String> column_model; 
    
	@FXML
	private Button btn_close;
	
	@FXML
	private Button btn_logout;
	
	@FXML
	private Button btn_mycollection;
	
	@FXML
	private TextField filterField;
    
    static boolean open_slide = false;
    
    public ObservableList<watch> columns = FXCollections.observableArrayList();
    
    public List<watch> watchselected = new ArrayList<watch>();
    
    public watch watchsel;
    /**
     * creates the table with the watches that are presented in the shop
     * @throws SQLException
     */
    @FXML
    public void createtable() throws SQLException
    {  
    	List<watch> watches = new ArrayList<>();
    	watches = WatchShopController.setwatch();
    	for (int i = 0; i < watches.size(); i++)
    	{
    		columns.add(watches.get(i));
    	}
    	FilteredList<watch> filteredData = new FilteredList<>(columns, p -> true);
    	filterField.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(watch -> {
				// If filter text is empty, display all persons.
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				
				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();
				
				if (watch.getBrand().toLowerCase().contains(lowerCaseFilter)) {
					return true; // Filter matches first name.
				} else if (watch.getModel().toLowerCase().contains(lowerCaseFilter)) {
					return true; // Filter matches last name.
				}
				return false; // Does not match.
			});
		});
    	
    	SortedList<watch> sortedData = new SortedList<>(filteredData);
		
		// 4. Bind the SortedList comparator to the TableView comparator.
		sortedData.comparatorProperty().bind(tableView.comparatorProperty());
    	
    	
    	tableView.setItems(sortedData);
    }
    /**
     * initializes the page with the tableview
     * @throws SQLException
     */
    @FXML
    public void initialize() throws SQLException
    {
    	column_brand.setCellValueFactory(new PropertyValueFactory<watch, String>("Brand"));
    	column_model.setCellValueFactory(new PropertyValueFactory<watch, String>("Model"));
    	
    	createtable();
    }
	/**
	 * if a row is clicked, the watch in that row is saved and changes the page to page3
	 * @param Event
	 */
    @FXML
    public void rowsclicked(MouseEvent Event) 
    {
    	watchsel = tableView.getSelectionModel().getSelectedItem();
    	
    	if (watchselected.contains(watchsel) == false)
    	{
    		watchselected.add(watchsel);
    	}
    	
    	if(watchsel != null) 
    	{
    		btn_switchpage3.fire();
    	}
    }
    /**
     * if the botton btn_switchpage3 is clicked, it changes page to page6, if the account is admin, or to page3, if the account is user
     * @param Event
     * @throws IOException
     */
    @FXML
    public void switchpage(ActionEvent Event) throws IOException
    {
    	if(WatchShopController.account_admin) 
		{
			FXMLLoader loader = new FXMLLoader(getClass().getResource("pag6.fxml"));
			Parent root = loader.load();
			Stage window = (Stage) btn_addwatch.getScene().getWindow();
			window.setScene(new Scene(root, 600, 400));
			ControllerPage6 controllerpage6 = loader.getController();
			controllerpage6.getSelectedwatch(watchsel);
		}
		else 
		{
			FXMLLoader loader = new FXMLLoader(getClass().getResource("pag3.fxml"));
			Parent root = loader.load();
			Stage window = (Stage) btn_addwatch.getScene().getWindow();
			window.setScene(new Scene(root, 600, 400));
			ControllerPage3 controllerpage3 = loader.getController();
			controllerpage3.getSelectedwatch(watchsel);
		}
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
     * if the botton btn_close is clicked, it closes the application
     * @param Event
     */
    @FXML
    public void close(ActionEvent Event)
    {
    	Stage stage = (Stage) btn_close.getScene().getWindow();
		stage.close();
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
			if (WatchShopController.account_admin)
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
     * if the botton btn_addwatch is clicked, it changes page to pag4 that has the collection of our watches
     * @param Event
     * @throws IOException
     */
    @FXML
    public void switchtomycollection(ActionEvent Event) throws IOException
    {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("pag4.fxml"));
    	Parent root = loader.load();
    	Stage window = (Stage) btn_addwatch.getScene().getWindow();
    	window.setScene(new Scene(root, 600, 400));
    }
    
}