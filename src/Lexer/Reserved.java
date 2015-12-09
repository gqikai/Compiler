package Lexer;

/**
 * Created by 高其凯 on 2015/12/9.
 */
public class Reserved {
    public static String[] Functions = {"sin","cos","tan","sqrt","exp","ln"};

    static boolean isFunction(String str){
        for(String s:Functions){
            if(s.equals(str)) return true;
        }
        return false;
    }

}
