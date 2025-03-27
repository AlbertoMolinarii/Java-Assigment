package application;
/*
 * Luca Foramacchi mat: 333060
 * Alberto Molinari mat: 330698	
 */
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
/*
 * the class {@code Main} starts the application
 */
public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("mainscene.fxml"));

			Scene scene = new Scene(root, 600, 400);
			primaryStage.setTitle("Negozio online");
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
