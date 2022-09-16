package com.techvify.interview.specification;

import com.techvify.interview.entity.LevelInterviewee;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class LevelIntervieweeSpecification {
	@SuppressWarnings("deprecation")
	public static Specification<LevelInterviewee> buildWhere(String search) {
		
		Specification<LevelInterviewee> where = null;
		
		if (!StringUtils.isEmpty(search)) {
			
		search = search.trim();
		
	/*	CustomSpecification code = new CustomSpecification("code", search);*/
		CustomLevelIntervieweeSpecification name = new CustomLevelIntervieweeSpecification("name", search);

		where = Specification.where(name);
		}

		return where;
	}
}
@SuppressWarnings("serial")
@RequiredArgsConstructor
class CustomLevelIntervieweeSpecification implements Specification<LevelInterviewee> {
	@NonNull
	private String field;
	@NonNull
	private Object value;

	public CustomLevelIntervieweeSpecification(String string, String search) {
		this.field = string;
		this.value = search;
	}
	

	@Override
	public Predicate toPredicate(
			Root<LevelInterviewee> root,
			CriteriaQuery<?> query, 
			CriteriaBuilder criteriaBuilder) {

		if (field.equalsIgnoreCase("name")) {
			return criteriaBuilder.like(root.get("name"), "%" + value.toString() + "%");
		}
		return null;
	}
}
