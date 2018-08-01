package zw.co.tsurutech.neatnote;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class NewNote extends AppCompatActivity {
    EditText edTitle, edBody;
    DBHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);
    }

    public void saveNewNote(View view) {
        helper = new DBHelper(this);
        edTitle = findViewById(R.id.txtSvTitle);
        edBody = findViewById(R.id.txtSvBody);

        try {
            if (helper.newNote(edTitle.getText().toString(), edBody.getText().toString())) {
                Toast.makeText(this, "Save successful", Toast.LENGTH_SHORT).show();
                this.finish();
            } else {
                Toast.makeText(this, "Error: Could not save note", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
