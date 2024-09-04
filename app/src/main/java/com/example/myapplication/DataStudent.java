package com.example.myapplication;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DataStudent {
    private DatabaseReference databaseReference;
    public DataStudent()
    {
        FirebaseDatabase db =FirebaseDatabase.getInstance();
        databaseReference =db.getReference(Student.class.getSimpleName());
    }
    public Task<Void> add(Student stu){

       return databaseReference.push().setValue(stu);

    }
}
