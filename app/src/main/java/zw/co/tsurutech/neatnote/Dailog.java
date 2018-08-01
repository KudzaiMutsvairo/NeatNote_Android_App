package zw.co.tsurutech.neatnote;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Dailog extends AppCompatActivity {

    String[] options = {"Edit", "Delete"};
    DBHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dailog);
        ListView listView = findViewById(R.id.dlist);

        helper = new DBHelper(this);

        final int id = getIntent().getIntExtra("id",0);

        listView.setAdapter(new ArrayAdapter<String>(Dailog.this, android.R.layout.simple_list_item_1, options));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (options[i].equals("Edit")){
                    Intent intent = new Intent(getApplication(), ViewNote.class);
                    intent.putExtra("id", id);
                    startActivity(intent);
                    Dailog.this.finish();
                }else if (options[i].equals("Delete")){
                    startDeleteProcess(id);
                }
            }
        });
    }

    private void startDeleteProcess(int id) {
        helper.deleteNote(id);
        Toast.makeText(this, "Note Deleted", Toast.LENGTH_SHORT).show();
        this.finish();
    }
}
