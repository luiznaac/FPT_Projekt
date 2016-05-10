package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ViewShop extends BorderPane {

	private TextField name = new TextField();
	private TextField price = new TextField();
	private TextField quantity = new TextField();
	private Button addButton = new Button("Add");
	private Button deleteButton = new Button("Delete");
	private ListView<fpt.com.Product> products = new ListView<>();

	public ViewShop() {
		HBox hbox = new HBox(addButton, deleteButton);
		VBox vbox = new VBox(name, price, quantity, hbox);
		vbox.setSpacing(5);
		setRight(vbox);
		setCenter(products);

		addButton.setId("add");
		deleteButton.setId("delete");

		products.setCellFactory(c -> {
			ListCell<fpt.com.Product> cell = new ListCell<fpt.com.Product>(){
				@Override
				protected void updateItem(fpt.com.Product product, boolean b) {
					super.updateItem(product, product == null || b);
					if (product != null)
						setText(product.toString());
					else
						setText("");
				}
			};
			return cell;
		});
	}

	public void addEventHandler(EventHandler<ActionEvent> eventHandler) {
		addButton.addEventHandler(ActionEvent.ACTION, eventHandler);
		deleteButton.addEventHandler(ActionEvent.ACTION, eventHandler);
	}

	public ListView<fpt.com.Product> getList() {
		return products;
	}

	public String getInputName(){
		return name.getText();
	}

	public String getInputPrice(){
		String Inputprice = price.getText();

		return Inputprice;

	}

	public String getInputQuantity(){
		return quantity.getText();
	}

}