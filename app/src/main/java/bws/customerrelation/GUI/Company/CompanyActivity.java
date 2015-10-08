
package bws.customerrelation.GUI.Company;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

import bws.customerrelation.Controller.CompanyController;
import bws.customerrelation.GUI.InflateLists.InflateCompany;
import bws.customerrelation.GUI.LoginActivity;
import bws.customerrelation.GUI.MainActivity;
import bws.customerrelation.Model.BECompany;
import bws.customerrelation.R;

public class CompanyActivity extends AppCompatActivity {
    LinearLayout _linearLayout;
    Button btnShowClient;
    ArrayList<BECompany> _selectedClients;
    public static BECompany SELECTEDCOMPANY;
    CompanyController _companyController;
    InflateCompany _adapter;
    private static String TAG = "CompanyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);
        _companyController = CompanyController.getInstance(this);
        findViews();
        PopulateClients();
        setListeners();
        inflateViews();
        if (_adapter.getSelectedClient() != null) {
            SELECTEDCOMPANY = _adapter.getSelectedClient();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (_companyController.getAllCompaniesFromDevice().isEmpty()) {
            SELECTEDCOMPANY = null;
            finish();
        }
        _selectedClients = _companyController.getAllCompaniesFromDevice();
        _linearLayout.removeAllViews();
        inflateViews();

    }

    private void inflateViews() {
        _adapter = new InflateCompany(this, _selectedClients, _linearLayout);
        _adapter.inflateView();
    }

    private void setListeners() {
        btnShowClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickBtnShowClient();
            }
        });
    }

    private void onClickBtnShowClient() {
        Intent clientDataIntent = new Intent();
        clientDataIntent.setClass(this, CompanyMainActivity.class);
        if (SELECTEDCOMPANY != null) {
            startActivity(clientDataIntent);
        } else {
            Toast.makeText(this, "Du skal vælge en kunde på listen", Toast.LENGTH_SHORT).show();
        }
    }

    private void PopulateClients() {
        _selectedClients = _companyController.getAllCompaniesFromDevice();
    }

    private void findViews() {
        btnShowClient = (Button) findViewById(R.id.btnShowClient);
        _linearLayout = (LinearLayout) findViewById(R.id.linear_listview);
    }
}