package view;

import controller.ControllerCustomer;
import controller.ControllerShop;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;
import view.ViewShop;
import serialization.*;

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
		primaryStage.setTitle("Shop Manager");
		primaryStage.show();

		Stage secondaryStage = new Stage();
		ViewCustomer viewCustomer = new ViewCustomer();
		ControllerCustomer controllerCustomer = new ControllerCustomer();
		controllerCustomer.link(model, viewCustomer);

		Scene sceneCustomer = new Scene(viewCustomer);
		secondaryStage.setScene(sceneCustomer);
		secondaryStage.setTitle("Shop Order");
		secondaryStage.show();

		Product p = new Product("test", 123, 123);
		XMLStrategy xs = new XMLStrategy();
		xs.writeObject(p);
//		xs = new XMLStrategy();
//		Product p2 = (Product)xs.readObject();
//		System.out.println(p2);
	}

}
