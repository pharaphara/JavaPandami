package fr.pandami.idao;

import java.util.List;

import fr.pandami.entity.Service;

public interface ServiceIDao {

	 void addService(Service service);

	List<Service> getAllServices();
	

}