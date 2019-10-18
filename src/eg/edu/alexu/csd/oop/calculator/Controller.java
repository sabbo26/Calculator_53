package eg.edu.alexu.csd.oop.calculator;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {
    @FXML
    private Label expression =new Label("");
    private Calccculator test = new Calccculator();
    private  boolean newinput = false ;
    @FXML
    public void process (ActionEvent event) throws IOException{
        if(newinput){
            expression.setText("");
            newinput = false ;
        }
        String value = ((Button)event.getSource()).getText();
        if(value.equals("=")){
            test.input(expression.getText());
            String x = test.getResult();
            if(x.equals("invalid expression !!")){
                Stage stage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("popwindow.fxml"));
                stage.setTitle("Error");
                stage.setScene(new Scene(root,350,150));
                stage.show();
            }
            else{
                expression.setText(x);
                newinput = true ;
            }
        }
        else{
            expression.setText(expression.getText()+value);
        }
    }
    public void process2 (ActionEvent event){
        String value = ((Button)event.getSource()).getText();
        if(value.equals("DEL")){
            for(int i = 0 ; i < expression.getText().length() ; i++){
                if(expression.getText().charAt(i)=='='){
                    return ;
                }
            }
            if(expression.getText().isEmpty())
                return;
            String temp = expression.getText().substring(0,expression.getText().length()-1);
            expression.setText(temp); ;
        }
        else if (value.equals("AC")){
            expression.setText("");
        }
        else if (value.equals("next")){
            newinput = false ;
            String x = test.next();
            if(x != null){
                expression.setText(x);
            }
            else{
                return;
            }
        }
        else if(value.equals("prev")){
            newinput = false ;
            String x = test.prev();
            if(x != null){
                expression.setText(x);
            }
            else{
                return;
            }
        }
        else if (value.equals("save")){
            test.save();
        }
        else if (value.equals("load")){
            test.load();
        }
        else if (value.equals("current")){
            expression.setText(test.current());
        }
    }
}