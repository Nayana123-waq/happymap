package com.example.happymop.config;

import com.example.happymop.model.ServiceItem;
import com.example.happymop.repository.ServiceRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ServiceDataLoader implements ApplicationRunner {
    private final ServiceRepository repo;
    public ServiceDataLoader(ServiceRepository repo){ this.repo = repo; }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<ServiceItem> seeds = List.of(
            s("office","Office Cleaning",2500,"Office cleaning service"),
            s("deep_commercial","Deep Cleaning (Commercial)",3500,"Deep commercial deep-cleaning"),
            s("housekeeping_commercial","Commercial Housekeeping",1800,"Commercial housekeeping"),
            s("gardening","Gardening & Grounds",1500,"Gardening and groundskeeping"),
            s("washing","Washing & Laundry",800,"Washing & laundry service"),
            s("cleaning","Home Cleaning",1200,"Home cleaning service"),
            s("housekeeping_res","Housekeeping (Home)",1000,"Residential housekeeping"),
            s("cooking","Cooking Support",900,"Cooking support service"),
            s("water_tank","Water-tank Cleaning",2000,"Water tank cleaning and servicing"),
            s("car_wash","Car Washing & Detailing",900,"Car washing and detailing")
        );

        for(ServiceItem item: seeds){
            repo.findByCode(item.getCode()).orElseGet(() -> repo.save(item));
        }
    }

    private ServiceItem s(String code, String title, int price, String desc){
        ServiceItem it = new ServiceItem();
        it.setCode(code); it.setTitle(title); it.setPrice(price); it.setDescription(desc);
        return it;
    }
}
