import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;

public class ZipDiffToolController {

    @FXML
    private Button DiffCreationBtn;

    @FXML
    private Button ZipComparisonButton;

    @FXML
    private Button PatchCreationBtn;

    
    private void set_scene(ActionEvent event ,String Resource) throws IOException {
		Parent home_parent=FXMLLoader.load(getClass().getResource(Resource));
		Scene home_scene= new Scene(home_parent);
		Stage home_stage=(Stage) ((Node) event.getSource()).getScene().getWindow();
		home_stage.hide();
		home_stage.setScene(home_scene);
		home_stage.show();
		}
    
    @FXML
    void ZipComparisonAct(ActionEvent event) throws IOException{
    	String Resource ="ZipComparison.fxml";
		set_scene(event,Resource);
    	
    }

    @FXML
    void DiffCreationAct(ActionEvent event) throws IOException{
    	String Resource ="DiffCreationModule.fxml";
		set_scene(event,Resource);
    	
    }

    @FXML
    void PatchCreationAndApplicationAct(ActionEvent event) throws IOException{
    	String Resource ="PatchCreation.fxml";
		set_scene(event,Resource);
    	
    }

}
