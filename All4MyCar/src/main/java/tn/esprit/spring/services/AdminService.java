package tn.esprit.spring.services;

import java.util.List;

import tn.esprit.spring.models.User;

public interface AdminService {
	
List<User> findUser();
List<User> findClientUser();
List<User>  findPrestataireUser();
}
