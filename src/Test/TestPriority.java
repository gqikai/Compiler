package Test;

import java.util.Stack;

/**
 * Created by 高其凯 on 2015/12/9.
 */

public class TestPriority {

    static String input = "(1+2)*(3+4)#";
    static char[] operators = {'+','-','*','/','(',')','#'};

    static Stack<Integer> operandStack = new Stack<>();
    static Stack<Character> operatorStack = new Stack<>();

    public static void main(String[] args) {

        operatorStack.push('#');

        int index = 0;

        while(index < input.length()){
            char current = input.charAt(index);
            if(contains(operators,current)){
                switch(precede(operatorStack.peek(),current)){
                    case '>' :{
                        operandStack.push(popAndCalc());
                        break;
                    }
                    case '<' :{
                        operatorStack.push(current);
                        index ++;
                        break;
                    }
                    case '=' :{
                        operatorStack.pop();
                        index ++;
                        break;
                    }
                }
            }else{
                operandStack.push(Integer.parseInt(current + ""));
                index ++;
            }
        }
        System.out.println(operandStack);
    }

    private static int popAndCalc() {
        int operand1 = operandStack.pop();
        int operand2 = operandStack.pop();
        char operator = operatorStack.pop();

        switch(operator){
            case '+': return operand1 + operand2;
            case '-': return operand1 - operand2;
            case '*': return operand1 * operand2;
            case '/': return operand1 / operand2;
        }
        return 0;
    }

    private static char precede(char first, char last)
    {
        String operate = "+-*/()#";
        char[][] level = {
                {'>','>','<','<','<','>','>'},
                {'>','>','<','<','<','>','>'},
                {'>','>','>','>','<','>','>'},
                {'>','>','<','<','<','>','>'},
                {'<','<','<','<','<','=',' '},
                {'>','>','>','>',' ','>','='},
                {'<','<','<','<','<',' ','='}
        };
        int rows = operate.indexOf(first);
        int cols = operate.indexOf(last);
        return level[rows][cols];
    }

    public static boolean contains(char[] arr, char targetValue) {
        for (char s : arr) {
            if (s == targetValue) {
                return true;
            }
        }
        return false;
    }
}

