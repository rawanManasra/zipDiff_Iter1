import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ContToPatchApplicationController {

    @FXML
    private Button NoBtn;

    @FXML
    private Button YesBtn;

    @FXML
    void YesBtnAction(ActionEvent event)throws IOException {
    	String Resource="PatchApplication.fxml";
    	set_scene(event,Resource);
    }

    @FXML
    void NoBtnAction(ActionEvent event) {
    	 Stage stage = (Stage) NoBtn.getScene().getWindow();
    	 stage.close();

    }
    private void set_scene(ActionEvent event ,String Resource) throws IOException {
		Parent home_parent=FXMLLoader.load(getClass().getResource(Resource));
		Scene home_scene= new Scene(home_parent);
		Stage home_stage=(Stage) ((Node) event.getSource()).getScene().getWindow();
		home_stage.hide();
		home_stage.setScene(home_scene);
		home_stage.show();
		}
}
