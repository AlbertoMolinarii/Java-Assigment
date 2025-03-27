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
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
/**
 * the class {@code MyOrderController} manages my order page.
 */
public class MyOrderController {

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
	private Button btn_refund;

	@FXML
	private Text txt_account;

	@FXML
	private TableColumn<product, Integer> id;

	@FXML
	private TableColumn<product, String> nome;

	@FXML
	private TableColumn<product, Double> prezzo;

	@FXML
	private TableColumn<product, Integer> quantità;

	@FXML
	private TableView<product> table_view;
	
	@FXML
    private Spinner<Integer> spinner_num;

	User user = new User("Agostino", "Poggi", "agostino.poggi@unipr.it", "agostino");
	boolean open_slide = false;
	ObjectInputStream  is2;
	ObjectOutputStream  os2;
	Message m =null;
	boolean prova = true;
	public ObservableList<product> col = FXCollections.observableArrayList();
	public List<product> myorders = new ArrayList<>();
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
     * The method open_shopping_list opens shopping_list's page.
     */
	@FXML
	void open_shopping_list() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("logged_in.fxml"));
		Parent root = loader.load();
		Stage window = (Stage) btn_shopping_list.getScene().getWindow();
		window.setScene(new Scene(root, 600, 400));

		LoggedInController loggedincontroller = loader.getController();
		loggedincontroller.getstream(os2, is2);
		loggedincontroller.get_user(user);
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
	/**
     * The method setOrder set account's item in the tableview.
     * @param user
     */
	public void setOrder(User user) 
	{
		myorders = user.getMyOrder();
		for(product i: myorders) 
		{
			if (i.getQuantity() != 0)
			{
				col.add(i);
			}
		}
		nome.setCellValueFactory(new PropertyValueFactory<product, String>("Name"));
		prezzo.setCellValueFactory(new PropertyValueFactory<product, Double>("Price"));
		id.setCellValueFactory(new PropertyValueFactory<product, Integer>("Id"));
		quantità.setCellValueFactory(new PropertyValueFactory<product, Integer>("Quantity"));

		table_view.setItems(col);
	}
	
	public int n = 0;
    product p; 
    List<product> productrefund = new ArrayList<product>();
    /**
     * The method rowsclicked set the selected product.
     */
    public void rowsclicked(MouseEvent event) throws IOException
    {	
    	int num = 0;
    	try 
    	{
    		p = table_view.getSelectionModel().getSelectedItem();
    		num = p.getQuantity();

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
     * The method refund refunds the selected item to the server.
     */
    public void refund(ActionEvent event) throws IOException, ClassNotFoundException
    {	
    	p.Buy(n);
    	Message mes = new Message(user,"refund " + p.getId() + " " + n);
    	os2.writeObject(mes);
		os2.flush();
		Object o = is2.readObject();
		open_shopping_list();
    }

}
