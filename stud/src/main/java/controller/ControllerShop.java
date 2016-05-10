package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import model.ModelShop;
import model.Product;
import view.ViewShop;

public class ControllerShop {

	public void link(ModelShop model, ViewShop view) {
		view.getList().setItems(model);
		view.addEventHandler(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				if(((Button)event.getSource()).getId() == "add")
					model.add(new Product(Double.parseDouble(view.getInputPrice()), Integer.parseInt(view.getInputQuantity()), view.getInputName()));
				else if(((Button)event.getSource()).getId() == "delete")
					model.remove(view.getList().getSelectionModel().getSelectedItem());
			}
		});
	}

}
