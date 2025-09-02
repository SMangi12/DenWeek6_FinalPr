package com.example.deninternship_week5.TaskManager.TaskActivityFiles;

import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.deninternship_week5.R;

public class TaskDetailActivity extends AppCompatActivity {
    TextView titleText, descriptionText, dueDateText, priorityText, categoryText;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.taskdetails);
        titleText = findViewById(R.id.detailTitle);
        descriptionText = findViewById(R.id.detailDescription);
        dueDateText = findViewById(R.id.detailDueDate);
        priorityText = findViewById(R.id.detailPriority);
        categoryText = findViewById(R.id.detailCategory);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String title = extras.getString("title", "N/A");
            String description = extras.getString("description", "N/A");
            String dueDate = extras.getString("dueDate", "N/A");
            String priority = extras.getString("priority", "N/A");
            String category = extras.getString("category", "N/A");
            titleText.setText(title);
            descriptionText.setText(description);
            dueDateText.setText("Due Date: " + dueDate);
            priorityText.setText("Priority: " + priority);
            categoryText.setText("Category: " + category);
        }
    }
}