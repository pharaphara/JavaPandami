package fr.pandami.business;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import fr.pandami.entity.Service;
import fr.pandami.entity.Subscription;
import fr.pandami.ibusiness.SubscriptionIBusiness;
import fr.pandami.idao.SubscriptionIDao;

@Remote(SubscriptionIBusiness.class)
@Stateless
public class SubscriptionBusiness implements SubscriptionIBusiness{
	
	@EJB
	private SubscriptionIDao proxySubscriptionDao;

	@Override
	public Subscription createSub(Subscription subscription) {
		return proxySubscriptionDao.createSub(subscription);
	}

	@Override
	public Subscription cancelSub(Subscription subscription) {
		
		return proxySubscriptionDao.cancelSub(subscription);
	}

	@Override
	public Subscription getSub(Service service) {
		Subscription sub=new Subscription();
		for (Subscription reponse: service.getSubscriptionList()) 
		{
			if (reponse.getUnsubscribeDate() ==null) 
			{
				 sub=reponse;				 
			}
			
		}
		return sub;
	}


}
