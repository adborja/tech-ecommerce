package co.edu.cedesistemas.commerce.sandbox;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Profile;

import co.edu.cedesistemas.commerce.model.Product;
import co.edu.cedesistemas.commerce.model.User;
import co.edu.cedesistemas.commerce.service.IUserService;
import co.edu.cedesistemas.common.SpringProfile;
import co.edu.cedesistemas.common.util.Utils;

@Profile(SpringProfile.SANDBOX)
public class UserServiceSandbox implements IUserService {

	@Override
	public User createUser(User user) {
		user.setId(UUID.randomUUID().toString());
		return user;
	}

	@Override
	public void deleteUser(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<User> getByEmail(String email) throws Exception {
		User u = new User();
		u.setEmail(email);
		u.setId(UUID.randomUUID().toString());
		u.setName("Jaime");
		u.setLastName("Barrera");
		
		User u1 = new User();
		u1.setEmail(email);
		u1.setId(UUID.randomUUID().toString());
		u1.setName("Pedro");
		u1.setLastName("Perez");
		
		User u2 = new User();
		u2.setEmail(email);
		u2.setId(UUID.randomUUID().toString());
		u2.setName("Camilo");
		u2.setLastName("Lopez");
		
		
		return Arrays.asList(u,u1,u2);
	}

	@Override
	public User getById(String id) {
		User u2 = new User();
		u2.setEmail("jbarrera027@hotmail.com");
		u2.setId(id);
		u2.setName("Jaime");
		u2.setLastName("Barrera");
		
		return u2;
	}

	@Override
	public User updateUser(String id, User user) throws Exception{
		if (user.getId() != null) {
            throw new Exception("id cannot be updated");
        }
        User found = getById(id);
        BeanUtils.copyProperties(user, found, Utils.getNullPropertyNames(user));
        return found;
	}

}
