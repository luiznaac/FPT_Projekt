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
		view.getChat().setItems(model.getMessagesList());
		view.addEventHandler(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				//Es prüft, welcher Button gedrückt wurde
				//und wählt die richtige Handlung aus.
				switch(((Button)event.getSource()).getId()){
					case "add":
						try{
							model.add(new Product(view.getInputName(), Double.parseDouble(view.getInputPrice()),
									Integer.parseInt(view.getInputQuantity())));
						}
						catch(RuntimeException re){
							System.out.println("Bad Input");
						}
						break;

					case "delete":
						model.remove(view.getList().getSelectionModel().getSelectedItem());
						break;

					case "load":
						model.setStrategy(view.getChoiceBox().getSelectionModel().getSelectedIndex());
						model.load();
						break;

					case "save":
						model.setStrategy(view.getChoiceBox().getSelectionModel().getSelectedIndex());
						model.save();
						break;

					case "send":
						model.sendMessage(view.getInputChat());
				}
			}
		});
	}

}
