package controller;

import model.ModelShop;
import view.ViewCustomer;

public class ControllerCustomer {

	public void link(ModelShop model, ViewCustomer view){
		view.getList().setItems(model);
	}

}
