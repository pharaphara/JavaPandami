package fr.pandami.dao;

import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import fr.pandami.entity.Service;
import fr.pandami.idao.ServiceIDao;

@Remote(ServiceIDao.class)
@Stateless
public class ServiceDao implements ServiceIDao{
	
	@PersistenceContext(unitName = "PUPandami")
	private EntityManager em;

	@Override
	public void addService(Service service) {
		try {
			em.persist(service);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Service> getAllServices() {
		TypedQuery<Service> query = em.createQuery("SELECT a From Service a", Service.class);
		System.out.println("Typed Query :)");
		return query.getResultList();
	}

	@Override
	public Service getServiceById(int serviceId) {
		return em.find(Service.class, serviceId);
	}

	@Override
	public List<Service> getMyActiveDemands(int userId) {
		Query query = em.createQuery("SELECT s FROM Service s WHERE s.creator.id = :paramidcreator AND s.startDate >= current_date AND s.cancellationDate = NULL");
		query.setParameter("paramidcreator", userId);
		return query.getResultList();
	}

	@Override
	public List<Service> getMyActiveSubcriptions(int userId) {
		Query query=em.createQuery("SELECT s.service FROM Subscription s where s.volunteer.id=:paramIdUser AND s.unsubscribeDate=null AND s.service.cancellationDate= null");
		query.setParameter("paramIdUser", userId);
		List <Service> activeSubscriptions=query.getResultList();
		return activeSubscriptions;
	}

	@Override
	public List<Service> getAllServicesWithNoActiveSubcription(int userId) {
		Query query = em.createQuery("SELECT s FROM Service s WHERE s.creator.id != :paramidcreator AND s.cancellationDate = null AND s.closingDate = null AND s.acceptationDate = null AND s.startDate >= current_date");
		query.setParameter("paramidcreator", userId);
		return query.getResultList();
	}

	@Override
	public void update(Service service) {
		em.merge(service);
		
	}

}
