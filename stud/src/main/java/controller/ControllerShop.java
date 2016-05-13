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
				//Es prüft, welcher Button gedrückt wurde
				//und wählt die richtige Handlung aus.
				switch(((Button)event.getSource()).getId()){
					case "add":
						try{
							model.add(new Product(Double.parseDouble(view.getInputPrice()),
									Integer.parseInt(view.getInputQuantity()), view.getInputName()));
						}
						catch(RuntimeException re){
							System.out.println("Bad Input");
						}
						break;

					case "delete":
						model.remove(view.getList().getSelectionModel().getSelectedItem());
						break;

					case "load":
						System.out.println("noch nicht");
						break;

					case "save":
						System.out.println("noch nicht");
						break;
				}
			}
		});
	}
}
