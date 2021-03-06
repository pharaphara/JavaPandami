package fr.pandami.business;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import fr.pandami.entity.Address;
import fr.pandami.entity.CancellationCause;
import fr.pandami.entity.Equipment;
import fr.pandami.entity.ServiceType;
import fr.pandami.ibusiness.ReferentialIBusiness;
import fr.pandami.idao.ReferentialIDao;

@Remote(ReferentialIBusiness.class)
@Stateless
public class ReferantialBusiness implements ReferentialIBusiness{

	@EJB
	private ReferentialIDao proxyReferentialDao;
	
	
	@Override
	public List<Address> listAdresses() {
		return proxyReferentialDao.getAllAdresses();
	}


	@Override
	public List<ServiceType> listTypes() {
		
		return proxyReferentialDao.getAllTypes();
	}


	@Override
	public List<Equipment> listEquipments() {
		
		return proxyReferentialDao.getAllEquipments();
	}


	@Override
	public List<CancellationCause> listCancelCauses() {
				return proxyReferentialDao.getAllCauses();
	}

	
}
