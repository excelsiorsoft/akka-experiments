package com.excelsiorsoft.akka.parallel.mapreduce.java;

import java.util.Arrays;

public class Utils {

	public static void printMsg(Object msg) {
		if (msg instanceof Object[]) {
			System.out.println(Arrays.toString((Object[]) msg));
		} else {
			System.out.println(msg);
		}
		
	}

}
