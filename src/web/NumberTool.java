package web;

public class NumberTool {
	public static String Division(float dividend, float divisor) {
		float result = dividend/divisor;
		if (result == (int)result) {
			return (int)result+"";
		}else {
			return result+"";
		}
	}
	
	public static String Division(int dividend, int divisor) {
		return NumberTool.Division((float)dividend, (float)divisor);
	}
}
