package com.ks.clarity.insight;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.regex.Pattern;

public class if_exe {
	public static String ls = System.getProperty("line.separetor");

	public String[] execute(String line,String vars, int times) {
		String lines = null;
		String[] returnString = null;

		priming priming = new priming();
		int brackets_layer = 0;
		int brackets_layer_max = 0;
		int times_a = times;
		while (brackets_layer == 0) {
			final String digest = priming.digest(lines,times_a);
			if (digest.contains("{")) {
				brackets_layer++;
				if (brackets_layer > brackets_layer_max) {
					brackets_layer_max = brackets_layer;
				}
			} else if (digest.contains("}")) {
				brackets_layer--;
			}
			times_a++;
		}
		int termNameStart = line.indexOf("(") + 1;
		int termNameEnd = line.indexOf("=") - 2;
		String termName = line.substring(termNameStart,termNameEnd);
		String termVar = searchvars(":number",termName, vars);
		String termVarType = ":number";
		if (termVar == "") {
			termVar = searchvars(":text",termName, vars);
			termVarType = ":text";
		}
		if (termVar == ""){
			termVar = searchvars(":yesno",termName, vars);
			termVarType = ":yesno";
		}

		int termStart = line.indexOf("=") + 3;
		int termEnd = line.indexOf(")") - 1;
		String term = line.substring(termStart,termEnd);
		if (termVarType == ":text") {
			term = term.substring(1,term.length() - 1);
		}
		boolean termTrue = false;
		if (term.matches(termVar)) {
			termTrue = true;
			returnString[1] = "true";
			returnString[2] = "false";
		}else {
			returnString[1] = "false";
			returnString[2] = "true";
		}
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
