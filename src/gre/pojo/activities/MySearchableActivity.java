package gre.pojo.activities;

import gre.pojo.R;
import gre.pojo.Records;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MySearchableActivity extends ListActivity {
	private final List<String> searchResults = new ArrayList<String>();
	private Records records;
	private LayoutInflater layoutInflater;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchable);
		records = new Records(this);
		layoutInflater = LayoutInflater.from(this);
		final StringListAdapter adapter = new StringListAdapter(searchResults);
		getListView().setAdapter(adapter);

		final EditText text = (EditText) findViewById(R.id.txt);
		Button search = (Button) findViewById(R.id.search);
		search.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				searchResults.clear();
				searchResults.addAll(records.search(text.getText().toString()));
				adapter.notifyDataSetChanged();
			}
		});

		Button add = (Button) findViewById(R.id.add);
		add.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				}
		});
    }

	private class StringListAdapter extends BaseAdapter {
		private final List<String> stringList;

		public StringListAdapter(List<String> stringList) {
			this.stringList = stringList;
		}

		@Override
		public int getCount() {
			return stringList.size();
		}

		@Override
		public Object getItem(int position) {
			return stringList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final View view;
			if (convertView == null) {
				view = layoutInflater.inflate(android.R.layout.simple_list_item_1, null);
			} else {
				view = convertView;
			}
			TextView textView = (TextView) view.findViewById(android.R.id.text1);
			textView.setText((String) getItem(position));
			return view;
		}
	}
}