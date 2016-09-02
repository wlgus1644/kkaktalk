package net.nigne.kkt.email;

public class Mathrandom {

	public static String random() {
		String result = "1234";
		int random[] = new int[4];
		
		for(int a=0; a<4; a++){
			random[a] = (int)(Math.random()*10);
			System.out.println("random["+a+"]:"+random[a]);
		}
		int aaa = random[0]*1000 + random[1]*100 + random[2]*10 + random[3];
		
		result = String.valueOf(aaa);
		
		System.out.println("result:"+result);
		
		return result;
	}

}
