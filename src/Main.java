
import java.io.IOException;

import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws IOException{
	
		URL url =getClass().getResource("ZipDiffTool.fxml");
		AnchorPane pane=FXMLLoader.load(url);
		Scene scene =new Scene (pane);
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("Zip Diff Tool");
		primaryStage.show();
	}
		
	
	public static void main(String[] args) {
		launch(args);
	}
}
