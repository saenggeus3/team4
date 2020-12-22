package test;
import java.util.Map;
import java.util.Map.Entry;

import team.Payment;

public class Codetest {

	
	public static void main(String[] args) {
		
		Payment pay = new Payment();
		
		int[] arr = {1,3,3,5,11};
		
		Map<String, Integer[]> tmp = pay.product_list(arr);
		
		for (Entry<String, Integer[]> entry : tmp.entrySet()) {
			System.out.println(entry.getKey() + " / " + entry.getValue()[0] + " / " + entry.getValue()[1]);
		}
		
		//System.out.println(pay.pay(tmp));
		
		System.out.println(pay.MemberPay(tmp, "c123456789"));
		
	
	}
	
	
	
	

}
