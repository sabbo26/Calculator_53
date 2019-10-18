package eg.edu.alexu.csd.oop.calculator;
import java.io.File;
import java.util.*;

public class Calccculator implements Calculator {
    private double x1 , x2 ;
    private String operator ;
    private String expression ;
    private LinkedList<String> operations = new LinkedList<>();
    private int pointer = -1 ;
    public void input(String s) {
        this.expression = s ;
        addOperation(s);
    }
    public String getResult() {
        boolean x = checkExpression(operations.get(pointer));
        if(x){
            return ""+calculate();
        }
        else{
            return "invalid expression !!";
        }
    }
    public String current(){
        if(pointer == -1)
            return null;
        return operations.get(pointer);
    }

    public String prev() {
        if(pointer == -1)
            return null;
        if(pointer==0){
            return null ;
        }
        else{
            pointer-- ;
            return operations.get(pointer);
        }
    }
    public String next(){
        if(pointer == -1)
            return null;
        else if(pointer==operations.size()-1){
            return null ;
        }
        else{
            pointer++;
            return operations.get(pointer);
        }
    }

    public void addOperation(String x){
        if(operations.size()==5){
            operations.add(x);
            operations.remove(0);
            pointer = 4 ;
        }
        else{
            operations.add(x);
            pointer = operations.size()-1 ;
        }
    }

    public void save(){
        Formatter x = new Formatter() ;
        try{
            x = new Formatter("save&load.txt");
        }
        catch (Exception e){
            System.out.println("no file found");
        }
        if(operations.size()==0){
            x.format("%s","");
            x.close();
            savePointer();
            return;
        }
        for(int i = 0 ; i < operations.size() ; i++){
            x.format("%s\n",operations.get(i));
        }
        savePointer();
        x.close();
    }
    public void load(){
        Scanner mido = new Scanner(System.in);
        try{
            mido = new Scanner(new File("save&load.txt"));
        }
        catch (Exception e){
            System.out.println("no file found");
        }
        if(!mido.hasNext()){
            operations = new LinkedList<>();
            pointer = -1 ;
            return;
        }
        operations = new LinkedList<String>();
        do {
            operations.add(mido.next());
        }while(mido.hasNext());
        mido.close();
        loadPointer();
    }
    public void savePointer (){
        Formatter x = new Formatter() ;
        try{
            x = new Formatter("save&load2.txt");
        }
        catch (Exception e){
            System.out.println("no file found");
        }
        if(operations.size()==0){
            x.format("%s",-1);
            return;
        }
        x.format("%s",pointer);
        x.close();
    }
    public void loadPointer (){
        Scanner mido = new Scanner(System.in);
        try{
            mido = new Scanner(new File("save&load2.txt"));
        }
        catch (Exception e){
            System.out.println("no file found");
        }
        pointer = Integer.parseInt(mido.next());
    }

    public double calculate (){
        if(operator.equals("+")){
            return x1+x2 ;
        }
        else if(operator.equals("-")){
            return x1-x2 ;
        }
        else if(operator.equals("/")){
            return x1/x2 ;
        }
        else if(operator.equals("*")){
            return x1*x2 ;
        }
        return 0 ;
    }

    public boolean checkExpression (String expression){
        ArrayList<Integer> indexes = new ArrayList<Integer>();
        for(int i = 0 ; i < expression.length() ; i++){
            if(expression.charAt(i)=='-' || expression.charAt(i)=='+' || expression.charAt(i)=='/' || expression.charAt(i)=='*'){
                indexes.add(i);
            }
        }
        if(indexes.size() == 0 ||indexes.size() == 1 || indexes.size() == 2 || indexes.size() == 3){
            if(indexes.size() == 0 ){
                if(!checkPoint(expression))
                    return false;
                x1 = Double.parseDouble(expression);
                x2 = 0 ;
                operator = "+" ;
                return true;
            }
            else if ( indexes.size() == 1 ){
                if(expression.charAt(0)=='+' || expression.charAt(0)=='-'){
                    if(!checkPoint(expression))
                        return false;
                    x1 = Double.parseDouble(expression);
                    x2 = 0 ;
                    operator = "+";
                    return true;
                }
                else if (indexes.get(0)!= 0 && indexes.get(0) != expression.length()-1){
                    String one = expression.substring(0,indexes.get(0));
                    String two = expression.substring(indexes.get(0)+1);
                    if(!checkPoint(one)|| !checkPoint(two))
                        return false;
                    x1 = Double.parseDouble(one);
                    x2 = Double.parseDouble(two);
                    operator =""+ expression.charAt(indexes.get(0));
                    if(operator.equals("/") && x2 == 0){
                        return false;
                    }
                    return true ;
                }
                else{
                    return false ;
                }

            }
            else if ( indexes.size() == 2){
                if((expression.charAt(0)=='-' || expression.charAt(0)=='+') && indexes.get(1)!= 1 && indexes.get(1)!= expression.length()-1){
                    String one = expression.substring(0,indexes.get(1));
                    String two = expression.substring(indexes.get(1)+1);
                    if(!checkPoint(one)|| !checkPoint(two))
                        return false;
                    x1 = Double.parseDouble(one);
                    x2 = Double.parseDouble(two);
                    operator =""+ expression.charAt(indexes.get(1));
                    if(operator.equals("/") && x2 == 0){
                        return false;
                    }
                    return true ;
                }
                else if (indexes.get(1) == indexes.get(0)+1 && (expression.charAt(indexes.get(1))=='-'|| expression.charAt(indexes.get(1))=='+')
                        && indexes.get(1)!= 1 && indexes.get(1) != expression.length()-1){
                    String one = expression.substring(0,indexes.get(0));
                    String two = expression.substring(indexes.get(0)+1);
                    if(!checkPoint(one)|| !checkPoint(two))
                        return false;
                    x1 = Double.parseDouble(one);
                    x2 = Double.parseDouble(two);
                    operator =""+ expression.charAt(indexes.get(0));
                    if(operator.equals("/") && x2 == 0){
                        return false;
                    }
                    return true ;
                }
                else
                    return false;
            }
            else if ( indexes.size() == 3){
                if( (expression.charAt(0)=='-' || expression.charAt(0)=='+') && (expression.charAt(indexes.get(2))=='-' || expression.charAt(indexes.get(2))=='-')
                        && indexes.get(2)== indexes.get(1)+1 && indexes.get(2) != expression.length()-1  && indexes.get(2) != 2 ){
                    String one = expression.substring(0,indexes.get(1));
                    String two = expression.substring(indexes.get(1)+1);
                    if(!checkPoint(one)|| !checkPoint(two))
                        return false;
                    x1 = Double.parseDouble(one);
                    x2 = Double.parseDouble(two);
                    operator =""+ expression.charAt(indexes.get(1));
                    if(operator.equals("/") && x2 == 0){
                        return false;
                    }
                    return true ;
                }
                return false ;
            }
        }
        return false;
    }

    public boolean checkPoint (String x){
        int counter = 0 ;
        for(int i = 0 ; i < x.length() ; i++){
            if(x.charAt(i)=='.'){
                counter++;
            }
        }
        if(counter == 0 || counter == 1)
            return true ;
        return false ;
    }
}

