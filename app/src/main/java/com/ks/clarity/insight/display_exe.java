package com.ks.clarity.insight;

import android.widget.TextView;
import android.content.Context;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.regex.Pattern;
import android.os.Bundle;

public class display_exe {
	
	public static String ls = System.getProperty("line.separetor");
	private Context context;
	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ

	}

	public String[] execute(String line,String vars, int times,Context conText) {
		context = conText;

		int termStart = line.indexOf("(") + 1;
		int termEnd = line.indexOf(")") ;
		String term = line.substring(termStart,termEnd);
		String out = "";
		if (term.startsWith("「")){
			out = term.substring(1,term.length() -1);
		}else {

			String termVar = searchvars(":number",term, vars);
			if (termVar == "") {
				termVar = searchvars(":text",term, vars);
			}
			if (termVar == ""){
				termVar = searchvars(":yesno",term, vars);
			}
			out = termVar;

		}
		out = out + "\n";


		TextView textView = (TextView) ((com.ks.clarity.insight.MainActivity)context).findViewById(R.id.message);
		out = textView.getText().toString() + out;
		textView.setText(out);

		String[] returnString = new String[5];
		returnString[0] = vars;

		return returnString;
	}




	public static String searchvars(String role,String name,String vars) {
		//変数の読み出し
		String returnstring = "";
		int times = 0;
		String[] splitVars = vars.split("\n");

		String[] assigned;
		for (;times < splitVars.length;) {
			String var_this = splitVars[times];
			assigned = var_this.split("૰");
			if (assigned[0].matches(role)) {
				if (assigned[1].matches(name)) {
					returnstring = assigned[2];
					times = splitVars.length + 1;
				}
			}
			times++;


		}

		return returnstring;


	}


	public static String var(String line, String vars) {
		//変数を新規登録する
		String[] data = line.split(" ");
		String content = "";
		if (data[2].matches(":text")) {
			int a = line.indexOf("「");
			if (a == -1) {
				content = searchvars(":text",data[1],vars);
				//これ以前に登録された変数を参照して新規登録
			} else {
				int b = line.indexOf("」");
				content = line.substring(a+1,b);
				//新規登録
			}
		}
		if (data[2].matches(":number")) {
			String lineContent = line.substring(line.indexOf("=")+2,line.length());
			Pattern number = Pattern.compile("^([1-9]\\d*|0)(\\.\\d+)?$|^(-[1-9]\\d*|0)(\\.\\d+)?$");
			if (number.matcher(lineContent).matches()) {
				content = lineContent;
				//新規登録
			} else {
				content = searchvars(":number",data[1],vars);
				//これ以前に登録された変数を参照して新規登録
				}

		}
		if (data[2].matches(":yesno")) {
			String lineContent = line.substring(line.indexOf("=")+2,line.length());
			if (lineContent.matches("yes") || lineContent.matches("no")) {
				content = lineContent;
				//新規登録
			}else {
				content = searchvars(":yesno",data[1],vars);
				//これ以前に登録された変数を参照して新規登録
			}
		}
		String preassigning = "\n" + data[2] + "૰" + data[1] + "૰" + content;
		vars = vars + preassigning;
		return vars;
	}

}
