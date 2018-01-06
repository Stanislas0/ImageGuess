package com.ImageGuess;

/**
 * Created by apple on 2018/1/5.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by apple on 2018/1/1.
 */

public class Register extends Activity {
    Button regButton;
    EditText userName;
    EditText passWord;
    JSONObject loginJSON=new JSONObject();
    private ClientSocket clientSocket;
    private String createUser;
    private String portNumber;
    private MyApp myApp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        regButton=(Button)findViewById(R.id.confirmRegister);
        userName=(EditText) findViewById(R.id.userName);
        passWord=(EditText)findViewById(R.id.passWord);
        myApp=(MyApp)getApplication();
        clientSocket = new ClientSocket(this, myApp.getServerIP(), myApp.getServerPort());

        regButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(Register.this,LogIn.class);
                try{
                    loginJSON.put("passWord",passWord.getText());
                    loginJSON.put("userName",userName.getText());
                    loginJSON.put("infoState",1);
                    myApp.setUserName(userName.getText().toString());
                    clientSocket.InfoToServer(loginJSON.toString(),new ClientSocket.DataListener(){
                        @Override
                        public void transData() {
                            try {
                                createUser = clientSocket.getServerMessage();
                                JSONObject message = new JSONObject(createUser);
                                System.out.println(message.get("serverInfo").toString());
                            }
                            catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                    });
                }
                catch(JSONException e) {
                    e.printStackTrace();
                }
                String prompt = "新用户创建成功，请重新登录" ;
                Toast.makeText(Register.this, prompt, Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
    }

}
