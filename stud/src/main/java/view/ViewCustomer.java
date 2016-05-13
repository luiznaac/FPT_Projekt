package view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import model.Product;

public class ViewCustomer extends BorderPane {

	Button buyButton = new Button("Buy");
	ListView<Product> availableProducts = new ListView<>();
	TableView<Product> boughtProducts = new TableView<>();

	public ViewCustomer(){
		//set the Name column of the table
		TableColumn<Product, String> nameColumn = new TableColumn<>("Name");
		nameColumn.setMinWidth(200);
		nameColumn.setSortable(false);
		//set the Price column of the table
		TableColumn<Product, String> priceColumn = new TableColumn<>("Price");
		priceColumn.setMinWidth(100);
		priceColumn.setSortable(false);
		//set the Buy Count column of the table
		TableColumn<Product, String> buyCountColumn = new TableColumn<>("Buy Count");
		buyCountColumn.setMinWidth(100);
		buyCountColumn.setSortable(false);
		boughtProducts.getColumns().addAll(nameColumn, priceColumn, buyCountColumn);
		//set the buyButton to the bottom right of the BorderPane
		setBottom(buyButton);
		BorderPane.setAlignment(buyButton, Pos.CENTER_RIGHT);
		//set the other children
		//the availableProducts uses the overridden toString Method in the Product Class
		//to print the information about the object in the list
		setRight(boughtProducts);
		setLeft(availableProducts);
	}

	public ListView<Product> getList() {
		return availableProducts;
	}

}