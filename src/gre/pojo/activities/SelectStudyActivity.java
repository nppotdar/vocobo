package gre.pojo.activities;

import gre.pojo.R;
import gre.pojo.activities.StudyActivity.FlashCardBean;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SelectStudyActivity extends ListActivity {
    /** Called when the activity is first created. */
	@Override
	 protected void onCreate(Bundle savedInstanceState) {
	  // TODO Auto-generated method stub
	  super.onCreate(savedInstanceState);
	  String[] BOXES = getResources().getStringArray(R.array.boxes_array);
 
	  setListAdapter(new ArrayAdapter<String>(this,
	            android.R.layout.simple_list_item_1, BOXES));
	  getListView().setTextFilterEnabled(true);
	 }

	 
	 @Override
	 protected void onListItemClick(ListView l, View v, int position, long id) {
	  // TODO Auto-generated method stub
	  super.onListItemClick(l, v, position, id);
	  Toast  temp1 = Toast.makeText(getApplicationContext(), "Hello",3);
	 temp1.show();  

	    Intent intent = new Intent(v.getContext(), StudyActivity.class);
	    String boxName =  l.getItemAtPosition(position).toString();
	    if(boxName.compareToIgnoreCase("High Frequency Words") == 0)
	    	boxName = "HF";
	    intent.putExtra("boxName", boxName);
	    setResult(RESULT_OK, intent);
	    startActivity(intent);
	  
	 }
        
}