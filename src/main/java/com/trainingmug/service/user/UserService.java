package com.trainingmug.service.user;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.trainingmug.dto.UserDto;
import com.trainingmug.exceptions.AlreadyExistsExceptoin;
import com.trainingmug.exceptions.ResourceNotFoundException;
import com.trainingmug.model.User;
import com.trainingmug.repository.UserRepository;
import com.trainingmug.request.CreateUserRequest;
import com.trainingmug.request.UserUpdateRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{

	private final UserRepository userRepository;
	private final ModelMapper modelMapper;
	
	@Override
	public User getUserById(Long userId) {
		// TODO Auto-generated method stub
		return userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not foud !"));
	}

	@Override
	public User createUser(CreateUserRequest request) {
		// TODO Auto-generated method stub
		return Optional.of(request)
				.filter(user -> !userRepository.existsByEmail(request.getEmail()))
				.map(req -> {
					User user = new User();
					user.setEmail(request.getEmail());
					user.setPassword(request.getPassword());
					user.setFirstName(request.getFirstName());
					user.setLastName(request.getLastName());
					return userRepository.save(user);
				})
				.orElseThrow(() -> new AlreadyExistsExceptoin(request.getEmail() + " already exists !"));
	}

	@Override
	public User updateUser(UserUpdateRequest request, Long userId) {

		return userRepository.findById(userId)
				.map(existingUser -> {
					existingUser.setFirstName(request.getFirstName());
					existingUser.setLastName(request.getLastName());
					return userRepository.save(existingUser);
				})
				.orElseThrow(() -> new ResourceNotFoundException("User not found !"));
		
	}

	@Override
	public void deleteUser(Long userId) {
		// TODO Auto-generated method stub
		userRepository.findById(userId).ifPresentOrElse(userRepository :: delete, () -> {
			throw new ResourceNotFoundException("User not found !");
		});
	}
	
	@Override
	public UserDto convertUserToDto(User user) {
		return modelMapper.map(user, UserDto.class);
	}

	
	
	
	
	
	
	
	
	
}
