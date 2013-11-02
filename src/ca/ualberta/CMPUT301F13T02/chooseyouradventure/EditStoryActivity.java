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

import ca.ualberta.CMPUT301F13T02.chooseyouradventure.elasticsearch.ESHandler;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
/**
 * This is the activity which is launched when a user
 * wants to edit a story's pages or tiles. 
 */
public class EditStoryActivity extends Activity {
	private Story currentStory;
	private ListView treePage;
	private Button createNew2;
	private Button deleteStory;
	private ArrayList<String> pageText = new ArrayList<String>();
	private ArrayList<Page> pageList = new ArrayList<Page>();
	private ArrayAdapter<String> adapter;
	private ESHandler eshandler = new ESHandler();
	private ControllerApp app;
	/**
	 * This binds the buttons the the views to this activity
	 * and sets the appropriate onclick listeners
	 */
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_story_activity);
        treePage = (ListView) findViewById(R.id.treeView);
        createNew2 = (Button) findViewById(R.id.createButton2);
        deleteStory = (Button) findViewById(R.id.deleteButton);
        createNew2.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
              try
			{
				createPage();
			} catch (HandlerException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
              adapter.notifyDataSetChanged();
            }
        });
        deleteStory.setOnClickListener(new OnClickListener() {
            
            public void onClick(View v) {
              deleteCurrentStory();
            }
        });       
        app = (ControllerApp) getApplication();
		currentStory = app.getStory();
		updateLists();
		/**
		 * Activity to restructure Click and longClick listeners to work in a list view
		 *  directly based on http://android.konreu.com/developer-how-to/click-long-press-event-listeners-list-activity/
		 */
		treePage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		    @Override
		    public void onItemClick(AdapterView<?> av, View v, int pos, long listNum) {
		        onListItemClick(v,pos,listNum);
		    }
		});
		adapter = new ArrayAdapter<String>(this,
				R.layout.list_item_base, pageText);
		treePage.setAdapter(adapter);
    }
	
	@Override
	public void onStart() {
        super.onStart();
        updateLists();
        adapter.notifyDataSetChanged();
    }
	
	protected void onListItemClick(View v, int pos, long id) {
		pageOptions(v, pos);
	}
	/**
	 * This moves the user to a different page
	 * @param input from a Listview
	 * @throws HandlerException
	 */
	public void jumpPage(View view, int pos) throws HandlerException {		
		Page toPage = currentStory.getPages().get(pos);
		app.setPage(toPage);
    	Intent intent = new Intent(this, ViewPageActivity.class);
    	startActivity(intent);
	}
	/**
	 * This creates a page
	 * @throws HandlerException
	 */
	private void createPage() throws HandlerException{
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setTitle("Create New");
    	final Page newPage = new Page();
    	
    	final EditText alertEdit = new EditText(this);
    	builder.setView(alertEdit);
    	builder.setMessage("Enter the title of your new page")
    	.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            	String pageTitle = alertEdit.getText().toString();
            	try
				{
            		newPage.setRefNum(currentStory.getCurrRefNum());
            		newPage.setTitle(pageTitle);
                	currentStory.addPage(newPage);
                	eshandler.updateStory(currentStory);
                	updateLists();
                	adapter.notifyDataSetChanged();
				} catch (HandlerException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	
            	
            }
        })
        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                
            }
        });
        builder.show();
    	
    	
    }
	/**
	 * This deletes a story
	 */
	private void deleteCurrentStory(){
		eshandler.deleteStory(currentStory);
		finish();
	}
	/**
	 * This updates the lists of pages in a story
	 */
	private void updateLists(){
		Page FP = currentStory.getFirstpage();
		pageList = currentStory.getPages();
		pageText.clear();
		
		
		if(pageList.size() != 0)
		{
			for (int i = 0; i < pageList.size(); i++) {
				String outList = "";
				if(pageList.get(i) == FP){
					outList = "{Start} ";
				}
				if(pageList.get(i).getDecisions().size() == 0)
					outList = outList + "{Endpoint} ";
				outList = outList + "(" + pageList.get(i).getRefNum() + ") " + pageList.get(i).getTitle();
				pageText.add(outList);
			}
		}
		
	}
	/**
	 * This shows the user a list of options on a story
	 * @param Input from longclick
	 */
	public void pageOptions(final View v, final int pos){
		final Page currentPage = pageList.get(pos);
		final Page FP = currentStory.getFirstpage();
		String[] titlesA = {"Goto/Edit","Rename","Cancel"};
		String[] titlesB = {"Goto/Edit","Rename","Assign as First Page","Delete","Cancel"};
		final String[] titles;
		if(currentPage == FP){titles = titlesA;}
		else{titles = titlesB;}
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog.Builder titleEditor = new AlertDialog.Builder(this);
        final EditText alertEdit = new EditText(this);
        builder.setTitle(R.string.page_options);
        builder.setItems(titles, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
            	
            	switch(item){
            	case(0):
            		try {
            			jumpPage(v, pos);
            		} catch (HandlerException e1) {
            			// TODO Auto-generated catch block
            			e1.printStackTrace();
            		}
            	break;
            	case(1):
            		
            		titleEditor.setTitle("Rename Page");
            		String titleText = currentPage.getTitle();
            		
            		alertEdit.setText(titleText);
            		titleEditor.setView(alertEdit);
            		titleEditor.setMessage("Enter the title of your story")
            		.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            			public void onClick(DialogInterface dialog, int id) {
            				String pageTitle = alertEdit.getText().toString();
            				try
            				{
            					
            					currentPage.setTitle(pageTitle);
            					updateLists();
            					eshandler.updateStory(currentStory);
                    			adapter.notifyDataSetChanged();
            					
            				} catch (HandlerException e)
            				{
            					// TODO Auto-generated catch block
            					e.printStackTrace();
            				}


            			}
            		})
            		.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            			public void onClick(DialogInterface dialog, int id) {

            			}
            		});
            		titleEditor.show();
            		break;
            	case(2):
            		UUID newID = currentPage.getId();
            		currentStory.setFirstpage(newID);
            		updateLists();
            		adapter.notifyDataSetChanged();
            		break;
            	case(3):
            		try
            		{	
            			pageList.remove(currentPage);
            			updateLists();
            			currentStory.deletePage(currentPage);
            			eshandler.updateStory(currentStory);
            			adapter.notifyDataSetChanged();

            			break;
            		} catch (HandlerException e)
            		{
            			// TODO Auto-generated catch block
            			e.printStackTrace();
            		}
            		break;

            	}

            }	
                });
        builder.show();
    }
}
