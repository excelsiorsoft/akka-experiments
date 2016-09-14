package com.excelsiorsoft.akka.parallel.mapreduce.java;

import java.util.Arrays;

public class Utils {

	public static void printMsg(Object msg) {
		if (msg instanceof Object[]) {
			System.out.println("result: "+Arrays.toString((Object[]) msg));
		} else {
			System.out.println("result: "+msg);
		}

	}

	public static Object[] msg(Object... elements) {
		return elements;
	}

}
