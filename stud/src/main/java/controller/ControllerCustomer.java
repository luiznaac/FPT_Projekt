package controller;

import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.util.Pair;
import model.ModelShop;
import view.ViewCustomer;

public class ControllerCustomer {

	public void link(ModelShop model, ViewCustomer view){
		view.getList().setItems(model);
		view.getBoughtList().setItems(model.getBoughtList());
		view.addEventHandler(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				//Es prüft, welcher Button gedrückt wurde
				//und wählt die richtige Handlung aus.
				switch(((Button)event.getSource()).getId()){
					case "add":
						model.addToOrder(view.getList().getSelectionModel().getSelectedItem(), view.getQuantity());
						break;
					case "buy":
						model.placeOrder(view.loginDialog.showAndWait());
						break;
				}
			}
		});
	}

}
