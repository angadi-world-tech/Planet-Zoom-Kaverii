package com.angadi.tripmanagementa.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.angadi.tripmanagementa.R;
import com.crashlytics.android.Crashlytics;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fabric.sdk.android.Fabric;

public class Create_PasswordFragmnet extends Fragment
{

    TextView TV_create_password;
    EditText EditTextPassword,EditTestCinfirmPasswoed;
    @BindView(R.id.Button_Done) Button Button_Done;
    @BindView(R.id.TextViewPassword) TextView TextViewPassword;
    @BindView(R.id.TextViewConfirmPassword) TextView TextViewConfirmPassword;
    @BindView(R.id.ViewPassword) View ViewPassword;
    @BindView(R.id.ViewConfirmPassword) View ViewConfirmPassword;
    @BindView(R.id.ImageViewPassword) ImageView ImageViewPassword;
    @BindView(R.id.ImageviewConfirmPassword) ImageView ImageviewConfirmPassword;
    @BindView(R.id.TextViewMatched) TextView TextViewMatched;
    @BindView(R.id.LinLayoutmatchedImage) LinearLayout LinLayoutmatchedImage;
    @BindView(R.id.eye) LinearLayout eye;
    @BindView(R.id.ImageView_eye) ImageView ImageView_eye;
    @BindView(R.id.bottom) RelativeLayout bottom;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.layout_create_password, container, false);
        ButterKnife.bind(this,view);
        Fabric.with(getActivity(), new Crashlytics());
        EditTextPassword = (EditText)view.findViewById(R.id.EditTextPassword);
        EditTestCinfirmPasswoed = (EditText)view.findViewById(R.id.EditTextConfirmpassword);



        ShowEdittextAnimations();
        return view;
    }

    @OnClick(R.id.Button_Done)
    public void setButton_Done()
    {
        RegistrationThanksFragment varify_email = new RegistrationThanksFragment();
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.addToBackStack(null);
        ft.add(R.id.content, varify_email);
        ft.commit();
    }


    public  void  ShowEdittextAnimations()
    {
        EditTextPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus)
            {
                if (hasFocus)
                {
                    EditTextPassword.setCursorVisible(true);
                }

            }
        });

        EditTextPassword.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(MotionEvent.ACTION_UP == event.getAction())

                    if (ViewPassword.getVisibility()==View.VISIBLE)
                    {
                        ImageViewPassword.setImageResource(R.mipmap.password_blue_icon);
                        ViewPassword.setVisibility(View.INVISIBLE);
                        EditTextPassword.setCursorVisible(false);
                    }
                    else
                    {
                        ImageViewPassword.setImageResource(R.mipmap.orange_password_icon);
                        ViewPassword.setVisibility(View.VISIBLE);
                        EditTextPassword.setCursorVisible(false);
                    }


                return false;
            }
        });

        EditTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

                if (i2 == 0 && EditTextPassword.length()==0)
                {
                    TextViewMatched.setTextColor(getActivity().getResources().getColor(R.color.grenn_disabled));
                    LinLayoutmatchedImage.setBackgroundResource(R.drawable.disabled_green_filled_circle);
                }
                else  if (i2 > 0 && EditTextPassword.length()>0)
                {
                    if (!EditTestCinfirmPasswoed.getText().toString().equals(EditTextPassword.getText().toString()))
                    {
                        TextViewMatched.setTextColor(getActivity().getResources().getColor(R.color.grenn_disabled));
                        LinLayoutmatchedImage.setBackgroundResource(R.drawable.disabled_green_filled_circle);

                    }
                    else if (EditTestCinfirmPasswoed.getText().toString().equals(EditTextPassword.getText().toString())&& !EditTestCinfirmPasswoed.getText().toString().isEmpty() || !EditTextPassword.getText().toString().isEmpty())
                    {
                        TextViewMatched.setTextColor(getActivity().getResources().getColor(R.color.green));
                        LinLayoutmatchedImage.setBackgroundResource(R.drawable.green_filled_circle);
                    }
                }



                if (i2==0 && EditTextPassword.length()==0)
                {
                    ImageViewPassword.setImageResource(R.mipmap.orange_password_icon);
                    ViewPassword.setVisibility(View.VISIBLE);
                    EditTextPassword.setCursorVisible(false);

                }
                else
                {
                    ImageViewPassword.setImageResource(R.mipmap.password_blue_icon);
                    ViewPassword.setVisibility(View.INVISIBLE);
                    EditTextPassword.setCursorVisible(true);

                }
            }

            @Override
            public void afterTextChanged(Editable editable)
            {

            }
        });


        //EdiitextConfirmPassword

        EditTestCinfirmPasswoed.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus)
            {
                if (hasFocus)
                {
                    EditTestCinfirmPasswoed.setCursorVisible(true);
                }

            }
        });

        EditTestCinfirmPasswoed.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(MotionEvent.ACTION_UP == event.getAction())

                    if (ViewPassword.getVisibility()==View.VISIBLE)
                    {
                        ImageviewConfirmPassword.setImageResource(R.mipmap.password_blue_icon);
                        ViewConfirmPassword.setVisibility(View.INVISIBLE);
                        EditTestCinfirmPasswoed.setCursorVisible(false);
                    }
                    else
                    {
                        ImageviewConfirmPassword.setImageResource(R.mipmap.orange_password_icon);
                        ViewConfirmPassword.setVisibility(View.VISIBLE);
                        EditTestCinfirmPasswoed.setCursorVisible(false);
                    }


                return false;
            }
        });

        EditTestCinfirmPasswoed.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                TextViewMatched.setTextColor(getActivity().getResources().getColor(R.color.grenn_disabled));
                LinLayoutmatchedImage.setBackgroundResource(R.drawable.disabled_green_filled_circle);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

                if (i2 == 0 && EditTestCinfirmPasswoed.length()==0)
                {

                }
                else  if (i2 > 0 && EditTestCinfirmPasswoed.length()>0)
                {
                    if (!EditTestCinfirmPasswoed.getText().toString().equals(EditTextPassword.getText().toString()))
                    {
                        TextViewMatched.setTextColor(getActivity().getResources().getColor(R.color.grenn_disabled));
                        LinLayoutmatchedImage.setBackgroundResource(R.drawable.disabled_green_filled_circle);
                    }
                    else if (EditTestCinfirmPasswoed.getText().toString().equals(EditTextPassword.getText().toString())&& !EditTestCinfirmPasswoed.getText().toString().isEmpty() || !EditTextPassword.getText().toString().isEmpty())
                    {
                        TextViewMatched.setTextColor(getActivity().getResources().getColor(R.color.green));
                        LinLayoutmatchedImage.setBackgroundResource(R.drawable.green_filled_circle);
                    }
                }
                if (i2==0 && EditTestCinfirmPasswoed.length()==0)
                {
                    ImageviewConfirmPassword.setImageResource(R.mipmap.orange_password_icon);
                    ViewConfirmPassword.setVisibility(View.VISIBLE);
                    EditTestCinfirmPasswoed.setCursorVisible(false);

                }
                else
                {
                    ImageviewConfirmPassword.setImageResource(R.mipmap.password_blue_icon);
                    ViewConfirmPassword.setVisibility(View.INVISIBLE);
                    EditTestCinfirmPasswoed.setCursorVisible(true);

                }
            }

            @Override
            public void afterTextChanged(Editable editable)
            {

            }
        });


    }



    public void HidePassword()
    {
        EditTestCinfirmPasswoed.setTransformationMethod(PasswordTransformationMethod.getInstance());

    }

    public void ShowPassword()
    {
        EditTestCinfirmPasswoed.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

    }



    @OnClick(R.id.ImageView_eye)
    public void setEye()
    {
        if(EditTestCinfirmPasswoed.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD)
        {
            ImageView_eye.setImageResource(R.drawable.hide);
            EditTestCinfirmPasswoed.setInputType( InputType.TYPE_CLASS_TEXT |
                    InputType.TYPE_TEXT_VARIATION_PASSWORD);

        }
        else
        {
            ImageView_eye.setImageResource(R.drawable.view);
            EditTestCinfirmPasswoed.setInputType( InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD );


        }





//        ImageView_eye.setTag(R.drawable.hide);
//        ImageView_eye.setImageResource(R.drawable.hide);
//        String backgroundImageName = String.valueOf(ImageView_eye.getTag());
//
//        Toast.makeText(getActivity(), backgroundImageName, Toast.LENGTH_SHORT).show();
//        if (!backgroundImageName.equalsIgnoreCase("bg"))
//        {
//
//
//            ShowPassword();
//            ImageView_eye.setImageResource(R.drawable.view);
//            Toast.makeText(getActivity(), "k1", Toast.LENGTH_SHORT).show();
//        }
//        else
//        {
//            HidePassword();
//            ImageView_eye.setImageResource(R.drawable.hide);
//            Toast.makeText(getActivity(), "k2", Toast.LENGTH_SHORT).show();
//
//        }


//        if (ImageView_eye.getDrawable().getConstantState() == getActivity().getResources().getDrawable(R.drawable.hide).getConstantState())
//        {
//            ShowPassword();
//            ImageView_eye.setImageResource(R.drawable.view);
//            Toast.makeText(getActivity(), "k2", Toast.LENGTH_SHORT).show();
//        }
//        else  if (ImageView_eye.getDrawable().getConstantState() == getActivity().getResources().getDrawable(R.drawable.view).getConstantState())
//        {
//
//            HidePassword();
//            Toast.makeText(getActivity(), "k1", Toast.LENGTH_SHORT).show();
//            ImageView_eye.setImageResource(R.drawable.hide);
//        }

    }


}


