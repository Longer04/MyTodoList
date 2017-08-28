package application;

import java.time.LocalDate;

import datamodel.TodoData;
import datamodel.TodoItem;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class DialogController 
{
	
	@FXML
	private TextField shortDescriptionField;
	
	@FXML
	private TextArea detailsDescriptionField;
	
	@FXML
	private DatePicker deadlinePick;
	
	//Reacting on OK button
	//Gathering the input -> creating new item -> adding to list
	public void processResults()
	{
		String shortDescription = shortDescriptionField.getText().trim();
		String details = detailsDescriptionField.getText().trim();
		LocalDate deadlineValue = deadlinePick.getValue();
		
		TodoData.getInstance().addTodoItem(new TodoItem(shortDescription, details, deadlineValue));
	}

}
