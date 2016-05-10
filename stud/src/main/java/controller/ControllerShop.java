package shop;

public class ControllerShop {

	public void link(ModelShop model, ViewShop view) {
		view.getList().setItems(model);
		view.addEventHandler(e -> {
			model.add(new Product(Double.parseDouble(view.getInputPrice()), Integer.parseInt(view.getInputQuantity()), view.getInputName()));
		});
	}

}
