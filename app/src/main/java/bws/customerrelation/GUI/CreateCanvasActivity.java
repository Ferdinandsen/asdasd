package bws.customerrelation.GUI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import bws.customerrelation.Controller.CanvasController;
import bws.customerrelation.Controller.SharedConstants;
import bws.customerrelation.Model.BECanvas;
import bws.customerrelation.Model.BECompany;
import bws.customerrelation.Model.BEUser;
import bws.customerrelation.R;

public class CreateCanvasActivity extends AppCompatActivity {
    Button btnSave;
    EditText txtCanvas;
    EditText txtSubject;
    CanvasController _canvasController;
    BECompany _selectedCompany;
    BEUser _selectedUser;
    private static String TAG = "CreateCanvasActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_canvas);
        Bundle b = getIntent().getExtras(); //Todo remove?
        _canvasController = CanvasController.getInstance(this);
        findViews();
        setListeners();
        populateData(b);
    }

    private void populateData(Bundle b) {
        //Todo Get from static instead
        _selectedCompany = (BECompany) b.getSerializable(SharedConstants.CLIENT);
        _selectedUser = LoginActivity.USER;
    }

    private void setListeners() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickBtnSave(_selectedCompany);
            }
        });
    }

    private void onClickBtnSave(BECompany comp) {
        String canvasId = "" + (Math.random()*1000 + Math.random()*1000);

        Date date = new Date();
        String res;
        SimpleDateFormat sdfOut = new SimpleDateFormat("dd-MM-yyyy");
        res = sdfOut.format(date);

        BECanvas canvas = new BECanvas(canvasId, comp.getM_companyId(), _selectedUser.getFirstname() + " " + _selectedUser.getLastname(), txtSubject.getText().toString(), res, txtCanvas.getText().toString());
        if (_canvasController.saveNewCanvas(canvas) != -1) {
            finish();
        }

    }

    private void findViews() {
        btnSave = (Button) findViewById(R.id.btnSave);
        txtCanvas = (EditText) findViewById(R.id.newCanvas);
        txtSubject = (EditText) findViewById(R.id.txtSubject);
    }

}
