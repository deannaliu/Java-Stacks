import java.util.DoubleSummaryStatistics;
import java.util.Scanner;
/**
 * @author Deanna Liu
 */
public class PostfixEvaluator<T> extends InfixToPostfixConverter<T>{
    public static void main(String[] args){
        InfixToPostfixConverter<Character> if2pf = new InfixToPostfixConverter<>();
        PostfixEvaluator<Character> pe = new PostfixEvaluator<>();
       /* String ex = "(3.534+28.23)*102.32+10";
        char[] line = new char[ex.length()];
       // String fix = if2pf.addSpaces(ex);
        //System.out.println("fix" + fix);

       // line = new char[size];
        for(int i = 0; i < line.length; i++) {
            //if(line[i+1] == '.')
            line[i] = ex.charAt(i);
            //System.out.print(line[i]);
        }
        System.out.println(if2pf.convert(ex.toCharArray()));

*/
        Scanner myScanner = new Scanner(System.in);
        System.out.println("Enter a valid Infix:");
        System.out.println("Please use * for multiplication.");
        Stack<String> inputs = new Stack<>();

        String infix = myScanner.nextLine();
        //System.out.println(if2pf.isValid(infix.toCharArray()));
        inputs.push(infix);
        //String[] temp2 = if2pf.newInfix(infix.toCharArray());
        //for(int i = 0; i < temp2.length; i++)
         //   System.out.println(temp2[i]);
        while(!(infix.equalsIgnoreCase("Q"))) {
            inputs.push(infix);
            if(if2pf.isValid(infix.toCharArray())) {
                System.out.println("Postfix: " + if2pf.convert(infix.toCharArray()));
                System.out.println("Evaluation: " + pe.evaluate(if2pf.convert(infix.toCharArray()).toCharArray()));
                infix = myScanner.nextLine();
            }
            else {
                System.out.println("Please enter a valid expression.");
                System.out.println("Remember to use * for multiplication.");
                infix = myScanner.nextLine();
            }
            //System.out.println(infix.equalsIgnoreCase("Q"));
        }
        //System.out.println(inputs.size());
        String[] temp = new String[inputs.size() - 1];
        for(int i = 0; i < inputs.size(); i++) {
            temp[i] = inputs.pop();
            //System.out.println(temp[i]);
        }
        for (int i = 0; i < temp.length/2; i++) //reversed
        {
            String temp1 = temp[i];
            temp[i] = temp[temp.length-1-i];
            temp[temp.length-1-i] = temp1;
        }
    }

    public int evaluate(char[] postfix) {
        String str = "";
        for(int i = 0; i < postfix.length; i++)
            str += postfix[i];
        if(str.equals("Invalid Expression"))
            return -999999;
        Stack<Double> NumStack = new Stack<>();
        String[] splitExp = str.split(" ");
        for(int i = 0; i < splitExp.length; i++)
        {
            String temp = splitExp[i];
            //System.out.println(temp);
            if(!isOperator(temp) && !temp.equals("."))
            {
                NumStack.push(Double.parseDouble(temp));
            }
            else
            {
                double op2 = NumStack.pop();
                double op1 = NumStack.pop();
                if(temp.equals("*"))
                {
                    NumStack.push(op1 * op2);
                }
                if(temp.equals("/"))
                {
                    NumStack.push(op1 / op2);
                }
                if(temp.equals("+"))
                {
                    NumStack.push(op1 + op2);
                }
                if(temp.equals("-"))
                {
                    NumStack.push(op1 - op2);
                }
            }
        }
        Double result = NumStack.pop();

        return result.intValue();
    }

}
