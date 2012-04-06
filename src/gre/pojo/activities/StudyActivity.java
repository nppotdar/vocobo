package gre.pojo.activities;

import gre.pojo.R;

import java.util.ArrayList;

import android.R.layout;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.gesture.Prediction;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class StudyActivity extends Activity implements OnGesturePerformedListener {
	
	private GestureLibrary mLibrary;
	public static int cursorId;
	public static Cursor cursor;
	public static TextView wordView;
	public static TextView typeView;
	public static TextView synView;
	public static TextView antView;
	public static TextView sentenceView;
	public static LinearLayout layout;
	public static LinearLayout v1;
	public static LinearLayout v2;
	public static int viewId;
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.study);
   
	     GestureOverlayView gestures = (GestureOverlayView) findViewById(R.id.gestures);
	     gestures.addOnGesturePerformedListener(this);
        
	     mLibrary = GestureLibraries.fromRawResource(this, R.raw.gestures);
	     if (!mLibrary.load()) {
	         finish();
	     }
        
   	 	gre.pojo.SqlHelper x = new gre.pojo.SqlHelper(getApplicationContext()); 
   	 	SQLiteDatabase temp =  x.loadDb(getApplicationContext());
   	 	getIntent().getStringExtra("boxName");
   	 	cursor = temp.rawQuery("SELECT * FROM "+getIntent().getStringExtra("boxName"), new String[]{});
 
   	 	
   	 	//EditText edit =  (EditText) findViewById(R.id.word);
		
   	 	
   	 	//((TextView)this.findViewById(R.id.word)).setText(FlashCardBean.word);
   	 	//((TextView)this.findViewById(R.id.type)).setText(FlashCardBean.type);
   	    //This finds the LinearLayout in main.xml that you gave an ID to
   	    layout = (LinearLayout)findViewById(R.id.my_linear_layout);
   	    v1 = new LinearLayout(getApplicationContext());
   	    v1.setOrientation(1);
   	    v2 = new LinearLayout(getApplicationContext());
   	    v2.setOrientation(1);
	    
   	    
   	    // Here, we initialise the TextViews
   	    wordView = new TextView(this);
   	    typeView = new TextView(this);
   	    synView = new TextView(this);
	    antView = new TextView(this);
	    sentenceView = new TextView(this);
	    
	    cursorId = 0;
	    FlashCardBean.setWordId(0);
	    layout.addView(v1);
	    layout.addView(v2);
	    v2.setVisibility(View.GONE);
	    viewId = 1;
	    // Here, we have to set these TextViews
	    /*wordView.setText(FlashCardBean.word);
   	    typeView.setText(FlashCardBean.type);
   	    synView.setText(FlashCardBean.type);
   	    antView.setText(FlashCardBean.type);
   	    sentenceView.setText(FlashCardBean.type);
   	    */
	    
   	    //Here, you have to add these TextViews to the LinearLayout


   	    layout.setOnClickListener(new View.OnClickListener() {
   	    @Override
   	    public void onClick(View pV) {
   	    	
   	    	if(viewId == 1)
   	    	{
   	    		viewId = 2;
   	    		v1.setVisibility(View.GONE);
   	    		v2.setVisibility(View.VISIBLE);
   	    	}else{
   	    	//inverting visibility
   	    		viewId = 1;
   	    		v1.setVisibility(View.VISIBLE);
   	    		v2.setVisibility(View.GONE);
   	    	}
   	    }
   	    });
   	    //Both TextViews should display at this point
    	Toast  temp1 = Toast.makeText(getApplicationContext(),FlashCardBean.word,3);
    	temp1.show();  
    }
	@Override
	public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
		ArrayList<Prediction> predictions = mLibrary.recognize(gesture);
		for (Prediction prediction : predictions) {
			if (prediction.score > 1.0) {
				
				//removing all views in database
				layout.removeAllViewsInLayout();
				if(prediction.name.compareToIgnoreCase("right") == 0)
				{
					//set The Word Cursor to next 
					FlashCardBean.setWordId(++cursorId);
				}
				if(prediction.name.compareToIgnoreCase("left") == 0)
				{
					//set The Word Cursor to next
					
					FlashCardBean.setWordId(--cursorId);
				}
				if(prediction.name.compareToIgnoreCase("down") == 0)
				{
					//set The Word Cursor to next 
					FlashCardBean.setWordId(cursorId);
				}
				v1.setVisibility(View.VISIBLE);
				layout.addView(v1);
				viewId = 1;
				v2.setVisibility(View.GONE);
				layout.addView(v2);
				
				//
				
				//adding the syn,ant and sentence view
				/*layout.addView(synView);
		   	    layout.addView(antView);
		   	    layout.addView(sentenceView);
				*/
				Toast.makeText(this, prediction.name, Toast.LENGTH_SHORT).show();
			}
		}
	}
	

/*	public static void flipCard(){
		//removing the existing views
		layout.removeView(wordView);
		layout.removeView(typeView);
		
		//adding the new views
		layout.addView(synView);
   	    layout.addView(antView);
   	    layout.addView(sentenceView);
		
		
	}*/

	
	
	
	
	//declared as inner static class so as to minimise function call and other overheads
	public static class FlashCardBean {
		
		public static String word;
		public static String type;
		public static String syn;
		public static String ant;
		public static String sentence;
		
		public static boolean setWordId(int id){
			
			//getting the upper bound
			int maxCursor = cursor.getCount() - 1;
			if(id < 0)
				id=cursorId = maxCursor;
			else
			if(id > maxCursor)
				id=cursorId = 0;
			
			//cursor of proper index, starts from 0
			cursor.moveToPosition(id);
			
			word = cursor.getString(1);
			switch(cursor.getInt(2)){
			
			case 0 : type = "noun";
				break;
			case 1:	 type = "verb";
				break;
			case 2:  type = "adjective";
				break;
			case 3:  type = "adverb";
				break;
			case 4:  type = "preposition";
				break;
			
			}
			syn  = cursor.getString(3);
			ant  = cursor.getString(4);
			sentence  = cursor.getString(5);
			
		    wordView.setText(FlashCardBean.word);
	   	    typeView.setText(FlashCardBean.type);
	   	    synView.setText(FlashCardBean.syn);
	   	    antView.setText(FlashCardBean.ant);
	   	    sentenceView.setText(FlashCardBean.sentence);
			
	   	    //successfully assigned the variables
	   	    v1.removeAllViewsInLayout();
	   	    v1.addView(wordView);
	   	    v1.addView(typeView);
	   	    
	   	    v2.removeAllViewsInLayout();
	   	    v2.addView(synView);
	   	    v2.addView(antView);
	   	    v2.addView(sentenceView);
			
	   	    return true;
		}

	}
	

}
