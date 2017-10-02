package com.tianrui.calculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //set variables
    private EditText result;
    private EditText newNum;
    private TextView operation;

    //set two operands and one operation
    private Double operand1 = null;
    String pendingOperation = "=";

    //handle the case when phone rotates
    private static final String STATE_PENDING_OPERATION = "Pending_operation";
    private static final String STATE_OPERAND1 = "operand1";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //two main layouts, one for landscape.

        result = (EditText) findViewById(R.id.result);
        newNum = (EditText) findViewById(R.id.newNum);
        operation = (TextView) findViewById(R.id.operation);

        //assign the button
        Button button0 = (Button) findViewById(R.id.BTN0);
        Button button1 = (Button) findViewById(R.id.BTN1);
        Button button2 = (Button) findViewById(R.id.BTN2);
        Button button3 = (Button) findViewById(R.id.BTN3);
        Button button4 = (Button) findViewById(R.id.BTN4);
        Button button5 = (Button) findViewById(R.id.BTN5);
        Button button6 = (Button) findViewById(R.id.BTN6);
        Button button7 = (Button) findViewById(R.id.BTN7);
        Button button8 = (Button) findViewById(R.id.BTN8);
        Button button9 = (Button) findViewById(R.id.BTN9);
        Button buttonPlus = (Button) findViewById(R.id.BTNPlus);
        Button buttonMinus = (Button) findViewById(R.id.BTNMinus);
        Button buttonMulti = (Button) findViewById(R.id.BTNMultiple);
        Button buttonDivide = (Button) findViewById(R.id.BTNDivide);
        Button buttonDot = (Button) findViewById(R.id.BTNDot);
        Button buttonEquals = (Button) findViewById(R.id.BTNEquals);
        Button buttonNegative = (Button) findViewById(R.id.BTNNegative);
        Button buttonClear = (Button) findViewById((R.id.BTNClear));

        View.OnClickListener numListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                newNum.append(b.getText().toString());
            }
        };
        button0.setOnClickListener(numListener);
        button1.setOnClickListener(numListener);
        button2.setOnClickListener(numListener);
        button3.setOnClickListener(numListener);
        button4.setOnClickListener(numListener);
        button5.setOnClickListener(numListener);
        button6.setOnClickListener(numListener);
        button7.setOnClickListener(numListener);
        button8.setOnClickListener(numListener);
        button9.setOnClickListener(numListener);
        buttonDot.setOnClickListener(numListener);


        View.OnClickListener operationListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                String op = b.getText().toString();
                String value = newNum.getText().toString();

                try {
                    Double numValue = Double.valueOf(value);
                    performOperation(numValue,op);
                }catch (NumberFormatException e){
                    newNum.setText("");
                }

                pendingOperation = op;
                operation.setText(pendingOperation);
            }
        };

        buttonDivide.setOnClickListener(operationListener);
        buttonMinus.setOnClickListener(operationListener);
        buttonPlus.setOnClickListener(operationListener);
        buttonMulti.setOnClickListener(operationListener);
        buttonEquals.setOnClickListener(operationListener);

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result.setText("");
                newNum.setText("");
                operation.setText("");
                operand1 = null;
                pendingOperation = "=";
            }
        });

        buttonNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = newNum.getText().toString();
                if(value.length() == 0){
                    newNum.setText("-");
                }else{
                   try{
                       Double doubleValue = Double.valueOf(value);
                       doubleValue *= -1;
                       newNum.setText(doubleValue.toString());
                   }catch (NumberFormatException e){
                       newNum.setText("");
                   }
                }
            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(STATE_PENDING_OPERATION,pendingOperation);
        if(operand1 != null){
            outState.putDouble(STATE_OPERAND1,operand1);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        operand1 = savedInstanceState.getDouble(STATE_OPERAND1);
        pendingOperation = savedInstanceState.getString(STATE_PENDING_OPERATION);
    }

    private void performOperation(Double value, String op){
        if(operand1 == null){
            operand1 = value;
        }else {
            if (pendingOperation.equals("=")) {
                pendingOperation = op;
            }
            switch (pendingOperation) {
                case "=":
                    operand1 = value;
                    break;
                case "+":
                    operand1 += value;
                    break;
                case "-":
                    operand1 -= value;
                    break;
                case "*":
                    operand1 *= value;
                    break;
                //handle the zero numerator
                case "/":
                    if (value == 0) {
                        operand1 = 0.0;
                    } else {
                        operand1 /= value;
                    }
                    break;
            }
        }

        result.setText(operand1.toString());
        newNum.setText("");
    }


}
