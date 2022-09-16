package com.techvify.interview.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.techvify.interview.payload.request.Filter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

public class  Specifications{
    @SuppressWarnings("deprecation")

    public static <E> Specification<E> buildWhere(String search, Filter filter ) {

        Specification<E> where = null;

        if (!StringUtils.isEmpty(search)) {

            search = search.trim();

            CustomSpecificationQuestion content = new CustomSpecificationQuestion("content", search);
            CustomSpecificationQuestion language = new CustomSpecificationQuestion("language", search);
            CustomSpecificationQuestion framework = new CustomSpecificationQuestion("framework", search);

            where = Specification.where(content).or(language).or(framework);
        }

        // if there is filter by levelName
        if (filter != null && filter.getLanguage() != null) {
            CustomSpecificationQuestion languageName = new CustomSpecificationQuestion("programmingLanguageName", filter.getLanguage());
            if (where == null) {
                where = languageName;
            } else {
                where = where.and(languageName);
            }
        } else if (filter != null && filter.getFramework() != null) {
            CustomSpecificationQuestion frameworkName = new CustomSpecificationQuestion("frameworkName", filter.getFramework());
            if (where == null) {
                where = frameworkName;
            } else {
                where = where.and(frameworkName);
            }
        }
        return where;
    }

}
@SuppressWarnings("serial")
@RequiredArgsConstructor
class CustomSpecificationQuestion implements Specification {

    @NonNull
    private String field;
    @NonNull
    private Object value;

    public CustomSpecificationQuestion(String string, String search) {
        this.field = string;
        this.value = search;
    }

    @Override
    public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder criteriaBuilder) {
        if (field.contains("content")) {
            return criteriaBuilder.like(root.get("content"), "%" + value.toString() + "%");
        }
        if (field.contains("languageName")) {
            return criteriaBuilder.like(root.get("language").get("name"), "%" + value.toString() + "%");}

        if (field.contains("frameworkName")) {
            return criteriaBuilder.like(root.get("framework").get("name"), "%" + value.toString() + "%");}
        return null;
    }
}
