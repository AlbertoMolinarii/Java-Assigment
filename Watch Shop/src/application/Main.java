package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

/**
 * @author Alberto Molinari 330698 
 * @author Luca Foramacchi 333060
 * loads the page1.fxml and starts the application
 * */
public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("pag1.fxml"));

			Scene scene = new Scene(root, 594, 397);
			primaryStage.setScene(scene);
			primaryStage.show();
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		launch(args);
		
	}
}
