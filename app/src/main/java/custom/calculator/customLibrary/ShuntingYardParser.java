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
package custom.calculator.customLibrary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

/***This is a custom Shunting-Yard parser that takes tokens of a expression, and transform it into a Reverse Polish Notation
 * according to the given precedence of operators.*/
public class ShuntingYardParser {
    protected HashMap<String,Integer> precedence = new HashMap<String,Integer>();

    protected ArrayList<String> tokensOfExp;

    public ShuntingYardParser(){
        setPrecedence();
    }

    public ShuntingYardParser(ArrayList<String> tokensOfExp){
        this.tokensOfExp = tokensOfExp;
        setPrecedence();
    }

    /***Set the default precedence of operators.***/
    public HashMap<String,Integer> setPrecedence(){
        String[] op = { "^","*","/","mod","+","-"};

        precedence.put(op[0], 0);

        for(int i=1;i<4;i++)
            precedence.put(op[i], 1);

        for(int i=4;i<6;i++)
            precedence.put(op[i], 2);

        return precedence;
    }

    /***Set custom precedence of operators. Precedence is represented by a HashMap that keeps the value of precedence for each operator.
     * The lower the value is, and the higher the precedence is.***/
    public HashMap<String,Integer> setPrecedence(HashMap<String,Integer> precedence){
        this.precedence = precedence;

        return precedence;
    }

    /***Parse the input tokens into Reverse Polish Notation according to the given precedence.***/
    public ArrayList<String> parse(ArrayList<String> tokensOfExp){
        Stack<String> op = new Stack<String>();
        ArrayList<String> result = new ArrayList<String>();

        for(String s : tokensOfExp){
            if(s.compareTo("(")==0)
                op.push(s);
            else if(s.compareTo(")")==0){
                while(op.peek().compareTo("(")!=0)
                    result.add(op.pop());
                op.pop();
            }else if(precedence.containsKey(s)){
                while(!op.empty() && op.peek().compareTo("(")!=0  && precedence.get(s) >= precedence.get(op.peek()))
                    result.add(op.pop());
                op.push(s);
            }else
                result.add(s);
        }

        while(!op.empty())
            result.add(op.pop());

        return result;
    }

    /***Parse the given tokens into Reverse Polish Notation according to the given precedence.***/
    public ArrayList<String> parse(){
        return parse(tokensOfExp);
    }

    /***Calculate the result of input Reverse Polish Notation.***/
    public double calculate(ArrayList<String> rpn) throws IncorrectExpressionException{
        Stack<Double> operands = new Stack<Double>();

        try {
            for (String s : rpn) {
                if (precedence.containsKey(s)) {
                    double x,
                            y;

                    y = operands.pop();
                    x = operands.pop();

                    if (s.compareTo("^") == 0) {
                        double i = x;

                        for (int j = 0; j < y - 1; j++)
                            x *= i;
                    } else if (s.compareTo("*") == 0)
                        x *= y;
                    else if (s.compareTo("/") == 0)
                        x /= y;
                    else if (s.compareTo("mod") == 0)
                        x %= y;
                    else if (s.compareTo("+") == 0)
                        x += y;
                    else if (s.compareTo("-") == 0)
                        x -= y;

                    operands.push(x);
                } else {
                    double temp;

                    temp = Double.parseDouble(s);

                    operands.push(temp);
                }
            }
        }catch (Exception e){
            throw new IncorrectExpressionException("Incorrect Expression!");
        }

        if(operands.size() > 1)
            throw new IncorrectExpressionException("Incorrect Expression!");


        return operands.pop();
    }

    /***Calculate the result of the Reverse Polish Notation parsed by the given tokens.***/
    public double calculate() throws IncorrectExpressionException{
        return calculate(parse());
    }

    /***Set tokens to the parser.***/
    public ArrayList<String> setTokensOfExp(ArrayList<String> tokensOfExp){
        this.tokensOfExp = tokensOfExp;

        return tokensOfExp;
    }

    /***Check if the input string is an operator.***/
    public boolean isOperator(String op){
        return precedence.containsKey(op);
    }

    private class IncorrectExpressionException extends Exception{
        private static final long serialVersionUID = 1L;

        IncorrectExpressionException(String message){
            super(message);
        }
    }
}