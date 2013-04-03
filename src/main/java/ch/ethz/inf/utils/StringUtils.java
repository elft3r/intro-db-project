package ch.ethz.inf.utils;

public class StringUtils {
	public static boolean isNullOrEmpty(String str) {
		boolean res = false;
		if(str == null || str.isEmpty()) {
			res = true;
		}
		
		return res;
	}
	
	public static boolean isNotNullOrEmpty(String str) {
		return !StringUtils.isNullOrEmpty(str);
	}
}
