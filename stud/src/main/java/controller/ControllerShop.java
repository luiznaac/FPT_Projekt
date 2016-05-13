package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import model.ModelShop;
import model.Product;
import view.ViewShop;

public class ControllerShop {

	public void link(ModelShop model, ViewShop view) {
		view.getList().setItems(model);
		view.addEventHandler(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				//Checks whether the button Add other Delete was pressed
				//and then does the specific action
				if(((Button)event.getSource()).getId() == "add"){
					Label warning = view.getLabel();
					String price = view.getInputPrice();
					String quantity = view.getInputQuantity();
					String name = view.getInputName();

					try{
						model.add(new Product(Double.parseDouble(price), Integer.parseInt(quantity), name));
					}
					catch(RuntimeException re){
						System.out.println("Bad Input");
						warningMsg(warning,"Bad Input");
					}
				}

				//when delete button is pressed
				else if(((Button)event.getSource()).getId() == "delete")
					model.remove(view.getList().getSelectionModel().getSelectedItem());
			}
		});
	}

	private void warningMsg(Label label, String text){

			label.setText(text);
			label.setVisible(true);

	}

}
