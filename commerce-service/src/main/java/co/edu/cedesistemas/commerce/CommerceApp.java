package co.edu.cedesistemas.commerce;

import co.edu.cedesistemas.commerce.config.CommerceConfig;
import co.edu.cedesistemas.commerce.model.Store;
import co.edu.cedesistemas.commerce.service.StoreService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class CommerceApp {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(CommerceConfig.class);
        ctx.scan("co.edu.cedesistemas.commerce");
        StoreService service = ctx.getBean(StoreService.class);

        Store store = new Store();
        store.setAddress("cra");
        store.setName("mystore");
        store.setPhone("2345144");
        store.setType(Store.Type.FOOD);

        store = service.createStore(store);

        List<Store> stores = service.getByType(Store.Type.FOOD);
        System.out.println(stores);
        System.out.println(service);
    }
}
