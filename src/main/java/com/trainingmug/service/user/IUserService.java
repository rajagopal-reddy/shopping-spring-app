package com.trainingmug.service.user;

import com.trainingmug.dto.UserDto;
import com.trainingmug.model.User;
import com.trainingmug.request.CreateUserRequest;
import com.trainingmug.request.UserUpdateRequest;

public interface IUserService {

	User getUserById(Long userId);
	User createUser(CreateUserRequest request);
	User updateUser(UserUpdateRequest request, Long userId);
	void deleteUser(Long userId);
	UserDto convertUserToDto(User user);
}
