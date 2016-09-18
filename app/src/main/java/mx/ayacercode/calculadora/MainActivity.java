package mx.ayacercode.calculadora;
import mx.ayacercode.operaciones.*;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    operaciones op=new operaciones();
    String cd;
    char []delet;
    int result, btonSelected;
    ArrayList <String> list=new ArrayList <String>();
    ArrayList <String> listPost=new ArrayList <String>();
    ArrayList <String> listA=new ArrayList <String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button n0=(Button)findViewById(R.id.button0);
        n0.setOnClickListener(this);
        Button n1=(Button)findViewById(R.id.button1);
        n0.setOnClickListener(this);
        Button n2=(Button)findViewById(R.id.button2);
        n0.setOnClickListener(this);
        Button n3=(Button)findViewById(R.id.button3);
        n0.setOnClickListener(this);
        Button n4=(Button)findViewById(R.id.button4);
        n0.setOnClickListener(this);
        Button n5=(Button)findViewById(R.id.button5);
        n0.setOnClickListener(this);
        Button n6=(Button)findViewById(R.id.button6);
        n0.setOnClickListener(this);
        Button n7=(Button)findViewById(R.id.button7);
        n0.setOnClickListener(this);
        Button n8=(Button)findViewById(R.id.button8);
        n0.setOnClickListener(this);
        Button n9=(Button)findViewById(R.id.button9);
        n0.setOnClickListener(this);
        Button n00=(Button)findViewById(R.id.button00);
        n0.setOnClickListener(this);
        Button nequal=(Button)findViewById(R.id.buttonResult);
        n0.setOnClickListener(this);
        Button nsum=(Button)findViewById(R.id.buttonSum);
        n0.setOnClickListener(this);
        Button nrest=(Button)findViewById(R.id.buttonRest);
        n0.setOnClickListener(this);
        Button nmult=(Button)findViewById(R.id.buttonMult);
        n0.setOnClickListener(this);
        Button ndiv=(Button)findViewById(R.id.buttonDiv);
        n0.setOnClickListener(this);
        Button npto=(Button)findViewById(R.id.buttonPto);
        n0.setOnClickListener(this);
        Button nc=(Button)findViewById(R.id.buttonC);
        n0.setOnClickListener(this);
        ImageButton ndel=(ImageButton)findViewById(R.id.imgBtonDelete);
        n0.setOnClickListener(this);
        Button nporcent=(Button)findViewById(R.id.buttonPorcent);
        n0.setOnClickListener(this);
        Button nparentOpen=(Button)findViewById(R.id.buttonParentOpen);
        n0.setOnClickListener(this);
        Button nparentClose=(Button)findViewById(R.id.buttonParentClose);
        n0.setOnClickListener(this);


    }

    @Override
    public void onClick(View v){
        TextView operacion=(TextView)findViewById(R.id.ope);
        TextView resultado=(TextView)findViewById(R.id.result);

        btonSelected =v.getId();

        /*Evaluar la variable de seleccion*/
       try {
           String cad= (String) operacion.getText();
           switch (btonSelected) {
               case R.id.button0:
                   operacion.setText(cad+"0");
                   break;
               case R.id.button00:
                   operacion.setText(cad+"00");
                   break;
               case R.id.button1:
                   operacion.setText(cad+"1");
                   break;
               case R.id.button2:
                   operacion.setText(cad+"2");
                   break;
               case R.id.button3:
                   operacion.setText(cad+"3");
                   break;
               case R.id.button4:
                   operacion.setText(cad+"4");
                   break;
               case R.id.button5:
                   operacion.setText(cad+"5");
                   break;
               case R.id.button6:
                   operacion.setText(cad+"6");
                   break;
               case R.id.button7:
                   operacion.setText(cad+"7");
                   break;
               case R.id.button8:
                   operacion.setText(cad+"8");
                   break;
               case R.id.button9:
                   operacion.setText(cad+"9");
                   break;
               case R.id.buttonPto:
                   operacion.setText(cad+".");
                   break;
               case R.id.buttonSum:
                   operacion.setText(cad+"+");
                   break;
               case R.id.buttonRest:
                   operacion.setText(cad+"-");
                   break;
               case R.id.buttonMult:
                   operacion.setText(cad+"*");
                   break;
               case R.id.buttonDiv:
                   operacion.setText(cad+"/");
                   break;
               case R.id.buttonC:
                   operacion.setText("");
                   resultado.setText("");
                   break;
               case R.id.buttonParentOpen:
                   operacion.setText(cad+"(");
                   break;
               case R.id.buttonParentClose:
                   operacion.setText(cad+")");
                   break;
               case R.id.buttonPorcent:
                   operacion.setText(cad+"%");
                   break;
               case R.id.imgBtonDelete:
                   delet=cad.toCharArray();
                   cad="";
                   for(int i=0; i<(delet.length-1);i++){
                       cad+=delet[i];
                   }
                   operacion.setText(cad);

                   break;
               case R.id.buttonResult:
                   operacion.setText("");
                       result = op.validar(cad);
                   if(result==1 || result==4 || result==5){
                       System.out.print("f(x): ");
                       for(int i=0;i<list.size();i++){
                           System.out.print(list.get(i));
                       }
                       //System.out.println("f(x): "+list);
                       System.out.println("");
                       list=op.preAcomodoOper(cad.toCharArray());
                       listPost=op.postfija(list);
                       //System.out.println("Post:"+listPost);
                       cad= String.valueOf(op.evaluaStack(listPost));
                       resultado.setText(cad);
                   }
                   else{
                       System.out.println("¡Expresión no Valida!");
                       operacion.setText("¡Expresion no Valida!");
                       resultado.setText("");}

                   break;
           }
       }
       catch (Exception e){
           operacion.setText("Error de operacion");
           resultado.setText("");
       }

    }

}
