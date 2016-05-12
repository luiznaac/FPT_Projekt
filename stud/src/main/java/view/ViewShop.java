package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.Product;

public class ViewShop extends BorderPane {

	private TextField name = new TextField();
	private TextField price = new TextField();
	private TextField quantity = new TextField();
	private Button addButton = new Button("Add");
	private Button deleteButton = new Button("Delete");
	private TableView<Product> products = new TableView<>();

	public ViewShop() {
		HBox hbox = new HBox(addButton, deleteButton);
		VBox vbox = new VBox(new Text("Name:"), name, new Text("Price:"), price, new Text("Quantity:"), quantity, hbox);
		hbox.setSpacing(5);
		vbox.setSpacing(5);
		vbox.setPadding(new Insets(5, 5, 5, 5));
		addButton.setDefaultButton(true);
		addButton.setId("add");
		deleteButton.setId("delete");
		//set the Name column of the table
		TableColumn<Product, String> nameColumn = new TableColumn<>("Name");
		nameColumn.setMinWidth(200);
		nameColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
		nameColumn.setSortable(false);
		//set the Price column of the table
		TableColumn<Product, String> priceColumn = new TableColumn<>("Price");
		priceColumn.setMinWidth(100);
		priceColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("price"));
		priceColumn.setSortable(false);
		//set the Quantity column of the table
		TableColumn<Product, String> quantityColumn = new TableColumn<>("Quantity");
		quantityColumn.setMinWidth(100);
		quantityColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("quantity"));
		quantityColumn.setSortable(false);
		products.getColumns().addAll(nameColumn, priceColumn, quantityColumn);
		//set the BorderPane's children
		setRight(vbox);
		setCenter(products);
	}

	public void addEventHandler(EventHandler<ActionEvent> eventHandler) {
		addButton.addEventHandler(ActionEvent.ACTION, eventHandler);
		deleteButton.addEventHandler(ActionEvent.ACTION, eventHandler);
	}

	public TableView<Product> getList() {
		return products;
	}

	public String getInputName(){
		return name.getText();
	}

	public double getInputPrice(){
		return Double.parseDouble(price.getText());
	}

	public int getInputQuantity(){
		return Integer.parseInt(quantity.getText());
	}

}