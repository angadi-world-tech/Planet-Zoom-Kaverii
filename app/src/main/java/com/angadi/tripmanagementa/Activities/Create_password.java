package com.angadi.tripmanagementa.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

import com.angadi.tripmanagementa.R;
import com.rengwuxian.materialedittext.MaterialEditText;

public class Create_password extends AppCompatActivity
{
  //  MaterialEditText Ed_password, ED_confirm;
    RelativeLayout Button_Done;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_create_password);
      //  Ed_password = (MaterialEditText)findViewById(R.id.ED_password);
       // ED_confirm = (MaterialEditText)findViewById(R.id.ED_cinfirm_pass);

        Button_Done = (RelativeLayout)findViewById(R.id.Button_Done);
    }
}
