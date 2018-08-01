package zw.co.tsurutech.neatnote;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DBHelper helper;
    Cursor cursor;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        helper = new DBHelper(this);
        listView = findViewById(R.id.noteList);

        listDisplay();


        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                cursor.moveToPosition(i);
                int rowId = cursor.getInt(cursor.getColumnIndexOrThrow(helper.KEY_ID));
                startEditActivity(rowId);
            }
        });

        registerClicks();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                        */
                newNote();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        listDisplay();
    }

    @Override
    public void onStart(){
        super.onStart();
        listDisplay();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete_all) {
            Toast.makeText(this, "Deleting all Notes", Toast.LENGTH_SHORT).show();
            helper.deleteAll();
            listDisplay();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Code for mapping db contents to list item
    public void listDisplay(){
        cursor = helper.fetchNotes();
        String[] from = {helper.KEY_TITLE, helper.KEY_TIME, helper.KEY_DATE};
        int[] to ={R.id.lstVTitle, R.id.lstVTime, R.id.lstVDate};

        ListAdapter adapter = new SimpleCursorAdapter(this, R.layout.list_item,cursor, from, to, 0);
        listView.setAdapter(adapter);
    }

    public void newNote() {
        Intent intent = new Intent(getBaseContext(), NewNote.class);
        startActivity(intent);
    }

    public void registerClicks(){

        listView.setLongClickable(true);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, final long id) {
                view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                cursor.moveToPosition(position);
                final int rowId = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
                Intent intent = new Intent(MainActivity.this, Dailog.class);
                intent.putExtra("id", rowId);
                startActivity(intent);
                return true;
            }

        });

    }


    private void startEditActivity(int id){
        //send the id here
        Intent intent = new Intent(this, ViewNote.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

}
