package pl.wawrzyniak.NoteApp.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import pl.wawrzyniak.NoteApp.Criteria.NoteCriteria;
import pl.wawrzyniak.NoteApp.Repository.Entities.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteQueryRepositoryImpl implements NoteQueryRepository {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<Note> findByCriteria(NoteCriteria criteria, int offset, int page, int size){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Note> criteriaQuery = criteriaBuilder.createQuery(Note.class);
        Root<Note> root = criteriaQuery.from(Note.class);
        List<Predicate> predicates = getPredicatesListFromCriteria(criteria, criteriaBuilder, root);
        if(predicates.isEmpty()) {
            criteriaQuery.select(root)
                    .orderBy(criteriaBuilder.desc(root.get("updateTime")));
        } else {
            criteriaQuery.select(root)
                    .where(predicates.toArray(new Predicate[]{}))
                    .orderBy(criteriaBuilder.desc(root.get("updateTime")));
        }
        TypedQuery<Note> query = entityManager.createQuery(criteriaQuery)
                .setFirstResult(size * page + offset)
                .setMaxResults(size);
        return query.getResultList();
    }

    private static List<Predicate> getPredicatesListFromCriteria(NoteCriteria criteria, CriteriaBuilder criteriaBuilder, Root<Note> root) {
        List<Predicate> predicates = new ArrayList<>();
        if(criteria.getText() != null) {
            Predicate containsText = criteriaBuilder.like(root.get("text"), "%" + criteria.getText() + "%");
            predicates.add(containsText);
        }
        if(criteria.getMaxUpdateTime() != null) {
            Predicate earlierThan = criteriaBuilder.lessThanOrEqualTo(root.get("updateTime"), criteria.getMaxUpdateTime());
            predicates.add(earlierThan);
        }
        if(criteria.getMinUpdateTime() != null) {
            Predicate laterThan = criteriaBuilder.greaterThanOrEqualTo(root.get("updateTime"), criteria.getMinUpdateTime());
            predicates.add(laterThan);
        }
        return predicates;
    }
}
