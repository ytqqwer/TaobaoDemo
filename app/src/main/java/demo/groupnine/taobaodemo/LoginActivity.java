package demo.groupnine.taobaodemo;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import demo.groupnine.taobaodemo.net.HttpRequest;


public class LoginActivity
        extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    private EditText mAccountET;
    private EditText mPasswordET;
    private EditText mServerAddrET;
    private Button mLoginButton;
    private String mAccount = "user_1";
    private String mPassword = "passwd";
    private String mServerAddr = "10.101.252.144:8080";


    // lifetime methods

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Set up the login form.
        mAccountET = (EditText) findViewById(R.id.login_account);
        mPasswordET = (EditText) findViewById(R.id.login_password);
        mServerAddrET = (EditText) findViewById(R.id.login_server_addr);
        setupTextChangedListener();

        mLoginButton = (Button) findViewById(R.id.login_button);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                attemptLogin();

                /* 根据登录结果决定 LoginActivity 行为 */

                if (HttpRequest.loginSuccess) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "用户名 / 密码 / 服务器地址有误", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });
    }

    // private methods

    private void setupTextChangedListener()
    {
        mAccountET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
                // do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                mAccount = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                mAccount = s.toString();
            }
        });
        mPasswordET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
                // do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                mPassword = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                mPassword = s.toString();
            }
        });
        mServerAddrET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
                // do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                mServerAddr = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                mServerAddr = s.toString();
            }
        });

    }

    private void attemptLogin()
    {

        HttpRequest.setServer(mServerAddr);

        /* 1. 尝试用户名登录，并等待其完成 */

        HttpRequest.hasTriedLogin = false;
        HttpRequest.login("?username=" + mAccount + "&password=" + mPassword);
        while (!HttpRequest.hasTriedLogin) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                // TODO
                // why InterruptedException ?
            }
        }

        /* 2. 若用户名登录失败，尝试手机号登录 */

        HttpRequest.hasTriedLogin = false;
        if (!HttpRequest.loginSuccess) {
            HttpRequest.login("?phone=" + mAccount + "&password=" + mPassword);
            while (!HttpRequest.hasTriedLogin) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    // TODO
                    // why InterruptedException ?
                }
            }
        }
    }

}

