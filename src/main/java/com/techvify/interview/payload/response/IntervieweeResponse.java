package com.techvify.interview.payload.response;

import com.techvify.interview.entity.LevelInterviewee;
import com.techvify.interview.entity.ProgrammingLanguage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IntervieweeResponse {
	
	private int id;
	
	private String name;
	
	private String email;

	private LevelInterviewee level;

	private Framework framework;

	private ProgrammingLanguage language;

	static class Framework{
		private int id;
		private String name;

		public Framework() {
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

}