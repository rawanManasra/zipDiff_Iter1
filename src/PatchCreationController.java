import java.io.File;
import java.io.IOException;

import Helpers.Module.AppConstants;
import Helpers.Module.logfile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import patchCreationAndApplication.PatchCreation;

public class PatchCreationController {

	@FXML
	public TextField secondList;

	@FXML
	private Button BrowseBtn1;

	@FXML
	public TextField firstList;

	@FXML
	public TextField pathlist;

	@FXML
	private Button OkBtn;

	@FXML
	private Button CancelBtn;

	@FXML
	private Button BrowseBtn2;

	@FXML
	private Button BrowseBtn3;

	private void set_scene(ActionEvent event, String Resource) throws IOException {
		Parent home_parent = FXMLLoader.load(getClass().getResource(Resource));
		Scene home_scene = new Scene(home_parent);
		Stage home_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		home_stage.hide();
		home_stage.setScene(home_scene);
		home_stage.show();
	}

	@FXML
	void CancelBtnAction(ActionEvent event) throws IOException {
		String Resource = "ZipDiffTool.fxml";
		set_scene(event, Resource);
	}

	@FXML
	void OkBtnAction(ActionEvent event) throws IOException {
		if (pathlist == null) {
			pathlist.setText(AppConstants.DEFAULT_PATH);
		}
		logfile log = new logfile(
				AppConstants.PATCH_CREATION + ": " + firstList.getText() + " " + secondList.getText());
		PatchCreation pc = new PatchCreation(firstList.getText(), secondList.getText(), pathlist.getText());
		pc.CreatePatch();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("PatchApplication.fxml"));
		Parent root = (Parent) loader.load();
		PatchApplicationController app = loader.getController();
		app.func(firstList.getText(), secondList.getText(), pathlist.getText());
		Stage s = new Stage();
		s.setScene(new Scene(root));
		s.show();
		Stage stage = (Stage) OkBtn.getScene().getWindow();
		stage.close();
		// String Resource = "ContToPatchApplication.fxml";
		// set_scene(event, Resource);
	}

	@FXML
	void BrowseBtn1Action(ActionEvent event) throws IOException {
		FileChooser fc = new FileChooser();
		fc.setInitialDirectory(new File("D:\\"));
		fc.getExtensionFilters().addAll(new ExtensionFilter("ZIP Files", "*.zip"));
		File selectedFile = fc.showOpenDialog(null);
		if (selectedFile != null) {
			firstList.setText(selectedFile.getAbsolutePath());
			// firstList.getItems().add(selectedFile.getAbsolutePath());

			if ((!firstList.getText().equals("")) && (!secondList.getText().equals(""))
					&& !pathlist.getText().equals(""))
				OkBtn.setDisable(false);
			else {
				System.out.println("file is not valid !");
			}
		}
	}

	@FXML
	void BrowseBtn2Action(ActionEvent event) throws IOException {
		FileChooser fc = new FileChooser();
		fc.setInitialDirectory(new File("D:\\"));
		fc.getExtensionFilters().addAll(new ExtensionFilter("ZIP Files", "*.zip"));
		File selectedFile1 = fc.showOpenDialog(null);
		if (selectedFile1 != null) {
			secondList.setText(selectedFile1.getAbsolutePath());
			if ((!firstList.getText().equals("")) && (!secondList.getText().equals(""))
					&& !pathlist.getText().equals(""))
				OkBtn.setDisable(false);
		}

	}

	@FXML
	void BrowseBtn3Action(ActionEvent event) throws IOException {
		DirectoryChooser chooser = new DirectoryChooser();
		chooser.setInitialDirectory(new File("D:\\"));
		File selectedDir = chooser.showDialog(null);

		if (selectedDir != null) {
			pathlist.setText(selectedDir.getAbsolutePath());
		} else {
			pathlist.setText(AppConstants.DEFAULT_PATH);

		}
		if ((!firstList.getText().equals("")) && (!secondList.getText().equals("")) && !pathlist.getText().equals(""))
			OkBtn.setDisable(false);
	}

}