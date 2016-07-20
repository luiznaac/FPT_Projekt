package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
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
	private ChoiceBox<String> serializationBox = new ChoiceBox<>();
	private Button loadButton = new Button("Load");
	private Button saveButton = new Button("Save");
	private ListView<String> messages = new ListView<>();
	private TextField message = new TextField();
	private Button sendButton = new Button("Send");
	private TextField login = new TextField();
	private Button loginButton = new Button("Login");

	public ViewShop() {
		//set the input area
		HBox inputHbox = new HBox(addButton, deleteButton);
		VBox inputVbox = new VBox(new Text("Name:"), name, new Text("Price:"),
								  price, new Text("Quantity:"), quantity, inputHbox);
		inputHbox.setSpacing(5);
		inputVbox.setSpacing(5);
		inputVbox.setPadding(new Insets(5, 5, 5, 5));
		addButton.setId("add");
		deleteButton.setId("delete");
		//set the serialization strategy area
		HBox serializationHbox = new HBox(serializationBox, loadButton, saveButton);
		serializationHbox.setSpacing(5);
		serializationHbox.setPadding(new Insets(5, 5, 5, 0));
		serializationBox.getItems().addAll("Binary Strategy",
										   "XML Strategy",
										   "XStream Strategy",
										   "Database (JDBC) Strategy",
										   "Database (OpenJPA) Strategy");
		serializationBox.setValue("Binary Strategy");
		loadButton.setId("load");
		saveButton.setId("save");
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
		//chat
		sendButton.setId("send");
		sendButton.setDefaultButton(true);
		HBox chatHbox = new HBox(message, sendButton);
		loginButton.setId("login");
		HBox loginHbox = new HBox(login, loginButton);
		VBox chatVbox = new VBox(loginHbox, messages, chatHbox);
		//set the BorderPane's children
		setLeft(products);
		setCenter(inputVbox);
		setRight(chatVbox);
		setBottom(serializationHbox);
	}

	public void addEventHandler(EventHandler<ActionEvent> eventHandler) {
		addButton.addEventHandler(ActionEvent.ACTION, eventHandler);
		deleteButton.addEventHandler(ActionEvent.ACTION, eventHandler);
		loadButton.addEventHandler(ActionEvent.ACTION, eventHandler);
		saveButton.addEventHandler(ActionEvent.ACTION, eventHandler);
		sendButton.addEventHandler(ActionEvent.ACTION, eventHandler);
		loginButton.addEventHandler(ActionEvent.ACTION, eventHandler);
	}

	public ChoiceBox<String> getChoiceBox() {
		return serializationBox;
	}

	public TableView<Product> getList() {
		return products;
	}

	public ListView<String> getChat() {
		return messages;
	}

	public String getInputName() {
		return name.getText();
	}

	public String getInputPrice() {
		return price.getText();
	}

	public String getInputQuantity() {
		return quantity.getText();
	}

	public String getInputChat() {
		return message.getText();
	}

	public String getLogin() {
		return login.getText();
	}
}