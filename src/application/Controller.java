package application;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import datamodel.TodoData;
import datamodel.TodoItem;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;

public class Controller 
{
	
	private List<TodoItem> todoItems;
	
	@FXML
	private ListView<TodoItem> todoListView;
	
	@FXML
	private TextArea itemDetailsTextArea;
	
	@FXML
	private Label deadlineLabel;
	
	@FXML
	private BorderPane mainBorderPane;
	
	@FXML
	private ContextMenu listContextMenu;
	
	public void initialize()
	{		
		listContextMenu = new ContextMenu();
		//Creating new menu item
		MenuItem deleteMenuItem = new MenuItem("Delete");
		//Adding event handler for menu item
		deleteMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				//Picking item to delete which is selected
				TodoItem item = todoListView.getSelectionModel().getSelectedItem();
				deleteItem(item);
			}
		});
		//Adding item to menu
		listContextMenu.getItems().addAll(deleteMenuItem);
		//Listener of changing items in the list - works for start of program also (no item marked at beginning)
		todoListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TodoItem>() {
			@Override
			public void changed(ObservableValue<? extends TodoItem> observable, TodoItem oldValue, TodoItem newValue) {
				// TODO Auto-generated method stub
				if(newValue != null)
				{
					TodoItem item = todoListView.getSelectionModel().getSelectedItem();
					itemDetailsTextArea.setText(item.getFullDescription());
					DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("MMMM d, yyyy");
					deadlineLabel.setText(dtFormatter.format(item.getDeadline()));
				}
			}
		});
		
		//Changed method to setItems from observable List
		todoListView.setItems(TodoData.getInstance().getTodoItems());
		todoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);	
		//Select first item when application starts.
		todoListView.getSelectionModel().selectFirst();
		
		//set a cell factory by calling the list views set cell factory method
		//passing anonymous class that implements callback interface - has 2 parameters
		//
		todoListView.setCellFactory(new Callback<ListView<TodoItem>, ListCell<TodoItem>>() {
			
			@Override
			public ListCell<TodoItem> call(ListView<TodoItem> param) {
				ListCell<TodoItem> cell = new ListCell<TodoItem>()
					{
						@Override
						protected void updateItem(TodoItem item, boolean empty)
						{
							super.updateItem(item, empty);
							//If cell is empty text is null
							//if cell item is today display red
							if(empty)
							{
								setText(null);
							} else {
								setText(item.getShortDescription().toString());
								if(item.getDeadline().isBefore(LocalDate.now().plusDays(1)))
								{
									setTextFill(Color.RED);
								} else if(item.getDeadline().equals(LocalDate.now().plusDays(1))) {
									setTextFill(Color.VIOLET);
								} 
							}
						}
					};
					//Associating context menu to not empty cell by using lambda expression
					cell.emptyProperty().addListener(
							(obs, wasEmpty, isNowEmpty) -> {
								if(isNowEmpty)
								{
									cell.setContextMenu(null);
								}else {
									cell.setContextMenu(listContextMenu);
								}
							});
					return cell;
			}
		});
	}
	
	@FXML
	public void showNewItemDialog()
	{
		Dialog<ButtonType> dialog = new Dialog<>();
		//Ensure that when dialog is pop up user can interact only with dialog window
		dialog.initOwner(mainBorderPane.getScene().getWindow());
		dialog.setTitle("Add New Todo Item");
		dialog.setHeaderText("Use this dialog to create new todo item.");
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("todoItemDialog.fxml"));
		try
		{
			//Loading the FXML of todoItemDialog
			dialog.getDialogPane().setContent(fxmlLoader.load());
		} catch(IOException ex)
		{
			System.out.println("Couldn't load the dialog");
			ex.printStackTrace();
			return;
		}
		//Added OK/Cancel buttons
		dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
		dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
		
		Optional<ButtonType> result = dialog.showAndWait();
		if(result.isPresent() && result.get() == ButtonType.OK)
		{
			//Adding controller and processing results
			DialogController controller = fxmlLoader.getController();
			TodoItem newItem = controller.processResults();
			//Selecting new Item when created
			todoListView.getSelectionModel().select(newItem);
		}
	}
	
	//Handler for the click list view
	@FXML
	public void handleClickListView()
	{
		//Selection of list item
		TodoItem item = todoListView.getSelectionModel().getSelectedItem();
		//Display detailed description 
		itemDetailsTextArea.setText(item.getFullDescription());
		//Display Due date
		deadlineLabel.setText(item.getDeadline().toString());

	}
	
	//Method to delete item with confirmation dialog
	public void deleteItem(TodoItem item)
	{
		//Confirmation alert
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Delete Todo Item");
		alert.setHeaderText("Delete item: " + item.getShortDescription());
		alert.setContentText("Are you sure? Press OK to confirm, or cancel to back out.");
		Optional<ButtonType> result = alert.showAndWait();
		//Checking what was pressed
		if(result.isPresent() && (result.get() == ButtonType.OK))
		{
			TodoData.getInstance().deleteTodoItem(item);
		}
	}

}
