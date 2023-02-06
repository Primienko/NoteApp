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

public class NoteQueryRepository implements NoteQueryRepositoryInterface{
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<Note> findByCriteria(NoteCriteria criteria){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Note> criteriaQuery = criteriaBuilder.createQuery(Note.class);
        Root<Note> root = criteriaQuery.from(Note.class);
        List<Predicate> predicates = new ArrayList<>();
        if(!criteria.getText().isBlank()) {
            Predicate containsText = criteriaBuilder.like(root.get("text"), "%" + criteria.getText() + "%");
            predicates.add(containsText);
        }
        if(!criteria.getMaxUpdateTime().equals(null)) {
            Predicate earlierThan = criteriaBuilder.lessThan(root.get("updateTime"), criteria.getMaxUpdateTime());
            predicates.add(earlierThan);
        }
        if(!criteria.getMinUpdateTime().equals(null)) {
            Predicate laterThan = criteriaBuilder.greaterThan(root.get("updateTime"), criteria.getMinUpdateTime());
            predicates.add(laterThan);
        }
        criteriaQuery.select(root).where(predicates.toArray(new Predicate[]{}));
        TypedQuery<Note> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }
}
