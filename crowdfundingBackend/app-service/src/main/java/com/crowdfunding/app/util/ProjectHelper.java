package com.crowdfunding.app.util;

import com.crowdfunding.common.exceptions.RequestNotProperException;

public class ProjectHelper {

	public static int[] parsePagenationInfo(String pagingInfoStr) {
        int[] res = new int[2];
		if(pagingInfoStr == null || "".equals(pagingInfoStr)) throw new RequestNotProperException("Pagination Info was not supplied");
		
		String[] pagingArgs = pagingInfoStr.split(",");
		
		if(pagingArgs.length < 2) throw new RequestNotProperException("Pagination Info was not supplied");
		
		boolean allArgsAvailable = true;
		
		
		for(int i=0;i<pagingArgs.length;i++) {
			String[] keyValue = pagingArgs[i].split(":");
			if(keyValue.length < 2) throw new RequestNotProperException("Pagination Info was not supplied");
			
			if(!"maxResults".equals(keyValue[0]) && !"firstResult".equals(keyValue[0])) throw new RequestNotProperException("Pagination Info was not supplied");
			
			try {
				Integer.parseInt(keyValue[1]);
			}catch(NumberFormatException e) {
				throw new RequestNotProperException("Pagination Info was not supplied");	
			}
			
			if("maxResults".equals(keyValue[0])) res[0] = Integer.parseInt(keyValue[1]); 
			else if ("firstResult".equals(keyValue[0])) res[1] = Integer.parseInt(keyValue[1]);
		    else allArgsAvailable = false;

			
		}
		
		if(!allArgsAvailable) throw new RequestNotProperException("Pagination Info was not supplied");
	
        return res;	
	}
}
