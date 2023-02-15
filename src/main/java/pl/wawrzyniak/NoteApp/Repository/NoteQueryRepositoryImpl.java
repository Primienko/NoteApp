package pl.wawrzyniak.NoteApp.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import pl.wawrzyniak.NoteApp.Criteria.NoteCriteria;
import pl.wawrzyniak.NoteApp.Repository.CustomExeption.EmptyPredicateExpetion;
import pl.wawrzyniak.NoteApp.Repository.Entities.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteQueryRepositoryImpl implements NoteQueryRepository {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<Note> findByCriteria(NoteCriteria criteria) throws EmptyPredicateExpetion {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Note> criteriaQuery = criteriaBuilder.createQuery(Note.class);
        Root<Note> root = criteriaQuery.from(Note.class);
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
        if(predicates.isEmpty()) {throw new EmptyPredicateExpetion("Your search parameters can't be empty");}
        criteriaQuery.select(root).where(predicates.toArray(new Predicate[]{}));
        TypedQuery<Note> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }
}
