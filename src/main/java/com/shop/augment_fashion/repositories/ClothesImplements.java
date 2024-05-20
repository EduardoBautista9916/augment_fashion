package com.shop.augment_fashion.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Repository;

import com.shop.augment_fashion.models.ClothesModel;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

/**
 *
 * @author EduardoBautista
 */
@Repository
@Transactional
public class ClothesImplements implements ClothesRepository{
    @PersistenceContext
    EntityManager entityManager;

    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public JSONObject addClothes(JSONObject newClothes, JSONObject jsonResponse){
        try{
            if(!newClothes.toString().contains("cimage") || !newClothes.toString().contains("cdescription") || !newClothes.toString().contains("cmaterial") || !newClothes.toString().contains("ccolor") || !newClothes.toString().contains("ctype_clothes") || !newClothes.toString().contains("csize") || !newClothes.toString().contains("fprice") || !newClothes.toString().contains("nstock") ) {
                jsonResponse.put("codeClothes", 401);
                jsonResponse.put("messageClothes","[ClothesImplement] some name within the JSON is not valid");
            }else{
                String queryClothes = "FROM ClothesModel WHERE "+
                "cdescription = '" + newClothes.getString("cdescription") +
                "' AND cmaterial = '"+ newClothes.getString("cmaterial") +
                "' AND ccolor = '" + newClothes.getString("ccolor") +
                "' AND ctype_clothes = '" + newClothes.getString("ctype_clothes") +
                "' AND csize = '"+ newClothes.getString("csize") +
                "' AND fprice = " + newClothes.getFloat("fprice") +
                " AND benable = True";

                List<ClothesModel> lstClothes = entityManager.createQuery(queryClothes).getResultList();

                if(lstClothes.isEmpty()){
                    ClothesModel clothes = new ClothesModel();
                    clothes.setCdescription(newClothes.getString("cdescription"));
                    clothes.setCimage(newClothes.getString("cimage"));
                    clothes.setCmaterial(newClothes.getString("cmaterial"));
                    clothes.setCcolor(newClothes.getString("ccolor"));
                    clothes.setCtype_clothes(newClothes.getString("ctype_clothes"));
                    clothes.setCsize(newClothes.getString("csize"));
                    clothes.setFprice(newClothes.getFloat("fprice"));
                    clothes.setNstock(newClothes.getInt("nstock"));
                    clothes.setBenable(true);
                    entityManager.merge(clothes);

                    queryClothes = "FROM ClothesModel WHERE "+
                    "cdescription = '" + newClothes.getString("cdescription") +
                    "' AND cmaterial = '"+ newClothes.getString("cmaterial") +
                    "' AND ccolor = '" + newClothes.getString("ccolor") +
                    "' AND ctype_clothes = '" + newClothes.getString("ctype_clothes") +
                    "' AND csize = '"+ newClothes.getString("csize") +
                    "' AND fprice = " + newClothes.getFloat("fprice") +
                    " AND nstock = " + newClothes.getInt("nstock") + 
                    " AND benable = True";

                    lstClothes = entityManager.createQuery(queryClothes).getResultList();

                    jsonResponse.put("codeClothes", 200);
                    jsonResponse.put("messageClothes","[ClothesImplement] Succesful clothes inserted");
                    jsonResponse.put("nid_Clothes", lstClothes.get(0).getNid_clothes());
                }else{
                    jsonResponse.put("codeClothes", 200);
                    jsonResponse.put("messageClothes","[ClothesImplement] Succesful clothes obtained");
                    jsonResponse.put("nid_Clothes", lstClothes.get(0).getNid_clothes());
                }
            }
        }catch(JSONException e){
            jsonResponse.put("codeClothes", 401);
            jsonResponse.put("messageClothes","[ClothesImplement] JSONException error in JSON");
        }catch(Exception e){
            jsonResponse.put("codeClothes", 401);
            jsonResponse.put("messageClothes","[ClothecsImplement] Exception unexpected error");
        }

        return jsonResponse;
    }

    @Override 
    @SuppressWarnings("unchecked")
    public JSONArray obtainClothes(){
        
        String queryClothes = "FROM ClothesModel WHERE benable = True";
        List<ClothesModel> lstClothes = entityManager.createQuery(queryClothes).getResultList();

        JSONObject jsonAux; 
        JSONArray jsonArrayResponse =  new JSONArray();
        System.out.println(lstClothes.size());
        //ClothesModel auxClothes = new ClothesModel();
        for(ClothesModel i : lstClothes){

            jsonAux = new JSONObject();
            jsonAux.put("nid_clothes", i.getNid_clothes());
            jsonAux.put("cimage", i.getCimage());
            jsonAux.put("cdescription", i.getCdescription());
            jsonAux.put("cmaterial", i.getCmaterial());
            jsonAux.put("ccolor", i.getCcolor());
            jsonAux.put("ctype_clothes",i.getCtype_clothes());
            jsonAux.put("csize", i.getCsize());
            jsonAux.put("fprice", i.getFprice());
            jsonAux.put("nstock", i.getNstock());
            
            jsonArrayResponse.put(jsonAux);
        }
        return jsonArrayResponse;
    }

    @Override
    @Transactional
    public JSONObject deleteClothes(int nid_clothes, JSONObject jsonResponse){
        try{
            ClothesModel clothes = entityManager.find(ClothesModel.class, nid_clothes);
            clothes.setBenable(false);
            clothes.setDcancellation_date(LocalDateTime.now());
            entityManager.merge(clothes);

            jsonResponse.put("codeClothes", 200);
            jsonResponse.put("messageClothes", "[ClothesImplements] Clothes successfully removed");
        }catch(JSONException e){
            jsonResponse.put("codeClothes", 401);
            jsonResponse.put("messageClothes", "[ClothesImplements] Error in JSON");
        }catch(Exception e){
            jsonResponse.put("codeClothes", 401);
            jsonResponse.put("messageClothes", "[ClothesImplements] Unexpected Error");
        }


        return jsonResponse;
    }
}