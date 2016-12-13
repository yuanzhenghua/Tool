package web;

public class UnitConversionTool {
//	public static String rem2px(float devicePixelRatio, float screen, String value){
//		int dpr = devicePixelRatio>=2? 2:1;
//		float rem = screen*dpr*750/640/10;
//		float px = Float.parseFloat(value.substring(0,value.length()-3))*rem;
//		px = (float) (Math.round(px*1000)/1000.0);
//		if (px==(int)px) {
//			return (int)px+"px";
//		} else {
//			return px+"px";
//		}		
//	}
//	
//	public static String px2rem(float devicePixelRatio, float screen, float value){
//		int dpr = devicePixelRatio>=2? 2:1;
//		float rem = screen*dpr*750/640/10;
//		float rem1 = value/rem;
//		if (rem1==(int)rem1) {
//			return (int)rem1+"rem";
//		} else {
//			return Math.round(rem1*1000)/1000.0+"rem";
//		}
//	}
	
	public static boolean compareRem(String rem1, String rem2) {
		float c1 = Float.parseFloat(rem1.substring(0, rem1.length()-3));
		float c2 = Float.parseFloat(rem2.substring(0, rem2.length()-3));
		if (Math.abs(c1-c2)<=0.5) {
			return true;
		} else {
			return false;
		}	
	}
	
	public static boolean comparePx(String px1, String px2) {
		float c1 = Float.parseFloat(px1.substring(0, px1.length()-2));
		float c2 = Float.parseFloat(px2.substring(0, px2.length()-2));
		if (Math.abs(c1-c2)<=0.5) {
			return true;
		} else {
			return false;
		}	
	}
	
	public static String rem2px2(float fontSize, float rem){
		float px = rem*fontSize;
		px = (float) (Math.round(px*1000)/1000.0);
		if (px==(int)px) {
			return (int)px+"px";
		} else {
			return px+"px";
		}		
	}
	
	public static String rem2px2(float fontSize, String rem){
		return UnitConversionTool.rem2px2(fontSize, Float.parseFloat(rem.substring(0, rem.length()-3)));
	}
	
	public static String px2rem2(float fontSize, float screen){
		float rem = screen/fontSize;
		if (rem==(int)rem) {
			return (int)rem+"rem";
		} else {
			return Math.round(rem*1000)/1000.0+"rem";
		}
	}

}
