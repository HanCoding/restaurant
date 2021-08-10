package com.sh.restaurant.interfaces;

import com.sh.restaurant.domain.RestaurantDTO;
import com.sh.restaurant.domain.RestaurantRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RestaurantController {

    private RestaurantRepository repository = new RestaurantRepository();


    @GetMapping("/restaurant")
    public List<RestaurantDTO> list() {
//        List<RestaurantDTO> restaurantDTOList = new ArrayList<>();
//        restaurantDTOList.add(new RestaurantDTO(1004L, "Misoya", "Tokyo"));

        List<RestaurantDTO> restaurantList = repository.findAll();

        return restaurantList;
    }

    @GetMapping("/restaurant/{id}")
    public RestaurantDTO detail(@PathVariable("id") Long id) {

//        List<RestaurantDTO> restaurantDTOList = repository.findAll();
//
//        RestaurantDTO result = restaurantDTOList
//                .stream()
//                .filter(r -> r.getId()
//                        .equals(id))
//                        .findFirst()
//                        .orElse(null);

        RestaurantDTO restaurant = repository.findById(id);

        return restaurant;
    }
}
