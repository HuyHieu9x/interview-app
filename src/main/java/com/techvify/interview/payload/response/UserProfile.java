package com.techvify.interview.payload.response;

import com.techvify.interview.entity.RoleName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {
    private int id;
    private String email;
    private String name;
    private String username;
    private int age;
    private String address;
    private Set<Role> roles = new HashSet<>();

    static class Role {
        private RoleName name;

        public Role() {
        }

        public Role(RoleName name) {
            this.name = name;
        }

        public RoleName getName() {
            return name;
        }

        public void setName(RoleName name) {
            this.name = name;
        }
    }
}
