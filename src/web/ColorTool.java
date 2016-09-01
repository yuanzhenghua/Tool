package web;

import java.awt.Color;

public class ColorTool {
	public static String ToRgba(String color, float alpha) {
		Color c = Color.decode(color);
		if (alpha == (int)alpha) {
			return "rgba("+c.getRed()+", "+c.getGreen()+", "+c.getBlue()+", "+(int)alpha+")";
		} else {
			return "rgba("+c.getRed()+", "+c.getGreen()+", "+c.getBlue()+", "+alpha+")";
		}		
	}
	
	public static String ToRgba(String color, String alpha) {
		float alpha2;
		if (alpha.equals("") || alpha==null) {
			alpha2 = 1f;
		} else {
			alpha2 = Float.parseFloat(alpha);
		}
		return ColorTool.ToRgba(color, alpha2);
	}
	
	public static String ToRgb(String color) {
		Color c = Color.decode(color);
		return "rgba("+c.getRed()+", "+c.getGreen()+", "+c.getBlue()+")";
	}
}
