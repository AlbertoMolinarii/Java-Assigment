package application;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
/**
 * the class {@code ShoppingListController} manages the shopping list page.
 */
public class ShoppingListController {

	@FXML
	private TableColumn<product, Integer> IdColumn;

	@FXML
	private TableColumn<product, String> NameColumn;

	@FXML
	private TableColumn<product, Double> PriceColumn;

	@FXML
	private TableColumn<product, Integer> QuantityColumn;

	@FXML
	private Button btn_buy;

	@FXML
	private TableView<product> tableView;

	@FXML
	private AnchorPane slide_pane;

	@FXML
	private Button btn_account;

	@FXML
	private Text txt_account;

	@FXML
	private Button btn_close;

	@FXML
	private Button btn_my_orders;

	@FXML
	private Button btn_shopping_list;

	@FXML
	private Button btn_log_out;

	User user = new User("Agostino", "Poggi", "agostino.poggi@unipr.it", "agostino");
	Message m =null;
	boolean prova = true;
	public ObservableList<product> col = FXCollections.observableArrayList();
	public product p;
	public ObjectInputStream  is2;
	public ObjectOutputStream  os2;
	public List<product> my_order = new ArrayList<>();
	boolean open_slide = false;
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
     * The method getSelectedItems set the selected items in the tableview.
     * @param It's the list of item selected from logged in page.
     */
	public void getSelectedItems(List<product> item)
	{
		for (int i = 0; i < item.size(); i++)
		{
			p = item.get(i);
			col.add(p);
			my_order.add(item.get(i));
		}


		NameColumn.setCellValueFactory(new PropertyValueFactory<product, String>("Name"));
		PriceColumn.setCellValueFactory(new PropertyValueFactory<product, Double>("Price"));
		IdColumn.setCellValueFactory(new PropertyValueFactory<product, Integer>("Id"));
		QuantityColumn.setCellValueFactory(new PropertyValueFactory<product, Integer>("Quantity"));

		tableView.setItems(col);
	}
	/**
     * The method btnbuypressed buy the item from the shopping list and set them in the account's my orders list, then opens the my orders page.
     */
	@FXML
	void btnbuypressed() throws IOException, ClassNotFoundException
	{
		
		if(my_order.size()==0) {return;}
		for(product i:my_order) 
		{
			m.setContent("buy " + i.getId() +" " + i.getQuantity());
			while(true) {
	    		if(prova) 
	    		{	
				os2.writeObject(m);
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
					continue;

				}
			}
			
		}
		m.getUser().newMyordersList(my_order);
		open_myorder();
	}
	/**
     * The method open_my_orders open my_orders' page.
     */
	@FXML
	public void open_myorder() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("myorder.fxml"));
		Parent root = loader.load();
		Stage window = (Stage) btn_buy.getScene().getWindow();
		window.setScene(new Scene(root, 600, 400));

		MyOrderController myordercontroller = loader.getController();
		myordercontroller.setOrder(m.getUser());
		myordercontroller.getstream(os2, is2);
		myordercontroller.get_user(user);
	}
	/**
     * The method get_user set the user;
     * @param user
     */
	public void get_user(User new_user) {user = new_user; m =  new Message(user, "");}
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
     * The method close_account closes the lateral menu.
     */
	@FXML
	void close_account(MouseEvent event) {
		//prevale su quella di open account, si potrebbe quindi levare quella di open account
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
}