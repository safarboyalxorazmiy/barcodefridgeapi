package uz.premier.fridge;

import lombok.RequiredArgsConstructor;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Calendar;

@Service
@RequiredArgsConstructor
public class FridgeService {
    private final FridgeRepository fridgeRepository;

    public Boolean create(FridgeCreateDTO createDTO) {
        FridgeEntity fridgeEntity = new FridgeEntity();
        fridgeEntity.setType(createDTO.getType());
        fridgeEntity.setColor(createDTO.getColor());
        fridgeEntity.setModel(createDTO.getModel());
        fridgeEntity.setOrientation(createDTO.getOrientation());

        String seriya = "A1";
        if (createDTO.getOrientation().equals(FridgeOrientation.LOCAL)) {
            seriya += "F";
        } else {
            seriya += "E";
        }

        if (createDTO.getModel().equals(FridgeModel.PRM211)) {
            seriya += "A";
        } else if (createDTO.getModel().equals(FridgeModel.PRM261)) {
            seriya += "B";
        } else {
            seriya += "C";
        }

        if (createDTO.getModel().equals(FridgeColor.white) && createDTO.getNickel()) {
            seriya += "05";
        } else if (createDTO.getModel().equals(FridgeColor.white)) {
            seriya += "01";
        } else if (createDTO.getColor().equals(FridgeColor.black) && createDTO.getNickel()) {
            seriya += "06";
        } else if (createDTO.getColor().equals(FridgeColor.black)) {
            seriya += "02";
        } else if (createDTO.getColor().equals(FridgeColor.INOX1)) {
            seriya += "04";
        } else if (createDTO.getColor().equals(FridgeColor.INOX2)) {
            seriya += "07";
        } else if (createDTO.getColor().equals(FridgeColor.INOX)) {
            seriya += "03";
        }


        LocalDate currentDate = LocalDate.now();
        int monthNumber = currentDate.getMonthValue();

        if (monthNumber > 9) {
            if (monthNumber == 10) {
                seriya += "A";
            } else if (monthNumber == 11) {
                seriya += "B";
            } else {
                seriya += "C";
            }
        } else {
            seriya += monthNumber;
        }

        Calendar calendar = Calendar.getInstance();
        int currentDayNumber = calendar.get(Calendar.DAY_OF_MONTH);

        if (currentDayNumber > 9 && currentDayNumber < 31) {
            char startLetter = 'A';
            char currentLetter = (char) (startLetter + currentDayNumber - 10);
            seriya += currentLetter;
        } else {
            seriya += "X";
        }


        int currentYear = calendar.get(Calendar.YEAR);
        int baseYear = 2023;
        char startLetter = 'F';

        int difference = currentYear - baseYear;

        if (difference >= 0 && difference < 26) {
            char currentLetter = (char) (startLetter + difference);
            seriya += currentLetter;
        } else {}

        Long id = fridgeRepository.countByColor(createDTO.getColor()) + 1;
        String paddedNumber = String.format("%04d", id);

        fridgeEntity.setSeriya(seriya + paddedNumber);

        System.out.println(seriya + paddedNumber);
        fridgeRepository.save(fridgeEntity);

        sendRequest(paddedNumber);
        return true;
    }

    private void sendRequest(String paddedNumber) {
        String url = "http://api.example.com:1111/printer/print";

        // The request body
        String requestBody = "{\"password\": \"Grechka4kg$\", \"orderId\": \""+ paddedNumber +"\"}";

        // Create an HttpClient instance
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // Create the HTTP POST request
            HttpPost httpPost = new HttpPost(url);

            // Set the request body
            StringEntity requestEntity = new StringEntity(requestBody, ContentType.APPLICATION_JSON);
            httpPost.setEntity(requestEntity);

            // Execute the request and get the response
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                // Get the response status code
                int statusCode = response.getStatusLine().getStatusCode();
                System.out.println("Response Code: " + statusCode);

                // Get the response body
                HttpEntity responseEntity = response.getEntity();
                String responseBody = EntityUtils.toString(responseEntity);
                System.out.println("Response Body: " + responseBody);

                // Consume the response entity to release resources
                EntityUtils.consume(responseEntity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}