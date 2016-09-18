package mx.ayacercode.calculadora;

/**
 * Created by Alberto on 07/09/16.
 */
public class Operaciones {

    public int reglas(char e){
        if(e>='0' && e<='9')return 0;
        else if(e=='(')return 1;
        else if(e==')')return 3;
        else if(e=='+'||e=='-'||e=='*'||e=='/')return 2;
        return -1;
    }
    public boolean expresion(String ex){
        char []e=ex.toCharArray();
        int tam=ex.length(),i,estado=-1;
        for(i=0;i<e.length;i++){

            if(estado==-1)break;
        }
        if(estado==-1)return false;
        return true;
    }
}
