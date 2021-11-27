## Kotlin E-Commerce MVVM
Make E-Commerce using Kotlin, and for backend i use Golang, and PHP

## Feature
- MVVM
- Retrofit2
- Payment Gateway (Razorpay & Midtrans)
- Firebase Auth (Sign in with email & Google Sign in)
- Live Data
- Network API 
- Picasso
- Shared Preference
- CRUD API
- Splash Screen, OnBoarding Screen, etc ... 

## Golang API
please check this https://github.com/achmadrizkin/golang_e-commerce_API

## Php API (for midtrans payment)
please create, and deploy to your own server

      <?php
      // Set your server key (Note: Server key for sandbox and production mode are different)
      $server_key = 'SB-Mid-server-ADDYOURKEYHERE';
      // Set true for production, set false for sandbox
      $is_production = false;

      $api_url = $is_production ? 
        'https://app.midtrans.com/snap/v1/transactions' : 
        'https://app.sandbox.midtrans.com/snap/v1/transactions';

      // get the HTTP POST body of the request
      $request_body = file_get_contents('php://input');
      // set response's content type as JSON
      header('Content-Type: application/json');
      // call charge API using request body passed by mobile SDK
      $charge_result = chargeAPI($api_url, $server_key, $request_body);
      // set the response http status code
      http_response_code($charge_result['http_code']);
      // then print out the response body
      echo $charge_result['body'];

      /**
       * call charge API using Curl
       * @param string  $api_url
       * @param string  $server_key
       * @param string  $request_body
       */
      function chargeAPI($api_url, $server_key, $request_body){
        $ch = curl_init();
        $curl_options = array(
          CURLOPT_URL => $api_url,
          CURLOPT_RETURNTRANSFER => 1,
          CURLOPT_POST => 1,
          CURLOPT_HEADER => 0,
          // Add header to the request, including Authorization generated from server key
          CURLOPT_HTTPHEADER => array(
            'Content-Type: application/json',
            'Accept: application/json',
            'Authorization: Basic ' . base64_encode($server_key . ':')
          ),
          CURLOPT_POSTFIELDS => $request_body
        );
        curl_setopt_array($ch, $curl_options);
        $result = array(
          'body' => curl_exec($ch),
          'http_code' => curl_getinfo($ch, CURLINFO_HTTP_CODE),
        );
        return $result;
      }
      
      
## Previrew App
  Splash Screen                 |   On Boarding Screen 1       |  On Boarding Screen 2
:-------------------------:|:-------------------------:|:-------------------------:
<img src="https://user-images.githubusercontent.com/75843138/143670715-d5de2b24-e34d-4dab-9d7d-71a3736c0a0f.png" width="850" height="700"> |<img src="https://user-images.githubusercontent.com/75843138/143670872-59bb44fb-d3de-43ec-ac5a-520495d9cc48.png" width="850" height="700"> |<img src="https://user-images.githubusercontent.com/75843138/143670977-66dd0b00-a27c-4bb6-9e85-311970cd9645.png" width="850" height="700">
