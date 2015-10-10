package com.example.arun.medicalpatientapp.UI.Activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.arun.medicalpatientapp.Constants;
import com.example.arun.medicalpatientapp.R;
import com.example.arun.medicalpatientapp.UI.Adapter.PrescriptionAdapter;
import com.example.arun.medicalpatientapp.UI.ParseObjects.Prescription;

import butterknife.Bind;
import butterknife.OnClick;

public class PrescriptionListActivity extends BaseActivity
{

    @Bind(R.id.prescription_recycler) RecyclerView mRecyclerView;
    private PrescriptionAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        manager.delegate = this;
    }

    @Override
    protected int getLayoutResource()
    {
        return R.layout.activity_prescription_list;
    }

    @Override
    protected void setupToolbar()
    {
        toolbar.setTitle("My Prescriptions");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void setupLayout()
    {
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);
        mAdapter = new PrescriptionAdapter(PrescriptionListActivity.this, manager.currentPatientPrescriptions);
        mRecyclerView.setAdapter(mAdapter);
    }

    @OnClick(R.id.fab_addPrescription)
    void AddPrescription()
    {
        mAdapter.add(new Prescription());
    }

    @Override
    public void processFinish(String result, int type)
    {
        Log.d("Process", "Process finished");
        if (type == Constants.TYPE_RECIEVED_PRESCRIPTIONS)
        {
            Log.d("Process", "Adding items");
            mAdapter.addItems(manager.currentPatientPrescriptions);
        }
    }


}
