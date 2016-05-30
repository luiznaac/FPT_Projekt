package view;

import java.io.IOException;

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

//		Product p1 = new Product("oi", 15, 15);
//		Product p2 = new Product("tchau", 15, 15);
//		Product p3 = new Product("ook", 15, 15);
//		XStreamStrategy bs = new XStreamStrategy("products.xml");
//		bs.writeObject(p1);
//		bs.writeObject(p2);
//		bs.writeObject(p3);
//		bs.close();
//		XStreamStrategy bs2 = new XStreamStrategy("products.xml");
//		Product p4, p5, p6;
//		p4 = bs2.readObject();
//		p5 = bs2.readObject();
//		p6 = bs2.readObject();
//		System.out.println(p4);
//		System.out.println(p5);
//		System.out.println(p6);
//		bs2.close();
	}

}
