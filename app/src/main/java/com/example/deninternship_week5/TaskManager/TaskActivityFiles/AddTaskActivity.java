package com.example.deninternship_week5.TaskManager.TaskActivityFiles;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.deninternship_week5.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
public class AddTaskActivity extends AppCompatActivity {
    private EditText taskNameEditText, dueDateEditText,taskDesc,taskCateg;
    TextView addNew;
    private DatabaseReference dbRef;

    private CheckBox priorityCheckBox;
    private Button saveButton;
    private Calendar calendar;
    private TaskViewModel taskViewModel;
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.addtask);
    addNew = findViewById(R.id.titleAddNew);
    taskDesc = findViewById(R.id.taskDesc);
    taskNameEditText = findViewById(R.id.taskNameEditText);
    dueDateEditText = findViewById(R.id.dueDateEditText);
    priorityCheckBox = findViewById(R.id.priorityCheckBox);
    saveButton = findViewById(R.id.saveButton);
    dbRef = FirebaseDatabase.getInstance().getReference("tasks");

    taskCateg = findViewById(R.id.taskCategory);
    calendar = Calendar.getInstance();
    taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
    Intent intent = getIntent();
    boolean isEdit = intent.getBooleanExtra("isEdit", false);
    int taskId = intent.getIntExtra("taskId", -1);

    if (isEdit) {
        addNew.setText("Edit Task");
        taskNameEditText.setText(intent.getStringExtra("title"));
        taskDesc.setText(intent.getStringExtra("desc"));
        taskCateg.setText(intent.getStringExtra("category"));
        dueDateEditText.setText(intent.getStringExtra("date"));
        priorityCheckBox.setChecked("high".equalsIgnoreCase(intent.getStringExtra("priority")));
    }

    dueDateEditText.setOnClickListener(v -> {
        new DatePickerDialog(AddTaskActivity.this, dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    });

//    saveButton.setOnClickListener(v -> {
//        String taskName = taskNameEditText.getText().toString().trim();
//        String taskDescr = taskDesc.getText().toString().trim();
//        String taskcate= taskCateg.getText().toString().trim();
//        String dueDate = dueDateEditText.getText().toString().trim();
//        boolean isHighPriority = priorityCheckBox.isChecked();
//        String taskPrior = isHighPriority ? "high" : "low";
//
//        if (taskName.isEmpty() || dueDate.isEmpty()) {
//            Toast.makeText(this, "Please enter task name and due date.", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        Task task = new Task(taskName, taskDescr, dueDate, taskPrior, false,taskcate);
//
//        if (isEdit) {
//            task.setId(taskId);
//            task.setCompleted(intent.getBooleanExtra("completed", false));
//            taskViewModel.update(task);
//            Toast.makeText(this, "Task updated!", Toast.LENGTH_SHORT).show();
//        } else {
//            taskViewModel.insert(task);
//            Toast.makeText(this, "Task added!", Toast.LENGTH_SHORT).show();
//        }
//
//        finish();
//    });


//    saveButton.setOnClickListener(v -> {
//        String taskName = taskNameEditText.getText().toString().trim();
//        String taskDescr = taskDesc.getText().toString().trim();
//        String taskcate = taskCateg.getText().toString().trim();
//        String dueDate = dueDateEditText.getText().toString().trim();
//        boolean isHighPriority = priorityCheckBox.isChecked();
//        String taskPrior = isHighPriority ? "high" : "low";
//
//        if (taskName.isEmpty() || dueDate.isEmpty()) {
//            Toast.makeText(this, "Please enter task name and due date.", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        Task task = new Task(taskName, taskDescr, dueDate, taskPrior, false, taskcate);
//
//        if (isEdit) {
//            task.setId(taskId);
//            task.setCompleted(intent.getBooleanExtra("completed", false));
//
//            // Update in Firebase
//            dbRef.child(String.valueOf(taskId)).setValue(task)
//                    .addOnSuccessListener(unused ->
//                            Toast.makeText(this, "Task updated in Firebase!", Toast.LENGTH_SHORT).show()
//                    )
//                    .addOnFailureListener(e ->
//                            Toast.makeText(this, "Failed to update task in Firebase", Toast.LENGTH_SHORT).show()
//                    );
//
//            // Update in Room
//            taskViewModel.update(task);
//
//        } else {
//            // Generate unique ID for Firebase
//            String taskIdFirebase = dbRef.push().getKey();
//            task.setId(Integer.parseInt(String.valueOf(System.currentTimeMillis()).substring(5, 10))); // Optional ID
//            dbRef.child(taskIdFirebase).setValue(task)
//                    .addOnSuccessListener(unused ->
//                            Toast.makeText(this, "Task added to Firebase!", Toast.LENGTH_SHORT).show()
//                    )
//                    .addOnFailureListener(e ->
//                            Toast.makeText(this, "Failed to add task to Firebase", Toast.LENGTH_SHORT).show()
//                    );
//            // Insert in Room DB
//            taskViewModel.insert(task);
//        }
//        finish();
//    });
//}


    saveButton.setOnClickListener(v -> {
        String taskName = taskNameEditText.getText().toString().trim();
        String taskDescr = taskDesc.getText().toString().trim();
        String taskCate = taskCateg.getText().toString().trim();
        String dueDate = dueDateEditText.getText().toString().trim();
        boolean isHighPriority = priorityCheckBox.isChecked();
        String taskPrior = isHighPriority ? "high" : "low";

        // Validate input
        if (taskName.isEmpty() || dueDate.isEmpty()) {
            Toast.makeText(this, "Please enter task name and due date.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get current logged-in user
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() == null) {
            Toast.makeText(this, "Please login to save tasks.", Toast.LENGTH_SHORT).show();
            return;
        }
        String uid = auth.getCurrentUser().getUid();

        // Reference to user's task node
        DatabaseReference userTaskRef = FirebaseDatabase.getInstance()
                .getReference("users")
                .child(uid)
                .child("tasks");

        // Create task object
        Task task = new Task(taskName, taskDescr, dueDate, taskPrior, false, taskCate);

        // Generate unique task ID and save it inside the task
        String taskKey = userTaskRef.push().getKey();
        task.setFirebaseId(taskKey);

        // Insert or Update task
        if (getIntent().getBooleanExtra("isEdit", false)) {
            task.setId(getIntent().getIntExtra("taskId", -1));
            task.setCompleted(getIntent().getBooleanExtra("completed", false));
            userTaskRef.child(taskKey).setValue(task)
                    .addOnSuccessListener(aVoid ->
                            Toast.makeText(this, "Task updated!", Toast.LENGTH_SHORT).show()
                    )
                    .addOnFailureListener(e ->
                            Toast.makeText(this, "Update failed: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                    );
        } else {
            userTaskRef.child(taskKey).setValue(task)
                    .addOnSuccessListener(aVoid ->
                            Toast.makeText(this, "Task added!", Toast.LENGTH_SHORT).show()
                    )
                    .addOnFailureListener(e ->
                            Toast.makeText(this, "Save failed: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                    );
        }

        finish();
    });

}
private final DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, dayOfMonth) -> {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        dueDateEditText.setText(sdf.format(calendar.getTime()));
    };
}
