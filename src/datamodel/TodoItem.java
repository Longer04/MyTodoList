package datamodel;

import java.time.LocalDate;

public class TodoItem 
{
	private String shortDescription;
	private String fullDescription;
	private LocalDate deadline;
	
	
	public TodoItem(String shortDescription, String fullDescription, LocalDate deadline) 
	{
		super();
		this.shortDescription = shortDescription;
		this.fullDescription = fullDescription;
		this.deadline = deadline;
	}


	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}


	public void setFullDescription(String fullDescription) {
		this.fullDescription = fullDescription;
	}


	public void setDeadline(LocalDate deadline) {
		this.deadline = deadline;
	}


	public String getShortDescription() {
		return shortDescription;
	}


	public String getFullDescription() {
		return fullDescription;
	}


	public LocalDate getDeadline() {
		return deadline;
	}


	@Override
	public String toString() {
		return shortDescription;
	}
	
	
	
	
}
