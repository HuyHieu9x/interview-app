package com.techvify.interview.service.interfaceservice;

import com.techvify.interview.entity.User;
import com.techvify.interview.payload.request.FilterUser;
import com.techvify.interview.payload.request.UserUpdate;
import com.techvify.interview.payload.request.UserUpdateAdmin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface IUserService {
    void deleteUser(int id);

    ResponseEntity<?> updateUSer(int id, UserUpdate userUpdate);
    Page<User> getFindAll(Pageable pageable, String search, FilterUser filterUser);
    ResponseEntity<?>UpdateRole(int id, UserUpdateAdmin userUpdateAdmin);
}
