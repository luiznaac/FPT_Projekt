package controller;

import model.ModelShop;
import view.ViewCustomer;

public class ControllerCustomer {

//	es wurde noch nicht wirklich implementiert,
//	da es Aufgabe von späteren Übungen ist
	public void link(ModelShop model, ViewCustomer view){
		view.getList().setItems(model);
	}

}
