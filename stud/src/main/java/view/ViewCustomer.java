package view;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Pair;
import model.Product;

public class ViewCustomer extends BorderPane {

	private Button buyButton = new Button("Buy");
	private Button addSelect = new Button("Add");
	private TableView<Product> availableProducts = new TableView<>();
	private TableView<Product> boughtProducts = new TableView<>();
	private Label timeLabel = new Label();
	private SimpleStringProperty timeString = new SimpleStringProperty();
	public Dialog<Pair<String, String>> loginDialog = new Dialog<>();
	private TextField quantitySelect = new TextField();

	public ViewCustomer(){
		TextField username = new TextField();
		username.setPromptText("Username");
		PasswordField password = new PasswordField();
		password.setPromptText("Password");
		GridPane loginGrid = new GridPane();
		loginGrid.setHgap(10);
		loginGrid.setVgap(10);
		loginGrid.add(new Text("Username:"), 0, 0);
		loginGrid.add(username, 1, 0);
		loginGrid.add(new Text("Password:"), 0, 1);
		loginGrid.add(password, 1, 1);
		ButtonType loginButtonType = new ButtonType("Login", ButtonData.OK_DONE);
		loginDialog = new Dialog<>();
		loginDialog.setTitle("Login");
		loginDialog.getDialogPane().setContent(loginGrid);
		loginDialog.setResultConverter(dialogButton -> {
		    if (dialogButton == loginButtonType) {
		        return new Pair<>(username.getText(), password.getText());
		    }
		    return null;
		});
		loginDialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);
		//set the Name column of the bought table
		TableColumn<Product, String> boughtNameColumn = new TableColumn<>("Name");
		boughtNameColumn.setMinWidth(200);
		boughtNameColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
		boughtNameColumn.setSortable(false);
		//set the Price column of the bought table
		TableColumn<Product, String> boughtPriceColumn = new TableColumn<>("Price");
		boughtPriceColumn.setMinWidth(100);
		boughtPriceColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("price"));
		boughtPriceColumn.setSortable(false);
		//set the Buy Count column of the bought table
		TableColumn<Product, String> bougthBuyCountColumn = new TableColumn<>("Buy Count");
		bougthBuyCountColumn.setMinWidth(100);
		bougthBuyCountColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("quantity"));
		bougthBuyCountColumn.setSortable(false);
		boughtProducts.getColumns().addAll(boughtNameColumn, boughtPriceColumn, bougthBuyCountColumn);
		//set the Name column of the available table
		TableColumn<Product, String> availableNameColumn = new TableColumn<>("Name");
		availableNameColumn.setMinWidth(200);
		availableNameColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
		availableNameColumn.setSortable(false);
		//set the Price column of the available table
		TableColumn<Product, String> availablePriceColumn = new TableColumn<>("Price");
		availablePriceColumn.setMinWidth(100);
		availablePriceColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("price"));
		availablePriceColumn.setSortable(false);
		//set the Quantity column of the available table
		TableColumn<Product, String> availableQuantityColumn = new TableColumn<>("Quantity");
		availableQuantityColumn.setMinWidth(100);
		availableQuantityColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("quantity"));
		availableQuantityColumn.setSortable(false);
		//set the Buy column of the available table
		TableColumn<Product, Product> availableBuyColumn = new TableColumn<>("Quantity");
		availableBuyColumn.setMinWidth(100);
		availableBuyColumn.setSortable(false);
		availableProducts.getColumns().addAll(availableNameColumn, availablePriceColumn, availableQuantityColumn);
		//set the tables positions on the border pane using a vbox
		Text productName = new Text("Name: ");
		Text productPrice = new Text("Price: ");
		Text productQuantity = new Text("Quantity: ");
		availableProducts.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && !availableProducts.getItems().isEmpty()) {
                        Product p = availableProducts.getSelectionModel().getSelectedItem();
                        if(p != null){
	                        productName.setText(    "Name:     " + p.getName());
	                		productPrice.setText(   "Price:       " + String.valueOf(p.getPrice()));
	                		productQuantity.setText("Quantity: " + String.valueOf(p.getQuantity()));
                        }
                }
            }
        });
		Region aux1Desc = new Region();
		Region aux2Desc = new Region();
		VBox selectVbox = new VBox(aux1Desc, productName, productPrice, productQuantity, quantitySelect, addSelect, aux2Desc);
		selectVbox.setSpacing(5);
		selectVbox.setVgrow(aux1Desc, Priority.ALWAYS);
		selectVbox.setVgrow(aux2Desc, Priority.ALWAYS);
		VBox availableVbox = new VBox(new Text("Available Products:"), availableProducts);
		VBox boughtVbox = new VBox(new Text("Bought Products:"), boughtProducts);
		availableVbox.setSpacing(5);
		availableVbox.setPadding(new Insets(5, 5, 5, 0));
		boughtVbox.setSpacing(5);
		boughtVbox.setPadding(new Insets(5, 0, 5, 5));
		setLeft(availableVbox);
		setCenter(selectVbox);
		setRight(boughtVbox);
		//set the bottom of the BorderPane
		Region aux = new Region();
		HBox bottomHbox = new HBox(timeLabel, aux, buyButton);
		buyButton.setId("buy");
		addSelect.setId("add");
		setBottom(bottomHbox);
		bottomHbox.setHgrow(aux, Priority.ALWAYS);
		bottomHbox.setAlignment(Pos.CENTER);
		bottomHbox.setPadding(new Insets(0, 0, 5, 5));
		timeLabel.textProperty().bind(timeString);
		startTime();
	}

	public TableView<Product> getList() {
		return availableProducts;
	}

	public TableView<Product> getBoughtList() {
		return boughtProducts;
	}

	public void addEventHandler(EventHandler<ActionEvent> eventHandler) {
		buyButton.addEventHandler(ActionEvent.ACTION, eventHandler);
		addSelect.addEventHandler(ActionEvent.ACTION, eventHandler);
	}

	public String getQuantity() {
		return quantitySelect.getText();
	}

	public void startTime(){

		Thread timeThread = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					int port = 6667;
					InetAddress address = null;
					//get the address
					try {
						address = InetAddress.getByName("localhost");
					} catch (UnknownHostException e2) {
						e2.printStackTrace();
					}

					try (DatagramSocket socket = new DatagramSocket(3431);) {
						try {
							while (true) {
								String command = "time:";
								byte buffer[] = null;
								buffer = command.getBytes();
								DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);
								socket.send(packet);
								Platform.runLater(new Runnable() {
									@Override
									public void run() {
										byte answer[] = new byte[1024];
										DatagramPacket packet = new DatagramPacket(answer, answer.length);
										//man braucht diese Try-Catch-Block weil
										//hier ist schon ein andere Scope, welches
										//von dem try-Block ausserhalb nich geprüft wird
										try {
											socket.receive(packet);
										} catch (IOException e) {
											e.printStackTrace();
										}
										timeString.set(new String(packet.getData()));
									}
								});
								try {
									Thread.sleep(1000);
								} catch (InterruptedException ex) {
									break;
								}
							}
						} catch (IOException e1) {
							break;
						}
					} catch (SocketException e1) {
						break;
					}
				}
			}
		});
		timeThread.start();
	}

}
