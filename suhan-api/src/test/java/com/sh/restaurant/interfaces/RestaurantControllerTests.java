package com.sh.restaurant.interfaces;

import com.sh.restaurant.application.RestaurantService;
import com.sh.restaurant.domain.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RestaurantController.class)
class RestaurantControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RestaurantService restaurantService;

    @Test
    public void list() throws Exception {

        List<RestaurantDto> restaurants = new ArrayList<>();
        restaurants.add(new RestaurantDto(1L, "yonan", "Daejeon"));

        given(restaurantService.getRestaurants()).willReturn(restaurants);

        mvc.perform(get("/restaurant"))
            .andExpect(status().isOk())
            .andExpect(content().string(
                containsString("\"id\":1")
            ))
            .andExpect(content().string(
                    containsString("\"name\":\"yonan\"")
            ));
    }

    @Test
    public void detail() throws Exception {

        RestaurantDto restaurant = new RestaurantDto(1L, "yonan", "Daejeon");
        restaurant.addMenuItem(new MenuItemDto("kimchi"));

        RestaurantDto restaurant2 = new RestaurantDto(2L, "misoya", "Daejeon");
        restaurant.addMenuItem(new MenuItemDto("kimchi"));

        given(restaurantService.getRestaurant(1L)).willReturn(restaurant);
        given(restaurantService.getRestaurant(2L)).willReturn(restaurant2);

        mvc.perform(get("/restaurant/1"))
            .andExpect(status().isOk())

            .andExpect(content().string(
                    containsString("\"id\":1"))
            )

            .andExpect(content().string(
                    containsString("\"name\":\"kimchi\""))
            )

            .andExpect(content().string(
                    containsString("\"name\":\"yonan\"")
            ));

        mvc.perform(get("/restaurant/2"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"id\":2")
                ))
                .andExpect(content().string(
                        containsString("\"name\":\"misoya\"")
                ));
    }

    @Test
    public void create() throws Exception {

        RestaurantDto restaurant = new RestaurantDto(1234L, "Hello", "Daejeon");

        mvc.perform(post("/restaurant")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"hello\",\"address\":\"Daejeon\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "/restaurant/1234"))
                .andExpect(content().string("{}"));


        verify(restaurantService).addRestaurant(any());
    }

}