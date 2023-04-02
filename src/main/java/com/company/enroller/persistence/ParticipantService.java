package com.company.enroller.persistence;

import java.util.Collection;

import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import com.company.enroller.model.Participant;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Component("participantService")
public class ParticipantService {

    DatabaseConnector connector;

    public ParticipantService() {
        connector = DatabaseConnector.getInstance();
    }

    public Collection<Participant> getAll() {
        String hql = "FROM Participant";
        Query query = connector.getSession().createQuery(hql);
        return query.list();
    }

    public Participant findByLogin(String login) {
        String hql = "from Participant where login =:login";
        Query query = connector.getSession().createQuery(hql);
        query.setParameter("login", login);
        return (Participant) query.getSingleResult();
    }

    public Participant addUser(Participant participant) {
        Transaction transaction = connector.getSession().beginTransaction();
        connector.getSession().save(participant);
        transaction.commit();
        return participant;
    }

    public void deleteUser(Participant participant) {
        Transaction transaction = connector.getSession().beginTransaction();
        connector.getSession().delete(findByLogin(participant.getLogin()));
        transaction.commit();
    }

	public void updateUser(Participant participant) {
		Transaction transaction = connector.getSession().beginTransaction();
		Participant participant1 = findByLogin(participant.getLogin());
		participant1.setPassword(participant.getPassword());
		connector.getSession().saveOrUpdate(participant1);
		transaction.commit();
	}
}
