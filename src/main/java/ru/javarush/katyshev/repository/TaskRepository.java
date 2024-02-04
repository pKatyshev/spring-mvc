package ru.javarush.katyshev.repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.hibernate.query.criteria.JpaCriteriaQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javarush.katyshev.model.Task;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public class TaskRepository {
    private final SessionFactory sessionFactory;

    @Autowired
    public TaskRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Task findById(int id) {
        return getSession().get(Task.class, id);
    }

    public List<Task> findAll() {
        return getSession().createQuery("from Task", Task.class).getResultList();
    }

    public List<Task> findAll(int limit, int offset) {
        CriteriaQuery<Task> selectQuery = getSession().getCriteriaBuilder().createQuery(Task.class);
        selectQuery.from(Task.class);

        Query<Task> query = getSession().createQuery(selectQuery);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return query.list();
    }

    @Transactional
    public void save(Task task) {
        getSession().persist(task);
    }

    @Transactional
    public void delete(int id) {
        getSession().remove(findById(id));
    }

    @Transactional
    public void update(Task task) {
        getSession().merge(task);
    }

    private Session getSession()  {
        return sessionFactory.getCurrentSession();
    }

    public int getCount() {
        return findAll().size();
    }
}
