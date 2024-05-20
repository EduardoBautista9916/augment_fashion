package com.shop.augment_fashion.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
//import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.shop.augment_fashion.models.UserModel;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

/**
 *
 * @author EduardoBautista
 */
@Repository
@Transactional
public class UserImplements implements UserRepository{

    @PersistenceContext
    EntityManager entityManager;

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public List<UserModel> getUsers(){
        String query = "FROM UserModel";
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    @Transactional
    public JSONObject getUser(JSONObject jsonUser, JSONObject jsonResponse){
        try{
            if(!jsonUser.toString().contains("cemail") || !jsonUser.toString().contains("cpassword") ){
                jsonResponse.put("codeUser", 401);
                jsonResponse.put("messageUser","[UserImplement] some name within the JSON is not valid");
            }else{
                /*String queryMail = "FROM UserModel WHERE "
                +"cemail = '"+ newUser.getString("cemail")+"'";
                List<UserModel> lstUserMail = entityManager.createQuery(queryMail).getResultList();
    
                    String queryNickname = "FROM UserModel WHERE "
                    +"cnickname = '"+ newUser.getString("cnickname")+"'";
                    List<UserModel> lstUserNickname = entityManager.createQuery(queryNickname).getResultList();
    
                if(!lstUserMail.isEmpty()){
                    jsonResponse.put("codeUser", 406);
                    jsonResponse.put("messageUser","[UserImplement] The email already exists");
                }else if(!lstUserNickname.isEmpty()){
                    jsonResponse.put("codeUser", 406);
                    jsonResponse.put("messageUser","[UserImplement] The nickname already exists");
                }else{
                        
                        UserModel userM = new UserModel();
                        userM.setCfirst_name(newUser.getString("cfirst_names"));
                        userM.setCsurnames(newUser.getString("csurnames"));
                        userM.setCnickname(newUser.getString("cnickname"));
                        userM.setCemail(newUser.getString("cemail"));
    
                        //String password = newUser.getString("cpassword");
                        //Argon2PasswordEncoder arrSpringSecurity = new Argon2PasswordEncoder(16, 32, 1, 60000, 10);
                        //String hasheo = arrSpringSecurity.encode(password);
    
                        userM.setCpassword(newUser.getString("cpassword"));
                        userM.setCphone(newUser.getString("cphone"));
                        userM.setNrole(2);
                        userM.setNid_Address(nid_address);
                        userM.setCnumber_credit_card(newUser.getString("cnumber_credit_card"));
                        userM.setBenable(true);
    
                        entityManager.merge(userM);
    
                        jsonResponse.put("codeUser", 200);
                        jsonResponse.put("messageUser","[UserImplement] User Created");
                    }
                */}
            }catch(JSONException e){
                jsonResponse.put("codeUser", 400);
                jsonResponse.put("messageUser","[UserImplement] JSONException, There was an error reading the JSON");
            }catch(Exception e){
                jsonResponse.put("codeUser", 400);
                jsonResponse.put("messageUser","[UserImplement] Unexpected Error");
            }
            return jsonResponse;
    }

    @Override
    @Transactional
    public void delUser(int nid_user){
        UserModel user = entityManager.find(UserModel.class, nid_user);
        user.setBenable(false);
        user.setDcancellation_date(LocalDateTime.now());
        entityManager.merge(user);
    }

    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public JSONObject newUser(JSONObject newUser, int nid_address, JSONObject jsonResponse){
        try{
        if(!newUser.toString().contains("cfirst_names") || !newUser.toString().contains("csurnames") || !newUser.toString().contains("cnickname") || !newUser.toString().contains("cemail") || !newUser.toString().contains("cpassword") || !newUser.toString().contains("cphone") || !newUser.toString().contains("cnumber_credit_card")){
            jsonResponse.put("codeUser", 401);
            jsonResponse.put("messageUser","[UserImplement] some name within the JSON is not valid");
        }else{
            String queryMail = "FROM UserModel WHERE "
            +"cemail = '"+ newUser.getString("cemail")+"'";
            List<UserModel> lstUserMail = entityManager.createQuery(queryMail).getResultList();

                String queryNickname = "FROM UserModel WHERE "
                +"cnickname = '"+ newUser.getString("cnickname")+"'";
                List<UserModel> lstUserNickname = entityManager.createQuery(queryNickname).getResultList();

            if(!lstUserMail.isEmpty()){
                jsonResponse.put("codeUser", 406);
                jsonResponse.put("messageUser","[UserImplement] The email already exists");
            }else if(!lstUserNickname.isEmpty()){
                jsonResponse.put("codeUser", 406);
                jsonResponse.put("messageUser","[UserImplement] The nickname already exists");
            }else{
                    
                    UserModel userM = new UserModel();
                    userM.setCfirst_name(newUser.getString("cfirst_names"));
                    userM.setCsurnames(newUser.getString("csurnames"));
                    userM.setCnickname(newUser.getString("cnickname"));
                    userM.setCemail(newUser.getString("cemail"));

                    //String password = newUser.getString("cpassword");
                    //Argon2PasswordEncoder arrSpringSecurity = new Argon2PasswordEncoder(16, 32, 1, 60000, 10);
                    //String hasheo = arrSpringSecurity.encode(password);

                    userM.setCpassword(newUser.getString("cpassword"));
                    userM.setCphone(newUser.getString("cphone"));
                    userM.setNrole(2);
                    userM.setNid_Address(nid_address);
                    userM.setCnumber_credit_card(newUser.getString("cnumber_credit_card"));
                    userM.setBenable(true);

                    entityManager.merge(userM);

                    jsonResponse.put("codeUser", 200);
                    jsonResponse.put("messageUser","[UserImplement] User Created");
                }
            }
        }catch(JSONException e){
            jsonResponse.put("codeUser", 400);
            jsonResponse.put("messageUser","[UserImplement] JSONException, There was an error reading the JSON");
        }catch(Exception e){
            jsonResponse.put("codeUser", 400);
            jsonResponse.put("messageUser","[UserImplement] Unexpected Error");
        }
        return jsonResponse;
    }

    @Override
    @Transactional
    public void updUser(String updaUser){
        JSONObject json = new JSONObject(updaUser);
        UserModel userOld = entityManager.find(UserModel.class, json.getInt("nid_user"));
        userOld.setCfirst_name(json.getString("cfirst_name"));
        userOld.setCsurnames(json.getString("csurnames"));
        userOld.setCnickname(json.getString("cnickname"));
        userOld.setCemail(json.getString("cemail"));
        userOld.setCpassword(json.getString("cpassword"));
        userOld.setNrole(json.getInt("nrole"));
        userOld.setNid_Address(json.getInt("nid_Address"));
        userOld.setCnumber_credit_card(json.getString("cnumber_credit_card"));
        userOld.setBenable(json.getBoolean("benable"));

        entityManager.merge(userOld);
    }

}
