package com.example.t2shop.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.t2shop.Common.MoMoConstants;
import com.example.t2shop.R;

public class MoMoActivity extends AppCompatActivity {

    int environment = 1;//developer default - Production environment = 2
    private RadioButton rdEnvironmentProduction;
    private RadioGroup rdGroupEnvironment;
    private Button btnPaymentMoMo;
    private Button btnMappingMoMo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mo_mo);
        rdEnvironmentProduction = findViewById(R.id.rdEnvironmentProduction);
        rdGroupEnvironment = findViewById(R.id.rdGroupEnvironment);
        btnPaymentMoMo = findViewById(R.id.btnPaymentMoMo);
        btnMappingMoMo = findViewById(R.id.btnMappingMoMo);
        rdGroupEnvironment.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
//                if (checkedId == R.id.rdEnvironmentDebug) {
//                    environment = 0;
//                }else if (checkedId == R.id.rdEnvironmentDeveloper) {
//                    environment = 1;
//                }else if (checkedId == R.id.rdEnvironmentProduction) {
//                    environment = 2;
//                }
            }
        });
        payMoMo();
        linkMoMo();
    }

    private void linkMoMo() {
        btnMappingMoMo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                Bundle data = new Bundle();
                intent = new Intent(MoMoActivity.this, MappingActivity.class);
                data.putInt(MoMoConstants.KEY_ENVIRONMENT, environment);
                intent.putExtras(data);
                startActivity(intent);
            }
        });
    }

    private void payMoMo() {
        btnPaymentMoMo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                Bundle data = new Bundle();
                intent = new Intent(MoMoActivity.this, PaymentActivity.class);
                data.putInt(MoMoConstants.KEY_ENVIRONMENT, environment);
                intent.putExtras(data);
                startActivity(intent);
            }
        });
    }

}