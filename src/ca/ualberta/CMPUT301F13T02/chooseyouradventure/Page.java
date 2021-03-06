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
 * This class represents a physical page of a story -- it is part of the model of the application.
 * 
 * Page objects are aggregated by Storys and are serialized and stored through a Handler implementation.
 *
 * @author all group members
 */

public class Page {
	
	private UUID id;
	private ArrayList<Comment> comments;
	private ArrayList<Tile> tiles;
	private ArrayList<Decision> decisions;
	private String title;
	private String pageEnding;
	private int refNum;
	private boolean fightingFrag = false;
	private int enemyHealth = 0;
	private String enemyName = "Enemy";
	
	/**
	 * This is the constructor that binds the arraylists to itself
	 */
	public Page() {
		id = UUID.randomUUID();
		tiles = new ArrayList<Tile>();
		decisions = new ArrayList<Decision>();
		comments = new ArrayList<Comment>();
		title = new String();
		pageEnding = ApplicationController.getInstance().getString(R.string.defaultEnding);
	}
	
	/**
	 * This is a special constructor that initializes a null page. It only exists within the Page selector 
	 * of a decision
	 * This exists as a vessel for random stories. When the Controller encounters this special
	 * Page, it will redirect the player through a random choice.
	 * @param enterAnything
	 */
	public Page(String enterAnything){
		id = null;
		tiles = new ArrayList<Tile>();
		decisions = new ArrayList<Decision>();
		comments = new ArrayList<Comment>();
		title = new String();
		pageEnding = ApplicationController.getInstance().getString(R.string.defaultEnding);
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
	 * This gets the title of the Page
	 * @return the title
	 */
	public String getTitle()
	{
		return title;
	}
	
	/**
	 * This returns the UUID of the page
	 * @return id UUID of the page
	 */
	public UUID getId() {
		return id;
	}

	/**
	 * Sets the page ending to the desired text.
	 * @param text
	 */
	public void setPageEnding(String text) {
		this.pageEnding = text;
	}

	/**
	 * Gets the page ending
	 * @return The page ending
	 */
	public String getPageEnding() {
		return this.pageEnding;
	}

	/**
	 * Sets the page's refNum to the given integer.
	 * @param refNum 
	 */
	public void setRefNum(int refNum) {
		this.refNum = refNum;
	}

	/**
	 * Gets the reference number
	 * @return The reference number
	 */
	public int getRefNum() {
		return refNum;
	}

	/**
	 * This returns the tiles of a page
	 * @return tiles The tiles
	 */
	public ArrayList<Tile> getTiles() {
		return tiles;
	}

	/**
	 * This adds a tile to a page
	 * @param tile A Text, Video or Audio tile
	 */
	public void addTile(Tile tile) {
		tiles.add(tile);
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
	 * This deletes a Tile.
	 * @param tile What tile to delete
	 */
	public void removeTile(int whichTile) {
		tiles.remove(whichTile);
	}
	
	/**
	 * This returns the Decisions from a page
	 * @return decisions The Decisions
	 */
	public ArrayList<Decision> getDecisions() {
		return decisions;
	}

	/**
	 * This adds a new decision to a page
	 * @param decision The decision to add
	 */
	public void addDecision(Decision decision) {
		decisions.add(decision);
	}
	
	/**
	 * Update the decision of this page at the passed position with the passed text and page reference
	 * 
	 * @param text The text to use in the updated decision
	 * @param page The page to link in the updated decision
	 * @param decisionNumber The position of the decision to update
	 */
	
	public void updateDecisionFight(String text, Page page, int decisionNumber, Counters counter) {
		decisions.get(decisionNumber).updateDecision(text, page, counter);
	}
	
	/**
	 * deletes the decision at whichDecision
	 *
	 * @param whichDecision
	 */
	public void deleteDecision(int whichDecision) {
		decisions.remove(whichDecision);
	}
	
	/**
	 * This returns the comments from this page
	 * @return comments The comments
	 */
	public ArrayList<Comment> getComments() {
		return comments;
	}

	/**
	 * This adds a comment to the page
	 * @param comment What comment to add
	 */
	public void addComment(Comment comment) {
		comments.add(comment);
	}
	
	/**
	 * Return whether this page includes combat or not.
	 * @return if this fragment is a fighting fragment
	 */

	public boolean getFightingState() {
		return fightingFrag;
	}

	/**
	 * Set whether this page includes combat or not.
	 * @param if this fragment is a fighting fragment
	 */
	public void setFightingFrag(boolean fightingFrag) {
		this.fightingFrag = fightingFrag;
	}

	/**
	 * @return the health of the enemy on this page
	 */
	public int getEnemyHealth() {
		return enemyHealth;
	}

	/**
	 * Sets the health of the enemy on this page
	 * @param enemyHealth
	 */
	public void setEnemyHealth(int enemyHealth) {
		this.enemyHealth = enemyHealth;
	}

	/**
	 * @return The name of the enemy on this page
	 */
	public String getEnemyName() {
		return enemyName;
	}

	/**
	 * Sets the name of the enemy for this page
	 * @param enemyName
	 */
	public void setEnemyName(String enemyName) {
		this.enemyName = enemyName;
	}
	
	/**
     * Compares this page for deep equality with another page
     * @param page What we are comparing to
     */
    public boolean equals(Page page) {
            //Check that the id's are the same
            if (!id.equals(page.id))
                    return false;
            
            return true;
    }

}
