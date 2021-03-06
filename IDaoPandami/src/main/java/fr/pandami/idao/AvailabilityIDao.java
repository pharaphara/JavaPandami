package fr.pandami.idao;

import java.util.List;

import fr.pandami.entity.Availability;
import fr.pandami.entity.User;


public interface AvailabilityIDao {
	Availability  addAvailability(Availability dispo);
	List<Availability> getAvailabilities(Integer userId);
	Availability updateAv(Availability availability);
	void deleteAv(Availability availability);
}
