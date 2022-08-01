package com.crowdfunding.app.util;

import org.springframework.stereotype.Component;

import com.crowdfunding.common.exceptions.RequestNotProperException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Util {

	public int[] parsePagenationInfo(String pagingInfoStr) {
        int[] res = new int[2];
		if(pagingInfoStr == null || "".equals(pagingInfoStr)) {
			log.info("Util::PARSE_PAGINATION_INFO -> Pagination Info was not supplied");
			throw new RequestNotProperException("Pagination Info was not supplied");
			
		}
		
		String[] pagingArgs = pagingInfoStr.split(",");
		
		if(pagingArgs.length < 2) {
			log.info("Util::PARSE_PAGINATION_INFO -> Pagination Info was not supplied");
			throw new RequestNotProperException("Pagination Info was not supplied");
		}
		
		
		boolean allArgsAvailable = true;
		
		
		for(int i=0;i<pagingArgs.length;i++) {
			String[] keyValue = pagingArgs[i].split(":");
			if(keyValue.length < 2) {
				log.info("Util::PARSE_PAGINATION_INFO -> Pagination Info was not supplied");
				throw new RequestNotProperException("Pagination Info was not supplied");
			}
			
			if(!"maxResults".equals(keyValue[0]) && !"firstResult".equals(keyValue[0])) {
				log.info("Util::PARSE_PAGINATION_INFO -> Pagination Info was not supplied");
		        throw new RequestNotProperException("Pagination Info was not supplied");
		    }
			
			try {
				Integer.parseInt(keyValue[1]);
			}catch(NumberFormatException e) {
				log.info("Util::PARSE_PAGINATION_INFO -> Pagination Info was not supplied");
				throw new RequestNotProperException("Pagination Info was not supplied");	
			}
			
			if("maxResults".equals(keyValue[0])) res[0] = Integer.parseInt(keyValue[1]); 
			else if ("firstResult".equals(keyValue[0])) res[1] = Integer.parseInt(keyValue[1]);
		    else allArgsAvailable = false;

			
		}
		
		if(!allArgsAvailable) {
			log.info("Util::PARSE_PAGINATION_INFO -> Pagination Info was not supplied");
			throw new RequestNotProperException("Pagination Info was not supplied");
		};
	
		log.info("Util::PARSE_PAGINATION_INFO -> Parsing Success");
        return res;	
	}
}
