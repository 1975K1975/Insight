package com.ks.clarity.insight;

public class je {
    public String jeTranslate (String lines){
        lines = lines.replace("変数","var");
        lines = lines.replace(":文字",":text");
        lines = lines.replace(":数字", ":number");
        lines = lines.replace(":はいいいえ",":yesno");
        lines = lines.replace("もし","if");
        lines = lines.replace("表示","display");
        lines = lines.replace("　"," ");
        return lines;
    }
    public String spaceTranslate (String lines){
        lines = lines.replace("　"," ");
        return lines;
    }
}
