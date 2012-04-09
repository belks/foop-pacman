package common.tools;

public class Tools {
	
	
	public static boolean arrayContains(Object[] array, Object item){
		boolean found = false;
		for(Object o : array){
			if(o.equals(item)){
				found=true;
				break;
			}
		}
		return found;
	}

}
