package com.shop.augment_fashion.controllers;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.shop.augment_fashion.repositories.CartRepository;

@RestController
public class CartController {
    @Autowired
    private CartRepository cart;

    @RequestMapping(value = "/Cart",method=RequestMethod.POST)
    public String addClothes(@RequestBody String addCart){
        JSONObject jsonCart, jsonResponse;
        jsonCart = new JSONObject(addCart);
        jsonResponse = cart.addCart(jsonCart, new JSONObject());

        return jsonResponse.toString();
    }

}
