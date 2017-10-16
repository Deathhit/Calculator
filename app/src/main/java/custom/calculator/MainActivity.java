/*
Copyright 2017 YANG-TUN-HUNG

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
package custom.calculator;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import custom.caculator.R;
import custom.calculator.customLibrary.ShuntingYardParser;

public class MainActivity extends Activity implements View.OnClickListener{
    private ScrollView logScrollView,
                       expScrollView;

    private TextView textView,
                     expTextView;

    private ShuntingYardParser parser;

    private ArrayList<String> tokens = new ArrayList<>();

    private String value = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        logScrollView = (ScrollView)findViewById(R.id.logScrollView);
        expScrollView = (ScrollView)findViewById(R.id.expScrollView);

        textView = (TextView)findViewById(R.id.textView);
        textView.setText("");
        expTextView = (TextView)findViewById(R.id.expTextView);
        expTextView.setText("");

        parser = new ShuntingYardParser();
        parser.setTokensOfExp(tokens);

        findViewById(R.id.button0).setOnClickListener(this);
        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
        findViewById(R.id.button4).setOnClickListener(this);
        findViewById(R.id.button5).setOnClickListener(this);
        findViewById(R.id.button6).setOnClickListener(this);
        findViewById(R.id.button7).setOnClickListener(this);
        findViewById(R.id.button8).setOnClickListener(this);
        findViewById(R.id.button9).setOnClickListener(this);
        findViewById(R.id.dotButton).setOnClickListener(this);
        findViewById(R.id.leftPButton).setOnClickListener(this);
        findViewById(R.id.rightPButton).setOnClickListener(this);
        findViewById(R.id.multiplyButton).setOnClickListener(this);
        findViewById(R.id.divideButton).setOnClickListener(this);
        findViewById(R.id.addButton).setOnClickListener(this);
        findViewById(R.id.subtractButton).setOnClickListener(this);
        findViewById(R.id.deleteButton).setOnClickListener(this);
        findViewById(R.id.clearButton).setOnClickListener(this);
        findViewById(R.id.calculateButton).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String temp;

        switch(v.getId()){
            case R.id.button0 :

                value = value + "0";
                expTextView.append("0");

                break;
            case R.id.button1 :

                value = value + "1";
                expTextView.append("1");

                break;
            case R.id.button2 :

                value = value + "2";
                expTextView.append("2");

                break;
            case R.id.button3 :

                value = value + "3";
                expTextView.append("3");

                break;
            case R.id.button4 :

                value = value + "4";
                expTextView.append("4");

                break;
            case R.id.button5 :

                value = value + "5";
                expTextView.append("5");

                break;
            case R.id.button6 :

                value = value + "6";
                expTextView.append("6");

                break;
            case R.id.button7 :

                value = value + "7";
                expTextView.append("7");

                break;
            case R.id.button8 :
                value = value + "8";
                expTextView.append("8");
                break;
            case R.id.button9 :

                value = value + "9";
                expTextView.append("9");

                break;
            case R.id.dotButton :

                value = value + ".";
                expTextView.append(".");

                break;
            case R.id.leftPButton :

                numberToToken();

                tokens.add("(");
                expTextView.append("(");

                break;
            case R.id.rightPButton :

                numberToToken();

                tokens.add(")");
                expTextView.append(")");

                break;
            case R.id.multiplyButton :

                numberToToken();
                tokens.add("*");
                expTextView.append("*");

                break;
            case R.id.divideButton :

                numberToToken();
                tokens.add("/");
                expTextView.append("/");

                break;
            case R.id.addButton :

                numberToToken();
                tokens.add("+");
                expTextView.append("+");

                break;
            case R.id.subtractButton :

                numberToToken();
                tokens.add("-");
                expTextView.append("-");

                break;
            case R.id.deleteButton :

                numberToToken();

                if(tokens.size() == 0)
                    return;

                temp = "";
                tokens.remove(tokens.size()-1);

                for(String s : tokens)
                    temp = temp + s;

                expTextView.setText(temp);

                break;
            case R.id.clearButton :

                tokens.clear();
                value = "";
                expTextView.setText("");

                break;
            case R.id.calculateButton :

                numberToToken();

                try {
                    temp = String.valueOf(parser.calculate());
                }catch(Exception e){
                    Toast.makeText(this,"Error!",Toast.LENGTH_SHORT).show();
                    return;
                }

                textView.append(expTextView.getText().toString() + "=" + temp + "\n");

                logScrollView.fullScroll(ScrollView.FOCUS_DOWN);
                expTextView.setText(temp);

                tokens.clear();
                value = value + temp;

                break;
        }

        expScrollView.fullScroll(ScrollView.FOCUS_DOWN);
    }

    private boolean numberToToken(){
        if(value.compareTo("")!=0) {
            tokens.add(value);
            value = "";

            return true;
        }else
            return false;
    }
}
