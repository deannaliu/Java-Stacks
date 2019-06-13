/**
 * @author Deanna Liu
 */
public class InfixToPostfixConverter<T> extends Stack<T>{
    public String convert(char[] infix) {
        String result = "";
        String[] exp = newInfix(infix);

        if(!isFullyParenthesized(infix) && !hasParenthesis(infix))
            return "Invalid Expression";
        if(hasParenthesis(infix)){
            Stack<String> opStacks = new Stack<>();
            opStacks.push("$");
            String expression = "";
            for(int i = 0; i < exp.length; i++)
                expression += exp[i];
            String top = opStacks.peek();
            for(int i = 0; i < exp.length; i++) {
                String token = exp[i];
                //  System.out.println(token + " operators " + opStacks.toString());
                if (!isParenthesis(token) && !isOperator(token) && !(token.equals("."))) {
                    result += token + " ";
                    // System.out.println(infix[i-1]);
                }
                if(token.equals(".")){
                    result += token;
                }
                if(isLeftParenthesis(token))
                    opStacks.push(token);
                if(isRightParenthesis(token)) {
                    top = opStacks.pop();
                    while(!isLeftParenthesis(top)) {
                        result += top + " ";
                        top = opStacks.pop();
                    }
                }
                if(isOperator(token)) {
                    top = opStacks.peek();
                    //  System.out.println(top);
                    while(precedence(top) >= precedence(token)) {
                        result += opStacks.pop() + " ";
                        top = opStacks.peek();
                    }
                    opStacks.push(token);
                }
            }
            top = opStacks.pop();
            while(!(top.equals("$"))) {
                result += top + " ";
                top = opStacks.pop();
            }
        }else {
            if(!hasParenthesis(infix)) {
                Stack<String> opStacks = new Stack<>();
                opStacks.push("$");
                String optop = opStacks.peek();
                for (int i = 0; i < exp.length; i++) {
                    String token = exp[i];
                    if (!isOperator(token) && !(token.equals("."))) {
                        result += token;
                        result += " ";
                    }
                    if (isOperator(token)) {
                        //optop = opStacks.peek();
                        while (precedence(optop) >= precedence(token)) {
                            result += opStacks.pop() + " ";
                            optop = opStacks.peek();
                           // System.out.println(optop);
                        }
                        opStacks.push(token);
                    }

                }
                optop = opStacks.pop();
                while(!(optop.equals("$"))) {
                    result += optop + " ";
                    optop = opStacks.pop();
                }
            }
            else if (isFullyParenthesized(infix)) {
                Stack<String> opStacks = new Stack<>();
                for (int i = 0; i < exp.length; i++) {
                    String token = exp[i];
                    //System.out.println(token + " operators " + opStacks.toString() + "!");
                    if (isOperator(token)) {
                        opStacks.push(token);
                        // System.out.println(isOperator(token));
                    }

                    if (!isParenthesis(token) && !isOperator(token)) {
                        result += token;
                    }
                    if (isRightParenthesis(token))
                        result += opStacks.pop();
                    //System.out.println(opStacks);
                }
            }
        }
        return result;
    }

    private int precedence(char c) {
        if(c == '$')
            return 0;
        if(isLeftParenthesis(c))
            return 1;
        if(c == '+' || c == '-')
            return 2;
        if(c == '*' || c == '/')
            return 3;
        else
            return -1;
    }

    private int precedence(String c) {
        if(c.equals("$"))
            return 0;
        if(isLeftParenthesis(c))
            return 1;
        if(c.equals("+") || c.equals("-"))
            return 2;
        if(c.equals("*") || c.equals("/"))
            return 3;
        else
            return -1;
    }
    public boolean isOperator(char c) {
        return (c == '+' || c == '-' || c == '*' || c == '/');
    }
    public boolean isOperator(String c) {
        return (c.equals("+") || c.equals("-") || c.equals("*") || c.equals("/"));
    }
    private boolean isParenthesis(char c) {
        return (c == ')' || c == ']' || c == '}' || c == '(' || c == '[' || c == '{');
    }
    private boolean isParenthesis(String c) {
        return (c.equals(")") || c.equals("("));
    }

    private boolean hasDecimal(String str) {
        char[] hold = str.toCharArray();
        for(int i = 0; i < hold.length;i++) {
            if (hold[i] == '.')
                return true;
        }
        return false;
    }

    public String addSpaces(String str)
    {
        String temp = "";
        if(!hasParenthesis(str.toCharArray()))
        {
            for(int i = 0; i < str.length(); i++) {
                temp += str.charAt(i);
                if (i + 1 < str.length() && isOperator(str.charAt(i + 1)))
                    temp += " ";
                if(isOperator(str.charAt(i)))
                    temp += " ";
            }
        }
        else {
            if (!hasDecimal(str)) {
                for (int i = 0; i < str.length() - 1; i++) {
                    if ((i + 1) < str.length()&& ((isOperator(str.charAt(i))) || (isParenthesis(str.charAt(i))) || isParenthesis(str.charAt(i + 1)) || isOperator(str.charAt(i + 1))))
                    { if(i>0 && (isParenthesis(str.charAt(i-1))))
                         temp+=str.charAt(i) + " ";
                    else
                        temp+=str.charAt(i) + " ";}
                    else {
                        temp += str.charAt(i);
                    }
                }
                temp +=str.charAt(str.length() - 1);
            } else {
                for (int i = 0; i < str.length() - 1; i++) {
                    if (str.charAt(i) != '.') {
                        temp += str.charAt(i);
                        if (isParenthesis(str.charAt(i)) || isOperator(str.charAt(i)) && i + 1 < str.length() && str.charAt(i + 1) != '.' || isParenthesis(str.charAt(i + 1)) || isOperator(str.charAt(i + 1))) {
                            temp += " ";
                        }
                    }
                    if (str.charAt(i) == '.' && i - 1 != -1 && (!isOperator(str.charAt(i - 1)) || !isParenthesis(str.charAt(i - 1))))
                        temp += str.charAt(i);
                }
                temp +=str.charAt(str.length() - 1);
            }
        }
        return temp;
    }

    public String[] newInfix(char[] infix) {
       String result = "";
        for(int i = 0; i < infix.length; i++)
           result+=infix[i];
        result = addSpaces(result);
       // System.out.println(result);
        String[] end = result.split(" ");
        replaceParenthesis(end);
        return end;
    }

    public int getNewSize(String str)
    {
        String[] hold = str.split("[\\s()\\+\\-*\\/]");
        int size = 0;
        String result = "";
        for(int i = 0; i < hold.length; i++){
                size++;
                result += hold[i] + " ";
                //System.out.println("hold" + hold[i]);
        }
        hold = result.split("\\s+");
        String[] hold2 = new String[hold.length - 1];
        for(int i = 1; i < hold2.length; i++)
            hold[2] = hold[i];
        return hold2.length;
    }

    private boolean isRightParenthesis(char c) {
        return (c == ')' || c == ']' || c == '}');
    }
    private boolean isRightParenthesis(String c){ return (c.equals(")") || c.equals("]") || c.equals("}"));}

    private void replaceParenthesis(String[] infix) {
        for(int i = 0; i < infix.length; i++){
            if(isLeftParenthesis(infix[i]))
                infix[i] = "(";
            if(isRightParenthesis(infix[i]))
                infix[i] = ")";
        }
    }

    public boolean hasParenthesis(char[] infix) {
        boolean left = false;
        boolean right = false;
        for(int i = 0; i < infix.length; i++) {
            if (isLeftParenthesis(infix[i]))
                left = true;
            if(isRightParenthesis(infix[i]))
                right = true;
        }
        return (left && right);
    }

    private boolean isLeftParenthesis(char c) {
        return (c == '(' || c == '[' || c == '{');
    }

    private boolean isLeftParenthesis(String c){ return (c.equals("(") || c.equals("[") || c.equals("{"));}

    public boolean isFullyParenthesized(char[] infix) //change to private
    {
        boolean isIt = false;
        if(infix.length == 0)
           return false;
        Stack<Character> parenth = new Stack<>();
        for(int i = 0; i < infix.length; i++) {
            if((infix[i] == ')' || infix[i] == ']' || infix[i] == '}') && parenth.size() == 0)
                return false;
            if(infix[i] == '(' || infix[i] == '[' || infix[i] == '{')
                parenth.push(infix[i]);
            if(infix[i] == ')' && parenth.peek() == '(')
                parenth.pop();
            if(infix[i] == ']' && parenth.peek() == '[')
                parenth.pop();
            if(infix[i] == '}' && parenth.peek() == '{')
                parenth.pop();
        }
        if(parenth.size() == 0)
            isIt = true;
        return isIt;
    }

    public boolean isValid(char[] infix) {
        int countLeft = 0;
        int countRight = 0;
        int invalid = 0;
        String[] temp = newInfix(infix);
        for(int i = 0; i < infix.length; i++){
            if(isLeftParenthesis(infix[i]))
                countLeft++;
            if(isRightParenthesis(infix[i]))
                countRight++;
            if(!Character.isDigit(infix[i]) && infix[i] != '.' && !(isOperator(infix[i])) && !(isParenthesis(infix[i])))
                invalid++;
            if(isOperator(infix[i]) && i+1 < infix.length && isOperator(infix[i+1]))
                return false;
        }
        if(isOperator(infix[infix.length-1]))
            return false;
        for(int i = 0; i < temp.length; i++) {
            if(temp[i].equals("/") && i+1<temp.length && temp[i+1].equals("0")) {
                System.out.println("Error: Divide By Zero");
                return false;
            }
        }
        return (countLeft == countRight) && (invalid == 0);
    }
}
