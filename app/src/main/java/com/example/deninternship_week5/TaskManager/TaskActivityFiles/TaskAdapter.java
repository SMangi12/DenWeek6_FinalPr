package com.example.deninternship_week5.TaskManager.TaskActivityFiles;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deninternship_week5.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private List<Task> taskList = new ArrayList<>();
    private OnTaskClickListener listener;

    public interface OnTaskClickListener {
        void onDeleteClick(Task task);
        void onMarkDoneClick(Task task, boolean isChecked);
        void onEditClick(Task task); // NEW
    }

    public void setOnTaskClickListener(OnTaskClickListener listener) {
        this.listener = listener;
    }

    public void setTasks(List<Task> tasks) {
        Collections.sort(tasks, (t1, t2) -> {
            if (t1.getPriority().equalsIgnoreCase("High") && !t2.getPriority().equalsIgnoreCase("High")) return -1;
            if (!t1.getPriority().equalsIgnoreCase("High") && t2.getPriority().equalsIgnoreCase("High")) return 1;
            return t1.getDate().compareTo(t2.getDate());
        });

        this.taskList = tasks;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.taskcard, parent, false);
        return new TaskViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), TaskDetailActivity.class);
            intent.putExtra("title", task.getTitle());
            intent.putExtra("description", task.getDescription());
            intent.putExtra("dueDate", task.getDate());
            intent.putExtra("priority", task.getPriority());
            intent.putExtra("category", task.getCategory());
            v.getContext().startActivity(intent);
        });

        holder.taskTitle.setText(task.getTitle());
        holder.taskDueDate.setText("Due: " + task.getDate());

        if (task.getPriority().equalsIgnoreCase("High")) {
            holder.priorityTag.setVisibility(View.VISIBLE);
        } else {
            holder.priorityTag.setVisibility(View.GONE);
        }

        String daysRemainingText = calculateDaysRemaining(task.getDate(), holder.daysRemaining);
        holder.daysRemaining.setText(daysRemainingText);

        holder.markDoneCheckbox.setChecked(task.isCompleted());
        if (task.isCompleted()) {
            holder.taskTitle.setPaintFlags(holder.taskTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            holder.taskTitle.setPaintFlags(holder.taskTitle.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
        }

        holder.deleteButton.setOnClickListener(v -> {
            if (listener != null) listener.onDeleteClick(task);
        });

        holder.markDoneCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (listener != null) listener.onMarkDoneClick(task, isChecked);
        });

        holder.editTaskV.setOnClickListener(v -> {
            if (listener != null) listener.onEditClick(task); // NEW
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView taskTitle, taskDueDate, daysRemaining, priorityTag,editTaskV,addNew;
        ImageView deleteButton;
        CheckBox markDoneCheckbox;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            addNew = itemView.findViewById(R.id.titleAddNew);
            taskTitle = itemView.findViewById(R.id.taskTitle);
            taskDueDate = itemView.findViewById(R.id.taskDueDate);
            daysRemaining = itemView.findViewById(R.id.daysRemaining);
            priorityTag = itemView.findViewById(R.id.priorityTag);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            markDoneCheckbox = itemView.findViewById(R.id.markDoneCheckbox);
           editTaskV=itemView.findViewById(R.id.editTask);
        }
    }

    private String calculateDaysRemaining(String dateString, TextView daysRemainingView) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            Date dueDate = sdf.parse(dateString);
            Date currentDate = sdf.parse(sdf.format(new Date()));

            long diff = dueDate.getTime() - currentDate.getTime();
            long days = TimeUnit.MILLISECONDS.toDays(diff);

            if (days < 0) {
                daysRemainingView.setTextColor(Color.RED);
                return "Overdue";
            } else if (days <= 2) {
                daysRemainingView.setTextColor(Color.parseColor("#FFA000"));
            } else {
                daysRemainingView.setTextColor(Color.parseColor("#009688"));
            }

            return days + " day(s) left";

        } catch (ParseException e) {
            daysRemainingView.setTextColor(Color.GRAY);
            return "Invalid Date";
        }
    }
}
