import java.io.File;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class PatchApplicationController {

    @FXML
    private Button OkBtn;

    @FXML
    private Button CancelBtn;

    @FXML 
    private TextField firstList ;
    
    @FXML
    void OkBtnAction(ActionEvent event)throws IOException {
///add class function
    }

    @FXML
    void CancelBtnAction(ActionEvent event) throws IOException{
    	String Resource="PatchCreation.fxml";
    	set_scene(event,Resource);

    }
    @FXML
    void BrowseBtn1Action(ActionEvent event) throws IOException{
    	FileChooser fc=new FileChooser();
    	fc.setInitialDirectory(new File("C:\\"));
    	fc.getExtensionFilters().addAll(new ExtensionFilter("ZIP Files","*.zip"));
    	File selectedFile=fc.showOpenDialog(null);
    	if(selectedFile!=null)
    	{
    		firstList.setText(selectedFile.getAbsolutePath());
    		//firstList.getItems().add(selectedFile.getAbsolutePath());
    		
    		if(!firstList.getText().equals(""))
    			OkBtn.setDisable(false);
    	}
    	else {
    		System.out.println("file is not valid !");
    	}
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
