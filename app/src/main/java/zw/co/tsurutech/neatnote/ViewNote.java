package zw.co.tsurutech.neatnote;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ViewNote extends AppCompatActivity {

    EditText edTitle;
    EditText edBody;
    int id = 0;
    DBHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_note);

        helper = new DBHelper(this);

        id = getIntent().getIntExtra("id",0);
        edTitle = findViewById(R.id.txtTitle);
        edBody = findViewById(R.id.txtBody);

        String title = helper.fetchTitle(id);
        String body = helper.fetchBody(id);

        edTitle.setText(title);
        edBody.setText(body);

        Button btnUpdate = findViewById(R.id.buttonUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(helper.editNote(id, edTitle.getText().toString(), edBody.getText().toString()))
                {
                    Toast.makeText(getBaseContext(), "Note updated", Toast.LENGTH_SHORT).show();
                    ViewNote.this.finish();
                }else{
                    Toast.makeText(getBaseContext(), "Error updating Note", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button btnDelete = findViewById(R.id.buttonDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helper.deleteNote(id);
                ViewNote.this.finish();
            }
        });
    }


}

