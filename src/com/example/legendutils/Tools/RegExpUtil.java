package com.example.legendutils.Tools;

public class RegExpUtil {

	public static final String IPV4RegExp = "((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)";
	public static final String PhoneRegExp = "\\(0\\d{2}\\)[- ]?\\d{8}|0\\d{2}[- ]?\\d{8}|\\(0\\d{3}\\)[- ]?\\d{7}|0\\d{3}[- ]?\\d{7}";
	public static final String NetPictureRegExp = "http://([\\w-]+\\.)+\\w{2,3}(:\\d{1,5})?(/[\\w-]+)+\\.(jpg|bmp|png|gif)";

	/**
	 * 生成比较数字的正则表达式,该正则表达式匹配包含有比num小的正整数的字符串.
	 * 如kk512it匹配regLessThanInt(513),但是5123不会,kk513it也不会
	 * 
	 * @param num
	 * @return
	 */
	public static String regLessThanInt(int num) {
		String front = "(?<=\\D|\\b)(";
		String back = ")(?=\\D|\\b)";
		int cou = String.valueOf(num).length();
		String reg = "";

		if (cou > 1) {
			int k1 = (int) ((num % Math.pow(10, cou)) / Math.pow(10, cou - 1));
			String lessThanK = null;
			if (k1 > 2) {
				lessThanK = "[1-" + (k1 - 1) + "]\\d{" + (cou - 1) + "}";
			} else {
				if (k1 == 2) {
					lessThanK = "1\\d{" + (cou - 1) + "}";
				}
			}
			if (lessThanK != null) {
				reg += lessThanK + "|";
			}

			for (int i = cou - 1; i > 0; i--) {
				int head = (int) (num / Math.pow(10, i));
				int tail = (int) ((num % Math.pow(10, i)) / Math.pow(10, i - 1));
				String top = null;
				if (tail > 1) {
					top = head + "[0-" + (tail - 1) + "]"
							+ (i == 1 ? "" : "\\d{" + (i - 1) + "}");
				} else if (tail == 1) {
					top = head + "0" + (i == 1 ? "" : "\\d{" + (i - 1) + "}");
				}
				if (top != null) {
					reg += top + "|";
				}
			}
			String low = null;
			if (cou > 2) {
				low = "\\d{1," + (cou - 1) + "}";
			} else {
				low = "\\d";
			}
			reg += low;
		} else {
			reg = "[0-" + (num - 1) + "]";
		}
		reg = front + reg + back;
		return reg;
	}

	/**
	 * 生成比较数字的正则表达式,该正则表达式匹配包含有比num大的正整数的字符串.
	 * 如kk512it匹配regLessThanInt(511),但是5123不会,kk511it也不会
	 * 
	 * @param num
	 * @return
	 */
	public static String regLargerThanInt(int num) {
		String front = "(?<=\\D|\\b)(";
		String back = ")(?=\\D|\\b)";
		int cou = String.valueOf(num).length();
		String reg = "";
		String longer = "\\d{" + (cou + 1) + ",}";// 更高位 TODO 前面是0的情况
		reg += longer + "|";

		if (cou > 1) {
			int k1 = (int) ((num % Math.pow(10, cou)) / Math.pow(10, cou - 1));
			String largerThanK = null;
			if (k1 == 8) {
				largerThanK = "9" + "\\d{" + (cou - 1) + "}";
			} else if (k1 < 8) {
				largerThanK = "[" + (k1 + 1) + "-9]" + "\\d{" + (cou - 1) + "}";
			}

			if (largerThanK != null) {
				reg += largerThanK + "|";
			}
			for (int i = cou - 1; i > 0; i--) {
				int head = (int) (num / Math.pow(10, i));
				int tail = (int) ((num % Math.pow(10, i)) / Math.pow(10, i - 1));
				String top = null;
				if (tail == 8) {
					top = head + "9" + (i == 1 ? "" : "\\d{" + (i - 1) + "}");
				} else if (tail < 8) {
					top = head + "[" + (tail + 1) + "-9]"
							+ (i == 1 ? "" : "\\d{" + (i - 1) + "}");
				}
				if (top != null) {
					reg += top + "|";
				}
			}
		} else {
			String lar = "";
			if (num == 8) {
				lar = "9";
			} else if (num < 8) {
				lar = "[" + (num + 1) + "-9]";
			}
			reg += lar;
		}

		if (reg.lastIndexOf("|") == reg.length() - 1) {
			reg = reg.substring(0, reg.length() - 1);
		}
		reg = front + reg + back;
		return reg;
	}
}
