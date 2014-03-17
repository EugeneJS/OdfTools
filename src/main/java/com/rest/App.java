package com.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.hello.*;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.xerces.impl.dv.util.Base64;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class App {

    public static void main(String[] args) throws IOException {

        App app = new App();
        //app.DrupalRestClient("","","","","");
        //app.MyRestClient(1);
        app.CreateUsersHashMap();

    }
    HashMap<String, Users> userMap = new HashMap<String, Users>();

    public static final Logger LOG = Logger.getLogger(App.class);

    public void DrupalRestClient(String filePath, String fileName, String metaDocId, String body, String certId, ArrayList<String> certs) throws IOException {

        RestTemplate restTemplate = new RestTemplate();
        CreateUsersHashMap();
        //getAuth(restTemplate);

        File f = new File(filePath);
        byte[] fb = FileUtils.readFileToByteArray(f);

        DrupalFile df = new DrupalFile(1, f.getName(), Base64.encode(fb));
        DrupalFile resp = restTemplate.postForObject("http://127.0.0.1:8888/rest/file/", df, DrupalFile.class);

        Integer[] users = new Integer[1];
        users[0] = 1;

        Integer[] files = new Integer[1];
        files[0] = resp.getFid();

        // Body
        ArrayList<DrupalBodyItems> db = new ArrayList<DrupalBodyItems>();
        db.add(new DrupalBodyItems(body));
        DrupalBodyUnd bu = new DrupalBodyUnd(db);
        // EndBody

        // File
        DrupalFilesItems flItem = new DrupalFilesItems(files[0]);
        ArrayList<DrupalFilesItems> flArr = new ArrayList<DrupalFilesItems>();
        flArr.add(flItem);
        DrupalFiles file = new DrupalFiles(flArr);
        // EndFile

        // Parent Document
        DrupalParentDocUnd parentDoc = new DrupalParentDocUnd();
        // End Parent Document

        // DocumentId
        DrupalDocIdItem docId = new DrupalDocIdItem(metaDocId);
        ArrayList<DrupalDocIdItem> docIdArr = new ArrayList<DrupalDocIdItem>();
        docIdArr.add(docId);
        DrupalDocIdUnd dId = new DrupalDocIdUnd(docIdArr);
        // EndDocumentId

        // Workers
        ArrayList<DrupalWorkersItems> workersArr = new ArrayList<DrupalWorkersItems>();
        //Users user = userMap.get("");
        //ArrayList<String> ids = new ArrayList<String>();
        for(String cert : certs)
        {
            try {
                Users user = userMap.get(cert.toString());
                //System.out.println(user.getName() + " " + "(" + user.getUid() + ")");
                DrupalWorkersItems workers = new DrupalWorkersItems("[target_id:" + user.getName() + " " + "(" + user.getUid() + ")]");
                workersArr.add(workers);
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        //DrupalWorkersItems workers = new DrupalWorkersItems(user.getName() + " " + "(" + user.getUid() + ")");
        DrupalWorkersUnd dWorker = new DrupalWorkersUnd(workersArr);
        // EndWorkers

        DrupalNode dn = new DrupalNode(fileName, dId, bu, dWorker, parentDoc, file, "rk");
        //Create
        try {
            DrupalNode respDN = restTemplate.postForObject("http://127.0.0.1:8888/rest/node/", dn, DrupalNode.class);
            System.out.println(respDN.getNid());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        //End Create


        ObjectMapper objMapper = new ObjectMapper();
        String jsonString = objMapper.writeValueAsString(dn);
        System.out.println(jsonString);



        System.out.println();
        System.out.println();
        //System.out.println(respDN.getNid());
    }

    private void getAuth(RestTemplate restTemplate){
        UserLogin ul = new UserLogin("alan", "1");
        LoginResponse response = (LoginResponse) restTemplate.postForObject("http://127.0.0.1:8888/rest/user/login", ul, LoginResponse.class);

        if (response.getToken().length() > 1) {
            final List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
            interceptors.add(new BasicAuthInterceptor(response.getToken(), response.getSessid(), response.getSessionName()));
            restTemplate.setInterceptors(interceptors);
        } else {
            return;
        }

    }

    private void MyRestClient(int nodeId) {

        RestTemplate restTemplate = new RestTemplate();

        Page page = restTemplate.getForObject("http://localhost:8888/rest/node/1", Page.class);

        System.out.println("Id is " + page.getId().toString());
        System.out.println("Text is " + page.getContent());
    }

    public void CreateUsersHashMap(){
        // Удаляем все что было в хэшмапе
        userMap.clear();

        RestTemplate rt = new RestTemplate();

        //Получаем масив пользователей
        Users[] usersArray = rt.getForObject("http://localhost:8888/rest/user/", Users[].class);
        List<Users> usersList = Arrays.asList(usersArray);

        // Записываем в HashMap пользователей, в качестве ключа используем сертификат
        for(int i = 0; i < usersArray.length - 1; i++)  // usersList.get(0) - самый врехний / usersList.get(n) - самый нижний = 0й пользователь, поэтому до него не доходим
        {
            try {
                Users user1 = rt.getForObject("http://localhost:8888/rest/user/" + usersList.get(i).getUid().toString(), Users.class);
                userMap.put(user1.getCertId().getUnd().get(0).getValue(), user1);
                System.out.println("Name: " + user1.getName() + "; Id: " + user1.getUid() + "; Certificate: " +  user1.getCertId().getUnd().get(0).getValue());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        Users u1 = userMap.get("3395694350439488025");
        System.out.println(u1.getName());
    }
}
