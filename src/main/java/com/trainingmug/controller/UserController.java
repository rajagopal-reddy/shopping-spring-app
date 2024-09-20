package com.trainingmug.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trainingmug.dto.UserDto;
import com.trainingmug.exceptions.AlreadyExistsExceptoin;
import com.trainingmug.exceptions.ResourceNotFoundException;
import com.trainingmug.model.User;
import com.trainingmug.request.CreateUserRequest;
import com.trainingmug.request.UserUpdateRequest;
import com.trainingmug.response.ApiResponse;
import com.trainingmug.service.user.IUserService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;


@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/users")
public class UserController {

	private final IUserService userService;
	
	@GetMapping("/{userId}/user")
	public ResponseEntity<ApiResponse> getUserById(@PathVariable Long userId){
		try {
			User user = userService.getUserById(userId);
			UserDto userDto = userService.convertUserToDto(user);
			return ResponseEntity.ok(new ApiResponse("Success", userDto));
		} catch (ResourceNotFoundException e) {
			// TODO Auto-generated catch block
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@PostMapping("/add")	
	public ResponseEntity<ApiResponse> createUser(@RequestBody CreateUserRequest request){
		try {
			User user = userService.createUser(request);
			UserDto userDto = userService.convertUserToDto(user);
			return ResponseEntity.ok(new ApiResponse("Created user success !", userDto));
			
		} catch (AlreadyExistsExceptoin e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(e.getMessage(), null));
		}		
	}
	
	@PostMapping("/{userId}/update+")
	public ResponseEntity<ApiResponse> updateUser(@RequestBody UserUpdateRequest request,@PathVariable Long userId){
		try {
			User user = userService.updateUser(request, userId);
			UserDto userDto = userService.convertUserToDto(user);
			return ResponseEntity.ok(new ApiResponse("Update user success !", userDto));
		} catch (ResourceNotFoundException e) {
			// TODO Auto-generated catch block
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse(e.getMessage(), null));
		}
		
	}
	
	@DeleteMapping("/{userId}/delete")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId){
		try {
			userService.deleteUser(userId);
			return ResponseEntity.ok(new ApiResponse("Delete user success !", null));
		} catch (ResourceNotFoundException e) {
			// TODO Auto-generated catch block
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse(e.getMessage(), null));
		}
		
	}
	
}
