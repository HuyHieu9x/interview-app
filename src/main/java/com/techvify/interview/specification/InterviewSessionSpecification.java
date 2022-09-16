package com.techvify.interview.specification;

import com.techvify.interview.entity.InterviewSession;
import com.techvify.interview.payload.request.IntervieweeFilterRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class InterviewSessionSpecification {
    @SuppressWarnings("deprecation")
    public static Specification<InterviewSession> buildWhere(String search, IntervieweeFilterRequest filterLevelName) {

        Specification<InterviewSession> where = null;

        if (!StringUtils.isEmpty(search)) {

            search = search.trim();

            CustomSpecificationInterviewSession name = new CustomSpecificationInterviewSession("name", search);
            CustomSpecificationInterviewSession email = new CustomSpecificationInterviewSession("email", search);

            where = Specification.where(name).or(email);
        }

        // if there is filter by levelName
        if (filterLevelName != null && filterLevelName.getLevelName() != null) {
            CustomSpecificationInterviewSession levelName = new CustomSpecificationInterviewSession("levelName", filterLevelName.getLevelName());
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
class CustomSpecificationInterviewSession implements Specification<InterviewSession> {

    @NonNull
    private String field;
    @NonNull
    private Object value;

    public CustomSpecificationInterviewSession(String string, String search) {
        this.field = string;
        this.value = search;
    }

    @Override
    public Predicate toPredicate(
            Root<InterviewSession> root,
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
