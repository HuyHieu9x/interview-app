package com.techvify.interview.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "Interviewee")
public class Interviewee implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Column(name = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "name",length = 50,nullable = false)
	private String name;
	
	@Column(name = "email",length = 150,nullable = false)
	private String email;

	@ManyToOne
	@JoinColumn(name = "level_interviewee_id", nullable = false)
	private LevelInterviewee levelInterviewee;

	@ManyToOne
	@JoinColumn(name = "framework_id", nullable = false)
	private Framework framework;

	@ManyToOne
	@JoinColumn(name = "programmingLanguage_id", nullable = false)
	private ProgrammingLanguage language;
}
