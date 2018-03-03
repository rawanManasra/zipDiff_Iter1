import java.io.File;
import java.io.IOException;

import ZipDiffCreation.Module.ZipDiffCreation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class DiffCreationModuleController {

	@FXML
	private TextField firstList;

	@FXML
	private Button BrowseBtn1;

	@FXML
	private TextField secondList;

	@FXML
	private Button BrowseBtn2;

	@FXML
	private Button OkBtn;

	@FXML
	private Button CancelBtn;

	@FXML
	private Label label1;

	private void set_scene(ActionEvent event, String Resource) throws IOException {
		Parent home_parent = FXMLLoader.load(getClass().getResource(Resource));
		Scene home_scene = new Scene(home_parent);
		Stage home_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		home_stage.hide();
		home_stage.setScene(home_scene);
		home_stage.show();
	}

	@FXML
	public void BrowseBtn1Action(ActionEvent event) throws IOException {
		FileChooser fc = new FileChooser();
		fc.setInitialDirectory(new File("D:\\"));
		fc.getExtensionFilters().addAll(new ExtensionFilter("ZIP Files", "*.zip"));
		File selectedFile = fc.showOpenDialog(null);
		if (selectedFile != null) {
			firstList.setText(selectedFile.getAbsolutePath());
			// firstList.getItems().add(selectedFile.getAbsolutePath());

			if ((!firstList.getText().equals("")) && (!secondList.getText().equals("")))
				OkBtn.setDisable(false);
		}
	}

	@FXML
	public void BrowseBtn2Action(ActionEvent event) throws IOException {
		FileChooser fc = new FileChooser();
		fc.setInitialDirectory(new File("D:\\"));
		fc.getExtensionFilters().addAll(new ExtensionFilter("ZIP Files", "*.zip"));
		File selectedFile1 = fc.showOpenDialog(null);
		if (selectedFile1 != null) {
			secondList.setText(selectedFile1.getAbsolutePath());
			// secondList.getItems().add(selectedFile1.getAbsoluteFile());
			if ((!firstList.getText().equals("")) && (!secondList.getText().equals("")))
				OkBtn.setDisable(false);
		}

	}

	public void OkBtnAction(ActionEvent event) throws IOException {
		ZipDiffCreation zdiff = new ZipDiffCreation(firstList.getText(), secondList.getText());
		zdiff.cmp();
		label1.setText("The differnce between the 2 archives is on console (meanwhile)");

	}

	public void CancelBtnAction(ActionEvent event) throws IOException {
		String Resource = "ZipDiffTool.fxml";
		set_scene(event, Resource);
	}
}
