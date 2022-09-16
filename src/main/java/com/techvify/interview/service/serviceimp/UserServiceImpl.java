package com.techvify.interview.service.serviceimp;

import com.techvify.interview.entity.Role;
import com.techvify.interview.entity.RoleName;
import com.techvify.interview.entity.User;
import com.techvify.interview.payload.request.FilterUser;
import com.techvify.interview.payload.request.UserUpdate;
import com.techvify.interview.payload.request.UserUpdateAdmin;
import com.techvify.interview.payload.response.MessageResponse;
import com.techvify.interview.repository.RoleRepository;
import com.techvify.interview.repository.UserRepository;
import com.techvify.interview.service.interfaceservice.IUserService;
import com.techvify.interview.specification.UserSpecification;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    RoleRepository roleRepository;
    @Override
    public void deleteUser(int id) {
        userRepository.findById(id)
                .map(user -> {
                    userRepository.delete(user);
                    return "Delete Successfully!";
                });
    }

    @Override
    public ResponseEntity<?> updateUSer(int id, UserUpdate userUpdate) {
        User user = userRepository.findById(id).get();
        User userRq = modelMapper.map(userUpdate, User.class);
        user.setAge(userRq.getAge());
        user.setName(userRq.getName());
        user.setAddress(userRq.getAddress());
        user.setEmail(userRq.getEmail());
        return new ResponseEntity<>(userRepository.save(user), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?>UpdateRole(int id, UserUpdateAdmin userUpdateAdmin){
        User user = userRepository.findById(id).get();
        Set<String> strRoles = userUpdateAdmin.getRoles();
        Set<Role> roles = new HashSet<>();
            strRoles.forEach(role -> {
                switch (role) {
                    case "ROLE_ADMIN":
                        Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;
                    case "ROLE_HR":
                        Role modRole = roleRepository.findByName(RoleName.ROLE_HR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);
                        break;
                    case "ROLE_INTERVIEWER":
                        Role userRole = roleRepository.findByName(RoleName.ROLE_INTERVIEWER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        User userRq = modelMapper.map(userUpdateAdmin, User.class);
        user.setAge(userRq.getAge());
        user.setName(userRq.getName());
        user.setAddress(userRq.getAddress());
        user.setEmail(userRq.getEmail());
        user.setRoles(roles);
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User update successfully!"));
    }
    @Override
    public Page<User> getFindAll(Pageable pageable, String search, FilterUser filterUser) {
        Specification<Object> where = UserSpecification.buildWhere(search, filterUser);
        return userRepository.findAll(where, pageable);
    }
}
