package cn.lym.json;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * json格式化工具。
 * 
 * @author liuyimin
 *
 */
public class JSONFormatter {
	/**
	 * logger
	 */
	private static final Logger logger = LogManager
			.getLogger(JSONFormatter.class);
	/**
	 * 换行符
	 */
	private static String lineSeparator;
	static {
		try {
			lineSeparator = System.getProperty("line.separator");
		} catch (Exception e) {
			logger.error(null, e);
			lineSeparator = "\r\n";
		}
	}

	/**
	 * 一个制表符
	 */
	private static final String oneTab = "\t";

	/**
	 * 
	 * @param source
	 * @return
	 */
	public static String format(String source) {
		if (source == null || "".equals(source)) {// 如果是null或者空串，直接返回空串
			return "";
		}

		if (source.startsWith("{")) {// JSONObject
			return format(new JSONObject(source), "");
		} else if (source.startsWith("[")) {// JSONArray
			return format(new JSONArray(source), "");
		}
		return "";
	}

	/**
	 * 格式化JSONArray对象
	 * 
	 * @param jsonArray
	 *            待格式化的JSONArray对象
	 * @param prefix
	 *            当前需要的缩进
	 * @return 格式化之后的字符串
	 */
	private static String format(JSONArray jsonArray, String prefix) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("[").append(lineSeparator);

		// 下一个层次的缩进，需要在当前缩进基础上，增加一个制表符
		String nextPrefix = prefix + oneTab;

		buffer.append(nextPrefix);
		Object value;
		for (int i = 0; i < jsonArray.length(); i++) {
			value = jsonArray.get(i);
			if (value instanceof String) {
				buffer.append("'").append(value).append("'");
			} else if (value instanceof Boolean || value instanceof Number) {
				buffer.append(value);
			} else if (value instanceof JSONObject) {
				buffer.append(format((JSONObject) value, prefix));
			}

			if (i != jsonArray.length() - 1) {// 不是最后一个元素，需要添加逗号分隔符
				buffer.append(", ");
			}
		}

		buffer.append(lineSeparator).append(prefix).append("]");
		return buffer.toString();
	}

	/**
	 * 格式化JSONObject对象
	 * 
	 * @param jsonObject
	 *            待格式化的JSONObject对象
	 * @param prefix
	 *            当前需要的缩进
	 * @return 格式化之后的字符串
	 */
	private static String format(JSONObject jsonObject, String prefix) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("{").append(lineSeparator);
		String[] names = JSONObject.getNames(jsonObject);

		// 下一个层次的缩进，需要在当前缩进基础上，增加一个制表符
		String nextPrefix = prefix + oneTab;

		String name;
		Object value;
		for (int i = 0; i < names.length; i++) {
			name = names[i];
			buffer.append(nextPrefix).append(name).append(": ");
			value = jsonObject.get(name);
			if (value instanceof String) {
				buffer.append("'").append(value).append("'");
			} else if (value instanceof Boolean || value instanceof Number) {
				buffer.append(value);
			} else if (value instanceof JSONArray) {
				buffer.append(format((JSONArray) value, nextPrefix));
			} else if (value instanceof JSONObject) {
				buffer.append(format((JSONObject) value, nextPrefix));
			}
			if (i != names.length - 1) {// 不是最后一个元素，需要添加逗号分隔符，并换行
				buffer.append(",");
			}
			buffer.append(lineSeparator);
		}
		buffer.append(prefix).append("}");
		return buffer.toString();
	}
}
