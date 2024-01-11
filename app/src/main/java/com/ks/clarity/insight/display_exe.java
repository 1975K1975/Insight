package com.ks.clarity.insight;

import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.regex.Pattern;
import android.os.Bundle;

public class display_exe {
	
	public static String ls = System.getProperty("line.separetor");
	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ

	}

	public String[] execute(String line,String vars, int times) {

		int termStart = line.indexOf("(") + 1;
		int termEnd = line.indexOf(")") - 1;
		String term = line.substring(termStart,termEnd);
		String out = "";
		if (term.startsWith("「")){
			out = term.substring(1,term.length());
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
		out = out + ls;

		MainActivity ma = new MainActivity();
		ma.printMessage(out);

		String[] returnString = null;
		returnString[0] = vars;

		return returnString;
	}



	public static String searchvars(String role,String name,String vars) {
		//変数の読み出し
		String returnstring = "";
		String[] assigned;
		try {
			BufferedReader br = new BufferedReader(new StringReader(vars));
			String line = null;
			while ((line= br.readLine()) != null) {
				assigned = line.split("૰");
				if (assigned[0].matches(role)) {
					if (assigned[1].matches(name)) {
						returnstring = assigned[2];
					}
				}
			}
		}catch (IOException e){
			e.printStackTrace();
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
		String preassigning = ls + data[2] + "૰" + data[1] + "૰" + content;
		vars = vars + preassigning;
		return vars;
	}

}
