package com.example.myapplication;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.android.gms.tasks.Task;
public class fetch {



    public class DataStudentForm {
        private DatabaseReference databaseReference;

        public DataStudentForm() {
            FirebaseDatabase db = FirebaseDatabase.getInstance();
            databaseReference = db.getReference(StudentForm.class.getSimpleName());
        }

        public Task<Void> add(StudentForm form) {
            return databaseReference.push().setValue(form);
        }

        public void getAll(ValueEventListener listener) {
            databaseReference.addValueEventListener(listener);
        }

        public void get(String key, ValueEventListener listener) {
            databaseReference.child(key).addListenerForSingleValueEvent(listener);
        }

        public Task<Void> update(String key, StudentForm form) {
            return databaseReference.child(key).setValue(form);
        }

        public Task<Void> delete(String key) {
            return databaseReference.child(key).removeValue();
        }
    }

}
