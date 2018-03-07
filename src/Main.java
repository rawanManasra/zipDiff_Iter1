
import java.io.IOException;
import java.net.URL;

import Helpers.Module.AppConstants;
import Helpers.Module.logfile;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {
	logfile log = new logfile(AppConstants.TURN_ON);

	@Override
	public void start(Stage primaryStage) throws IOException {
		URL url = getClass().getResource("ZipDiffTool.fxml");
		AnchorPane pane = FXMLLoader.load(url);
		Scene scene = new Scene(pane);

		primaryStage.setScene(scene);
		primaryStage.setTitle("Zip Diff");
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void stop() throws Exception {
		// TODO Auto-generated method stub
		logfile log = new logfile(AppConstants.TURN_OFF);

		super.stop();
	}
}
