package com.techvify.interview.specification;

import com.techvify.interview.entity.Interviewee;
import com.techvify.interview.payload.request.IntervieweeFilterRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class IntervieweeSpecification {
	@SuppressWarnings("deprecation")
	public static Specification<Interviewee> buildWhere(String search, IntervieweeFilterRequest filterLevelName) {
		
		Specification<Interviewee> where = null;
		if (!StringUtils.isEmpty(search)) {
			
		search = search.trim();
		
		CustomSpecificationInterviewee name = new CustomSpecificationInterviewee("name", search);
		CustomSpecificationInterviewee email = new CustomSpecificationInterviewee("email", search);

		where = Specification.where(name).or(email);
		}	
		
		// if there is filter by levelName
				if (filterLevelName != null && filterLevelName.getLevelName() != null) {
					CustomSpecificationInterviewee levelName = new CustomSpecificationInterviewee("levelName", filterLevelName.getLevelName());
					if (where == null) {
						where = levelName;
					} else {
						where = where.and(levelName);
					}
				}
		return where;
	}
}
@SuppressWarnings("serial")
@RequiredArgsConstructor
class CustomSpecificationInterviewee implements Specification<Interviewee> {

	@NonNull
	private String field;
	@NonNull
	private Object value;

	public CustomSpecificationInterviewee(String string, String search) {
		this.field = string;
		this.value = search;
	}

	@Override
	public Predicate toPredicate(
			Root<Interviewee> root, 
			CriteriaQuery<?> query, 
			CriteriaBuilder criteriaBuilder) {

		if (field.equalsIgnoreCase("name")) {
			return criteriaBuilder.like(root.get("name"), "%" + value.toString() + "%");
		}
		if (field.equalsIgnoreCase("email")) {
			return criteriaBuilder.like(root.get("email"), "%" + value.toString() + "%");
		}
		if (field.equalsIgnoreCase("levelName")) {
			return criteriaBuilder.like(root.get("level").get("name"), "%" + value.toString() + "%");
		}
		return null;
	}
}
