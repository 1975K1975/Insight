package com.ks.clarity.insight;

import android.content.Context;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.regex.Pattern;

public class priming {
	public static String debug;
	public static String ls = System.getProperty("line.separetor");
	//種別_名前_内容
	//public static String vars;
	public static String temporary = "";
	public static boolean enable = true;
	private static Context context;
	
	public static void main(String lines, Context conText, int linecount) {
		// TODO 自動生成されたメソッド・スタブ
		String vars = "";

		context = conText;
		int times = 0;
		String line = "start";
		String debug = "";
		for (;times <= linecount-1;) {
			line = digest(lines,times);
			vars = translation(line,times,conText,vars);
			if (!(line.equals(""))) {
			}
			debug = debug + line;
			times++;

		}


		
	}
	
	public static String digest(String lines,int times) {
		//textviewから読み込んだソースコードを1行ずつに整理する
		String line_times = "";
		String[] splitLines = lines.split("\n");
		line_times = splitLines[times];


		return line_times;
		
	}
	
	public static String translation(String line,int times,Context conText,String vars) {
		//ソースコードを解釈する

		boolean tempenable = true;
		if (enable == true) {

			if (line.startsWith("var")) {
				vars = var(line,vars);




			} else if (line.startsWith("if")) {
				if_exe if_exe = new if_exe();
				String[] returned = if_exe.execute(line,vars,times);
				vars = returned[0];
				if (returned[1] == "false") {
					tempenable = false;
				} else if (returned[2] == "true") {
					tempenable = true;
				}
			} else if (line.startsWith("display")) {
				display_exe display_exe = new display_exe();
				String[] returned = display_exe.execute(line, vars, times,conText);
				vars = returned[0];
			}
		} else if (enable == false) {
			if (line.startsWith("}")) {
				//if_exe if_exe = new if_exe();
				//String[] returned = if_exe.execute(line,vars,times);
				//vars = returned[0];
				//if (returned[1] == "false") {
			//		tempenable = false;
			//	} else if (returned[2] == "true") {
					tempenable = true;
			//	}
			}
		}
		if (tempenable == false) {
			enable = false;
		} else if (tempenable == true) {
			enable = true;
		}
		return vars;

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
