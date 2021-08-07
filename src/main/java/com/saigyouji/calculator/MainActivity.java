package com.saigyouji.calculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;


import org.jetbrains.annotations.NotNull;

import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import static com.saigyouji.calculator.Service.Common.getLastNumber;

import static com.saigyouji.calculator.Service.Common.isNumber;
import static com.saigyouji.calculator.Service.Common.isOperator;
import static com.saigyouji.calculator.Service.Formula_Service.Brackets_Match;

import com.saigyouji.calculator.Service.Common;

public class MainActivity extends AppCompatActivity
{
    private List<Out> answerList = new ArrayList<>();
    // show out input string
    private StringBuilder tempInput;
    // inner input string
    private StringBuilder innerInput;

    /**
     * To find the result is error or last
     * 0 is not last result
     * 1 is last result
     * -1 is error result
     */
    private int resultType = 0;



    private RecyclerView figureRecyclerView;
    private OutAdapter adapter;
    private AppCompatTextView inputView;

    private final String sign = "+-*/";
    private static final String TAG = "MainActivity";

    @Override
    public void onConfigurationChanged(@NonNull @NotNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        int orientation = getResources().getConfiguration().orientation;
        if(orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.activity_main_vertical);

            inputView = findViewById(R.id.inputView);
            figureRecyclerView = findViewById(R.id.figure_recyclerView);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            figureRecyclerView.setLayoutManager(layoutManager);

            adapter = new OutAdapter(answerList);
            figureRecyclerView.setAdapter(adapter);
            inputView.setText(tempInput);

            InitButton();

        }
        else
        {
            setContentView(R.layout.activity_main_horizontal);
            inputView = findViewById(R.id.inputView_horizontal);
            figureRecyclerView = findViewById(R.id.figure_recyclerView_horizontal);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

            figureRecyclerView.setLayoutManager(layoutManager);
            adapter = new OutAdapter(answerList);
            figureRecyclerView.setAdapter(adapter);
            inputView.setText(tempInput);
            InitHorizontalButton();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        int orientation = getResources().getConfiguration().orientation;
        if(orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.activity_main_vertical);

            inputView = findViewById(R.id.inputView);
            figureRecyclerView = findViewById(R.id.figure_recyclerView);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            figureRecyclerView.setLayoutManager(layoutManager);

            adapter = new OutAdapter(answerList);
            figureRecyclerView.setAdapter(adapter);
            inputView.setText(tempInput);

            InitButton();

        }
        else
        {
            setContentView(R.layout.activity_main_horizontal);
            inputView = findViewById(R.id.inputView_horizontal);
            figureRecyclerView = findViewById(R.id.figure_recyclerView_horizontal);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

            figureRecyclerView.setLayoutManager(layoutManager);
            adapter = new OutAdapter(answerList);
            figureRecyclerView.setAdapter(adapter);
            inputView.setText(tempInput);
            InitHorizontalButton();
        }
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
        {
            actionBar.hide();
        }
        var len = inputView.getText().length();
        if(len > 60)
        {
            innerInput = new StringBuilder();
            tempInput = new StringBuilder();
            resultType = -1;
            inputView.setText("数据过大，无法计算");
        }
        tempInput = new StringBuilder();
        innerInput = new StringBuilder();

    }

    /**
     * Initialize all the button
     */
    private void InitHorizontalButton()
    {
        Button button_0 = findViewById(R.id.button_0_horizontal);
        Button button_1 = findViewById(R.id.button_1_horizontal);
        Button button_2 = findViewById(R.id.button_2_horizontal);
        Button button_3 = findViewById(R.id.button_3_horizontal);
        Button button_4 = findViewById(R.id.button_4_horizontal);
        Button button_5 = findViewById(R.id.button_5_horizontal);
        Button button_6 = findViewById(R.id.button_6_horizontal);
        Button button_7 = findViewById(R.id.button_7_horizontal);
        Button button_8 = findViewById(R.id.button_8_horizontal);
        Button button_9 = findViewById(R.id.button_9_horizontal);
        Button button_clean = findViewById(R.id.button_clean_horizontal);
        Button button_back = findViewById(R.id.button_back_horizontal);
        Button button_percent = findViewById(R.id.button_percent_horizontal);
        Button button_derive = findViewById(R.id.button_derive_horizontal);
        Button button_multiply = findViewById(R.id.buttton_multiply_horizontal);
        Button button_reduce = findViewById(R.id.button_reduce_horizontal);
        Button button_add = findViewById(R.id.button_add_horizontal);
        Button button_equals = findViewById(R.id.button_equals_horizontal);
        Button button_point = findViewById(R.id.button_point_horizontal);
        Button button_left_bracket = findViewById(R.id.button_left_brackets_horizontal);
        Button button_right_bracket = findViewById(R.id.button_right_brackets_horizontal);
        Button button_sin = findViewById(R.id.button_sin_horizontal);
        Button button_cos = findViewById(R.id.button_cos_horizontal);
        Button button_tan = findViewById(R.id.button_tan_horizontal);
        Button button_pi = findViewById(R.id.button_pi_horizontal);
        Button button_e = findViewById(R.id.button_e_horizontal);
        Button button_G = findViewById(R.id.button_G_horizontal);
        Button button_square = findViewById(R.id.button_square_horizontal);
        Button button_cube = findViewById(R.id.button_cube_horizontal);
        Button button_exponent = findViewById(R.id.button_exponent_horizontal);

        button_sin.setOnClickListener(v->{
            if(resultType == -1 || resultType == 1)
            {
                tempInput = new StringBuilder();
                innerInput = new StringBuilder();
            } var len = inputView.getText().length();
            if(len > 60)
            {
                innerInput = new StringBuilder();
                tempInput = new StringBuilder();
                resultType = -1;
                inputView.setText("数据过大，无法计算");return;
            }
            String lastNum = getLastNumber(innerInput.toString());
            if(lastNum.length() == 1 && lastNum.charAt(0) == '0')
            {
                tempInput.delete(tempInput.length() -1,tempInput.length());
                innerInput.delete(innerInput.length()- 1, innerInput.length());
            }
            resultType = 0;
            if(!innerInput.toString().equals("") && (innerInput.charAt(innerInput.length() - 1) == ')' ||
                    innerInput.charAt(innerInput.length() - 1) == '%'|| isNumber(String.valueOf(innerInput.charAt(innerInput.length() - 1)))))
                innerInput.append("*");
            tempInput.append("sin(");
            innerInput.append("sin(");
            inputView.setText(tempInput);
        });
        button_cos.setOnClickListener(v->{
            if(resultType == -1 || resultType == 1)
            {
                tempInput = new StringBuilder();
                innerInput = new StringBuilder();
            } var len = inputView.getText().length();
            if(len > 60)
            {
                innerInput = new StringBuilder();
                tempInput = new StringBuilder();
                resultType = -1;
                inputView.setText("数据过大，无法计算");return;
            }
            String lastNum = getLastNumber(innerInput.toString());
            if(lastNum.length() == 1 && lastNum.charAt(0) == '0')
            {
                tempInput.delete(tempInput.length() -1,tempInput.length());
                innerInput.delete(innerInput.length()- 1, innerInput.length());
            }
            if(!innerInput.toString().equals("") && (innerInput.charAt(innerInput.length() - 1) == ')' ||
                    innerInput.charAt(innerInput.length() - 1) == '%'|| isNumber(String.valueOf(innerInput.charAt(innerInput.length() - 1)))))
                innerInput.append("*");
            resultType = 0;
            tempInput.append("cos(");
            innerInput.append("cos(");
            inputView.setText(tempInput);
        });
        button_tan.setOnClickListener(v->{
            if(resultType == -1 || resultType == 1)
            {
                tempInput = new StringBuilder();
                innerInput = new StringBuilder();
            } var len = inputView.getText().length();
            if(len > 60)
            {
                innerInput = new StringBuilder();
                tempInput = new StringBuilder();
                resultType = -1;
                inputView.setText("数据过大，无法计算");return;
            }
            String lastNum = getLastNumber(innerInput.toString());
            if(lastNum.length() == 1 && lastNum.charAt(0) == '0')
            {
                tempInput.delete(tempInput.length() -1,tempInput.length());
                innerInput.delete(innerInput.length()- 1, innerInput.length());
            }
            resultType = 0;
            if(!innerInput.toString().equals("") && (innerInput.charAt(innerInput.length() - 1) == ')' ||
                    innerInput.charAt(innerInput.length() - 1) == '%'|| isNumber(String.valueOf(innerInput.charAt(innerInput.length() - 1)))))
                innerInput.append("*");
            tempInput.append("tan(");
            innerInput.append("tan(");
            inputView.setText(tempInput);
        });
        button_pi.setOnClickListener(v->{
            if(resultType == -1) {
                tempInput = new StringBuilder();
                innerInput = new StringBuilder();
            }


            else if(resultType == 1) {
                tempInput = new StringBuilder();
                innerInput = new StringBuilder();
            } var len = inputView.getText().length();
            if(len > 60)
            {
                innerInput = new StringBuilder();
                tempInput = new StringBuilder();
                resultType = -1;
                inputView.setText("数据过大，无法计算");return;
            }
            String lastNum = getLastNumber(innerInput.toString());
            if(lastNum.length() == 1 && lastNum.charAt(0) == '0')
            {
                tempInput.delete(tempInput.length() -1,tempInput.length());
                innerInput.delete(innerInput.length()- 1, innerInput.length());
            }
            resultType = 0;

            if(!innerInput.toString().equals("") && (innerInput.charAt(innerInput.length() - 1) == ')' ||
                    innerInput.charAt(innerInput.length() - 1) == '%' || isNumber(String.valueOf(innerInput.charAt(innerInput.length() - 1)))))
                innerInput.append("*");
            tempInput.append("\u03c0");
            innerInput.append(StrictMath.PI);
            inputView.setText(tempInput);
            figureRecyclerView.scrollToPosition(answerList.size() - 1);
        });
        button_e.setOnClickListener(v->{
            if(resultType == -1) {
                tempInput = new StringBuilder();
                innerInput = new StringBuilder();
            }


            else if(resultType == 1) {
                tempInput = new StringBuilder();
                innerInput = new StringBuilder();
            } var len = inputView.getText().length();
            if(len > 60)
            {
                innerInput = new StringBuilder();
                tempInput = new StringBuilder();
                resultType = -1;
                inputView.setText("数据过大，无法计算");return;
            }
            String lastNum = getLastNumber(innerInput.toString());
            if(lastNum.length() == 1 && lastNum.charAt(0) == '0')
            {
                tempInput.delete(tempInput.length() -1,tempInput.length());
                innerInput.delete(innerInput.length()- 1, innerInput.length());
            }
            resultType = 0;

            if(!innerInput.toString().equals("") && (innerInput.charAt(innerInput.length() - 1) == ')' ||
                    innerInput.charAt(innerInput.length() - 1) == '%'|| isNumber(String.valueOf(innerInput.charAt(innerInput.length() - 1)))))
                innerInput.append("*");
            tempInput.append("e");
            innerInput.append(StrictMath.E);
            inputView.setText(tempInput);
            figureRecyclerView.scrollToPosition(answerList.size() - 1);
        });
        button_G.setOnClickListener(v->{
            if(resultType == -1) {
                tempInput = new StringBuilder();
                innerInput = new StringBuilder();
            }


            else if(resultType == 1) {
                tempInput = new StringBuilder();
                innerInput = new StringBuilder();
            } var len = inputView.getText().length();
            if(len > 60)
            {
                innerInput = new StringBuilder();
                tempInput = new StringBuilder();
                resultType = -1;
                inputView.setText("数据过大，无法计算");return;
            }
            String lastNum = getLastNumber(innerInput.toString());
            if(lastNum.length() == 1 && lastNum.charAt(0) == '0')
            {
                tempInput.delete(tempInput.length() -1,tempInput.length());
                innerInput.delete(innerInput.length()- 1, innerInput.length());
            }
            resultType = 0;

            if(!innerInput.toString().equals("") && (innerInput.charAt(innerInput.length() - 1) == ')' ||
                    innerInput.charAt(innerInput.length() - 1) == '%'|| isNumber(String.valueOf(innerInput.charAt(innerInput.length() - 1)))))
                innerInput.append("*");
            tempInput.append("G");
            innerInput.append("0.0000000000667");
            inputView.setText(tempInput);
            figureRecyclerView.scrollToPosition(answerList.size() - 1);
        });
        button_square.setOnClickListener(v->{

            if(resultType == -1)
                return;
            resultType = 0;
            if(tempInput.toString().equals(""))
                return; var len = inputView.getText().length();
            if(len > 60)
            {
                innerInput = new StringBuilder();
                tempInput = new StringBuilder();
                resultType = -1;
                inputView.setText("数据过大，无法计算");return;
            }
            else if (sign.contains(innerInput.subSequence(innerInput.length() - 1, innerInput.length()))) {
                tempInput.setCharAt(tempInput.length() - 1, '^');
                innerInput.setCharAt(innerInput.length() - 1, '^');
                tempInput.append("2");
                innerInput.append("2");
            }
            else {
                tempInput.append("^2");
                innerInput.append("^2");
            }
            inputView.setText(tempInput);
            figureRecyclerView.scrollToPosition(answerList.size() - 1);
        });
        button_cube.setOnClickListener(v->{

            if(resultType == -1)
                return;
            resultType = 0;
            if(tempInput.toString().equals(""))
                return; var len = inputView.getText().length();
            if(len > 60)
            {
                innerInput = new StringBuilder();
                tempInput = new StringBuilder();
                resultType = -1;
                inputView.setText("数据过大，无法计算");return;
            }
            else if (sign.contains(innerInput.subSequence(innerInput.length() - 1, innerInput.length()))) {
                tempInput.setCharAt(tempInput.length() - 1, '^');
                innerInput.setCharAt(innerInput.length() - 1, '^');
                tempInput.append("3");
                innerInput.append("3");
            }
            else {
                tempInput.append("^3");
                innerInput.append("^3");
            }
            inputView.setText(tempInput);
            figureRecyclerView.scrollToPosition(answerList.size() - 1);
        });



        button_left_bracket.setOnClickListener(v->
        {
            if(resultType == -1)
            {
                tempInput = new StringBuilder();
                innerInput = new StringBuilder();
            } var len = inputView.getText().length();
            if(len > 60)
            {
                innerInput = new StringBuilder();
                tempInput = new StringBuilder();
                resultType = -1;
                inputView.setText("数据过大，无法计算");return;
            }
            // if the input is empty, add left brackets
            if(innerInput.toString().equals("")) {
                tempInput.append("(");
                innerInput.append("(");
            }
            else
            {
                char temp = innerInput.charAt(innerInput.length() - 1);
                if ((48 <= temp && temp <= 57) || temp == ')' || temp == '%') {
                    tempInput.append("(");
                    innerInput.append("*(");
                }
                else if (sign.contains(String.valueOf(temp))) {
                    tempInput.append("(");
                    innerInput.append("(");
                }
                else
                {
                    tempInput.append("(");
                    innerInput.append("(");
                }
            }
            resultType = 0;
            inputView.setText(tempInput);
            figureRecyclerView.scrollToPosition(answerList.size() - 1);
        });
        button_right_bracket.setOnClickListener(v->{
            if(resultType == -1)
            {
                tempInput = new StringBuilder();
                innerInput = new StringBuilder();
            } var len = inputView.getText().length();
            if(len > 60)
            {
                innerInput = new StringBuilder();
                tempInput = new StringBuilder();
                resultType = -1;
                inputView.setText("数据过大，无法计算");return;
            }
            // if the input is empty, add left brackets
            if(innerInput.toString().equals("")) {
                tempInput.append(")");
                innerInput.append(")");
            }
            else
            {
                char temp = innerInput.charAt(innerInput.length() - 1);
                if ((48 <= temp && temp <= 57) || temp == ')' || temp == '%') {
                    tempInput.append(")");
                    innerInput.append(")");
                }
                else if (sign.contains(String.valueOf(temp))) {
                    tempInput.append(")");
                    innerInput.append(")");
                }
                else
                {
                    tempInput.append(")");
                    innerInput.append(")");
                }
            }
            resultType = 0;
            inputView.setText(tempInput);
            figureRecyclerView.scrollToPosition(answerList.size() - 1);
        });
        button_clean.setOnClickListener(v->
        {
            resultType = 0;
            if(innerInput.toString().equals(""))
            {
                answerList.clear();
                answerList.add(new Out(""));
                adapter = new OutAdapter(answerList);
                figureRecyclerView.setAdapter(adapter);
            }
            else {
                tempInput = new StringBuilder();
                innerInput = new StringBuilder();
                inputView.setText("");
                figureRecyclerView.scrollToPosition(answerList.size() - 1);
            }
        });
        button_exponent.setOnClickListener(v->{
        if(resultType == -1)
            return;
        resultType = 0;
        if(tempInput.toString().equals(""))
            return; var len = inputView.getText().length();
            if(len > 60)
            {
                innerInput = new StringBuilder();
                tempInput = new StringBuilder();
                resultType = -1;
                inputView.setText("数据过大，无法计算");return;
            }
        else if (sign.contains(innerInput.subSequence(innerInput.length() - 1, innerInput.length()))) {
            tempInput.setCharAt(tempInput.length() - 1, '^');
            innerInput.setCharAt(innerInput.length() - 1, '^');
            tempInput.append("(");
            innerInput.append("(");
        }
        else {
            tempInput.append("^(");
            innerInput.append("^(");
        }
        inputView.setText(tempInput);
        figureRecyclerView.scrollToPosition(answerList.size() - 1);
        });

        button_back.setOnClickListener(v ->
        {
            if(resultType == -1)
            {
                innerInput = new StringBuilder();
                tempInput = new StringBuilder();
                inputView.setText(tempInput);
            }
            if(resultType == 1)
            {
                innerInput = new StringBuilder();
                tempInput = new StringBuilder();
                inputView.setText(tempInput);
                return;
            }
            resultType = 0;
            if((!tempInput.toString().equals("") )&& innerInput.length() >= 2 && tempInput.length() >=2)
            {
                String tempString1 = innerInput.toString();
                tempString1 = tempString1.substring(tempString1.length() - 2);
                String tempString2 = tempInput.toString();
                tempString2 = tempString2.substring(tempString2.length() - 2);
                if(tempString1.equals(tempString2)) {
                    tempInput.delete(tempInput.length() - 1, tempInput.length());
                    innerInput.delete(innerInput.length() - 1,innerInput.length());
                }
                else
                {
                    tempInput.delete(tempInput.length() - 1, tempInput.length());
                    innerInput.delete(innerInput.length() - 2, innerInput.length());
                }
            }
            else if(innerInput.toString().equals(""))
            {
                return;
            }
            else
            {
                innerInput.delete(innerInput.length()- 1, innerInput.length());
                tempInput.delete(tempInput.length() -1, tempInput.length());
            }
            inputView.setText(tempInput);
            figureRecyclerView.scrollToPosition(answerList.size() - 1);
        });

        button_percent.setOnClickListener(v->
        {
            if(resultType == -1)
                return; var len = inputView.getText().length();
            if(len > 60)
            {
                innerInput = new StringBuilder();
                tempInput = new StringBuilder();
                resultType = -1;
                inputView.setText("数据过大，无法计算");return;
            }
            resultType = 0;
            tempInput.append("%");
            innerInput.append("%");
            inputView.setText(tempInput);
            figureRecyclerView.scrollToPosition(answerList.size() - 1);
        });

        button_derive.setOnClickListener(v->
        {
            if(resultType == -1)
                return;
            resultType = 0;
            if(tempInput.toString().equals(""))
                return; var len = inputView.getText().length();
            if(len > 60)
            {
                innerInput = new StringBuilder();
                tempInput = new StringBuilder();
                resultType = -1;
                inputView.setText("数据过大，无法计算");return;
            }
            else if (sign.contains(innerInput.subSequence(innerInput.length() - 1, innerInput.length()))) {
                tempInput.setCharAt(tempInput.length() - 1, '/');
                innerInput.setCharAt(innerInput.length() - 1, '/');
            }
            else {
                tempInput.append("/");
                innerInput.append("/");
            }
            inputView.setText(tempInput);
            figureRecyclerView.scrollToPosition(answerList.size() - 1);
        });
        button_multiply.setOnClickListener(v->{

            if(resultType == -1)
                return; var len = inputView.getText().length();
            if(len > 60)
            {
                innerInput = new StringBuilder();
                tempInput = new StringBuilder();
                resultType = -1;
                inputView.setText("数据过大，无法计算");return;
            }
            resultType = 0;
            if(tempInput.toString().equals(""))
                return;
            else if (sign.contains(innerInput.subSequence(innerInput.length() - 1, innerInput.length()))) {
                tempInput.setCharAt(tempInput.length() - 1, '*');
                innerInput.setCharAt(innerInput.length() - 1, '*');
            }
            else {
                tempInput.append("*");
                innerInput.append("*");
            }
            inputView.setText(tempInput);
            figureRecyclerView.scrollToPosition(answerList.size() - 1);
        });
        button_reduce.setOnClickListener(v ->
        {
            if(resultType == -1)
                return; var len = inputView.getText().length();
            if(len > 60)
            {
                innerInput = new StringBuilder();
                tempInput = new StringBuilder();
                resultType = -1;
                inputView.setText("数据过大，无法计算");return;
            }
            resultType = 0;
            if(tempInput.toString().equals("")) {
                tempInput.append("-");
                innerInput.append("-");
            }
            else if (sign.contains(innerInput.subSequence(innerInput.length() - 1, innerInput.length()))) {
                tempInput.setCharAt(tempInput.length() - 1, '-');
                innerInput.setCharAt(innerInput.length() -  1, '-');
            }
            else {
                tempInput.append("-");
                innerInput.append("-");
            }
            inputView.setText(tempInput);
            figureRecyclerView.scrollToPosition(answerList.size() - 1);
        });
        button_add.setOnClickListener(v ->
        {
            if(resultType == -1)
                return; var len = inputView.getText().length();
            if(len > 60)
            {
                innerInput = new StringBuilder();
                tempInput = new StringBuilder();
                resultType = -1;
                inputView.setText("数据过大，无法计算");return;
            }
            resultType = 0;
            if(tempInput.toString().equals(""))
                return;
            else if (sign.contains(innerInput.subSequence(innerInput.length() - 1, innerInput.length()))) {
                tempInput.setCharAt(tempInput.length() - 1, '+');
                innerInput.setCharAt(innerInput.length() - 1, '+');
            }
            else {
                tempInput.append("+");
                innerInput.append("+");
            }
            inputView.setText(tempInput);
            figureRecyclerView.scrollToPosition(answerList.size() - 1);
        });

        //biggest point!!!!!
        button_equals.setOnClickListener(v->
        { var len = inputView.getText().length();
            if(len > 60)
            {
                innerInput = new StringBuilder();
                tempInput = new StringBuilder();
                resultType = -1;
                inputView.setText("数据过大，无法计算");return;
            }
            if(resultType == -1)
                return;
            if(resultType == 1)
                return;
            answerList.add(new Out(tempInput.toString()));
            Log.d(TAG, "the expression:" + tempInput);

            tempInput =new StringBuilder(new Out(innerInput.toString()).getAnswer());
            innerInput = new StringBuilder(tempInput.toString());
            if (!isNumber(tempInput.toString()))
                resultType = -1;
            else
                resultType = 1;
            Log.d(TAG, "the Answer: "  +tempInput);
            inputView.setText(tempInput);
            adapter.notifyItemInserted(answerList.size() - 1);
            figureRecyclerView.scrollToPosition(answerList.size() - 1);
        });


        button_7.setOnClickListener(v->
        {
            if(resultType == -1) {
                tempInput = new StringBuilder();
                innerInput = new StringBuilder();
            }

            else if(resultType == 1) {
                tempInput = new StringBuilder();
                innerInput = new StringBuilder();
            } var len = inputView.getText().length();
            if(len > 60)
            {
                innerInput = new StringBuilder();
                tempInput = new StringBuilder();
                resultType = -1;
                inputView.setText("数据过大，无法计算");return;
            }
            String lastNum = getLastNumber(innerInput.toString());
            if(lastNum.length() == 1 && lastNum.charAt(0) == '0')
            {
                tempInput.delete(tempInput.length() -1,tempInput.length());
                innerInput.delete(innerInput.length()- 1, innerInput.length());
            }
            resultType = 0;

            if(!innerInput.toString().equals("") && (innerInput.charAt(innerInput.length() - 1) == ')' ||
                    innerInput.charAt(innerInput.length() - 1) == '%' || Common.isConstant(String.valueOf(tempInput.charAt(tempInput.length() -1)))))
                innerInput.append("*");
            tempInput.append("7");
            innerInput.append("7");
            inputView.setText(tempInput);
            figureRecyclerView.scrollToPosition(answerList.size() - 1);
        });
        button_8.setOnClickListener(v->
        {
            if(resultType == -1) {
                tempInput = new StringBuilder();
                innerInput = new StringBuilder();
            }

            else if(resultType == 1) {
                tempInput = new StringBuilder();
                innerInput = new StringBuilder();
            } var len = inputView.getText().length();
            if(len > 60)
            {
                innerInput = new StringBuilder();
                tempInput = new StringBuilder();
                resultType = -1;
                inputView.setText("数据过大，无法计算");return;
            }
            String lastNum = getLastNumber(innerInput.toString());
            if(lastNum.length() == 1 && lastNum.charAt(0) == '0')
            {
                tempInput.delete(tempInput.length() -1,tempInput.length());
                innerInput.delete(innerInput.length()- 1, innerInput.length());
            }

            resultType = 0;
            if(!innerInput.toString().equals("") && (innerInput.charAt(innerInput.length() - 1) == ')' ||
                    innerInput.charAt(innerInput.length() - 1) == '%' || Common.isConstant(String.valueOf(tempInput.charAt(tempInput.length() -1)))))
                innerInput.append("*");
            tempInput.append("8");
            innerInput.append("8");
            inputView.setText(tempInput);
            figureRecyclerView.scrollToPosition(answerList.size() - 1);
        });
        button_9.setOnClickListener(v->
        {
            if(resultType == -1) {
                tempInput = new StringBuilder();
                innerInput = new StringBuilder();
            }

            else if(resultType == 1) {
                tempInput = new StringBuilder();
                innerInput = new StringBuilder();
            } var len = inputView.getText().length();
            if(len > 60)
            {
                innerInput = new StringBuilder();
                tempInput = new StringBuilder();
                resultType = -1;
                inputView.setText("数据过大，无法计算");return;
            }

            String lastNum = getLastNumber(innerInput.toString());
            if(lastNum.length() == 1 && lastNum.charAt(0) == '0')
            {
                tempInput.delete(tempInput.length() -1,tempInput.length());
                innerInput.delete(innerInput.length()- 1, innerInput.length());
            }

            resultType = 0;
            if(!innerInput.toString().equals("") && (innerInput.charAt(innerInput.length() - 1) == ')' ||
                    innerInput.charAt(innerInput.length() - 1) == '%' || Common.isConstant(String.valueOf(tempInput.charAt(tempInput.length() -1)))))
                innerInput.append("*");
            tempInput.append("9");
            innerInput.append("9");
            inputView.setText(tempInput);
            figureRecyclerView.scrollToPosition(answerList.size() - 1);
        });
        button_4.setOnClickListener(v->{
            if(resultType == -1) {
                tempInput = new StringBuilder();
                innerInput = new StringBuilder();
            }

            else if(resultType == 1) {
                tempInput = new StringBuilder();
                innerInput = new StringBuilder();
            }
            var len = inputView.getText().length();
            if(len > 60)
            {
                innerInput = new StringBuilder();
                tempInput = new StringBuilder();
                resultType = -1;
                inputView.setText("数据过大，无法计算");return;
            }

            String lastNum = getLastNumber(innerInput.toString());
            if(lastNum.length() == 1 && lastNum.charAt(0) == '0')
            {
                tempInput.delete(tempInput.length() -1,tempInput.length());
                innerInput.delete(innerInput.length()- 1, innerInput.length());
            }
            resultType = 0;
            if(!innerInput.toString().equals("") && (innerInput.charAt(innerInput.length() - 1) == ')' ||
                    innerInput.charAt(innerInput.length() - 1) == '%' || Common.isConstant(String.valueOf(tempInput.charAt(tempInput.length() -1)))))
                innerInput.append("*");
            tempInput.append("4");
            innerInput.append("4");
            inputView.setText(tempInput);
            figureRecyclerView.scrollToPosition(answerList.size() - 1);
        });
        button_5.setOnClickListener(v->
        {
            if(resultType == -1) {
                tempInput = new StringBuilder();
                innerInput = new StringBuilder();
            }

            else if(resultType == 1) {
                tempInput = new StringBuilder();
                innerInput = new StringBuilder();
            } var len = inputView.getText().length();
            if(len > 60)
            {
                innerInput = new StringBuilder();
                tempInput = new StringBuilder();
                resultType = -1;
                inputView.setText("数据过大，无法计算");return;
            }

            String lastNum = getLastNumber(innerInput.toString());
            if(lastNum.length() == 1 && lastNum.charAt(0) == '0')
            {
                tempInput.delete(tempInput.length() -1,tempInput.length());
                innerInput.delete(innerInput.length()- 1, innerInput.length());
            }
            resultType = 0;
            if(!innerInput.toString().equals("") && (innerInput.charAt(innerInput.length() - 1) == ')' ||
                    innerInput.charAt(innerInput.length() - 1) == '%' || Common.isConstant(String.valueOf(tempInput.charAt(tempInput.length() -1)))))
                innerInput.append("*");
            tempInput.append("5");
            innerInput.append("5");
            inputView.setText(tempInput);
            figureRecyclerView.scrollToPosition(answerList.size() - 1);
        });

        button_6.setOnClickListener(v->
        {
            if(resultType == -1) {
                tempInput = new StringBuilder();
                innerInput = new StringBuilder();
            }

            else if(resultType == 1) {
                tempInput = new StringBuilder();
                innerInput = new StringBuilder();
            }
            var len = inputView.getText().length();
            if(len > 60)
            {
                innerInput = new StringBuilder();
                tempInput = new StringBuilder();
                resultType = -1;
                inputView.setText("数据过大，无法计算");return;
            }
            String lastNum = getLastNumber(innerInput.toString());
            if(lastNum.length() == 1 && lastNum.charAt(0) == '0')
            {
                tempInput.delete(tempInput.length() -1,tempInput.length());
                innerInput.delete(innerInput.length()- 1, innerInput.length());
            }

            resultType = 0;
            if(!innerInput.toString().equals("") && (innerInput.charAt(innerInput.length() - 1) == ')' ||
                    innerInput.charAt(innerInput.length() - 1) == '%' || Common.isConstant(String.valueOf(tempInput.charAt(tempInput.length() -1)))))
                innerInput.append("*");
            tempInput.append("6");
            innerInput.append("6");
            inputView.setText(tempInput);
            figureRecyclerView.scrollToPosition(answerList.size() - 1);
        });
        button_1.setOnClickListener(v->
        {
            if(resultType == -1) {
                tempInput = new StringBuilder();
                innerInput = new StringBuilder();
            }

            else if(resultType == 1) {
                tempInput = new StringBuilder();
                innerInput = new StringBuilder();
            }
            var len = inputView.getText().length();
            if(len > 60)
            {
                innerInput = new StringBuilder();
                tempInput = new StringBuilder();
                resultType = -1;
                inputView.setText("数据过大，无法计算");return;
            }
            String lastNum = getLastNumber(innerInput.toString());
            if(lastNum.length() == 1 && lastNum.charAt(0) == '0')
            {
                tempInput.delete(tempInput.length() -1,tempInput.length());
                innerInput.delete(innerInput.length()- 1, innerInput.length());
            }
            resultType = 0;
            if(!innerInput.toString().equals("") && (innerInput.charAt(innerInput.length() - 1) == ')' ||
                    innerInput.charAt(innerInput.length() - 1) == '%' || Common.isConstant(String.valueOf(tempInput.charAt(tempInput.length() -1)))))
                innerInput.append("*");
            tempInput.append("1");
            innerInput.append("1");
            inputView.setText(tempInput);
            figureRecyclerView.scrollToPosition(answerList.size() - 1);
        });
        button_2.setOnClickListener(v->
        {
            if(resultType == -1) {
                tempInput = new StringBuilder();
                innerInput = new StringBuilder();
            }


            else if(resultType == 1) {
                tempInput = new StringBuilder();
                innerInput = new StringBuilder();
            }
            var len = inputView.getText().length();
            if(len > 60)
            {
                innerInput = new StringBuilder();
                tempInput = new StringBuilder();
                resultType = -1;
                inputView.setText("数据过大，无法计算");return;
            }
            String lastNum = getLastNumber(innerInput.toString());
            if(lastNum.length() == 1 && lastNum.charAt(0) == '0')
            {
                tempInput.delete(tempInput.length() -1,tempInput.length());
                innerInput.delete(innerInput.length()- 1, innerInput.length());
            }
            resultType = 0;
            if(!innerInput.toString().equals("") && (innerInput.charAt(innerInput.length() - 1) == ')' ||
                    innerInput.charAt(innerInput.length() - 1) == '%' || Common.isConstant(String.valueOf(tempInput.charAt(tempInput.length() -1)))))
                innerInput.append("*");
            tempInput.append("2");
            innerInput.append("2");
            inputView.setText(tempInput);
            figureRecyclerView.scrollToPosition(answerList.size() - 1);
        });
        button_3.setOnClickListener(v->
        {
            if(resultType == -1) {
                tempInput = new StringBuilder();
                innerInput = new StringBuilder();
            }

            else if(resultType == 1) {
                tempInput = new StringBuilder();
                innerInput = new StringBuilder();
            } var len = inputView.getText().length();
            if(len > 60)
            {
                innerInput = new StringBuilder();
                tempInput = new StringBuilder();
                resultType = -1;
                inputView.setText("数据过大，无法计算");return;
            }

            String lastNum = getLastNumber(innerInput.toString());
            if(lastNum.length() == 1 && lastNum.charAt(0) == '0')
            {
                tempInput.delete(tempInput.length() -1,tempInput.length());
                innerInput.delete(innerInput.length()- 1, innerInput.length());
            }
            resultType = 0;
            if(!innerInput.toString().equals("") && (innerInput.charAt(innerInput.length() - 1) == ')' ||
                    innerInput.charAt(innerInput.length() - 1) == '%' || Common.isConstant(String.valueOf(tempInput.charAt(tempInput.length() -1)))))
                innerInput.append("*");
            tempInput.append("3");
            innerInput.append("3");
            inputView.setText(tempInput);
            figureRecyclerView.scrollToPosition(answerList.size() - 1);
        });


        button_0.setOnClickListener(v->{
            if(resultType == -1) {
                tempInput = new StringBuilder();
                innerInput = new StringBuilder();
            }
            else if(resultType == 1) {
                tempInput = new StringBuilder();
                innerInput = new StringBuilder();
            } var len = inputView.getText().length();
            if(len > 60)
            {
                innerInput = new StringBuilder();
                tempInput = new StringBuilder();
                resultType = -1;
                inputView.setText("数据过大，无法计算");return;
            }
            resultType = 0;
            if(!innerInput.toString().equals("") && (innerInput.charAt(innerInput.length() - 1) == ')' ||
                    innerInput.charAt(innerInput.length() - 1) == '%' || Common.isConstant(String.valueOf(tempInput.charAt(tempInput.length() -1))))) {
                innerInput.append("*0");
                tempInput.append("0");
            }
            else if(innerInput.toString().equals("")) {
                tempInput.append("0");
                innerInput.append("0");
            }
            else if(getLastNumber(innerInput.toString()).equals("0"))
            {
                return;
            }
            else {
                tempInput.append("0");
                innerInput.append("0");
            }
            inputView.setText(tempInput);
            figureRecyclerView.scrollToPosition(answerList.size() - 1);
        });

        button_point.setOnClickListener(v->
        {
            if(resultType == -1) {
                tempInput = new StringBuilder();
                innerInput = new StringBuilder();
            }

            if (innerInput.toString().equals("")) {
                tempInput.append(".");
                innerInput.append("0.");
                return;
            } var len = inputView.getText().length();
            if(len > 60)
            {
                innerInput = new StringBuilder();
                tempInput = new StringBuilder();
                resultType = -1;
                inputView.setText("数据过大，无法计算");return;
            }
            if((innerInput.charAt(innerInput.length() - 1) == ')' ||
                    innerInput.charAt(innerInput.length() - 1) == '%'))
            {
                innerInput.append("*0.");
                tempInput.append(".");
            }
            else if(innerInput.charAt(innerInput.length() - 1) == '(')
            {
                innerInput.append("0.");
                tempInput.append(".");

            }
            else if (sign.contains(innerInput.subSequence(innerInput.length() - 1, innerInput.length())))
            {
                tempInput.append(".");
                innerInput.append("0.");
            }
            else if (getLastNumber(innerInput.toString()).contains("."))
            {
                return;
            }
            else
            {
                tempInput.append(".");
                innerInput.append(".");
            }
            resultType = 0;
            inputView.setText(tempInput);
            figureRecyclerView.scrollToPosition(answerList.size() - 1);
        });
    }
    private void InitButton()
    {
        Button button_0 = findViewById(R.id.button_0);
        Button button_1 = findViewById(R.id.button_1);
        Button button_2 = findViewById(R.id.button_2);
        Button button_3 = findViewById(R.id.button_3);
        Button button_4 = findViewById(R.id.button_4);
        Button button_5 = findViewById(R.id.button_5);
        Button button_6 = findViewById(R.id.button_6);
        Button button_7 = findViewById(R.id.button_7);
        Button button_8 = findViewById(R.id.button_8);
        Button button_9 = findViewById(R.id.button_9);
        Button button_clean = findViewById(R.id.button_clean);
        Button button_back = findViewById(R.id.button_back);
        Button button_derive = findViewById(R.id.button_derive);
        Button button_multiply = findViewById(R.id.buttton_multiply);
        Button button_reduce = findViewById(R.id.button_reduce);
        Button button_add = findViewById(R.id.button_add);
        Button button_equals = findViewById(R.id.button_equals);
        Button button_point = findViewById(R.id.button_point);
        Button button_left_bracket= findViewById(R.id.button_left_brackets);
        Button button_right_bracket = findViewById(R.id.button_right_brackets);


        button_clean.setOnClickListener(v->
        {
            resultType = 0;
            if(innerInput.toString().equals(""))
            {
                answerList.clear();
                answerList.add(new Out(""));
                adapter = new OutAdapter(answerList);
                figureRecyclerView.setAdapter(adapter);
            }
            else {
                tempInput = new StringBuilder();
                innerInput = new StringBuilder();
                inputView.setText("");
                figureRecyclerView.scrollToPosition(answerList.size() - 1);
            }
        });

        button_left_bracket.setOnClickListener(v->
        {
            if(resultType == -1)
            {
                tempInput = new StringBuilder();
                innerInput = new StringBuilder();
            } var len = inputView.getText().length();
            if(len > 60)
            {
                innerInput = new StringBuilder();
                tempInput = new StringBuilder();
                resultType = -1;
                inputView.setText("数据过大，无法计算");return;
            }
            // if the input is empty, add left brackets
            if(innerInput.toString().equals("")) {
                tempInput.append("(");
                innerInput.append("(");
            }
            else
            {
                char temp = innerInput.charAt(innerInput.length() - 1);
                if ((48 <= temp && temp <= 57) || temp == ')' || temp == '%') {
                    tempInput.append("(");
                    innerInput.append("*(");
                }
                else if (sign.contains(String.valueOf(temp))) {
                    tempInput.append("(");
                    innerInput.append("(");
                }
                else
                {
                    tempInput.append("(");
                    innerInput.append("(");
                }
            }
            resultType = 0;
            inputView.setText(tempInput);
            figureRecyclerView.scrollToPosition(answerList.size() - 1);
    });
        button_right_bracket.setOnClickListener(v->{
            if(resultType == -1)
            {
                tempInput = new StringBuilder();
                innerInput = new StringBuilder();
            } var len = inputView.getText().length();
            if(len > 60)
            {
                innerInput = new StringBuilder();
                tempInput = new StringBuilder();
                resultType = -1;
                inputView.setText("数据过大，无法计算");return;
            }
            // if the input is empty, add left brackets
            if(innerInput.toString().equals("")) {
                tempInput.append(")");
                innerInput.append(")");
            }
            else
            {
                char temp = innerInput.charAt(innerInput.length() - 1);
                if ((48 <= temp && temp <= 57) || temp == ')' || temp == '%') {
                    tempInput.append(")");
                    innerInput.append(")");
                }
                else if (sign.contains(String.valueOf(temp))) {
                    tempInput.append(")");
                    innerInput.append(")");
                }
                else
                {
                    tempInput.append(")");
                    innerInput.append(")");
                }
            }
            resultType = 0;
            inputView.setText(tempInput);
            figureRecyclerView.scrollToPosition(answerList.size() - 1);
        });
        button_back.setOnClickListener(v ->
        {
            if(resultType == -1)
            {
                innerInput = new StringBuilder();
                tempInput = new StringBuilder();
                inputView.setText(tempInput);
            }
            if(resultType == 1)
            {
                innerInput = new StringBuilder();
                tempInput = new StringBuilder();
                inputView.setText(tempInput);
                return;
            }
            resultType = 0;
            if((!tempInput.toString().equals("") )&& innerInput.length() >= 2 && tempInput.length() >=2)
            {
                String tempString1 = innerInput.toString();
                tempString1 = tempString1.substring(tempString1.length() - 2);
                String tempString2 = tempInput.toString();
                tempString2 = tempString2.substring(tempString2.length() - 2);
                if(tempString1.equals(tempString2)) {
                    tempInput.delete(tempInput.length() - 1, tempInput.length());
                    innerInput.delete(innerInput.length() - 1,innerInput.length());
                }
                else
                {
                    tempInput.delete(tempInput.length() - 1, tempInput.length());
                    innerInput.delete(innerInput.length() - 2, innerInput.length());
                }
            }
            else if(innerInput.toString().equals(""))
            {
                return;
            }
            else
            {
                innerInput.delete(innerInput.length()- 1, innerInput.length());
                tempInput.delete(tempInput.length() -1, tempInput.length());
            }
            inputView.setText(tempInput);
            figureRecyclerView.scrollToPosition(answerList.size() - 1);
        });



        button_derive.setOnClickListener(v->
        {
            if(resultType == -1)
                return;
                resultType = 0; var len = inputView.getText().length();
            if(len > 60)
            {
                innerInput = new StringBuilder();
                tempInput = new StringBuilder();
                resultType = -1;
                inputView.setText("数据过大，无法计算");return;
            }
            if(tempInput.toString().equals(""))
                return;
            else if (sign.contains(innerInput.subSequence(innerInput.length() - 1, innerInput.length()))) {
                tempInput.setCharAt(tempInput.length() - 1, '/');
                innerInput.setCharAt(innerInput.length() - 1, '/');
            }
            else {
                tempInput.append("/");
                innerInput.append("/");
            }
            inputView.setText(tempInput);
            figureRecyclerView.scrollToPosition(answerList.size() - 1);
        });
        button_multiply.setOnClickListener(v->{

            if(resultType == -1)
                return; var len = inputView.getText().length();
            if(len > 60)
            {
                innerInput = new StringBuilder();
                tempInput = new StringBuilder();
                resultType = -1;
                inputView.setText("数据过大，无法计算");return;
            }
            resultType = 0;
            if(tempInput.toString().equals(""))
                return;
            else if (sign.contains(innerInput.subSequence(innerInput.length() - 1, innerInput.length()))) {
                tempInput.setCharAt(tempInput.length() - 1, '*');
                innerInput.setCharAt(innerInput.length() - 1, '*');
            }
            else {
                tempInput.append("*");
                innerInput.append("*");
            }
            inputView.setText(tempInput);
            figureRecyclerView.scrollToPosition(answerList.size() - 1);
        });
        button_reduce.setOnClickListener(v ->
        {
            if(resultType == -1)
                return;
                resultType = 0; var len = inputView.getText().length();
            if(len > 60)
            {
                innerInput = new StringBuilder();
                tempInput = new StringBuilder();
                resultType = -1;
                inputView.setText("数据过大，无法计算");return;
            }
            if(tempInput.toString().equals("")) {
                tempInput.append("-");
            innerInput.append("-");
            }
                else if (sign.contains(innerInput.subSequence(innerInput.length() - 1, innerInput.length()))) {
                tempInput.setCharAt(tempInput.length() - 1, '-');
                innerInput.setCharAt(innerInput.length() -  1, '-');
                }
                else {
                tempInput.append("-");
            innerInput.append("-");
                }
            inputView.setText(tempInput);
            figureRecyclerView.scrollToPosition(answerList.size() - 1);
        });
        button_add.setOnClickListener(v ->
        {
            if(resultType == -1)
                return; var len = inputView.getText().length();
            if(len > 60)
            {
                innerInput = new StringBuilder();
                tempInput = new StringBuilder();
                resultType = -1;
                inputView.setText("数据过大，无法计算");return;
            }
                resultType = 0;
            if(tempInput.toString().equals(""))
                return;
            else if (sign.contains(innerInput.subSequence(innerInput.length() - 1, innerInput.length()))) {
                tempInput.setCharAt(tempInput.length() - 1, '+');
            innerInput.setCharAt(innerInput.length() - 1, '+');
            }
                else {
                tempInput.append("+");
                innerInput.append("+");
                }
            inputView.setText(tempInput);
            figureRecyclerView.scrollToPosition(answerList.size() - 1);
        });

        //biggest point!!!!!
        button_equals.setOnClickListener(v->
        {
            if(resultType == -1)
                return;
            else if(resultType == 1)
                return;
            answerList.add(new Out(tempInput.toString()));
            Log.d(TAG, "the expression:" + tempInput);

            tempInput =new StringBuilder(new Out(innerInput.toString()).getAnswer());
            innerInput = new StringBuilder(tempInput.toString());
            if (!isNumber(tempInput.toString()))
                resultType = -1;
                else
                    resultType = 1;
            Log.d(TAG, "the Answer: "  +tempInput);
            inputView.setText(tempInput);
            adapter.notifyItemInserted(answerList.size() - 1);
            figureRecyclerView.scrollToPosition(answerList.size() - 1);
        });


        button_7.setOnClickListener(v->
        {
            if(resultType == -1) {
                tempInput = new StringBuilder();
                innerInput = new StringBuilder();
            }

            else if(resultType == 1) {
                tempInput = new StringBuilder();
                innerInput = new StringBuilder();
            } var len = inputView.getText().length();
            if(len > 60)
            {
                innerInput = new StringBuilder();
                tempInput = new StringBuilder();
                resultType = -1;
                inputView.setText("数据过大，无法计算");return;
            }
            String lastNum = getLastNumber(innerInput.toString());
            if(lastNum.length() == 1 && lastNum.charAt(0) == '0')
            {
                tempInput.delete(tempInput.length() -1,tempInput.length());
                innerInput.delete(innerInput.length()- 1, innerInput.length());
            }
            resultType = 0;

            if(!innerInput.toString().equals("") && (innerInput.charAt(innerInput.length() - 1) == ')' ||
                    innerInput.charAt(innerInput.length() - 1) == '%'))
                innerInput.append("*");
            tempInput.append("7");
            innerInput.append("7");
            inputView.setText(tempInput);
            figureRecyclerView.scrollToPosition(answerList.size() - 1);
        });
        button_8.setOnClickListener(v->
        {
            if(resultType == -1) {
                tempInput = new StringBuilder();
                innerInput = new StringBuilder();
            }

            else if(resultType == 1) {
                tempInput = new StringBuilder();
                innerInput = new StringBuilder();
            } var len = inputView.getText().length();
            if(len > 60)
            {
                innerInput = new StringBuilder();
                tempInput = new StringBuilder();
                resultType = -1;
                inputView.setText("数据过大，无法计算");return;
            }
            String lastNum = getLastNumber(innerInput.toString());
            if(lastNum.length() == 1 && lastNum.charAt(0) == '0')
            {
                tempInput.delete(tempInput.length() -1,tempInput.length());
                innerInput.delete(innerInput.length()- 1, innerInput.length());
            }

            resultType = 0;
            if(!innerInput.toString().equals("") && (innerInput.charAt(innerInput.length() - 1) == ')' ||
                    innerInput.charAt(innerInput.length() - 1) == '%'))
                innerInput.append("*");
            tempInput.append("8");
            innerInput.append("8");
            inputView.setText(tempInput);
            figureRecyclerView.scrollToPosition(answerList.size() - 1);
        });
        button_9.setOnClickListener(v->
        {
            if(resultType == -1) {
                tempInput = new StringBuilder();
                innerInput = new StringBuilder();
            }

            else if(resultType == 1) {
                tempInput = new StringBuilder();
                innerInput = new StringBuilder();
            } var len = inputView.getText().length();
            if(len > 60)
            {
                innerInput = new StringBuilder();
                tempInput = new StringBuilder();
                resultType = -1;
                inputView.setText("数据过大，无法计算");return;
            }

            String lastNum = getLastNumber(innerInput.toString());
            if(lastNum.length() == 1 && lastNum.charAt(0) == '0')
            {
                tempInput.delete(tempInput.length() -1,tempInput.length());
                innerInput.delete(innerInput.length()- 1, innerInput.length());
            }

            resultType = 0;
            if(!innerInput.toString().equals("") && (innerInput.charAt(innerInput.length() - 1) == ')' ||
                    innerInput.charAt(innerInput.length() - 1) == '%'))
                innerInput.append("*");
            tempInput.append("9");
            innerInput.append("9");
            inputView.setText(tempInput);
            figureRecyclerView.scrollToPosition(answerList.size() - 1);
        });
        button_4.setOnClickListener(v->{
            if(resultType == -1) {
                tempInput = new StringBuilder();
                innerInput = new StringBuilder();
            }

            else if(resultType == 1) {
                tempInput = new StringBuilder();
                innerInput = new StringBuilder();
            } var len = inputView.getText().length();
            if(len > 60)
            {
                innerInput = new StringBuilder();
                tempInput = new StringBuilder();
                resultType = -1;
                inputView.setText("数据过大，无法计算");return;
            }


            String lastNum = getLastNumber(innerInput.toString());
            if(lastNum.length() == 1 && lastNum.charAt(0) == '0')
            {
                tempInput.delete(tempInput.length() -1,tempInput.length());
                innerInput.delete(innerInput.length()- 1, innerInput.length());
            }
            resultType = 0;
            if(!innerInput.toString().equals("") && (innerInput.charAt(innerInput.length() - 1) == ')' ||
                    innerInput.charAt(innerInput.length() - 1) == '%'))
                innerInput.append("*");
            tempInput.append("4");
            innerInput.append("4");
            inputView.setText(tempInput);
            figureRecyclerView.scrollToPosition(answerList.size() - 1);
        });
        button_5.setOnClickListener(v->
        {
            if(resultType == -1) {
                tempInput = new StringBuilder();
                innerInput = new StringBuilder();
            }

            else if(resultType == 1) {
                tempInput = new StringBuilder();
                innerInput = new StringBuilder();
            } var len = inputView.getText().length();
            if(len > 60)
            {
                innerInput = new StringBuilder();
                tempInput = new StringBuilder();
                resultType = -1;
                inputView.setText("数据过大，无法计算");return;
            }

            String lastNum = getLastNumber(innerInput.toString());
            if(lastNum.length() == 1 && lastNum.charAt(0) == '0')
            {
                tempInput.delete(tempInput.length() -1,tempInput.length());
                innerInput.delete(innerInput.length()- 1, innerInput.length());
            }
            resultType = 0;
            if(!innerInput.toString().equals("") && (innerInput.charAt(innerInput.length() - 1) == ')' ||
                    innerInput.charAt(innerInput.length() - 1) == '%'))
                innerInput.append("*");
            tempInput.append("5");
            innerInput.append("5");
            inputView.setText(tempInput);
            figureRecyclerView.scrollToPosition(answerList.size() - 1);
        });

        button_6.setOnClickListener(v->
        {
            if(resultType == -1) {
                tempInput = new StringBuilder();
                innerInput = new StringBuilder();
            }

            else if(resultType == 1) {
                tempInput = new StringBuilder();
                innerInput = new StringBuilder();
            } var len = inputView.getText().length();
            if(len > 60)
            {
                innerInput = new StringBuilder();
                tempInput = new StringBuilder();
                resultType = -1;
                inputView.setText("数据过大，无法计算");return;
            }

            String lastNum = getLastNumber(innerInput.toString());
            if(lastNum.length() == 1 && lastNum.charAt(0) == '0')
            {
                tempInput.delete(tempInput.length() -1,tempInput.length());
                innerInput.delete(innerInput.length()- 1, innerInput.length());
            }

            resultType = 0;
            if(!innerInput.toString().equals("") && (innerInput.charAt(innerInput.length() - 1) == ')' ||
                    innerInput.charAt(innerInput.length() - 1) == '%'))
                innerInput.append("*");
            tempInput.append("6");
            innerInput.append("6");
            inputView.setText(tempInput);
            figureRecyclerView.scrollToPosition(answerList.size() - 1);
        });
        button_1.setOnClickListener(v->
        {
            if(resultType == -1) {
                tempInput = new StringBuilder();
                innerInput = new StringBuilder();
            }

            else if(resultType == 1) {
                tempInput = new StringBuilder();
                innerInput = new StringBuilder();
            } var len = inputView.getText().length();
            if(len > 60)
            {
                innerInput = new StringBuilder();
                tempInput = new StringBuilder();
                resultType = -1;
                inputView.setText("数据过大，无法计算");return;
            }

            String lastNum = getLastNumber(innerInput.toString());
            if(lastNum.length() == 1 && lastNum.charAt(0) == '0')
            {
                tempInput.delete(tempInput.length() -1,tempInput.length());
                innerInput.delete(innerInput.length()- 1, innerInput.length());
            }
            resultType = 0;
            if(!innerInput.toString().equals("") && (innerInput.charAt(innerInput.length() - 1) == ')' ||
                    innerInput.charAt(innerInput.length() - 1) == '%'))
                innerInput.append("*");
            tempInput.append("1");
            innerInput.append("1");
            inputView.setText(tempInput);
            figureRecyclerView.scrollToPosition(answerList.size() - 1);
        });
        button_2.setOnClickListener(v->
        {
            if(resultType == -1) {
                tempInput = new StringBuilder();
                innerInput = new StringBuilder();
            }


            else if(resultType == 1) {
                tempInput = new StringBuilder();
                innerInput = new StringBuilder();
            } var len = inputView.getText().length();
            if(len > 60)
            {
                innerInput = new StringBuilder();
                tempInput = new StringBuilder();
                resultType = -1;
                inputView.setText("数据过大，无法计算");return;
            }

            String lastNum = getLastNumber(innerInput.toString());
            if(lastNum.length() == 1 && lastNum.charAt(0) == '0')
            {
                tempInput.delete(tempInput.length() -1,tempInput.length());
                innerInput.delete(innerInput.length()- 1, innerInput.length());
            }
            resultType = 0;
            if(!innerInput.toString().equals("") && (innerInput.charAt(innerInput.length() - 1) == ')' ||
                    innerInput.charAt(innerInput.length() - 1) == '%'))
                innerInput.append("*");
            tempInput.append("2");
            innerInput.append("2");
            inputView.setText(tempInput);
            figureRecyclerView.scrollToPosition(answerList.size() - 1);
        });
        button_3.setOnClickListener(v->
        {
            if(resultType == -1) {
                tempInput = new StringBuilder();
                innerInput = new StringBuilder();
            }

            else if(resultType == 1) {
                tempInput = new StringBuilder();
                innerInput = new StringBuilder();
            }
            var len = inputView.getText().length();
            if(len > 60)
            {
                innerInput = new StringBuilder();
                tempInput = new StringBuilder();
                resultType = -1;
                inputView.setText("数据过大，无法计算");return;
            }
            String lastNum = getLastNumber(innerInput.toString());
            if(lastNum.length() == 1 && lastNum.charAt(0) == '0')
            {
                tempInput.delete(tempInput.length() -1,tempInput.length());
                innerInput.delete(innerInput.length()- 1, innerInput.length());
            }
            resultType = 0;
            if(!innerInput.toString().equals("") && (innerInput.charAt(innerInput.length() - 1) == ')' ||
                    innerInput.charAt(innerInput.length() - 1) == '%'))
                innerInput.append("*");
            tempInput.append("3");
            innerInput.append("3");
            inputView.setText(tempInput);
            figureRecyclerView.scrollToPosition(answerList.size() - 1);
        });

        button_0.setOnClickListener(v->{
            if(resultType == -1) {
                tempInput = new StringBuilder();
                innerInput = new StringBuilder();
            }
            else if(resultType == 1) {
                tempInput = new StringBuilder();
            innerInput = new StringBuilder();
            } var len = inputView.getText().length();
            if(len > 60)
            {
                innerInput = new StringBuilder();
                tempInput = new StringBuilder();
                resultType = -1;
                inputView.setText("数据过大，无法计算");return;
            }
            resultType = 0;
            if(!innerInput.toString().equals("") && (innerInput.charAt(innerInput.length() - 1) == ')' ||
                    innerInput.charAt(innerInput.length() - 1) == '%')) {
                innerInput.append("*0");
                tempInput.append("0");
            }
            else if(innerInput.toString().equals("")) {
                tempInput.append("0");
                innerInput.append("0");
            }
            else if(getLastNumber(innerInput.toString()).equals("0"))
            {
                return;
            }
            else {
                tempInput.append("0");
                innerInput.append("0");
            }
            inputView.setText(tempInput);
            figureRecyclerView.scrollToPosition(answerList.size() - 1);
        });

        button_point.setOnClickListener(v->
        {
            if(resultType == -1) {
                tempInput = new StringBuilder();
                innerInput = new StringBuilder();
            }
            var len = inputView.getText().length();
            if(len > 60)
            {
                innerInput = new StringBuilder();
                tempInput = new StringBuilder();
                resultType = -1;
                inputView.setText("数据过大，无法计算");
                return;
            }
            if (innerInput.toString().equals("")) {
                tempInput.append(".");
                innerInput.append("0.");
                return;
            }
            if((innerInput.charAt(innerInput.length() - 1) == ')' ||
                    innerInput.charAt(innerInput.length() - 1) == '%'))
            {
                innerInput.append("*0.");
                tempInput.append(".");
            }
            else if(innerInput.charAt(innerInput.length() - 1) == '(')
            {
                innerInput.append("0.");
                tempInput.append(".");

            }
            else if (sign.contains(innerInput.subSequence(innerInput.length() - 1, innerInput.length())))
            {
                tempInput.append(".");
            innerInput.append("0.");
            }
            else if (getLastNumber(innerInput.toString()).contains("."))
            {
                return;
            }
            else
                {
                tempInput.append(".");
            innerInput.append(".");
            }
            resultType = 0;
            inputView.setText(tempInput);
            figureRecyclerView.scrollToPosition(answerList.size() - 1);
        });
    }
}