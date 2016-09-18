package mx.ayacercode.operaciones;

/**
 * Created by Alberto on 10/09/16.
 */
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

public class operaciones {
    int tam=7;
    int parent=0;
    private int[][]matriz={{1,2,-1,-1,-1},
            {1,2,3,4,5},
            {1,2,-1,-1,-1},
            {1,2,-1,-1,-1},
            {1,-1,3,4,5},
            {-1,-1,-1,4,-1}};
    private String [][]nivelOper=
            {{"(","1"},
                    {"%","2"},
                    {"^","3"},
                    {"*","4"},
                    {"/","4"},
                    {"+","5"},
                    {"-","5"}};


    private int exp(char e){
        if((e>='0' && e<='9') || e=='x' ){return 0;}
        else if(e=='('){parent++;return 1;}
        else if(e=='+' || e=='-' || e=='*' || e=='/' ||e=='^' ){return 2;}
        else if(e==')'){parent--; if(parent==-1){return -1;}else{return 3;}}
        else if(e=='%'){return 4;}
        return -1;
    }

    public int validar(String e) throws IOException{
        char  []c= e.toCharArray();
        int i,col=0,fil=0;
        for(i=0; i<c.length; i++){

            col=exp(c[i]);
            if( col==-1 ){fil =-1;break;}
            else {
                //	System.out.println(c[i]+"["+fil+"]["+col+"]: "+matriz[fil][col]);
                if(matriz[fil][col]==-1 ){fil=-1; break;}
                else{fil=matriz[fil][col];}
            }
        }
        if(parent==0 && (fil==1 || fil==4 || fil==5)){/* postfija(c);*/ return fil;}
        //	System.out.println("Tparent: "+parent);
        return -1;
    }
    /*Adecua los caracteres en unidades correspondientes*/
    protected ArrayList <String> preExp(char [] exp){
        ArrayList <String> e=new ArrayList <String>();
        String element=""; //(455*x^12-5x+22)
        for(int i=0;i<exp.length; i++){
            if ((exp[i]>='0' && exp[i]<='9')){
                element+=exp[i];
            }
            else {//if(exp[i]==')'|| exp[i]=='+' || exp[i]=='-' || exp[i]=='*' || exp[i]=='/' || exp[i]=='^'){
                if(element !="" ){e.add(element);}
                e.add(String.valueOf(exp[i]));
                element="";
            }

        }
        if(element !="" ){e.add(element);}
        return e;
    }

    /*Agrega operador faltante por la multiplicación implicita de una variable con una constante o un parentesis ')'*/
    public  ArrayList <String> preAcomodoOper(char [] exp){

        String element=""; //(455*x^12-5x+22)
        for(int i=0;i<exp.length; i++){
            if ((i-1)>=0 && exp[i]=='x' && ((exp[i-1]>='0' && exp[i-1]<='9')|| exp[i-1]==')' )){
                element+='*';
                element+=exp[i];
            }
            else if(exp[i]>='0' && exp[i]<='9'){
                if(((i-1)>=0 && exp[i-1]==')' )){
                    element+='*';
                    element+=exp[i];
                }
                else if(i+1<exp.length &&exp[i+1]=='('){
                    element+=exp[i];
                    element+='*';
                }
                else element+=exp[i];
            }
            else element+=exp[i];
        }
        return preExp(element.toCharArray());
    }
    /*Prapara la expresión a postfija para despues evaluarla*/
    public ArrayList <String> postfija(ArrayList <String>exp){
        String op = "", aux = "";
        boolean continua=false;
        Stack <String> pilaOpe=new Stack <String>();
        ArrayList<String> pilaVal=new ArrayList <String>();

        for(int i=0; i<exp.size(); i++){
            //if ((exp[i]>='0' && exp[i]<='9')||exp[i]=='x'){pilaVal.push(exp[i]);}
            //	System.out.println("i:"+i+"_Exp:"+exp.get(i));

            if(exp.get(i).equals(")")){
                //	System.out.println(aux+","+op+ ": "+pilaOpe);
                if(!pilaOpe.empty()) op=pilaOpe.pop();

                while(!pilaOpe.empty() && aux!="("){
                    aux=pilaOpe.pop();
                    //		System.out.println(aux+","+op+ ": "+pilaOpe);
                    //			System.out.println(aux+" . "+op+ ": "+pilaVal);
                    if(!aux.equals("(")&& valor(aux)<valor(op)){
                        //pilaVal.push(aux);
                        //		System.out.println("aux1:"+aux);
                        pilaVal.add(aux);
                    }
                    else if(!op.equals("(") ){
                        //	pilaVal.push(op);
                        //		System.out.println(aux+",,"+op+ ": "+pilaOpe);
                        pilaVal.add(op);
                        op=aux;
                    }
                    else{ pilaOpe.push(aux); break;}
                }
                if(!op.equals("(")){ pilaVal.add(op);}//System.out.println("op2:"+op);/*pilaVal.push(op);*/}
                aux=""; op="";
            }
            else if (exp.get(i).equals("(") || exp.get(i).equals("+") || exp.get(i).equals("-") ||
                    exp.get(i).equals("*") || exp.get(i).equals("/") || exp.get(i).equals("^")){
                if(pilaOpe.empty()){
                    pilaOpe.push((String) exp.get(i));
                    //		System.out.println("Vacio: "+pilaOpe);
                }
                else{
                    op=(String) exp.get(i);
                    while(!pilaOpe.empty() ){
                        aux=pilaOpe.pop();
                        //	System.out.println(aux+"|"+op+ "; "+pilaOpe);
                        if(!aux.equals("(") && valor(op)>=valor(aux)){
                            pilaVal.add(aux);
                            //pilaVal.push(aux);
                            continua=true;
                        }
                        else{
                            pilaOpe.push(aux);
                            pilaOpe.push(op);
                            aux=""; op="";
                            continua=false;
                            break;
                        }
                        //		System.out.println("op:"+op+"aux:"+aux+" valor(op):"+valor(op)+" valor(aux):"+valor(aux));
                    }
                    if(continua)pilaOpe.push(op);
                }
            }
            else{
                //pilaVal.push((String) exp.get(i));
                pilaVal.add(exp.get(i));
                //	System.out.println("PilaVal "+pilaVal);
            }
        }
        while(!pilaOpe.isEmpty()){
            op=pilaOpe.pop();
            if(!op.equals("(")){pilaVal.add(op);} //System.out.println("op1:"+op);/*pilaVal.push(op);*/}
        }
        //System.out.println(pilaVal);
        return pilaVal;
    }

    /*Provee el numero de prioridad de los operadores  para la operacion de conversion a postfija*/
    private int valor(String c){
        int val=0;
        for(int i=0;i<tam;i++){
            if (nivelOper[i][0].equals(c)){
                val = Integer.parseInt(nivelOper[i][1]);
            }
        }
        return val;
    }//x^3-8x^2-23x+30

    /*Evalua la expresión*/
    public Double evaluaStack(ArrayList <String> cad){
        double aux=0;
        for(int i=0;i<cad.size(); i++){
            //	System.out.print(i);
            switch(cad.get(i)){
                case "+":
                    aux=suma(Double.parseDouble(cad.get(i-2)),Double.parseDouble(cad.get(i-1)));
                    //		System.out.print("SUMA cad.get(i):"+cad.get(i)+" cad.get(i-1):"+cad.get(i-1)+" cad.get(i-2):"+cad.get(i-2)+ " aux:"+aux);
                    cad.set(i,String.valueOf(aux));
                    //	System.out.print(" list1:"+cad);
                    for(int k=1;k<=2;k++)cad.remove((i-k));
                    //	System.out.println(" list2:"+cad);
                    if(cad.size()>1)evaluaStack(cad);
                    else return aux;
                    break;
                case "-":
                    aux=resta(Double.parseDouble(cad.get(i-2)),Double.parseDouble(cad.get(i-1)));
                    //	System.out.print("RESTA cad.get(i):"+cad.get(i)+" cad.get(i-1):"+cad.get(i-1)+" cad.get(i-2):"+cad.get(i-2)+ " aux:"+aux);
                    cad.set(i,String.valueOf(aux));
                    //System.out.print(" list1:"+cad);
                    for(int k=1;k<=2;k++)cad.remove((i-k));
                    //	 System.out.println(" list2:"+cad);
                    if(cad.size()>1)evaluaStack(cad);
                    else return aux;
                    break;
                case "*":
                    aux=multiplica(Double.parseDouble(cad.get(i-2)),Double.parseDouble(cad.get(i-1)));
                    //	System.out.print("MULT cad.get(i):"+cad.get(i)+" cad.get(i-1):"+cad.get(i-1)+" cad.get(i-2):"+cad.get(i-2)+ " aux:"+aux);
                    cad.set(i,String.valueOf(aux));
                    //System.out.print(" list1:"+cad);
                    for(int k=1;k<=2;k++)cad.remove((i-k));
                    //	 System.out.println(" list2:"+cad);
                    if(cad.size()>1)evaluaStack(cad);
                    else return aux;
                    break;
                case "/":
                    aux=division(Double.parseDouble(cad.get(i-2)),Double.parseDouble(cad.get(i-1)));
                    //	System.out.print("DIV cad.get(i):"+cad.get(i)+" cad.get(i-1):"+cad.get(i-1)+" cad.get(i-2):"+cad.get(i-2)+ " aux:"+aux);
                    cad.set(i,String.valueOf(aux));
                    //	System.out.print(" list1:"+cad);
                    for(int k=1;k<=2;k++)cad.remove((i-k));
                    //	 System.out.println(" list2:"+cad);
                    if(cad.size()>1)evaluaStack(cad);
                    else return aux;
                    break;
                case "^":
                    aux=potencia(Double.parseDouble(cad.get(i-2)),Double.parseDouble(cad.get(i-1)));
                    //	System.out.print("POT cad.get(i):"+cad.get(i)+" cad.get(i-1):"+cad.get(i-1)+" cad.get(i-2):"+cad.get(i-2)+ " aux:"+aux);
                    cad.set(i,String.valueOf(aux));
                    //	System.out.print(" list1:"+cad);
                    for(int k=1;k<=2;k++)cad.remove((i-k));
                    //	 System.out.println(" list2:"+cad);
                    if(cad.size()>1)evaluaStack(cad);
                    else return aux;
                    break;
                case "%":
                    aux=porcent(Double.parseDouble(cad.get(i-1)));
                    System.out.print("POT cad.get(i):"+cad.get(i)+" cad.get(i-1):"+cad.get(i-1)+" cad.get(i-2):"+cad.get(i-2)+ " aux:"+aux);
                    cad.set(i,String.valueOf(aux));
                    System.out.print(" list1:"+cad);
                    cad.remove((i-1));
                    System.out.println(" list2:"+cad);
                    if(cad.size()>1)evaluaStack(cad);
                    else return aux;
                    break;
                default:
                    //		System.out.println("NO ENTRA cad.get(i):"+cad.get(i));
                    break;
            }


        }
        //	System.out.println("Result List:"+cad);
        return Double.parseDouble(cad.get(0));
    }

    /*Sustituye valores de la variable x */
    protected ArrayList <String> sustituir(ArrayList <String> e, String val){
        ArrayList<String> exp=new ArrayList <String>();
        for(int i=0;i<e.size(); i++){
            if (e.get(i).equals("x")){
                exp.add(val);
            }
            else{
                exp.add(e.get(i));
            }
        }
        return exp;
    }

    /*Operaciones validad*/
    protected double suma(double a, double b){
        return (a+(b));
    }
    protected double resta(double a, double b){
        return (a-(b));
    }
    protected double multiplica(double a, double b){
        return (a*(b));
    }
    protected double division(double a, double b){
        return (a/(b));
    }
    protected double potencia(double a, double b){
        return (Math.pow(a,b));
    }
    protected double porcent(double a){
        return (a/100);
    }
}
