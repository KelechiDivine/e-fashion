package com.houseofo.web.service;

import com.houseofo.data.dtos.UserDto;
import com.houseofo.data.model.Order;
import com.houseofo.data.model.Role;
import com.houseofo.data.model.User;
import com.houseofo.data.repository.UserRepository;
import com.houseofo.exceptions.OrderException;
import com.houseofo.exceptions.UserException;
import com.houseofo.util.UserMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

//    @Autowired
//    UserMapper mapper;

    @Override
    public UserDto createUser(UserDto userDto) throws UserException {
        if (userRepository.findById(userDto.getId()).isPresent()){
            throw new UserException("user already exists");
        }
        User user = modelMapper.map(userDto, User.class);
        userRepository.save(user);
        return userDto;
    }

    @Override
    public UserDto findUserById(String id) throws UserException {
        User user = userRepository.findById(id)
                .orElseThrow(()-> new UserException("No user matches the ID passed in."));
        UserDto userDto = modelMapper.map(user, UserDto.class);
        return userDto;
    }

    @Override
    public List <UserDto> findUserByRole(Role role) {
        List<User> users = userRepository.findUsersByRole(role);
        List<UserDto> userDtos = users.stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
        return userDtos;
    }


    public void updateUser(UserDto userDto,String id) throws UserException {
        User myUser = userRepository.findById(id)
                .orElseThrow(()-> new UserException("No matching user ID found."));
        modelMapper.map(userDto, myUser);
        userRepository.save(myUser);
    }

    @Override
    public void deleteUser(String id) throws UserException {
        User myUser = userRepository.findById(id)
                .orElseThrow(()-> new UserException("No matching user ID found."));
        userRepository.delete(myUser);
    }

    @Override
    public User internalFindUserById(String id) throws UserException {
        User user = userRepository.findById(id)
                .orElseThrow(()-> new UserException("No user matches the ID passed in."));
        return modelMapper.map(user, User.class);
    }
}
