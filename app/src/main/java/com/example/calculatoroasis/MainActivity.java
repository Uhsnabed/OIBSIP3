package com.example.calculatoroasis;

import static android.webkit.WebSettings.PluginState.ON;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
   TextView t1,t2;
    Button b0,b1,b2,b3,b4,b5,b6,b7,b8,b9;
    Button C,AC,dot;
    Button divide,add,sub,mul,equal;
    Button on,off;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        t1 = findViewById(R.id.textView3);
        t2 = findViewById(R.id.textView4);

         assignId(b0,R.id.button2);
        assignId(b1,R.id.button12);
         assignId(b2,R.id.button8);
         assignId(b3,R.id.button18);
        assignId(b4,R.id.button11);
         assignId(b5,R.id.button13);
         assignId(b6,R.id.button20);
         assignId(b7,R.id.button15);
        assignId(b8,R.id.button6);
         assignId(b9,R.id.button14);

        assignId(divide,R.id.button9);
        assignId(add,R.id.button);
        assignId(sub,R.id.button19);
        assignId(mul,R.id.button7);
         assignId(equal,R.id.button16);

         assignId(C,R.id.button5);
        assignId(AC,R.id.button4);
        assignId(dot,R.id.button17);

        assignId(on,R.id.button3);
        assignId(off,R.id.button10);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    void assignId(Button btn,int id)
    {
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

  @Override
    public void onClick(View view){
        Button btn1 = (Button) view;
        String btn2 = btn1.getText().toString();
        String calculateData = t1.getText().toString();

        if(btn2.equals("ON"))
        {   t1.setText("");
            t2.setText("0");
            Toast.makeText(this, "Calculator Switched On", Toast.LENGTH_SHORT).show();
            return;
        }
        if(btn2.equals("OFF"))
        {
            finish();
            System.exit(0);
        }

        if(btn2.equals("AC")){
            t1.setText("");
            t2.setText("0");
            return;
        }
        if(btn2.equals("=")){
            t1.setText(t2.getText());
            return;
        }
        if(btn2.equals("C"))
        {    if(calculateData.length()>1)
            calculateData = calculateData.substring(0,calculateData.length()-1);
            else
           {
               return;
           }
        }
        else
        {
            calculateData = calculateData + btn2;
        }
        t1.setText(calculateData);

        String finalResult = getResult(calculateData);
        if(!finalResult.equals("Error")){
            t2.setText(finalResult);
        }
  }

  String getResult(String data)
  {
      try {
          Context context = Context.enter();
          context.setOptimizationLevel(-1);
          Scriptable scriptable = context.initStandardObjects();
          String finalResult = context.evaluateString(scriptable,data,"Javascript",1,null).toString();
          if(finalResult.endsWith(".0")){
              finalResult = finalResult.replace(".0","");
          }
          return finalResult;
      } catch (Exception e){
          return "Error";
      }
  }
}