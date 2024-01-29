package com.ks.clarity.insight;

public class ej {
    public String ejTranslate (String lines){
        lines = lines.replace("変数","var");
        lines = lines.replace(":文字",":text");
        lines = lines.replace(":数字", ":number");
        lines = lines.replace(":はいいいえ",":yesno");
        lines = lines.replace("もし","if");
        lines = lines.replace("表示","display");
        lines = lines.replace("　"," ");


        lines = lines.replace("var","変数");
        lines = lines.replace(":text",":文字");
        lines = lines.replace(":number", ":数字");
        lines = lines.replace(":yesno",":はいいいえ");
        lines = lines.replace("if","もし");
        lines = lines.replace("display","表示");
        lines = lines.replace("　"," ");
        return lines;
    }
}
