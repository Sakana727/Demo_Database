package sg.edu.rp.c346.id22020383.demodatabase;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnInsert, btnGetTasks;
    TextView tvResults;
    ListView lv;

    EditText etDescription, etDate;
    boolean ascendingOrder = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnInsert = findViewById(R.id.btnInsert);
        btnGetTasks = findViewById(R.id.btnGetTasks);
        tvResults = findViewById(R.id.tvResults);
        lv =  findViewById(R.id.lv);
        etDescription = findViewById(R.id.etDescription);
        etDate= findViewById(R.id.etDate);

        // Create an empty ArrayList to store tasks
        ArrayList<Task> tasks = new ArrayList<>();

        // Create an ArrayAdapter and bind it to the ListView
        ArrayAdapter<Task> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tasks);
        lv.setAdapter(adapter);




        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String description = etDescription.getText().toString();
                String date = etDate.getText().toString();

                // Create the DBHelper object, passing in the activity's Context
                DBHelper db = new DBHelper(MainActivity.this);

                // Insert a task with user-entered description and date
                db.insertTask(description, date);

                // Clear the EditTexts after inserting the task
                etDescription.setText("");
                etDate.setText("");
            }
        });


        btnGetTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper db = new DBHelper(MainActivity.this);

                // Toggle the sort order
                ascendingOrder = !ascendingOrder;

                // Retrieve tasks from the database, using the updated sort order
                ArrayList<Task> tasks = db.getTasks(ascendingOrder);

                db.close();

                ArrayAdapter<Task> adapter = (ArrayAdapter<Task>) lv.getAdapter();
                adapter.clear();
                adapter.addAll(tasks);
                adapter.notifyDataSetChanged();
            }
        });

    }
}
