package com.example.myapplication;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DataStudentForm {
    private DatabaseReference databaseReference;
    public DataStudentForm()
    {
        FirebaseDatabase db =FirebaseDatabase.getInstance();
        databaseReference =db.getReference(StudentForm.class.getSimpleName());
    }
    public Task<Void> add(StudentForm stuf){

        return databaseReference.push().setValue(stuf);

    }
}

