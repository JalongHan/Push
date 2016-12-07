package haoqu.com.push.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import haoqu.com.push.R;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    private ImageView mLActivityPhone;
    private ImageView mLActivityDeletePN;
    private EditText mLActivityAccountInput;
    private ImageView mLActivityPassWord;
    private ImageView mLActivityDeletePW;
    private EditText mLActivityPassWordInput;
    private Button mLActivityBtLogin;
    private TextView mLActivityBtRegister;
    private TextView mLActivityBtForgetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();


    }


    private void initView() {
        mLActivityPhone = (ImageView) findViewById(R.id.lActivity_phone);
        mLActivityDeletePN = (ImageView) findViewById(R.id.lActivity_deletePN);
        mLActivityAccountInput = (EditText) findViewById(R.id.lActivity_account_input);
        mLActivityPassWord = (ImageView) findViewById(R.id.lActivity_passWord);
        mLActivityDeletePW = (ImageView) findViewById(R.id.lActivity_deletePW);
        mLActivityPassWordInput = (EditText) findViewById(R.id.lActivity_passWord_input);
        mLActivityBtLogin = (Button) findViewById(R.id.lActivity_bt_login);
        mLActivityBtRegister = (TextView) findViewById(R.id.lActivity_bt_register);
        mLActivityBtForgetPassword = (TextView) findViewById(R.id.lActivity_bt_forgetPassword);

        mLActivityBtLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lActivity_bt_login:
                startActivity(new Intent(this,MainActivity.class));
                break;
        }
    }

    private void submit() {
        // validate
        String input = mLActivityAccountInput.getText().toString().trim();
        if (TextUtils.isEmpty(input)) {
            Toast.makeText(this, "input不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String passWord = mLActivityPassWordInput.getText().toString().trim();
        if (TextUtils.isEmpty(input)) {
            Toast.makeText(this, "input不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something


    }
}

