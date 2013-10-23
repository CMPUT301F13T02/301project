package ca.ualberta.CMPUT301F13T02.chooseyouradventure;

import java.util.ArrayList;

public class Story {
	
	/** 
	 * @uml.property name="pages"
	 * @uml.associationEnd aggregation="composite" inverse="story:ca.ualberta.CMPUT301F13T02.chooseyouradventure.Page"
	 */
    private ArrayList<Page> pages = new ArrayList<Page>();
    private String id;
    
    public Story() {
    	
    }
    
    public ArrayList<Page> getPages() {
    	return pages;
    }
    
    public void addPage(Page newPage) {
    	pages.add(newPage);
    }
    
    public void deletePage(Page aPage) {
    	
    }
    
    public void setId(String id) {
    	this.id = id;
    }
    
    public String getId() {
    	return id;
    }
    
	/**
	 * Compares this story for deep equality with another story
	 */
	public boolean equals(Story story) {

		if (pages.size() != story.getPages().size())
			return false;

		//Check that all comments are the same
		for (int i = 0; i < pages.size(); i++) {
			if (!pages.get(i).equals(story.getPages().get(i))) 
				return false;
		}
		
		return true;
	}
}
