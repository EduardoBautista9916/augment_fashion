package com.shop.augment_fashion.repositories;

import java.util.List;

import org.json.JSONObject;

import com.shop.augment_fashion.models.AddressModel;

/**
 *
 * @author EduardoBautista
 */
public interface AddressRepository {
    List<AddressModel> getAddresses();

    JSONObject getAddress(int nid_user);

    void delAddress(int nid_address);

    JSONObject newAddress(JSONObject newAddress, JSONObject jsonResponse);

    void updAddress(String updaAddress);
}
