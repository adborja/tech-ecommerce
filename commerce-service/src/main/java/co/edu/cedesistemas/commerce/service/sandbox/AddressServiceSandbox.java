package co.edu.cedesistemas.commerce.service.sandbox;

import co.edu.cedesistemas.commerce.model.Address;
import co.edu.cedesistemas.commerce.service.IAddressService;
import co.edu.cedesistemas.common.SpringProfile;
import co.edu.cedesistemas.common.util.Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Profile(SpringProfile.SANDBOX)
@Service
public class AddressServiceSandbox implements  IAddressService {

    @Override
    public Address createAddress(final Address address)
    {
        address.setId(UUID.randomUUID().toString());
        address.setCity("Bogota");
        return address;
    }

    @Override
    public Address getById(final String id)
    {
        Address address = new Address();
        address.setId(id);
        address.setCity("Medellin");
        address.setDescription("Texto descripcion");
        address.setName("texto name");
        address.setPhoneNumber("0573008765443");
        address.setStreet1("Diagonal 75");
        address.setStreet2("calle 23");
        address.setStreet3("carrera 89");
        address.setZip("050001");
        return address;
    }

    //@Override
    public Address updateAddress(String id, Address address) throws Exception
    {
        if (address.getId() != null) {
            throw new Exception("id cannot be updated");
        }
        Address found = getById(id);
        BeanUtils.copyProperties(address, found, Utils.getNullPropertyNames(address));
        return found;
    }
}
