package com.ks.clarity.insight;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.regex.Pattern;

public class priming {
	public static String debug;
	public static String ls = System.getProperty("line.separetor");
	//種別_名前_内容
	public static String vars;
	public static String temporary = "";
	public static boolean enable = true;
	
	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		String lines = "";
		int times = 0;
		String line = "start";
		while (line == "") {
			line = digest(lines,times);
			if (line != "") {
				translation(line,times);
			}
		}
		
		
		
	}
	
	public static String digest(String lines,int times) {
		//textviewから読み込んだソースコードを1行ずつに整理する
		String line_times = "";
		int timesNow = 0;
		try {
			String line = "";
			BufferedReader br = new BufferedReader(new StringReader(lines));
			while ((line = br.readLine()) != null) {
				timesNow++;
				if (timesNow == times) {
					line_times = line.trim();
				}
			}			
		}catch (IOException e) {
			e.printStackTrace();
		}
		return line_times;
		
	}
	
	public static void translation(String line,int times) {
		//ソースコードを解釈する
		if (enable == true) {
			if (line.startsWith("var")) {
				var(line);
			} else if (line.startsWith("if")) {
				if_exe if_exe = new if_exe();
				String[] returned = if_exe.execute(line,vars,times);
				vars = returned[0];
				if (returned[1] == "false") {
					enable = false;
				} else if (returned[2] == "true") {
					enable = true;
				}
			} else if (line.startsWith("display")) {
				display_exe display_exe = new display_exe();
				String[] returned = display_exe.execute(line, vars, times);
				vars = returned[0];
			}
		} else if (enable == false) {
			if (line.startsWith("}")) {
				if_exe if_exe = new if_exe();
				String[] returned = if_exe.execute(line,vars,times);
				vars = returned[0];
				if (returned[1] == "false") {
					enable = false;
				} else if (returned[2] == "true") {
					enable = true;
				}
			}
		}
	}
	
	public static void var(String line) {
		//変数を新規登録する
		String[] data = line.split(" ");
		String content = "";
		if (data[2].matches(":text")) {
			int a = line.indexOf("「");
			if (a == -1) {
				content = searchvars(":text",data[1]);
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
				content = searchvars(":number",data[1]);
				//これ以前に登録された変数を参照して新規登録
				}
			
		}
		if (data[2].matches(":yesno")) {
			String lineContent = line.substring(line.indexOf("=")+2,line.length());
			if (lineContent.matches("yes") || lineContent.matches("no")) {
				content = lineContent;
				//新規登録
			}else {
				content = searchvars(":yesno",data[1]);
				//これ以前に登録された変数を参照して新規登録
			}
		}
		String preassigning = ls + data[2] + "૰" + data[1] + "૰" + content;
		vars = vars + preassigning;
	}
	
	public static String searchvars(String role,String name) {
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

}
