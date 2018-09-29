package it.unibo.sd1819.lab1.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import it.unibo.sd1819.lab1.controller.ControllerView;
import it.unibo.sd1819.lab1.utils.Pair;
import it.unibo.sd1819.lab1.utils.ViewUtils;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ChatView extends Application {
	private TextArea chatArea;
	private ListView<String> users;
	private Optional<ControllerView> controller = Optional.empty();
	private TextField inputField;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle(ViewUtils.SCENE_NAME);
		primaryStage.setWidth(ViewUtils.SCENE_WIDTH);
	    primaryStage.setHeight(ViewUtils.SCENE_HEIGHT);
        handleHideView(primaryStage);
	    List<Pair<String,Pane>> tabs = new ArrayList<Pair<String,Pane>>();
	    tabs.add(Pair.of(ViewUtils.CHAT_TAB_NAME, chatTab()));
	    tabs.add(Pair.of(ViewUtils.USERS_TAB_NAME, usersTab()));
        Scene scene = new Scene(tabPane(tabs));
        
        primaryStage.setScene(scene);
        primaryStage.show();
	}
	
	public void setController(ControllerView controller) {
		this.controller = Optional.ofNullable(controller);
	}
	
	/**
	 * Add new message into chat text area.
	 * @param message String
	 */
	public void addMessage(String message) {
		this.chatArea.setText(this.chatArea.getText() + ViewUtils.NEW_LINE + message);  
	}
	
	/**
	 * Add user to list.
	 * @param user String
	 */
	public void addUser(String user) {
		this.users.getItems().add(user);
	}
	
	/**
	 * Delete user from list.
	 * @param user String
	 */
	public void deleteUser(String user) {
		if(this.users.getItems().contains(user)) {
			this.users.getItems().remove(user);
		}
	}
	
	
	
	private void handleHideView(Stage primaryStage) {
		primaryStage.setOnHiding(new EventHandler<WindowEvent>() {

	         @Override
	         public void handle(WindowEvent event) {
	             Platform.runLater(new Runnable() {
	                 @Override
	                 public void run() {
	                     System.out.println("Application Closed by click to Close Button(X)");
	                     System.exit(0);
	                 }
	             });
	         }
	     });
	}
	
	private Parent tabPane(List<Pair<String,Pane>> panes) {
		TabPane tabPane = new TabPane();
		panes.forEach(pane -> {
			Tab tab = new Tab();
			tab.setText(pane.getFirst());
			tab.setContent(pane.getSecond());
			tabPane.getTabs().add(tab);
		});
		tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		return tabPane;
	}
	
	private BorderPane usersTab() {
		BorderPane userPane = new BorderPane();
		this.users = new ListView<String>();
		ObservableList<String> items =FXCollections.observableArrayList();
		this.users.setItems(items);	
		userPane.setCenter(this.users);
        return userPane;
	}

	private BorderPane chatTab() {
		BorderPane chatPane = new BorderPane();
        this.chatArea = new TextArea();
        chatPane.setCenter(chatArea);
        chatPane.setBottom(chatTabBottomContent());
        return chatPane;
	}

	private BorderPane chatTabBottomContent() {
	    BorderPane bottomPane = new BorderPane();
	    Button btnSend = new Button(ViewUtils.BTN_SEND_NAME);
	    handleSendButtonClick(btnSend);
	    this.inputField = new TextField();
	    bottomPane.setRight(btnSend);
	    bottomPane.setCenter(inputField);
	    return bottomPane;
	}
	
	private void handleSendButtonClick(Button button) {
		button.setOnAction((event) -> {
		    // Button was clicked, do something...
			String message = this.inputField.getText();
			if(!(message == null || message.replaceAll(" ","").length() <= 0)) {
				if(controller.isPresent()) controller.get().sendMessage(message);
				else showErrorDialog("La view non ha il controller,"+ ViewUtils.NEW_LINE +"forse è il caso di passarglierlo");
			} else {
				showErrorDialog("Perfavore inserisci un messaggio...");
			}
			this.inputField.clear();
		});
	}
	
	private void showErrorDialog(String text) {
		Alert alert = new Alert(AlertType.ERROR, text, ButtonType.CLOSE);
		alert.showAndWait();
	}
}
