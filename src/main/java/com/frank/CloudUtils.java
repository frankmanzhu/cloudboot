package com.frank;

import java.util.Scanner;

/**
 * Created by Frank on 2017/5/28.
 */
public class CloudUtils {
	public static String execCmd(String cmd) throws java.io.IOException {
		try (Scanner s = new Scanner(Runtime.getRuntime().exec(cmd).getInputStream())) {
			s.useDelimiter("\n");
			return s.hasNext() ? s.next() : "";
		}
	}
}
