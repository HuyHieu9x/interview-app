package com.techvify.interview.service.serviceimp;

import com.techvify.interview.entity.Role;
import com.techvify.interview.entity.RoleName;
import com.techvify.interview.entity.User;
import com.techvify.interview.jwt.JwtUtils;
import com.techvify.interview.payload.request.LoginForm;
import com.techvify.interview.payload.request.SignUpForm;
import com.techvify.interview.payload.response.JwtResponse;
import com.techvify.interview.payload.response.MessageResponse;
import com.techvify.interview.repository.RoleRepository;
import com.techvify.interview.repository.UserRepository;
import com.techvify.interview.service.interfaceservice.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService implements IAuthService {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtUtils jwtUtils;
    @Override
    public ResponseEntity<?> authenticateUser(LoginForm loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        com.techvify.interview.service.serviceimp.UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    private static final String ACTION_1 = "Error: Role is not found.";
    @Override
    public ResponseEntity<?> registerUser(SignUpForm signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }
        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getName(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()),
                signUpRequest.getAge(),
                signUpRequest.getAddress());

        Set<String> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();
        if (strRoles == null) {
            Role userRole = roleRepository.findByName(RoleName.ROLE_INTERVIEWER)
                    .orElseThrow(() -> new RuntimeException(ACTION_1));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "ROLE_ADMIN":
                        Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException(ACTION_1));
                        roles.add(adminRole);
                        break;
                    case "ROLE_HR":
                        Role modRole = roleRepository.findByName(RoleName.ROLE_HR)
                                .orElseThrow(() -> new RuntimeException(ACTION_1));
                        roles.add(modRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(RoleName.ROLE_INTERVIEWER)
                                .orElseThrow(() -> new RuntimeException(ACTION_1));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));

    }

}
