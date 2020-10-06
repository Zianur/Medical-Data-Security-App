package com.medicalsecurityapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterPatientsRecords extends RecyclerView.Adapter<AdapterPatientsRecords.Viewholder> {

    private Context context;
    private ArrayList<MedicalRecordModule> recordModuleArrayList;

    public AdapterPatientsRecords(Context context, ArrayList<MedicalRecordModule> recordModuleArrayList) {
        this.context = context;
        this.recordModuleArrayList = recordModuleArrayList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.allpatientdatabase,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        MedicalRecordModule medicalRecordModule = recordModuleArrayList.get(position);

        holder.name.setText(medicalRecordModule.getUserfullname());
        holder.age.setText(medicalRecordModule.getAge());
        holder.userid.setText(medicalRecordModule.getId());

    }

    @Override
    public int getItemCount() {
        return recordModuleArrayList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        TextView name, age, userid;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.patient_name_id);
            age = itemView.findViewById(R.id.patient_age_id);
            userid = itemView.findViewById(R.id.patient_user_id);
        }
    }
}
