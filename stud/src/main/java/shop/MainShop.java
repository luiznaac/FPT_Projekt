package shop;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainShop extends Application {

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		ViewShop view = new ViewShop();
		ModelShop model = new ModelShop();
		ControllerShop controller = new ControllerShop();
		controller.link(model, view);

		Scene scene = new Scene(view);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Shop");
		primaryStage.show();
	}

}
