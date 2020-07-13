package co.edu.cedesistemas.commerce.service.sandbox;

import co.edu.cedesistemas.commerce.model.Store;
import co.edu.cedesistemas.commerce.model.User;
import co.edu.cedesistemas.commerce.service.IUserService;
import co.edu.cedesistemas.common.SpringProfile;
import co.edu.cedesistemas.common.util.Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Profile(SpringProfile.SANDBOX)
@Service
public class UserServiceSandbox implements IUserService {

    @Override
    public User createUser(final User user)
    {
        user.setId(UUID.randomUUID().toString());
        user.setName("Pedro");
        user.setLastName("Lopez");
        user.setEmail("pedro@prueba.com");
        return user;
    }

    @Override
    public User getById(final String id)
    {
        User user = new User();
        user.setId(id);
        user.setName("nombre "+id);
        user.setLastName("Apellido " +id);
        user.setEmail("correo"+id+"@prueba.com");
        return user;
    }

    @Override
    public List<User> getByEmail(final String mail)
    {
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setEmail("correo"+mail);
        user.setName("nombre "+mail);

        User user1 = new User();
        user1.setId(UUID.randomUUID().toString());
        user1.setEmail("correo2"+mail);
        user1.setName("nombre 2 "+mail);

        return Arrays.asList(user, user1);

    }

    @Override
    public List<User> getByName(final String name)
    {
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setEmail("1"+name+"@gmail.com");
        user.setName("nombre "+name);

        User user1 = new User();
        user1.setId(UUID.randomUUID().toString());
        user1.setEmail(name+"@prueba.com");
        user1.setName("nombre 2 "+name);

        return Arrays.asList(user, user1);

    }


    @Override
    public User updateUser(String id, User user)  throws Exception
    {
        if (user.getId() != null) {
            throw new Exception("id cannot be updated");
        }
        User found = getById(id);
        BeanUtils.copyProperties(user, found, Utils.getNullPropertyNames(user));
        return found;
    }

    @Override
    public void deleteUser(final String id) {

    }

}
