/*
The First Peter-Jordan Project
 */
package infixtoprefix;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import java.util.*;

class operatorPrecedence
{
    public char ch;
    public int precedence;
}
//import javafx.scene.
class conversion
{
    
    private String converted_String;
    public conversion(String val)
    {
        this.converted_String = val;
    }
    public Boolean check_Brackets(String converted_String)
    {
        String str = converted_String;
        Stack bracketchecker = new Stack();
        for(int i=0;i<str.length();i++)
        {
            if(str.charAt(i) == '[')
            {
                bracketchecker.push('[');
                
                        
            }
            if(str.charAt(i) == ']')
            {
                int pos = -1;
                pos = bracketchecker.search('[');
                if(pos<0)
                {
                    return false;
                }
                else
                {
                    bracketchecker.pop();
                   // bracketchecker.remove(pos);
                }
            }
                        
            
            if(str.charAt(i) == '(')
            {
                bracketchecker.push('(');  
            }
            if(str.charAt(i) == ')')
            {
                int pos = -1;
                pos = bracketchecker.search('(');
                if(pos<0)
                {
                    return false;
                }
                else
                {
                    bracketchecker.pop();
                   // bracketchecker.remove(pos);
                }
            }
                        
            if(str.charAt(i) == '{')
            {
                bracketchecker.push('{');
                
                        
            }
            if(str.charAt(i) == '}')
            {
                int pos = -1;
                pos = bracketchecker.search('{');
                if(pos<0)
                {
                    return false;
                }
                else
                {
                    bracketchecker.pop();
                   // bracketchecker.remove(pos);
                }
            }
        }
        return true;
    }
    public String swap_order(String input)
    {
        String returnable = new StringBuilder(input).reverse().toString();
        return returnable;
    }
    public String toPrefix(String input)
    {
        //Assume it's being passed the reversed string
        Stack<operatorPrecedence> equation_stack = new Stack();
        String prefixString = "";
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<input.length();i++)
        {
            
            char c;
            c = input.charAt(i);
            operatorPrecedence op = new operatorPrecedence();
            
            op.ch = c;
            if(Character.isDigit(op.ch) || Character.isLetter(op.ch))
            {
                //Precedence is irrelevant
                sb.append(op.ch);
            }
            else if(op.ch == ')'|| op.ch == '}' || op.ch == ']')
            {
                op.precedence = 3;
                equation_stack.push(op);
            }
            else if(op.ch == '*' || op.ch == '/' || op.ch == '%')
            {
                
                op.precedence = 2;
                operatorPrecedence temp = new operatorPrecedence();
                if(equation_stack.empty())
                {
                    equation_stack.push(op);
                }
                else if(equation_stack.peek().ch == ')' || equation_stack.peek().ch == ']'|| equation_stack.peek().ch == '}')
                {
                     equation_stack.push(op);              
                }
                else if(equation_stack.peek().precedence <= op.precedence)
                {
                    equation_stack.push(op);
                }
                else
                {
                    int flag = 0;
                    while(flag == 0)
                    {
                        if(equation_stack.empty())
                        {
                            flag = 1;
                            continue;
                        }
                        else if(equation_stack.peek().precedence > op.precedence)
                        {
                            temp = equation_stack.pop();
                            sb.append(temp.ch);
                        }
                        else if(equation_stack.peek().precedence <= op.precedence)
                        {
                            flag = 1;
                        }
                    }
                    equation_stack.push(op);
                }
            }
            else if(op.ch == '+' || op.ch == '-')
            {
                op.precedence = 1;
                operatorPrecedence temp = new operatorPrecedence();
                if(equation_stack.empty())
                {
                    equation_stack.push(op);
                }
                else if(equation_stack.peek().ch == ')' || equation_stack.peek().ch == ']'|| equation_stack.peek().ch == '}')
                {
                     equation_stack.push(op);              
                }
                else if(equation_stack.peek().precedence <= op.precedence)
                {
                    equation_stack.push(op);
                }
                else
                {
                    int flag = 0;
                    while(flag == 0)
                    {
                        if(equation_stack.empty())
                        {
                            flag = 1;
                            continue;
                        }
                        
                        else if(equation_stack.peek().precedence > op.precedence)
                        {
                            temp = equation_stack.pop();
                            sb.append(temp.ch);
                        }
                        else if(equation_stack.peek().precedence <= op.precedence)
                        {
                            flag = 1;
                        }
                    }
                    equation_stack.push(op);
                }
            }
            else if(op.ch == '('|| op.ch == '{' || op.ch == '[')
            {
                if(op.ch == '(')
                {
                    operatorPrecedence op2 = new operatorPrecedence();
                    op2.ch = ' ';
                    while(op2.ch != ')')
                    {
                        op2 = equation_stack.pop();
                        if(op2.ch == '*' || op2.ch == '/' || op2.ch == '%' || op2.ch == '+' || op2.ch == '-')
                        {
                            sb.append(op2.ch);
                        }
                    }
                    equation_stack.pop();
                }
                
                if(op.ch == '{')
                {
                    operatorPrecedence op2 = new operatorPrecedence();
                    op2.ch = ' ';
                    while(op2.ch != '}')
                    {
                        op2 = equation_stack.pop();
                        if(op2.ch == '*' || op2.ch == '/' || op2.ch == '%' || op2.ch == '+' || op2.ch == '-')
                        {
                            sb.append(op2.ch);
                        }
                    }
                    equation_stack.pop();
                }
            
                 if(op.ch == '[')
                 {
                    operatorPrecedence op2 = new operatorPrecedence();
                    while(op2.ch != ']')
                    {
                        op2 = equation_stack.pop();
                        if(op2.ch == '*' || op2.ch == '/' || op2.ch == '%' || op2.ch == '+' || op2.ch == '-')
                        {
                            sb.append(op2.ch);
                        }
                    }
                 equation_stack.pop();
                }
            }
            else if(op.ch == ' ')
            {
              continue; 
            }
                
            //REMEMBER TO INVERT THE PREFIX STRING
            //  (5*2)-[4+1]
            
            else
            {
                op.precedence = 10;
                System.out.print("Invalid operand\n");
                //System.exit(0);
            }

            
        }
        operatorPrecedence op2 = new operatorPrecedence();
        while(!equation_stack.empty())
        {
            op2 = equation_stack.pop();
            sb.append(op2.ch);
        }
        prefixString = sb.toString();
        
        return prefixString;
      }
    
}

public class InfixtoPrefix extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception 
    {
        Button btn = new Button();
        final TextField tf = new TextField();
        final Label l = new Label();
        l.setText("Converted string will be here");
        tf.setText("");
        
        
        btn.setText("Convert to prefix");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) 
            {
                //Here's where we're putting our shit
                //System.out.println("Eat a delicious pizza");
                String convertedval = " ";
                convertedval = tf.getText();
                conversion convert = new conversion(convertedval);
                String reversed = convert.swap_order(convertedval);
                System.out.println(reversed);
                
                //l.setText(reversed);
                if(convert.check_Brackets(convertedval) == true)
                {
                    System.out.println("success");
                }
                String converted = convert.toPrefix(reversed);
                String reversedconverted = convert.swap_order(converted);
                
                l.setText(reversedconverted);
                //l.setText(convertedval);
            }
        });
        
        GridPane grid = new GridPane();
        GridPane.setConstraints(btn,10,150);
        GridPane.setConstraints(tf,10,50);
        GridPane.setConstraints(l,10,10);
        grid.setPadding(new Insets(20,20,200,20)); 
        grid.setMargin(l, new Insets(10,10,10,10));
        grid.setMargin(tf, new Insets(10,10,10,10));
        grid.setMargin(btn, new Insets(10,10,10,10));
        
        grid.getChildren().addAll(btn, tf, l);
        
        
        Scene scene = new Scene(grid, 700, 450);
        scene.getStylesheets().add("infixCSS.css");
        
        primaryStage.setTitle("Infix-to-Prefix Converter");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
