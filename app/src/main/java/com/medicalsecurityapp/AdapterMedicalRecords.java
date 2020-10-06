package com.medicalsecurityapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterMedicalRecords extends RecyclerView.Adapter<AdapterMedicalRecords.Viewholder> {

    private Context context;
    private ArrayList<MedicalRecordModule> medicalRecordModuleArrayList;

    OnRecordClickListener onRecordClickListener;

    public AdapterMedicalRecords(Context context, ArrayList<MedicalRecordModule> medicalRecordModuleArrayList) {
        this.context = context;
        this.medicalRecordModuleArrayList = medicalRecordModuleArrayList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.allrecord_layout,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        MedicalRecordModule medicalRecordModule = medicalRecordModuleArrayList.get(position);

        holder.date.setText(medicalRecordModule.getDate());
        holder.doctor.setText(medicalRecordModule.getDoctor());
        holder.issues.setText(medicalRecordModule.getIssues());
        holder.medicine.setText(medicalRecordModule.getMedicine());
        holder.test.setText(medicalRecordModule.getTestreports());


    }

    @Override
    public int getItemCount() {
        return medicalRecordModuleArrayList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView date, doctor, issues, medicine, test;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.date_id);
            doctor = itemView.findViewById(R.id.doctor_id);
            issues = itemView.findViewById(R.id.issues_id);
            medicine = itemView.findViewById(R.id.medicine_id);
            test = itemView.findViewById(R.id.test_id);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            if (onRecordClickListener != null)
            {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION)
                {
                    onRecordClickListener.OnRecordClick(position);
                }
            }

        }
    }

    public interface OnRecordClickListener{

        void OnRecordClick(int position);


    }

    public void setOnRecordClickListener(OnRecordClickListener listener)
    {
        onRecordClickListener = listener;
    }

}
