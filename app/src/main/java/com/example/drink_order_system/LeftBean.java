package com.example.drink_order_system;

public class LeftBean {
    //记录右边列表置顶项所在的item的位置
    private int rightPosition;
    private String title;
    //设置是否选中，改变背景色
    private boolean isSelect;

    public LeftBean(int rightPosition, String title) {
        this.rightPosition = rightPosition;
        this.title = title;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public int getRightPosition() {
        return rightPosition;
    }

    public void setRightPosition(int rightPosition) {
        this.rightPosition = rightPosition;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}