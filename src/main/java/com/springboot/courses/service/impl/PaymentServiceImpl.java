package com.springboot.courses.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.courses.entity.Courses;
import com.springboot.courses.entity.User;
import com.springboot.courses.exception.ResourceNotFoundException;
import com.springboot.courses.payload.payment.*;
import com.springboot.courses.repository.CoursesRepository;
import com.springboot.courses.repository.UserRepository;
import com.springboot.courses.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Base64;

@Service
public class PaymentServiceImpl implements PaymentService {
    final String BANK_NUMBER = "90813535314";
    final String ACCOUNT_NAME = "PHẠM NGỌC VIỄN ĐÔNG";
    final String BANK_BRANCH = "TPBANK TP.HCM";
    private String tokenBank;
    private LocalDateTime lastLoginTime;

    @Autowired
    private CoursesRepository coursesRepository;
    @Autowired
    private UserRepository userRepository;


    @Override
    public PaymentResponse getPaymentInfo(PaymentRequest paymentRequest) {
        Courses course = coursesRepository.findById(paymentRequest.getCourseId()).orElseThrow(() -> new ResourceNotFoundException("Course", "id", paymentRequest.getCourseId()));
        User user = userRepository.findByEmail(paymentRequest.getEmail()).orElseThrow(() -> new ResourceNotFoundException("User", "email", paymentRequest.getEmail()));

        String content = String.format("TC%02d%02d", user.getId(), course.getId());
        int amount = (int) (course.getPrice() - (course.getPrice() * course.getDiscount()));

        String qrCode = String.format("https://img.vietqr.io/image/970423-%s-qr_only.png?amount=%d&addInfo=%s&accountName=%s", BANK_NUMBER, amount, content, ACCOUNT_NAME);

        return PaymentResponse.builder().qrCode(qrCode).bankNumber(BANK_NUMBER).content(content).accountName(ACCOUNT_NAME).bankBranch(BANK_BRANCH).build();
    }

    // Login tpbank
    private String login() {
        String url = "https://ebank.tpb.vn/gateway/api/auth/login";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json, text/plain, */*");
        headers.set("Accept-Encoding", "gzip, deflate, br, zstd");
        headers.set("Accept-Language", "en-US,en;q=0.9,vi;q=0.8");
        headers.set("Device_id", "PDwAAkeiJplpQ2g2BEAvo8pQSdLAsry1xD9wS5w1YMGiS");
        headers.set("Referer", "https://ebank.tpb.vn");
        headers.set("Sec-Ch-Ua", "\"Microsoft Edge\";v=\"123\", \"Not:A-Brand\";v=\"8\", \"Chromium\";v=\"123\"");
        headers.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36 Edg/123.0.0.0");

        String un = new String(Base64.getDecoder().decode("MDgxMzUzNTMxNA=="));
        String pw = new String(Base64.getDecoder().decode("RG9uZ3BoLjA1MDJAZ21haWwuY29t"));

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("deviceId", "PDwAAkeiJplpQ2g2BEAvo8pQSdLAsry1xD9wS5w1YMGiS");
        requestBody.add("password", pw);
        requestBody.add("step_2FA", "VERIFY");
        requestBody.add("username", un);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestEntity, String.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            String jsonResponse = responseEntity.getBody();
            String accessToken = parseAccessToken(jsonResponse);

            return accessToken;
        } else {
            System.err.println("Failed to login. Status code: " + responseEntity.getStatusCode());
            return null;
        }
    }

    // Lấy token của tpbank
    private String parseAccessToken(String jsonResponse) {
        int startIndex = jsonResponse.indexOf("\"access_token\":") + 16;
        int endIndex = jsonResponse.indexOf("\"", startIndex);

        if (endIndex != -1) {
            return jsonResponse.substring(startIndex, endIndex);
        }

        return null;
    }

    private TransactionInfo[] getTransactions() {

        // Vì sao phải check? vì khi login nhiều quá sẽ bị block ip nên phải kiểm tra khi nào token hết hạn thì gọi token mới
        if (tokenBank == null || lastLoginTime == null || lastLoginTime.plusMinutes(15).isBefore(LocalDateTime.now())) {
            tokenBank = login(); // Gọi hàm login() để lấy token mới
            lastLoginTime = LocalDateTime.now();
        }
        String token = tokenBank;
        String url = "https://ebank.tpb.vn/gateway/api/smart-search-presentation-service/v2/account-transactions/find";
        LocalDate date = LocalDate.now();
        // Lấy ra ngày giờ hiện tại
        String toDate = String.format("%d%02d%02d", date.getYear(), date.getMonthValue(), date.getDayOfMonth());

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json, text/plain, */*");
        headers.set("Accept-Encoding", "gzip, deflate, br, zstd");
        headers.set("Accept-Language", "en-US,en;q=0.9,vi;q=0.8");
        headers.set("Device_id", "PDwAAkeiJplpQ2g2BEAvo8pQSdLAsry1xD9wS5w1YMGiS");
        headers.set("Referer", "https://ebank.tpb.vn");
        headers.set("Sec-Ch-Ua", "\"Microsoft Edge\";v=\"123\", \"Not:A-Brand\";v=\"8\", \"Chromium\";v=\"123\"");
        headers.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36 Edg/123.0.0.0");
        headers.set("Authorization", "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setAccountNo("90813535314");
        transactionRequest.setCurrency("VND");
        transactionRequest.setFromDate("20240118");
        transactionRequest.setKeyword("");
        transactionRequest.setMaxAcentrysrno("");
        transactionRequest.setPageNumber(1);
        transactionRequest.setPageSize(400);
        transactionRequest.setToDate(toDate);

        // Chuyển đổi đối tượng Java thành JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody;
        try {
            requestBody = objectMapper.writeValueAsString(transactionRequest);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<?> responseEntity = restTemplate.postForEntity(url, requestEntity, TransactionResponse.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            TransactionResponse transactionResponse = (TransactionResponse) responseEntity.getBody();
            return transactionResponse.getTransactionInfos();
        } else {
            System.err.println("Failed to login. Status code: " + responseEntity.getStatusCode());
        }
        return null;
    }
}
