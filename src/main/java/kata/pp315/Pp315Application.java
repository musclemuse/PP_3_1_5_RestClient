package kata.pp315;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.net.CookieHandler;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class Pp315Application {

    static RestTemplate restTemplate = new RestTemplate();
    static String url = "http://94.198.50.185:7081/api/users";
    static User user;
    static HttpHeaders headers = new HttpHeaders();

    public static void main(String[] args) {
        SpringApplication.run(Pp315Application.class, args);
        getAllUsers();
    }


    public static void getAllUsers() {

        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity<User> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity = restTemplate
                .exchange(url, HttpMethod.GET, requestEntity, String.class);

        String resultGet = String.valueOf(responseEntity.getHeaders().get("Set-Cookie"));
        System.out.println(resultGet);

        List<String> cookies = responseEntity.getHeaders().get("Set-Cookie");
        headers.set("Cookie", cookies.stream().collect(Collectors.joining(";")));



        user = new User(3L, "James", "Brown", (byte)66 );
        HttpEntity<User> requestEntityAdd = new HttpEntity<>(user, headers);
        addUsers(requestEntityAdd);



        user.setName("Thomas");
        user.setLastName("Shelby");
        HttpEntity<User> requestEntityUpdate = new HttpEntity<>(user, headers);
        updateUsers(requestEntityUpdate);
        deleteUsers(requestEntityUpdate);

    }

    public static void addUsers(HttpEntity<User> requestEntity) {


        ResponseEntity<String> responseEntity = restTemplate
                .exchange(url, HttpMethod.POST, requestEntity, String.class);


        String resultAdd = String.valueOf(responseEntity.getBody());
        System.out.println(resultAdd);

    }

    public static void updateUsers(HttpEntity<User> requestEntity) {
        ResponseEntity<String> responseEntity = restTemplate
                .exchange(url, HttpMethod.PUT, requestEntity, String.class);
        String resultUpdate = String.valueOf(responseEntity.getBody());
        System.out.println(resultUpdate);
    }

    public static void deleteUsers (HttpEntity<User> requestEntity) {
        ResponseEntity<String> responseEntity = restTemplate
                .exchange(url + "/" + user.getId(), HttpMethod.DELETE, requestEntity, String.class);
        String resultDelete = String.valueOf(responseEntity.getBody());
        System.out.println(resultDelete);
    }


}
