package com.solab.alarms.util;

/** A class containing some utility functions.
 * 
 * @author Enrique Zamudio
 */
public class Utils {

	/** Replaces all occurrences of var with value, inside the specified string. */
	public static String replaceAll(String var, String value, String string) {
		if (string.indexOf(var) >= 0) {
			StringBuilder buf = new StringBuilder(string);
			int pos = buf.indexOf(var);
			while (pos >= 0) {
				buf.replace(pos, pos + value.length(), value);
				pos = buf.indexOf(var, pos + value.length());
			}
			return buf.toString();
		} else {
			return string;
		}
	}

}
