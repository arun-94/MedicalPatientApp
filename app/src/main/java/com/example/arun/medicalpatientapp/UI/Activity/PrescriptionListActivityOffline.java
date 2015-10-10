package com.example.arun.medicalpatientapp.UI.Activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.arun.medicalpatientapp.Constants;
import com.example.arun.medicalpatientapp.R;
import com.example.arun.medicalpatientapp.UI.Adapter.PrescriptionAdapter;

import butterknife.Bind;

public class PrescriptionListActivityOffline extends BaseActivity
{

    @Bind(R.id.prescription_recycler) RecyclerView mRecyclerView;
    @Bind(R.id.progress_view) ProgressBar progressLoading;

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
        /*progressLoading.setVisibility(View.VISIBLE);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);
        mAdapter = new PrescriptionAdapter(PrescriptionListActivityOffline.this, null);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(PrescriptionListActivityOffline.this, new RecyclerItemClickListener.OnItemClickListener()
        {
            @Override
            public void onItemClick(View view, int itempos)
            {
                manager.selectedPrescription = manager.currentPatientPrescriptions.get(itempos);
                Intent productListIntent = new Intent(PrescriptionListActivityOffline.this, PrescriptionDetailActivity.class);
                startActivity(productListIntent);
            }
        }));*/
    }

    @Override
    public void processFinish(String result, int type)
    {
        Log.d("Process", "Process finished");
        if (type == Constants.TYPE_RECIEVED_PRESCRIPTIONS)
        {
            progressLoading.setVisibility(View.INVISIBLE);
            Log.d("Process", "Adding items");
            mAdapter.addItems(manager.currentPatientPrescriptions);
        }
    }
}
