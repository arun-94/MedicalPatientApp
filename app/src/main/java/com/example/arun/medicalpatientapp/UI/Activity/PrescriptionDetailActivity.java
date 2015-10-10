package com.example.arun.medicalpatientapp.UI.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.arun.medicalpatientapp.R;
import com.example.arun.medicalpatientapp.UI.Adapter.PrescribedMedicineAdapter;
import com.example.arun.medicalpatientapp.UI.Adapter.PrescriptionAdapter;
import com.example.arun.medicalpatientapp.UI.Custom.RecyclerItemClickListener;

import butterknife.Bind;

/**
 * Created by Puneet on 10-10-2015.
 */
public class PrescriptionDetailActivity extends BaseActivity
{
    @Bind(R.id.prescription_recycler) RecyclerView mRecyclerView;
    PrescribedMedicineAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        manager.delegate = this;
    }

    @Override
    protected int getLayoutResource()
    {
        return R.layout.activity_prescription_details;
    }

    @Override
    protected void setupToolbar()
    {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void setupLayout()
    {
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);
        mAdapter = new PrescribedMedicineAdapter(PrescriptionDetailActivity.this, manager.selectedPrescription.getMedicineList());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(PrescriptionDetailActivity.this, new RecyclerItemClickListener.OnItemClickListener()
        {
            @Override
            public void onItemClick(View view, int itempos)
            {
                Intent productListIntent = new Intent(PrescriptionDetailActivity.this, PrescriptionDetailActivity.class);
                startActivity(productListIntent);
            }
        }));
    }
}

