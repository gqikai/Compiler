package Lexer;

/**
 * Created by 高其凯 on 2015/12/4.
 */
public enum State{
    Begin,
    InPreComment,
    InComment,
    InWord,
    inConst
}
