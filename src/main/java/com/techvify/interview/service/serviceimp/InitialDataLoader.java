package com.techvify.interview.service.serviceimp;

import com.techvify.interview.entity.Role;
import com.techvify.interview.entity.RoleName;
import com.techvify.interview.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class InitialDataLoader {
	
	@Autowired
	private RoleRepository roleRepository;

	@Bean
	public ApplicationRunner initializer() {
		List<RoleName> roles = Arrays.asList(RoleName.ROLE_ADMIN, RoleName.ROLE_HR, RoleName.ROLE_INTERVIEWER);
	    return args -> roles.forEach(i -> createRoleIfNotFound(i));
	}
	
	private Optional<Role> createRoleIfNotFound(RoleName roleName) {
        Optional<Role> role = roleRepository.findByName(roleName);
        if (!role.isPresent()) {
        	Role newRole = new Role();
        	newRole.setName(roleName);
			roleRepository.save(newRole);
        }
        return role;
    }
}
