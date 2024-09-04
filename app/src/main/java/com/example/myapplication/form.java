package com.example.myapplication;

import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.DatePicker;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import android.widget.Spinner;
import android.view.View;
import java.util.Locale;

public class form extends AppCompatActivity {

    String[] item={"Sports","Event","Health Issue"};
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String>adapterItem;
    EditText StartDate;
    EditText EndDate;
    Calendar StartCalendar;
    Calendar EndCalendar;

    private EditText userNameEditText, usnEditText, startDateEditText, endDateEditText, certificateEditText, coordinatorEditText;
    private AutoCompleteTextView reasonEditText;
    private Button submitButton;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        userNameEditText = findViewById(R.id.username);
        usnEditText = findViewById(R.id.usn);
        startDateEditText = findViewById(R.id.Startdate);
        endDateEditText = findViewById(R.id.Enddate);
        reasonEditText = findViewById(R.id.reason);
        certificateEditText = findViewById(R.id.certificate);
        coordinatorEditText = findViewById(R.id.Coordinator);
        submitButton = findViewById(R.id.loginbtn);

        // Get a reference to the Firebase database
        databaseReference = FirebaseDatabase.getInstance().getReference("students");

        submitButton.setOnClickListener(v -> saveStudentForm());
        autoCompleteTextView=findViewById(R.id.reason);
        adapterItem=new ArrayAdapter<String>(this,R.layout.list_item,item);

        autoCompleteTextView.setAdapter(adapterItem);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                Toast.makeText(form.this, "Item: " + selectedItem, Toast.LENGTH_SHORT).show();
            }
        });

        StartDate = findViewById(R.id.Startdate);
        EndDate = findViewById(R.id.Enddate);

        StartCalendar = Calendar.getInstance();
        EndCalendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener startDateListener = (view, year, month, dayOfMonth) -> {
            StartCalendar.set(Calendar.YEAR, year);
            StartCalendar.set(Calendar.MONTH, month);
            StartCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel(StartDate, StartCalendar);
        };

        DatePickerDialog.OnDateSetListener endDateListener = (view, year, month, dayOfMonth) -> {
            EndCalendar.set(Calendar.YEAR, year);
            EndCalendar.set(Calendar.MONTH, month);
            EndCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel(EndDate, EndCalendar);
        };

        StartDate.setOnClickListener(view ->
                new DatePickerDialog(form.this, startDateListener,
                        StartCalendar.get(Calendar.YEAR),
                        StartCalendar.get(Calendar.MONTH),
                        StartCalendar.get(Calendar.DAY_OF_MONTH)).show());

        EndDate.setOnClickListener(view ->
                new DatePickerDialog(form.this, endDateListener,
                        EndCalendar.get(Calendar.YEAR),
                        EndCalendar.get(Calendar.MONTH),
                        EndCalendar.get(Calendar.DAY_OF_MONTH)).show());
    }

    private void updateLabel(EditText editText, Calendar calendar) {
        String myFormat = "MM/dd/yy.EEEE";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        editText.setText(dateFormat.format(calendar.getTime()));
    }

    private void saveStudentForm() {
        String name = userNameEditText.getText().toString().trim();
        String usn = usnEditText.getText().toString().trim();
        String startDate = startDateEditText.getText().toString().trim();
        String endDate = endDateEditText.getText().toString().trim();
        String reason = reasonEditText.getText().toString().trim();
        String certificate = certificateEditText.getText().toString().trim();
        String coordinator = coordinatorEditText.getText().toString().trim();

        if (!name.isEmpty() && !usn.isEmpty() && !startDate.isEmpty() && !endDate.isEmpty() && !reason.isEmpty() && !certificate.isEmpty() && !coordinator.isEmpty()) {
            StudentForm student = new StudentForm(name, usn, startDate, endDate, reason, certificate, coordinator);
            databaseReference.push().setValue(student).addOnSuccessListener(aVoid -> {
                Toast.makeText(form.this, "Data saved successfully!", Toast.LENGTH_SHORT).show();
                sendEmail(student); // Call method to send email after saving to database
                clearForm();
            }).addOnFailureListener(e -> {
                Toast.makeText(form.this, "Failed to save data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            });
        } else {
            Toast.makeText(this, "Please fill out all fields!", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearForm() {
        userNameEditText.setText("");
        usnEditText.setText("");
        startDateEditText.setText("");
        endDateEditText.setText("");
        reasonEditText.setText("");
        certificateEditText.setText("");
        coordinatorEditText.setText("");
    }

    private void sendEmail(StudentForm student) {
        String recipientEmail = "hemashrihema0@gmail.com"; // Replace with recipient's email address
        String subject = "Student Details";
        String messageBody = createMessageBody(student);

        // Validate recipient email, subject, and message before sending
        if (!recipientEmail.isEmpty() && !subject.isEmpty() && !messageBody.isEmpty()) {
            EmailService emailService = new EmailService(recipientEmail, subject, messageBody);
            emailService.execute();
            Toast.makeText(form.this, "Sending email...", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(form.this, "Please fill out all fields!", Toast.LENGTH_SHORT).show();
        }
    }

    private String createMessageBody(StudentForm student) {
        StringBuilder sb = new StringBuilder();
        sb.append("Student Name: ").append(student.name).append("\n");
        sb.append("USN: ").append(student.usn).append("\n");
        sb.append("Start Date: ").append(student.startDate).append("\n");
        sb.append("End Date: ").append(student.endDate).append("\n");
        sb.append("Reason: ").append(student.reason).append("\n");
        sb.append("Certificate: ").append(student.certificate).append("\n");
        sb.append("Coordinator: ").append(student.coordinator).append("\n");
        return sb.toString();
    }

    public static class StudentForm {
        public String name;
        public String usn;
        public String startDate;
        public String endDate;
        public String reason;
        public String certificate;
        public String coordinator;

        public StudentForm() {
            // Default constructor required for calls to DataSnapshot.getValue(StudentForm.class)
        }

        public StudentForm(String name, String usn, String startDate, String endDate, String reason, String certificate, String coordinator) {
            this.name = name;
            this.usn = usn;
            this.startDate = startDate;
            this.endDate = endDate;
            this.reason = reason;
            this.certificate = certificate;
            this.coordinator = coordinator;
        }
    }
}
