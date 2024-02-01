package com.example.butApp.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.butApp.R;
import com.example.butApp.models.LabelModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class LabelFragment extends Fragment {

    EditText label,desc;

    LinearLayout layout;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_label,container,false);
        label = root.findViewById(R.id.ltitle);
        desc  =  root.findViewById(R.id.desc);
        layout = root.findViewById(R.id.layout);

        Button add = root.findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Submit(view);
            }
        });
        getLabel();
        return root;
    }
    public void Submit(View view){
        String str_label = label.getText().toString();
        String str_desc = desc.getText().toString();

        if(!str_label.isEmpty() && !str_desc.isEmpty()){

            LabelModel label = new LabelModel(str_label,str_desc);
            db.collection("LabelModel").add(label)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(getContext(),"Label Başarıyla eklendi",Toast.LENGTH_SHORT).show();
                            getLabel();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(),"Bir hata ile karşılaşıldı",Toast.LENGTH_SHORT).show();
                        }
                    });

        }else{
            Toast.makeText(getContext(),"Lütfen  Bilgileri doldurunuz",Toast.LENGTH_SHORT).show();
        }
    }
    public void getLabel(){
        layout.removeAllViews();
        db.collection("LabelModel").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        LinearLayout child = new LinearLayout(requireContext());
                        TextView textView = new TextView(requireContext());
                        ImageView img = new ImageView(requireContext());
                        img.setImageResource(R.drawable.baseline_bookmark_add_24);
                        textView.setText(document.getString("label"));
                        textView.setTextSize(20);
                        child.addView(img);
                        child.addView(textView);
                        layout.addView(child);
                    }
                }else{
                    Toast.makeText(getContext(),"İnternet Bağlantınızı kontrol Ediniz",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}