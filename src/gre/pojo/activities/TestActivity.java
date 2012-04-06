package gre.pojo.activities;

import gre.pojo.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class TestActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
    	Toast  temp = Toast.makeText(getApplicationContext(),"Hello",3);
    	temp.show();   
    }
}
