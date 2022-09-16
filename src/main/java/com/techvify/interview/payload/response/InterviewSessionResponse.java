package com.techvify.interview.payload.response;

import com.techvify.interview.entity.Interviewee;
import com.techvify.interview.entity.Question;
import com.techvify.interview.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InterviewSessionResponse {
    private int id;

    private Interviewee interviewee;

    private List<UserProfile> users;

    private String date;

    private String status;

    private List<Question> questions;

    private String conclusion;

    static class UserProfile{
        private int id;
        private String username;
        private String name;
        private List<Role> roles;
        public UserProfile() {
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public List<Role> getRoles() {
            return roles;
        }

        public void setRoles(List<Role> roles) {
            this.roles = roles;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }
}
