package com.shop.augment_fashion.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Repository;

import com.shop.augment_fashion.models.AddressModel;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class AddressImplements implements AddressRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public List<AddressModel> getAddresses(){
        String query = "FROM AddressModel";
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    @Transactional
    public JSONObject getAddress(int nid_address){
        AddressModel address = entityManager.find(AddressModel.class, nid_address);
        JSONObject jsonAddress = new JSONObject();

        jsonAddress.put("cstreet",address.getCstreet());
        jsonAddress.put("coutdoor_number",address.getCoutdoor_number());
        jsonAddress.put("cinterior_number",address.getCinterior_number());
        jsonAddress.put("ccologne",address.getCcologne());
        jsonAddress.put("ctown_hall",address.getCtown_hall());
        jsonAddress.put("cstate",address.getCstate());
        jsonAddress.put("ccountry",address.getCstate());

        return jsonAddress;
    }

    @Override
    @Transactional
    public void delAddress(int nid_address){
        AddressModel address = entityManager.find(AddressModel.class, nid_address);
        address.setBenable(false);
        address.setDcancellation_date(LocalDateTime.now());
        entityManager.merge(address);
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public JSONObject newAddress(JSONObject newAddress, JSONObject jsonResponse){
        
        try{
            if(!newAddress.toString().contains("cstreet") || !newAddress.toString().contains("coutdoor_number") || !newAddress.toString().contains("cinterior_number") || !newAddress.toString().contains("ccologne") || !newAddress.toString().contains("ctown_hall") || !newAddress.toString().contains("cstate") || !newAddress.toString().contains("ccountry")){
                jsonResponse.put("codeAddress", 401);
                jsonResponse.put("messageAddress","[AddressImplement] some name within the JSON is not valid");
            } else{
                String query = "FROM AddressModel WHERE "
                +"cstreet = '"+ newAddress.getString("cstreet")
                +"' AND coutdoor_number = '" + newAddress.getString("coutdoor_number")
                +"' AND cinterior_number = '" + newAddress.getString("cinterior_number")
                +"' AND ccologne = '" + newAddress.getString("ccologne")
                +"' AND ctown_hall = '" + newAddress.getString("ctown_hall")
                +"' AND cstate = '"+ newAddress.getString("cstate")
                +"' AND ccountry = '" + newAddress.getString("ccountry")
                +"' AND benable = True ";

                List<AddressModel> lstAddress = entityManager.createQuery(query).getResultList();

                if(lstAddress.isEmpty()){
                    AddressModel address = new AddressModel();
                    address.setCstreet(newAddress.getString("cstreet"));
                    address.setCoutdoor_number(newAddress.getString("coutdoor_number"));
                    address.setCinterior_number(newAddress.getString("cinterior_number"));
                    address.setCcologne(newAddress.getString("ccologne"));
                    address.setCtown_hall(newAddress.getString("ctown_hall"));
                    address.setCstate(newAddress.getString("cstate"));
                    address.setCcountry(newAddress.getString("ccountry"));
                    address.setBenable(true);

                    entityManager.merge(address);

                    query = "FROM AddressModel WHERE "
                    +"cstreet = '"+ address.getCstreet()
                    +"' AND coutdoor_number = '" + address.getCoutdoor_number()
                    +"' AND cinterior_number = '" + address.getCinterior_number()
                    +"' AND ccologne = '" + address.getCcologne()
                    +"' AND ctown_hall = '" + address.getCtown_hall() 
                    +"' AND cstate = '"+address.getCstate()
                    +"' AND ccountry = '" + address.getCcountry()
                    +"' AND benable = True ";

                    lstAddress = entityManager.createQuery(query).getResultList();
                    
                    jsonResponse.put("codeAddress", 200);
                    jsonResponse.put("nid_address", lstAddress.get(0).getNid_address());
                    jsonResponse.put("messageAddress","[AddressImplement] Address Created");
                }else{
                    jsonResponse.put("codeAddress", 200);
                    jsonResponse.put("nid_address", lstAddress.get(0).getNid_address());
                    jsonResponse.put("messageAddress","[AddressImplement] Address Obtained");
                }
            }
        }catch(JSONException e){
            jsonResponse.put("codeAddress", 400);
            jsonResponse.put("messageAddress","[AddressImplement] JSONException, There was an error reading the JSON");
        }catch(Exception e){
            jsonResponse.put("codeAddress", 400);
            jsonResponse.put("messageAddress","[AddressImplement] Unexpected Error");
        }
        return jsonResponse;
    }

    @Override
    @Transactional
    public void updAddress(String updaAddress){
        JSONObject json = new JSONObject(updaAddress);
        AddressModel  addressOld = entityManager.find(AddressModel.class, json.getInt("nid_user"));
        addressOld.setCstreet(json.getString("cstreet"));
        addressOld.setCoutdoor_number(json.getString("coutdoor_number"));
        addressOld.setCinterior_number(json.getString("cinterior_number"));
        addressOld.setCcologne(json.getString("ccologne"));
        addressOld.setCtown_hall(json.getString("ctown_hall"));
        addressOld.setCstate(json.getString("cstate"));
        addressOld.setCcountry(json.getString("ccountry"));
        addressOld.setBenable(true);

        entityManager.merge(addressOld);
    }
}
