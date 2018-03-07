import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Helpers.Module.AppConstants;
import Helpers.Module.logfile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import patchCreationAndApplication.patchApplication;

public class PatchApplicationController implements Initializable {
	public String name1;
	public String name2;
	public String path;

	@FXML
	private Button OkBtn;

	@FXML
	private Button CancelBtn;

	@FXML
	private TextField firstList;

	@FXML
	private Label label1;

	@FXML
	void OkBtnAction(ActionEvent event) throws IOException {
		patchApplication pa = new patchApplication(name1, name2, path, firstList.getText());
		logfile log = new logfile(AppConstants.PATCH_APPLICATION + ": " + firstList.getText());
		System.out.println(firstList.getText());
		if (pa.ApplyPatch())
			label1.setText("Patch applicaton was performed");
		else
			label1.setText("This archive and the first archive in " + System.getProperty(AppConstants.NEW_LINE)
					+ "the previous stage are not content " + System.getProperty(AppConstants.NEW_LINE)
					+ "metadata path identical.");
	}

	@FXML
	void CancelBtnAction(ActionEvent event) throws IOException {
		String Resource = "ZipDiffTool.fxml";
		set_scene(event, Resource);

	}

	@FXML
	void BrowseBtn1Action(ActionEvent event) throws IOException {
		FileChooser fc = new FileChooser();
		fc.setInitialDirectory(new File("C:\\"));
		fc.getExtensionFilters().addAll(new ExtensionFilter("ZIP Files", "*.zip"));
		File selectedFile = fc.showOpenDialog(null);
		if (selectedFile != null) {
			firstList.setText(selectedFile.getAbsolutePath());
			if (!firstList.getText().equals(""))
				OkBtn.setDisable(false);
		} else {
			System.out.println("file is not valid !");
		}
	}

	private void set_scene(ActionEvent event, String Resource) throws IOException {
		Parent home_parent = FXMLLoader.load(getClass().getResource(Resource));
		Scene home_scene = new Scene(home_parent);
		Stage home_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		home_stage.hide();
		home_stage.setScene(home_scene);
		home_stage.show();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

	public void func(String n1, String n2, String n3) {
		name1 = n1;
		name2 = n2;
		path = n3;
		// TODO Auto-generated method stub

	}
}
