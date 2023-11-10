package com.mygdx.utils;

public class UtilsFunc {
    public static boolean isClickIn(int topLeftX, int topLeftY, int bottRightX, int bottRightY, int mouseX, int mouseY){
        return (mouseX >= topLeftX && mouseX <= bottRightX && mouseY >= topLeftY && mouseY <= bottRightY);
    }
    public static boolean isClickIn(int topLeft, int sideSize, int mouseX, int mouseY){
        return (mouseX >= topLeft && mouseX <= topLeft + sideSize && mouseY >= topLeft && mouseY <= topLeft + sideSize);
    }
}
