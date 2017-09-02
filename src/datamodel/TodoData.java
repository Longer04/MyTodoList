package datamodel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

//Singleton example to simplify example of todo list
//If any part of application wants to access the data it has to call TodoData.getInstance.getTodoItems
public class TodoData 
{
	private static TodoData instance = new TodoData();
	private static String filename = "TodoListItems.txt";
	
	
	private ObservableList<TodoItem> todoItems;
	private DateTimeFormatter formatter;
	
	
	//Method which return instance of class
	public static TodoData getInstance()
	{
		return instance;
	}
	
	//Private constructor to ensure only one instance is created
	private TodoData()
	{
		formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	}

	//Setter & Getter for items in the list.
	public ObservableList<TodoItem> getTodoItems() {
		return todoItems;
	}
	
	public void addTodoItem(TodoItem item)
	{
		todoItems.add(item);
	}

	//Loading items from the file.
	public void loadTodoItems() throws IOException
	{
		//use observableArrayList because in controller setAll method
		todoItems = FXCollections.observableArrayList();
		//using Path for get path to file to load 
		Path path = Paths.get(filename);
		//creating new buffered reader for particular file
		BufferedReader br = Files.newBufferedReader(path);
		
		String input;
		
		try {
			//loop that retrives the data
			while((input = br.readLine()) != null)
			{
				//Array with data + split
				String[] itemPieces = input.split("\t");
				
				//Converting details
				String shortDescription = itemPieces[0];
				String fullDescription = itemPieces[1];
				String dateString = itemPieces[2];
				
				//Converting date to format that we can read.
				LocalDate date = LocalDate.parse(dateString, formatter);
				
				//Creating new todoItem with data and adding it to list.
				TodoItem todoItem = new TodoItem(shortDescription, fullDescription, date);
				todoItems.add(todoItem);
			}	
		}finally {
			if(br != null)
			{
				br.close();
			}
		}	
	}
	
	//Method to save the data
	public void storeTodoItems() throws IOException
	{
		
		//Path where we save the data
		Path path = Paths.get(filename);
		BufferedWriter bw = Files.newBufferedWriter(path);
		
		try {
			//Iterator to iterate through list of items and save the one entry at the time to text file
			//Builds up iterator that
			Iterator<TodoItem> iterator1 = todoItems.iterator();
			//Testing to see if there is another entry in the iterator we can use
			while(iterator1.hasNext())
			{
				TodoItem item = iterator1.next();
				bw.write(String.format("%s\t%s\t%s\t",
						item.getShortDescription(),
						item.getFullDescription(),
						item.getDeadline().format(formatter)));
				bw.newLine();
			}
			
		}finally {
			if(bw!=null)
			{
				bw.close();
			}
		}
	}
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
