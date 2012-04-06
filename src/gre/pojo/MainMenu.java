package gre.pojo;

import gre.pojo.activities.AboutActivity;
import gre.pojo.activities.MySearchableActivity;
import gre.pojo.activities.SelectStudyActivity;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainMenu extends Activity {
    /** Called when the activity is first created. */
	Cursor z;
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    public void switchToSelectStudyActivity(View view){ 	
       	Intent myIntent = new Intent(view.getContext(), SelectStudyActivity.class);
    	startActivityForResult(myIntent, 0);
  
    }
    public void switchToTestActivity(View view){
    	Toast  temp = Toast.makeText(getApplicationContext(),"Hi",3);
    	temp.show();
        Intent myIntent = new Intent(view.getContext(), MySearchableActivity.class);
        startActivityForResult(myIntent, 0);    	
    }
    public void switchToAboutActivity(View view){
    	Toast  temp = Toast.makeText(getApplicationContext(),"Hi",3);
    	temp.show();
        Intent myIntent = new Intent(view.getContext(), AboutActivity.class);
        startActivityForResult(myIntent, 0);
    }
    public void switchToQuitActivity(View view){
    	Toast  temp = Toast.makeText(getApplicationContext(),"Hi",3);
    	temp.show();

    }
}