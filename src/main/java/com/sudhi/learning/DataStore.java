package com.sudhi.learning;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class DataStore {
	static List<Map<String, Object>> numberList = new ArrayList<Map<String, Object>>();
	static List<Map<String, Object>> SortID = new ArrayList<Map<String, Object>>();
	
	public static void fillStaticData(){
		List<Map<String, Object>> sortId = new ArrayList<Map<String, Object>>();
		Map<String, Object> sort = new HashMap<String, Object>();
		sort.put("SortId", "1");
		sortId.add(sort);
		setSortID(sortId);
		List<Map<String, Object>> numberList = new ArrayList<Map<String, Object>>();
		for(int i=0;i<10;i++){
			Map<String,Object> number = new HashMap<String, Object>();
			number.put("SortId", "1");
			number.put("NumberId", ThreadLocalRandom.current().nextInt(0, 100));
			numberList.add(number);
		}
		setNumberList(numberList);
	}
	
	public static List<Map<String, Object>> getNumberList() {
		return numberList;
	}
	public static void setNumberList(List<Map<String, Object>> numberList) {
		DataStore.numberList = numberList;
	}
	public static List<Map<String, Object>> getSortID() {
		return SortID;
	}
	public static void setSortID(List<Map<String, Object>> sortID) {
		SortID = sortID;
	}
	public static List<Map<String, Object>> getNumbersForSort(String sortID){
		List<Map<String, Object>> numberListForSortId = new ArrayList<Map<String, Object>>();
		for(Map<String, Object> number : numberList){
			if(number.get("SortId").equals(sortID)){
				numberListForSortId.add(number);
			}
		}
		return numberListForSortId;
	}
	public static List<Map<String, Object>> getSortIDForNumber(int number){
		List<Map<String, Object>> sortIdforNumber = new ArrayList<>();
		Map<String, Object> sort = new HashMap<>();
		for(Map<String, Object> number1 : numberList){
			if(number1.get("NumberId").equals(number)){
				sort.put("SortId", number1.get("SortId"));
				sortIdforNumber.add(sort);
			}
		}
		return sortIdforNumber;
	}
}

