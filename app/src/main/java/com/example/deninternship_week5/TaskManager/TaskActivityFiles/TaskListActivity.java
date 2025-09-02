////package com.example.deninternship_week5.TaskManager.TaskActivityFiles;
////
////import android.content.Intent;
////import android.graphics.Paint;
////import android.os.Bundle;
////import android.view.View;
////import android.widget.TextView;
////
////import androidx.appcompat.app.AppCompatActivity;
////import androidx.lifecycle.ViewModelProvider;
////import androidx.recyclerview.widget.LinearLayoutManager;
////import androidx.recyclerview.widget.RecyclerView;
////
////import com.example.deninternship_week5.R;
////import com.google.android.material.floatingactionbutton.FloatingActionButton;
////import com.google.firebase.auth.FirebaseAuth;
////import com.google.firebase.database.DatabaseReference;
////import com.google.firebase.database.FirebaseDatabase;
////
////public class TaskListActivity extends AppCompatActivity {
////
////    private TaskViewModel taskViewModel;
////    private TaskAdapter adapter;
////    private TextView greetingTextView, emptyView;
////
////
////    private FirebaseAuth auth;
////    private DatabaseReference dbRef;
////
////
////    @Override
////    protected void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        setContentView(R.layout.taskmanager);
////
////        greetingTextView = findViewById(R.id.userGreeting);
////
////
////        auth = FirebaseAuth.getInstance();
////        String currentUid = auth.getCurrentUser().getUid();
////
////        dbRef = FirebaseDatabase.getInstance()
////                .getReference("users")
////                .child(currentUid)
////                .child("tasks");
////
////        emptyView = findViewById(R.id.emptyView);
////     auth=FirebaseAuth.getInstance();
////        assert auth.getCurrentUser() != null;
////       String username= auth.getCurrentUser().getDisplayName();
////        // Greet user by name
////       // String userName = getIntent().getStringExtra("username");
////        if (username != null && !username.isEmpty()) {
////            greetingTextView.setText("Welcome, " + username + " 👋");
////        }
////
////        // RecyclerView setup
////        RecyclerView recyclerView = findViewById(R.id.taskRecyclerView);
////        recyclerView.setLayoutManager(new LinearLayoutManager(this));
////        adapter = new TaskAdapter();
////        recyclerView.setAdapter(adapter);
////
////        // ViewModel setup
////        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
////        taskViewModel.getAllTasks().observe(this, tasks -> {
////            adapter.setTasks(tasks);
////            if (tasks.isEmpty()) {
////                emptyView.setVisibility(View.VISIBLE);
////            } else {
////                emptyView.setVisibility(View.GONE);
////            }
////        });
////
////        // Adapter listener
//////        adapter.setOnTaskClickListener(new TaskAdapter.OnTaskClickListener() {
//////            @Override
//////            public void onDeleteClick(Task task) {
//////                taskViewModel.delete(task);
//////            }
//////
//////            @Override
//////            public void onMarkDoneClick(Task task, boolean isChecked) {
//////                task.setCompleted(isChecked);
//////                taskViewModel.update(task);
//////            }
//////        });
////        // inside onCreate:
////        adapter.setOnTaskClickListener(new TaskAdapter.OnTaskClickListener() {
////            @Override
////            public void onDeleteClick(Task task) {
////                taskViewModel.delete(task);
////            }
////
////            @Override
////            public void onMarkDoneClick(Task task, boolean isChecked) {
////                task.setCompleted(isChecked);
////                taskViewModel.update(task);
////            }
////
////            @Override
////            public void onEditClick(Task task) {
////                Intent intent = new Intent(TaskListActivity.this, AddTaskActivity.class);
////                intent.putExtra("isEdit", true);
////                intent.putExtra("taskId", task.getId());
////                intent.putExtra("title", task.getTitle());
////                intent.putExtra("desc", task.getDescription());
////                intent.putExtra("date", task.getDate());
////                intent.putExtra("priority", task.getPriority());
////                intent.putExtra("completed", task.isCompleted());
////                startActivity(intent);
////            }
////        });
////
////
////        // Floating button to add new task
////        FloatingActionButton addTaskFab = findViewById(R.id.addTaskButton);
////        addTaskFab.setOnClickListener(v -> {
////            Intent intent = new Intent(TaskListActivity.this, AddTaskActivity.class);
////            startActivity(intent);
////        });
////    }
////}
//
//
//package com.example.deninternship_week5.TaskManager.TaskActivityFiles;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.lifecycle.ViewModelProvider;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.deninternship_week5.R;
//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class TaskListActivity extends AppCompatActivity {
//
//    private TaskViewModel taskViewModel;
//    private TaskAdapter adapter;
//    private TextView greetingTextView, emptyView;
//
//    private FirebaseAuth auth;
//    private DatabaseReference dbRef;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.taskmanager);
//
//        greetingTextView = findViewById(R.id.userGreeting);
//        emptyView = findViewById(R.id.emptyView);
//
//        // Firebase authentication instance
//        auth = FirebaseAuth.getInstance();
//        if (auth.getCurrentUser() == null) {
//            Toast.makeText(this, "Please login first", Toast.LENGTH_SHORT).show();
//            finish();
//            return;
//        }
//
//        // Get user details
//        String userId = auth.getCurrentUser().getUid();
//        String username = auth.getCurrentUser().getDisplayName();
//
//        // Greeting
//        if (username != null && !username.isEmpty()) {
//            greetingTextView.setText("Welcome, " + username + " 👋");
//        } else {
//            greetingTextView.setText("Welcome 👋");
//        }
//
//        // Firebase DB reference for this user's tasks
//        dbRef = FirebaseDatabase.getInstance()
//                .getReference("users")
//                .child(userId)
//                .child("tasks");
//
//        // RecyclerView setup
//        RecyclerView recyclerView = findViewById(R.id.taskRecyclerView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        adapter = new TaskAdapter();
//        recyclerView.setAdapter(adapter);
//
//        // ViewModel setup (for Room)
//        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
//
//        // Load tasks from Firebase
//        loadTasksFromFirebase();
//
//        // Adapter actions
//        adapter.setOnTaskClickListener(new TaskAdapter.OnTaskClickListener() {
//            @Override
//            public void onDeleteClick(Task task) {
//               // deleteTaskFromFirebase(task);
//            }
//
//            @Override
//            public void onMarkDoneClick(Task task, boolean isChecked) {
//                task.setCompleted(isChecked);
//              //  updateTaskInFirebase(task);
//            }
//
//            @Override
//            public void onEditClick(Task task) {
//                Intent intent = new Intent(TaskListActivity.this, AddTaskActivity.class);
//                intent.putExtra("isEdit", true);
//                intent.putExtra("taskId", task.getId());
//                intent.putExtra("title", task.getTitle());
//                intent.putExtra("desc", task.getDescription());
//                intent.putExtra("date", task.getDate());
//                intent.putExtra("priority", task.getPriority());
//                intent.putExtra("completed", task.isCompleted());
//                startActivity(intent);
//            }
//        });
//
//        // Floating button to add new task
//        FloatingActionButton addTaskFab = findViewById(R.id.addTaskButton);
//        addTaskFab.setOnClickListener(v -> {
//            Intent intent = new Intent(TaskListActivity.this, AddTaskActivity.class);
//            startActivity(intent);
//        });
//    }
//
//    private void loadTasksFromFirebase() {
//        dbRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                List<Task> firebaseTasks = new ArrayList<>();
//                for (DataSnapshot ds : snapshot.getChildren()) {
//                    Task task = ds.getValue(Task.class);
//                    if (task != null) {
//                        firebaseTasks.add(task);
//                    }
//                }
//
//                adapter.setTasks(firebaseTasks);
//
//                // Update empty view visibility
//                if (firebaseTasks.isEmpty()) {
//                    emptyView.setVisibility(TextView.VISIBLE);
//                } else {
//                    emptyView.setVisibility(TextView.GONE);
//                }
//
//                // Optional: Sync tasks to Room for offline
//                //taskViewModel.insertAll(firebaseTasks);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(TaskListActivity.this, "Failed to load tasks: " + error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
////    private void deleteTaskFromFirebase(Task task) {
////        dbRef.child(String.valueOf(task.getId())).removeValue()
////                .addOnSuccessListener(unused ->
////                        Toast.makeText(this, "Task deleted", Toast.LENGTH_SHORT).show()
////                )
////                .addOnFailureListener(e ->
////                        Toast.makeText(this, "Failed to delete task", Toast.LENGTH_SHORT).show()
////                );
////        taskViewModel.delete(task);
////    }
//
////    private void updateTaskInFirebase(Task task) {
////        dbRef.child(String.valueOf(task.getId())).setValue(task)
////                .addOnSuccessListener(unused ->
////                        Toast.makeText(this, "Task updated", Toast.LENGTH_SHORT).show()
////                )
////                .addOnFailureListener(e ->
////                        Toast.makeText(this, "Failed to update task", Toast.LENGTH_SHORT).show()
////                );
////        taskViewModel.update(task);
////    }
//}


package com.example.deninternship_week5.TaskManager.TaskActivityFiles;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deninternship_week5.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TaskListActivity extends AppCompatActivity {

    private TaskAdapter adapter;
    private TextView greetingTextView, emptyView;

    private FirebaseAuth auth;
    private DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.taskmanager);

        greetingTextView = findViewById(R.id.userGreeting);
        emptyView = findViewById(R.id.emptyView);

        // Firebase Auth
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() == null) {
            Toast.makeText(this, "Please login first", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        String userId = auth.getCurrentUser().getUid();
        String username = auth.getCurrentUser().getDisplayName();

        // Set greeting
        greetingTextView.setText(username != null && !username.isEmpty()
                ? "Welcome, " + username + " 👋"
                : "Welcome 👋");

        // Firebase DB reference
        dbRef = FirebaseDatabase.getInstance()
                .getReference("users")
                .child(userId)
                .child("tasks");

        // RecyclerView setup
        RecyclerView recyclerView = findViewById(R.id.taskRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TaskAdapter();
        recyclerView.setAdapter(adapter);

        // Load tasks
        loadTasksFromFirebase();

        // Task adapter actions
        adapter.setOnTaskClickListener(new TaskAdapter.OnTaskClickListener() {
            @Override
            public void onDeleteClick(Task task) {
                deleteTaskFromFirebase(task);
            }

            @Override
            public void onMarkDoneClick(Task task, boolean isChecked) {
                task.setCompleted(isChecked);
                updateTaskInFirebase(task);
            }

            @Override
            public void onEditClick(Task task) {
                Intent intent = new Intent(TaskListActivity.this, AddTaskActivity.class);
                intent.putExtra("isEdit", true);
                intent.putExtra("firebaseId", task.getFirebaseId()); // use firebaseId for edit
                intent.putExtra("title", task.getTitle());
                intent.putExtra("desc", task.getDescription());
                intent.putExtra("date", task.getDate());
                intent.putExtra("priority", task.getPriority());
                intent.putExtra("category", task.getCategory());
                intent.putExtra("completed", task.isCompleted());
                startActivity(intent);
            }
        });

        // Add new task button
        FloatingActionButton addTaskFab = findViewById(R.id.addTaskButton);
        addTaskFab.setOnClickListener(v -> {
            Intent intent = new Intent(TaskListActivity.this, AddTaskActivity.class);
            startActivity(intent);
        });
    }

    private void loadTasksFromFirebase() {
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Task> firebaseTasks = new ArrayList<>();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Task task = ds.getValue(Task.class);
                    if (task != null) {
                        task.setFirebaseId(ds.getKey()); // save the unique key for update/delete
                        firebaseTasks.add(task);
                    }
                }

                adapter.setTasks(firebaseTasks);

                emptyView.setVisibility(firebaseTasks.isEmpty() ? TextView.VISIBLE : TextView.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(TaskListActivity.this,
                        "Failed to load tasks: " + error.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteTaskFromFirebase(Task task) {
        if (task.getFirebaseId() == null) return;
        dbRef.child(task.getFirebaseId()).removeValue()
                .addOnSuccessListener(unused ->
                        Toast.makeText(this, "Task deleted", Toast.LENGTH_SHORT).show()
                )
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Delete failed: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                );
    }

    private void updateTaskInFirebase(Task task) {
        if (task.getFirebaseId() == null) return;
        dbRef.child(task.getFirebaseId()).setValue(task)
                .addOnSuccessListener(unused ->
                        Toast.makeText(this, "Task updated", Toast.LENGTH_SHORT).show()
                )
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Update failed: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                );
    }
}
