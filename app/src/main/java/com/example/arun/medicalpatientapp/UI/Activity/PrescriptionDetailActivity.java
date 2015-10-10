package com.example.arun.medicalpatientapp.UI.Activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.arun.medicalpatientapp.R;
import com.example.arun.medicalpatientapp.UI.Adapter.PrescribedMedicineAdapter;
import com.example.arun.medicalpatientapp.UI.Adapter.PrescriptionAdapter;
import com.example.arun.medicalpatientapp.UI.Custom.RecyclerItemClickListener;

import butterknife.Bind;
import butterknife.OnClick;

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

    @OnClick(R.id.button_call_doctor)
    void callDoctor()
    {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:+91 " + manager.selectedPrescription.getDoctorID().getPhone()));
        startActivity(callIntent);
    }

    @OnClick(R.id.button_share_prescription)
    void sharePresc()
    {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_SUBJECT, "My Prescription");
        String content = "";
        for (int index = 0; index < manager.selectedPrescription.getMedicineList().size(); index++)
        {
            content = content.concat(manager.selectedPrescription.getMedicineList().get(index).getMedicine().getMedicineName() + "\n");
        }
        content = content.concat("\n\n\n - Sent via ePrescription Android app");
        i.putExtra(Intent.EXTRA_TEXT, content);
        try
        {
            startActivity(Intent.createChooser(i, "Send Email"));
        }
        catch (ActivityNotFoundException ex)
        {
            Toast.makeText(PrescriptionDetailActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

}

