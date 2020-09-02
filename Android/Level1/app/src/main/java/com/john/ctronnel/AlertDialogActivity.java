package com.john.ctronnel;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.john.ctronnel.utils.ToastUtil;

public class AlertDialogActivity extends AppCompatActivity {

    private Button btn1, btn2, btn3, btn4, btn5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_dialog);

        btn1 = (Button)findViewById(R.id.bt1);
        btn2 = (Button)findViewById(R.id.bt2);
        btn3 = (Button)findViewById(R.id.bt3);
        btn4 = (Button)findViewById(R.id.bt4);
        btn5 = (Button)findViewById(R.id.bt5);
        setOnClickListener();
    }

    void setOnClickListener()
    {
        MyOnClick clickListener = new MyOnClick();
        btn1.setOnClickListener(clickListener);
        btn2.setOnClickListener(clickListener);
        btn3.setOnClickListener(clickListener);
        btn4.setOnClickListener(clickListener);
        btn5.setOnClickListener(clickListener);
    }

    class MyOnClick implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            switch (view.getId())
            {
                case R.id.bt1:
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AlertDialogActivity.this);
                    builder.setTitle("Please Answer");
                    builder.setMessage("How about this class?");
                    builder.setIcon(R.drawable.icon);
                    builder.setPositiveButton("Good", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ToastUtil.showMsg(AlertDialogActivity.this, "You are honyst");
                        }
                    });

                    builder.setNeutralButton("Not bad", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ToastUtil.showMsg(AlertDialogActivity.this, "okokok");
                        }
                    });

                    builder.setNegativeButton("bad", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ToastUtil.showMsg(AlertDialogActivity.this, "Not Happy!!");
                        }
                    });

                    builder.show();
                }
                 break;
                case R.id.bt2:
                {
                    final String[] array = new String[]{"Man", "Woman"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(AlertDialogActivity.this);
                    builder.setTitle("Please choose sex").setItems(array, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ToastUtil.showMsg(AlertDialogActivity.this, "You Choose:"+array[i]);
                        }
                    }).show();
                }
                break;
                case R.id.bt3:
                {
                    final String[] array = new String[]{"Man", "Woman"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(AlertDialogActivity.this);

                    builder.setTitle("Please Choose Sex");
                    builder.setCancelable(false);
                    builder.setSingleChoiceItems(array, 0, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ToastUtil.showMsg(AlertDialogActivity.this, "You Choose:"+array[i]);
                            dialogInterface.dismiss();
                        }
                    }).show();
                }
                break;
                case R.id.bt4:
                {
                    final String[] array = new String[]{"Jump", "Song", "Code"};
                    boolean[] selected = new boolean[]{false, false, true};

                    AlertDialog.Builder builder = new AlertDialog.Builder(AlertDialogActivity.this);
                    builder.setTitle("Please Select hobby:");
                    builder.setMultiChoiceItems(array, selected, new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                            ToastUtil.showMsg(AlertDialogActivity.this, "You Choose:"+array[i]+":"+b);
                        }
                    }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).show();
                }
                break;
                case R.id.bt5:
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AlertDialogActivity.this);
                    builder.setTitle("Please Login");
                    LayoutInflater inflater = LayoutInflater.from(AlertDialogActivity.this);
                    View v = inflater.inflate(R.layout.layout_dailog, null);
                    builder.setView(v);
                    EditText userName = v.findViewById(R.id.username);
                    EditText passWord = v.findViewById(R.id.password);
                    Button btn = v.findViewById(R.id.loggin);
                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    });

                    builder.show();
                }
                break;
            }
        }
    }
}