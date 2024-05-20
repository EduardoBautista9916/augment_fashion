package com.shop.augment_fashion.controllers;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.shop.augment_fashion.repositories.AddressRepository;
import com.shop.augment_fashion.repositories.UserRepository;

/**
 *
 * @author EduardoBautista
 */
@RestController
public class UserController {

    @Autowired
    private UserRepository user;

    @Autowired
    private AddressRepository address;

    @RequestMapping(value = "/login",method=RequestMethod.POST)
    public String insertUser(@RequestBody String newUser){
        JSONObject jsonUser, jsonAddress, jsonResponse;
        jsonUser=new JSONObject(newUser);
        if(jsonUser.toString().contains("oaddress")){
            jsonAddress= jsonUser.getJSONObject("oaddress");
            jsonResponse=address.newAddress(jsonAddress, new JSONObject());
        }else{
            jsonAddress= new JSONObject();
            jsonAddress.put("coutdoor_number","none");
            jsonAddress.put("cinterior_number","none");
            jsonAddress.put("cstreet","none");
            jsonAddress.put("ccologne","none");
            jsonAddress.put("ctown_hall","none");
            jsonAddress.put("cstate","none");
            jsonAddress.put("ccountry","none");

            jsonResponse=address.newAddress(jsonAddress, new JSONObject());
        }

        if(jsonResponse.getInt("codeAddress")==200){
            int valor = jsonResponse.getInt("nid_address");
            jsonResponse=user.newUser(jsonUser,valor, jsonResponse);
        }else{
            jsonResponse.put("codeController",400);
            jsonResponse.put("messageController", "[UserController] nid_address was not obtained due to some error in the creation of the address object ");
        }
        

        return jsonResponse.toString();
    }

    @RequestMapping(value = "/access",method=RequestMethod.POST)
    public String getUsers(@RequestBody String accessUser){
        JSONObject jsonResponse=new JSONObject();

        

        return jsonResponse.toString();
    }

    @RequestMapping(value = "/Access",method=RequestMethod.POST)
    public String getUser(@RequestBody String userAccess){

        return null;
    }

    @RequestMapping(value = "/Users/{nid_user}",method=RequestMethod.DELETE)
    public void delUser(@PathVariable int nid_user){
        user.delUser(nid_user);
    }

    @RequestMapping(value="/Users", method=RequestMethod.PUT)
    public void updateUser(@RequestBody String updaUser){
        user.updUser(updaUser);
    }

}
