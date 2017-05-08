package com.sudhi.learning.controller;

import java.util.ArrayList;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SortServiceController {
	
	@RequestMapping(value="/selectionsort")
	public ArrayList<Integer> doSelectionSort(ArrayList<Integer> numberList){
		int low=0;
		int lowIndex=0;
		int pivot=0;
		for(int j=0; j<numberList.size(); j++){
			for(int i=pivot; i<numberList.size(); i++){
				low=numberList.get(i);
				if(numberList.get(i)<low){
					low = numberList.get(i);
					lowIndex = numberList.indexOf(low);
				}
			}
			int temp =numberList.get(pivot);
			numberList.set(pivot, low);
			numberList.set(lowIndex, temp);
			pivot++;
		}
		return numberList;
	}
	

}
