package com.tester.hjx.geoquiz;

public class Question {
    private int mTextResId;
    private  boolean mAnswerTrue;

    public Question(int textResId, boolean aswerTrue){
        mTextResId = textResId;
        mAnswerTrue = aswerTrue;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }
}
