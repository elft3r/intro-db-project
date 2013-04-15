package ch.ethz.inf.utils;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class StringUtils {
	public static boolean isNullOrEmpty(String str) {
		return (str == null || str.trim().isEmpty());
	}

	public static boolean isNotNullNorEmpty(String str) {
		return !StringUtils.isNullOrEmpty(str);
	}

	public static String toString(Map<? extends Object, ? extends Object> map, String delimiter) {
		String res = "";

		for (Entry<? extends Object, ? extends Object> entry : map.entrySet()) {
			res += entry.getKey() + "=" + entry.getValue() + delimiter;
		}

		// when we have a result, we need to remove the last delimiter
		if (StringUtils.isNotNullNorEmpty(res)) {
			res = res.substring(0, res.length() - 1);
		}

		return res;
	}

	public static String toJSArray(List<String> list) {
		String res = "[";

		boolean first = true;
		for (String cat : list) {
			if (first) {
				first = false;
			} else {
				res += ",";
			}

			res += "\"" + cat + "\"";
		}

		return res + "]";
	}
}
