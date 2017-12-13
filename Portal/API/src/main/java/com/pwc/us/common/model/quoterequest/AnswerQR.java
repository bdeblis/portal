package com.pwc.us.common.model.quoterequest;

/**
 *
 * @author Roger Turnau - <roger.turnau@us.pwc.com>
 */
public class AnswerQR {

    protected Boolean booleanAnswer;
    protected Integer integerAnswer;
    protected String textAnswer;
    protected String questionCode;

    public Boolean getBooleanAnswer() {
        return booleanAnswer;
    }

    public void setBooleanAnswer(Boolean booleanAnswer) {
        this.booleanAnswer = booleanAnswer;
    }

    public Integer getIntegerAnswer() {
        return integerAnswer;
    }

    public void setIntegerAnswer(Integer integerAnswer) {
        this.integerAnswer = integerAnswer;
    }

    public String getTextAnswer() {
        return textAnswer;
    }

    public void setTextAnswer(String textAnswer) {
        this.textAnswer = textAnswer;
    }

    public String getQuestionCode() {
        return questionCode;
    }

    public void setQuestionCode(String questionCode) {
        this.questionCode = questionCode;
    }

    @Override
    public String toString() {
        return "AnswerQR{" + "booleanAnswer=" + booleanAnswer + ", integerAnswer=" + integerAnswer + ", textAnswer=" + textAnswer + ", questionCode=" + questionCode + '}';
    }
    
    
}
