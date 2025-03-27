package application;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;


/**
 * the class {@code LoggedInController} manages the logged in page.
 */

public class LoggedInController {

	@FXML
    private TableView<product> tableView;
    
    @FXML
    private TableColumn<product, Integer> IdColumn;

    @FXML
    private TableColumn<product, String> NameColumn;

    @FXML
    private TableColumn<product, Double> PriceColumn;

    @FXML
    private TableColumn<product, Integer> QuantityColumn;
    
    @FXML
    private Button btn_refresh;

    @FXML
    private Button btn_account;
    
    @FXML
    private Button btn_log_out;
    
    @FXML
    private AnchorPane slide_pane;
    
    @FXML
    private Button btn_close;
	
    @FXML
    private Button btn_my_orders;

    @FXML
    private Button btn_shopping_list;
    
    @FXML
    private AnchorPane principal_pane;

    @FXML
    private Text txt_account;
    
    @FXML
    private TextField txt_id;

    @FXML
    private TextField txt_name;

    @FXML
    private TextField txt_price;

    @FXML
    private TextField txt_quantity;
    
    @FXML
    private Button btn_add;
    
    @FXML
    private Spinner<Integer> spinner_num;
    
    @FXML
    private Button btn_addtolist;
    
    User user = new User("Agostino", "Poggi", "agostino.poggi@unipr.it", "agostino");
    Message m =null;
    boolean prova = true;
    boolean open_slide = false;
    ObjectInputStream  is2;
    ObjectOutputStream  os2;
    public String[] product_list = {};
    public ObservableList<product> col = FXCollections.observableArrayList();
    
    /**
     * the method getsream set the client input and output.
     * @param server output
     * @param server input
     **/
    
    public void getstream(ObjectOutputStream os1, ObjectInputStream  is1)
    {
    	os2 = os1;
    	is2 = is1;
    }
    /**
     * The method get_user set the user;
     * @param user
     **/
    public void get_user(User new_user) {user = new_user; m =  new Message(user, "");}
    /**
     * The method initialize set the tableview's column values.
     **/
    public void initialize() throws ClassNotFoundException, IOException 
    {
    	NameColumn.setCellValueFactory(new PropertyValueFactory<product, String>("Name"));
		PriceColumn.setCellValueFactory(new PropertyValueFactory<product, Double>("Price"));
		IdColumn.setCellValueFactory(new PropertyValueFactory<product, Integer>("Id"));
		QuantityColumn.setCellValueFactory(new PropertyValueFactory<product, Integer>("Quantity"));
    }
    /**
     * The method show_product show the product in the table view
     */
    public void show_product() throws IOException, ClassNotFoundException
    {
    	tableView.getItems().clear();
    	Message mes = new Message(user, "show product");
    	System.out.format(" Client sends: %s to Server", mes.getContent());
    	while(true) {
    		if(prova) {
			os2.writeObject(mes);
			os2.flush();
			prova = false;
		}
        	Object o = is2.readObject();
        	if ((o != null) && (o instanceof Message))
			{
				Message n = (Message) o;
				
				if (n.getContent().equals("end"))
				{
					break;
				}
				if (n.getContent().equals("done"))
				{
					prova = true;
					break;
				}
				product_list = n.getContent().split(",");
				product new_product = new product(product_list[0], Double.parseDouble(product_list[1]), Integer.parseInt(product_list[2]), Integer.parseInt(product_list[3]));
				
				col.add(new_product);				
		        tableView.setItems(col);
				
				continue;

			}
        	
    	}
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
    
    /**
     * the method log_out does the log out from the current user.
     */
    @FXML
    void log_out(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("mainscene.fxml"));
    	Parent root = loader.load();
    	Stage window = (Stage) btn_log_out.getScene().getWindow();
    	window.setScene(new Scene(root, 600, 400));
    	
    	ShopController shopcontroller = loader.getController();
    	boolean tappo = false;
    	shopcontroller.getstart(tappo);
    	shopcontroller.getstream(os2, is2);
    }
    /**
     * The method open_my_orders open my_orders' page.
     */
    @FXML
    void open_my_orders(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("myorder.fxml"));
		Parent root = loader.load();
		Stage window = (Stage) btn_my_orders.getScene().getWindow();
		window.setScene(new Scene(root, 600, 400));

		MyOrderController myordercontroller = loader.getController();
		myordercontroller.setOrder(m.getUser());
		myordercontroller.getstream(os2, is2);
		myordercontroller.get_user(user);
    }
    /**
     * The method open_shopping_list opens shopping_list's page.
     */
    @FXML
    void open_shopping_list() throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("shoppinglist.fxml"));
    	Parent root = loader.load();
    	Stage window = (Stage) btn_shopping_list.getScene().getWindow();
    	window.setScene(new Scene(root, 600, 400));
    	
    	ShoppingListController shoppinglistcontroller = loader.getController();
    	shoppinglistcontroller.getSelectedItems(productselected);
    	shoppinglistcontroller.getstream(os2, is2);
    	shoppinglistcontroller.get_user(user);
    }
    /**
     * The method close_account closes the lateral menu.
     */
    @FXML
    void close_account(MouseEvent event) {
    	if(open_slide) 
    	{
    		TranslateTransition slide = new TranslateTransition();
        	slide_pane.setTranslateX(-200);
        	slide.setNode(slide_pane);
        	slide.setToX(1000);
        	slide.play();
        	open_slide = false;
    	}
    }
    /**
     * The method open_account opens the lateral menu.
     */
    @FXML
    void open_account(ActionEvent event) {
    	
    	if(open_slide) 
    	{
    		TranslateTransition slide = new TranslateTransition();
        	slide_pane.setTranslateX(0);
        	slide.setNode(slide_pane);
        	slide.setToX(1000);
        	slide.play();
        	open_slide = false;
    	}
    	else 
    	{
        	TranslateTransition slide = new TranslateTransition();
        	slide_pane.setTranslateX(1000);
        	slide.setNode(slide_pane);
        	slide.setToX(-200);
        	slide.play();
        	open_slide = true;
        	txt_account.setText(m.getUser().getAddress());
    	}
    	
    }
    /**
     * The method add_products adds new product to the server product's list.
     */
    @FXML
    public void add_products(ActionEvent event) throws IOException, ClassNotFoundException
    {
    	Message mes = new Message(user, "new product "+ txt_name.getText()+" "+txt_price.getText()+" "+txt_id.getText()+" "+txt_quantity.getText());
    	System.out.format(" Client sends: %s to Server", mes.getContent());
    	os2.writeObject(mes);
		os2.flush();
		Object o = is2.readObject();
		show_product();
    	txt_name.clear();
		txt_id.clear();
		txt_price.clear();
		txt_quantity.clear();
    }
    
    public int n = 0;
    product p; 
    List<product> productselected = new ArrayList<product>();
    /**
     * The method rowsclicked set the selected product.
     */
    public void rowsclicked(MouseEvent event) throws IOException
    {	
    	int num = 0;
    	try 
    	{
    		p = tableView.getSelectionModel().getSelectedItem();
    		num = p.getQuantity();
    		if (productselected.contains(p) == false)
    		{
    			productselected.add(p);
    		}
    		System.out.println(p.getName().getClass().getTypeName());

    		SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, num);
    		valueFactory.setValue(1);
    		spinner_num.setValueFactory(valueFactory);
    		n = spinner_num.getValue();

    		spinner_num.valueProperty().addListener(new ChangeListener<Integer>() {

    			@Override
    			public void changed(ObservableValue<? extends Integer> arg0, Integer arg1, Integer arg2) {
    				n = spinner_num.getValue();
    			}
    		});	
    	} catch(NullPointerException ex) {}
    	
    	
    }
    /**
     * The method addtoshoplist adds to the shop list the product set by the rowsclicked.
     * @throws IOException 
     */
    public void addtoshoplist(ActionEvent event) throws IOException
    {	
    	p.setQuantity(n);
    	open_shopping_list();
    }
    
}


