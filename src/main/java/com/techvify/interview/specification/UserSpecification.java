package com.techvify.interview.specification;

import com.techvify.interview.payload.request.FilterUser;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


public class UserSpecification {
	@SuppressWarnings("deprecation")
	public static <E> Specification<E> buildWhere(String search, FilterUser filterUser) {
		
		Specification<E> where = null;
		
		if (!StringUtils.isEmpty(search)) {
			
		search = search.trim();

			CustomUserSpecification email = new CustomUserSpecification("email", search);
			CustomUserSpecification name = new CustomUserSpecification("name", search);

		where = Specification.where(email).or(name);
		}	

	 if (filterUser != null && filterUser.getEmail() != null) {
		CustomUserSpecification userEmail = new CustomUserSpecification("email", filterUser.getEmail());
		if (where == null) {
			where = userEmail;
		} else {
			where = where.and(userEmail);
		}
	} else if (filterUser != null && filterUser.getName() != null) {
		CustomUserSpecification userName = new CustomUserSpecification("name", filterUser.getName());
		if (where == null) {
			where = userName;
		} else {
			where = where.and(userName);
		}
	}
        return where;
}
}
@SuppressWarnings("serial")
@RequiredArgsConstructor
class CustomUserSpecification implements Specification {

	@NonNull
	private String field;
	@NonNull
	private Object value;

	public CustomUserSpecification(String string, String search) {
		this.field = string;
		this.value = search;
	}


	@Override
	public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder criteriaBuilder) {

		if (field.equalsIgnoreCase("email")) {
			return criteriaBuilder.like(root.get("email"), "%" + value.toString() + "%");
		}
		if (field.equalsIgnoreCase("name")) {
			return criteriaBuilder.like(root.get("name"), "%" + value.toString() + "%");
		}

		return null;
	}
}
