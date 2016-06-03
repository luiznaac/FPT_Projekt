package view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import model.Product;

public class ViewCustomer extends BorderPane {

	private Button buyButton = new Button("Buy");
	private ListView<Product> availableProducts = new ListView<>();
	private TableView<Product> boughtProducts = new TableView<>();

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
		//cell configuration of availableProducts
		availableProducts.setCellFactory(e -> {
			ListCell<Product> cell = new ListCell<Product>() {
				@Override protected void updateItem(Product myObject, boolean b) {
					super.updateItem(myObject, myObject == null || b);
					if (myObject != null) {
					TextField toBuy = new TextField();
				    toBuy.setMaxWidth(60);
				    toBuy.setMinWidth(60);
					HBox hCell = new HBox(new Text(myObject.getName() + " | €" + myObject.getPrice()+ " | "
											+ myObject.getQuantity()), toBuy);
					hCell.setSpacing(5);
					setGraphic(hCell);
					} else {
						setGraphic(null);
						setText("");
					}
				}
			};
			return cell;
		});
		//set the other children
		//to print the information about the object in the list
		setRight(boughtProducts);
		setLeft(availableProducts);
	}

	public ListView<Product> getList() {
		return availableProducts;
	}

}
