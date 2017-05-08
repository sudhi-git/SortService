package com.sudhi.learning;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class DataStore {
	public static List<Map<String, Object>> getSortID(){
		List<Map<String, Object>> sortId = new ArrayList<Map<String, Object>>();
		Map<String, Object> sort = new HashMap<String, Object>();
		sort.put("SortId", "1");
		sortId.add(sort);
		return sortId;
	}
	
	public static List<Map<String, Object>> generateNumbers(){
		List<Map<String, Object>> numberList = new ArrayList<Map<String, Object>>();
		for(int i=0;i<10;i++){
			Map<String,Object> number = new HashMap<String, Object>();
			number.put("NumberId", ThreadLocalRandom.current().nextInt(0, 100));
			numberList.add(number);
		}
		return numberList;
	}
}
