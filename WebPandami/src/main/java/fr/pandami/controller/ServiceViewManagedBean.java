package fr.pandami.controller;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import fr.pandami.entity.Service;
import fr.pandami.entity.Subscription;
import fr.pandami.entity.User;
import fr.pandami.ibusiness.AccountIBusiness;
import fr.pandami.ibusiness.NegociationIBusiness;
import fr.pandami.ibusiness.ServiceIBusiness;
import fr.pandami.ibusiness.SubscriptionIBusiness;

@ManagedBean (name = "mbView")
@ViewScoped
public class ServiceViewManagedBean implements Serializable{


	private static final long serialVersionUID = 1L;

	private List<Service> services;
	private User user = new User();
	private String viewId="";
	private Service selectedService = new Service();
    private Subscription sub = new Subscription();
    private User volunteer=new User();
   
	


	@ManagedProperty (value = "#{mbConnexion.userId}")
	private int userId;


	@EJB
	private ServiceIBusiness proxyServiceBU;
	@EJB
	private AccountIBusiness proxyAccountBU;
	@EJB
	private SubscriptionIBusiness proxySubscriptionBU;
	@EJB
	private NegociationIBusiness proxyNegoBU;


	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		viewId = getviewIdParam(fc);

		services = proxyServiceBU.listServices((viewId==null)? 0:Integer.parseInt(viewId), userId);
		
		user = proxyAccountBU.getUser(userId);

	}

	public String getviewIdParam(FacesContext fc){

		Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
		return params.get("viewId");

	}

	public  String distance(double lat1, double lat2, double lon1,
			double lon2) {

		final int R = 6371; // Radius of the earth

		double latDistance = Math.toRadians(lat2 - lat1);
		double lonDistance = Math.toRadians(lon2 - lon1);
		double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
				+ Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
				* Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double distance = R * c * 1000; // convert to meters



		distance = Math.pow(distance, 2); 

		distance = Math.sqrt(distance);
		return  new DecimalFormat("#.##").format(distance/1000);
	}

	public String addSubscription() {
		selectedService.setAcceptationDate(LocalDate.now());
		proxyServiceBU.updateService(selectedService);
		sub.setSubscriptionDate(LocalDateTime.now());
		sub.setVolunteer(user);
		sub.setService(selectedService);
		sub = proxySubscriptionBU.createSub(sub);
		return "ServiceView.xhtml?faces-redirect=true&viewId=2";
	}

	public String cancelSubscription() {

		selectedService.setAcceptationDate(null);
		proxyServiceBU.updateService(selectedService);
		proxySubscriptionBU.cancelSub(selectedService);

		return "ServiceView.xhtml?faces-redirect=true";	
	}
		
		
	public boolean affichageBtnNego(Integer serviceId) {
		return proxyNegoBU.isNegociable(serviceId);
	}
	
	public String getStatus(Integer serviceId) {
		
		
		return proxySubscriptionBU.getStatusBU(serviceId);
	}
	
   
	public List<Service> getServices() {
		return services;
	}


	public void setServices(List<Service> services) {
		this.services = services;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getViewId() {
		return viewId;
	}

	public void setViewId(String viewId) {
		this.viewId = viewId;
	}

	public Service getSelectedService() {
		return selectedService;
	}

	public void setSelectedService(Service selectedService) {
		this.selectedService = selectedService;
	}

	public Subscription getSub() {
		return sub;
	}

	public void setSub(Subscription sub) {
		this.sub = sub;
	}

	public User getVolunteer() {
		return volunteer;
	}

	public void setVolunteer(User volunteer) {
		this.volunteer = volunteer;
	}







}
