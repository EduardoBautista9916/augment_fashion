package com.shop.augment_fashion.controllers;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.shop.augment_fashion.repositories.ClothesRepository;

/**
 *
 * @author EduardoBautista
 */
@CrossOrigin
@RestController
public class ClothesController {

    @Autowired
    private ClothesRepository clothes;


    @RequestMapping(value = "/Clothes",method=RequestMethod.POST)
    public String insertClothes(@RequestBody String newClothes){
        JSONObject jsonClothes, jsonResponse;
        jsonClothes = new JSONObject(newClothes);
        jsonResponse = clothes.addClothes(jsonClothes, new JSONObject());

        /*if(jsonResponse.getInt("codeClothes")==200){
            int valor = jsonResponse.getInt("nid_Clothes");
            jsonResponse=categories.addCategory(jsonClothes.getJSONArray("acategories"),valor, jsonResponse);
        }else{
            jsonResponse.put("codeController",400);
            jsonResponse.put("messageController", "[ClothesController] nid_clothes was not obtained due to some error in the creation of the clothes object ");
        }*/
        return jsonResponse.toString();
    }

    @RequestMapping(value = "/Clothes",method=RequestMethod.GET)
    public String obtainClothes(){
        JSONArray jsonArrayResponse = clothes.obtainClothes();
        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("products",jsonArrayResponse);
        return jsonResponse.toString();
    }

    @RequestMapping(value = "/Clothes/{nid_clothes}",method=RequestMethod.DELETE)
    public String deleteClothes(@PathVariable int nid_clothes){
        JSONObject jsonResponse;
        jsonResponse = clothes.deleteClothes(nid_clothes, new JSONObject());
        return jsonResponse.toString();
    }



}
