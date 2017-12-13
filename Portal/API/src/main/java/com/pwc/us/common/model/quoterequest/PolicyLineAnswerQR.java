package com.pwc.us.common.model.quoterequest;

/**
 * This is just a marker class.
 * @author Roger Turnau - <roger.turnau@us.pwc.com>
 */
public class PolicyLineAnswerQR extends AnswerQR {
    public PolicyLineAnswerQR(String code, boolean ans) {
        this.questionCode = code;
        this.booleanAnswer = ans;
    }
    
    public PolicyLineAnswerQR(String code, int number) {
        this.questionCode = code;
        this.integerAnswer = number;
    }
    
    public PolicyLineAnswerQR(String code, String text) {
        this.questionCode = code;
        this.textAnswer = text;
    }

}
