package com.shop.augment_fashion.repositories;

import java.util.List;

import org.json.JSONObject;

import com.shop.augment_fashion.models.CartModel;

public interface CartRepository{
    JSONObject addCart(JSONObject addCart, JSONObject jsonResponse);

    List<CartModel> obtainItems(int nid_user);

    void updateItem(String newCart);

    void deleteItem(String delCart);
}