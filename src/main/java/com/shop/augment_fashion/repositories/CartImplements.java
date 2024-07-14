package com.shop.augment_fashion.repositories;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.shop.augment_fashion.models.CartModel;
import com.shop.augment_fashion.models.CartModelPK;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 *
 * @author EduardoBautista
 */
public class CartImplements implements CartRepository{
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public JSONObject addCart(JSONObject addCart, JSONObject jsonResponse){
        try{
            CartModel cart = new CartModel();
            CartModelPK cartPK = new CartModelPK();
            cartPK.setNid_clothes(addCart.getInt("nid_clothes"));
            cartPK.setNid_user(addCart.getInt("nid_user"));
            cart.setId(cartPK);

            entityManager.merge(cart);
            jsonResponse.put("code",200);
            jsonResponse.put("Message","Successful");
        }catch(JSONException e){

        }catch(Exception e){
            
        }
        return jsonResponse;
    }

    @Override
    public List<CartModel> obtainItems(int nid_user) {
        return null;
    }

    @Override
    public void updateItem(String newCart) {

    }

    @Override
    public void deleteItem(String delCart) {

    }

}
