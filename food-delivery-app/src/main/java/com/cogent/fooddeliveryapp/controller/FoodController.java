package com.cogent.fooddeliveryapp.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cogent.fooddeliveryapp.dto.Food;
import com.cogent.fooddeliveryapp.enums.FoodType;
import com.cogent.fooddeliveryapp.exception.NoDataFoundException;
import com.cogent.fooddeliveryapp.repository.FoodRepository;

@RestController
//Controller and response body are both included in restcontroller
@RequestMapping("/food")
@Validated //validated goes on classes and activates validation specifications on method arguments
//while valid goes on objects of payloads and marks them to be checked
public class FoodController {

	@Autowired
	//not having a service is a time-saver, but in practice one is needed
	FoodRepository foodRepository;
	
	@PostMapping(value ="")
	public ResponseEntity<?> createFood(@RequestBody @Valid Food food) {
		
		Food food2 = foodRepository.save(food);
		
		
		return ResponseEntity.status(HttpStatus.CREATED).body(food2);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> getFoodById(@PathVariable("id") @Min(1) Long id) {
		Food food = foodRepository.findById(id).orElseThrow(
				()->new NoDataFoundException("No food of id " + id + " found."));
		return ResponseEntity.ok(food);
	}

	@GetMapping(value = "/")
	public ResponseEntity<?> getAllFoods() {
		List<Food> foods = foodRepository.findAll();
		if(foods.size() > 0) 
			return ResponseEntity.ok(foods);
		else
		{
			Map<String, String> map = new HashMap<String, String>();
			map.put("message", "Food not found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
		}
	}
	
	@GetMapping(value = "/all/desc")
	public ResponseEntity<?> getAllDescOrder() {
		List<Food> foods = foodRepository.findAll();
		Collections.sort(foods, (a,b)-> b.getId().compareTo(a.getId()));
		
		
		if(foods.size() > 0) 
			return ResponseEntity.ok(foods);
		else
		{
			Map<String, String> map = new HashMap<String, String>();
			map.put("message", "Food not found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
		}
	}
	
	@GetMapping(value = "/all/asc")
	public ResponseEntity<?> getAllAscOrder() {
		List<Food> foods = foodRepository.findAll();
		Collections.sort(foods, (a,b)-> a.getId().compareTo(b.getId()));
		
		
		if(foods.size() > 0) 
			return ResponseEntity.ok(foods);
		else
		{
			Map<String, String> map = new HashMap<String, String>();
			map.put("message", "Food not found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
		}
	}
	
	@GetMapping(value = "/:{type}")
	public ResponseEntity<?> getFoodsByType(@PathVariable("type") @NotBlank String type) {
		FoodType foodType = null;
		switch (type) {
		case "mexican":
			foodType = FoodType.MEXICAN;
			break;
		case "italian":
			foodType = FoodType.ITALIAN;
			break;
		case "chinese":
			foodType = FoodType.CHINESE;
			break;
		case "korean":
			foodType = FoodType.KOREAN;
			break;
		}
		
		if(foodType != null) {
			List<Food> foods = foodRepository.findAllByFoodType(foodType);
			if(foods.size() > 0)
				return ResponseEntity.ok(foods);
			else {
				Map<String, String> map = new HashMap<String, String>();
				map.put("message", "Food not found");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
			}
		}
		else {
			Map<String, String> map = new HashMap<String, String>();
			map.put("message", "Improper category");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
		}
	}
}
