/*
* Copyright (c) 2013, TeamCMPUT301F13T02
* All rights reserved.
* 
* Redistribution and use in source and binary forms, with or without modification,
* are permitted provided that the following conditions are met:
* 
* Redistributions of source code must retain the above copyright notice, this
* list of conditions and the following disclaimer.
* 
* Redistributions in binary form must reproduce the above copyright notice, this
* list of conditions and the following disclaimer in the documentation and/or
* other materials provided with the distribution.
* 
* Neither the name of the {organization} nor the names of its
* contributors may be used to endorse or promote products derived from
* this software without specific prior written permission.
* 
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
* ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
* WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
* DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR
* ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
* (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
* LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
* (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
* SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

package ca.ualberta.CMPUT301F13T02.chooseyouradventure;

import java.util.ArrayList;
import java.util.UUID;

/**
 * A page represents a physical page of a story. 
 */
public class Page {
	public UUID id;
	private ArrayList<Comment> comments = new ArrayList<Comment>();
	private ArrayList<Tile> tiles = new ArrayList<Tile>();
	private ArrayList<Decision> decisions = new ArrayList<Decision>();
	private String title;
	private String pageEnding;
	private int refNum;
	/**
	 * This gets the title of the Page
	 * @return the title
	 */
	public String getTitle()
	{
		return title;
	}
	
	/**
	 * This sets a page title
	 * @param title the title to set
	 */
	public void setTitle(String title)
	{
		this.title = title;
	}
	
	/**
	 * This sets the page ID
	 * @param UUid
	 */
	public void setId(UUID id) {
		this.id = id;
	}
	
	/**
	 * This is the constructor that binds the arraylists to itself
	 */
	public Page() {
		id = UUID.randomUUID();
		tiles = new ArrayList<Tile>();
		decisions = new ArrayList<Decision>();
		comments = new ArrayList<Comment>();
		pageEnding = "+ Add an ending to this page";
	}
	
	/**
	 * This adds a tile to a page
	 * @param tile A Text, Video or Audio tile
	 */
	public void addTile(Tile tile) {
		tiles.add(tile);
	}
	
	/**
	 * This adds a new decision to a page
	 * @param decision The decision to add
	 */
	public void addDecision(Decision decision) {
		decisions.add(decision);
	}
	
	/**
	 * This deletes a Tile (called a segment here for some reason)
	 * @param tile What tile to delete
	 */
	public void deleteSegment(Tile tile) {
		
	}
	/**
	 * This adds a comment to the apge
	 * @param comment What comment to add
	 */
	public void addComment(Comment comment) {
		comments.add(comment);
	}
	
	/**
	 * Compares this page for deep equality with another page
	 * @param page What we are comparing to
	 */
	public boolean equals(Page page) {

		//Fail if different number of comments of segments
		if (comments.size() != page.getComments().size() ||
			tiles.size() != page.getTiles().size())
			return false;

		//Check that all comments are the same
		for (int i = 0; i < comments.size(); i++) {
			if (!comments.get(i).equals(page.getComments().get(i))) 
				return false;
		}

		//Check that all segments are the same
		for (int i = 0; i < tiles.size(); i++) {
			if (!tiles.get(i).equals(page.getTiles().get(i))) 
				return false;
		}
		
		//Check that the id's are the same
		if (!id.equals(page.id))
			return false;
		
		return true;
	}
	
	/**
	 * This returns the string representation of the page
	 * @return String Representation of a page
	 */
	public String toString() {
		return "" + id + comments + tiles;
	}
	
	/**
	 * This returns the comments from this page
	 * @return comments The comments
	 */
	public ArrayList<Comment> getComments() {
		return comments;
	}
	
	/**
	 * This returns the tiles of a page
	 * @return tiles The tiles
	 */
	public ArrayList<Tile> getTiles() {
		return tiles;
	}
	
	/**
	 * This returns the Decisions from a page
	 * @return decisions The Decisions
	 */
	public ArrayList<Decision> getDecisions() {
		return decisions;
	}
	
	/**
	 * This returns the UUID of the page
	 * @return id UUID of the page
	 */
	public UUID getId() {
		return id;
	}
	
	/**
	 * Update tile at position i in tiles to the passed tile.
	 * @param tile
	 * @param i
	 */
	public void updateTile(Object content, int i) {
		tiles.get(i).setContent(content);
	}
	
	/**
	 * Update the decision of this page at the passed position with the passed text and page reference
	 * 
	 * @param text The text to use in the updated decision
	 * @param page The page to link in the updated decision
	 * @param decisionNumber The position of the decision to update
	 */
	public void updateDecision(String text, Page page, int decisionNumber) {
		decisions.get(decisionNumber).updateDecision(text, page);
	}
	
	/**
	 * Sets the page ending to the passed ID
	 * 
	 * @param text The ID to set for the page ending
	 */
	public void setPageEnding(String text) {
		this.pageEnding = text;
	}
	
	/**
	 * Gets the page ending
	 * 
	 * @return The page ending
	 */
	public String getPageEnding() {
		return this.pageEnding;
	}
/*
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		
	}
	*/
	
	/**
	 * Gets the reference number
	 * 
	 * @return The reference number
	 */
	public int getRefNum() {
		return refNum;
	}
	
	/**
	 * Sets the reference number
	 * 
	 * @param refNum The new reference number
	 */
	public void setRefNum(int refNum) {
		this.refNum = refNum;
	}
}
