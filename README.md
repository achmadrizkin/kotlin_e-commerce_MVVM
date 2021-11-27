## Kotlin E-Commerce MVVM
Make E-Commerce using Kotlin, and for backend i use Golang, and PHP

## Feature
- MVVM
- Retrofit2
- 15+ Screen
- Payment Gateway (Razorpay & Midtrans)
- Firebase Auth (Sign in with email & Google Sign in)
- Live Data
- Network API 
- Picasso
- Shared Preference
- CRUD API
- Splash Screen, OnBoarding Screen, etc ... 

## Please change this, if not it will ERROR
use your own apiKey

      object Constant {
          const val MERCHAT_ID_MIDTRANS = "YOUR_API_KEY"
          const val CLIENT_KEY_MIDTRANS = "SB-Mid-client-YOUR_API_KEY"
          const val SERVER_KEY_MIDTRANS = "SB-Mid-server-YOUR_API_KEY"
          const val BASE_URL_MIDTRANS = "PHP API SERVER"

          const val KEY_ID_RAZORAPP = "rzp_test_YOUR_API_KEY";
          const val KEY_SECRET_RAZORAPP = "YOUR_API_KEY";
      }

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
<img src="https://user-images.githubusercontent.com/75843138/143670715-d5de2b24-e34d-4dab-9d7d-71a3736c0a0f.png"> |<img src="https://user-images.githubusercontent.com/75843138/143670872-59bb44fb-d3de-43ec-ac5a-520495d9cc48.png"> |<img src="https://user-images.githubusercontent.com/75843138/143670977-66dd0b00-a27c-4bb6-9e85-311970cd9645.png">


  On Boarding Screen 3                 |   Login Screen        |  Transaction Empty Screen
:-------------------------:|:-------------------------:|:-------------------------:
<img src="https://user-images.githubusercontent.com/75843138/143671040-e8429426-65d0-4ca9-b31f-9f1399a0b381.png"> |<img src="https://user-images.githubusercontent.com/75843138/143671069-46fc4861-e695-43a1-aa05-7728effa1583.png"> |<img src="https://user-images.githubusercontent.com/75843138/143671340-b823fe74-8713-4583-b85c-63f0052f7fda.png">

  Empty My Product  Screen               |   Profile Screen        |  Product details 1
:-------------------------:|:-------------------------:|:-------------------------:
<img src="https://user-images.githubusercontent.com/75843138/143671369-53f7652b-cf08-4c59-8057-b5946a8496c6.png"> |<img src="https://user-images.githubusercontent.com/75843138/143671417-cc30828e-557f-4f98-bb4a-240d3b6e18ac.png"> |<img src="https://user-images.githubusercontent.com/75843138/143671555-9074bc41-e195-4947-8385-0c0c12dd586e.png">

  Add/Edit Product  Screen               |   My Product  Screen        |  Home Screen 1
:-------------------------:|:-------------------------:|:-------------------------:
<img src="https://user-images.githubusercontent.com/75843138/143671734-6e588836-1c8a-4716-9e00-1b008c61efb1.png"> |<img src="https://user-images.githubusercontent.com/75843138/143671816-55d94b5b-306c-4f0f-be45-edfc1dbc843c.png"> |<img src="https://user-images.githubusercontent.com/75843138/143671914-e9aded43-1063-4454-80d5-6f13fb6ef2b3.png">

  Home Screen 2               |   Product Details  Screen        |  Midtrans
:-------------------------:|:-------------------------:|:-------------------------:
<img src="https://user-images.githubusercontent.com/75843138/143671972-f511d313-40e9-4ad4-a512-d640d2296471.png"> |<img src="https://user-images.githubusercontent.com/75843138/143672021-404eb90d-36ed-4048-8927-399d10ef6a65.png"> |<img src="https://user-images.githubusercontent.com/75843138/143672067-8c018c60-1d5a-44fc-a53d-119468269d18.png">

  Razorpay               |   Transaction Screen        |  User Profile (if name, and image null)
:-------------------------:|:-------------------------:|:-------------------------:
<img src="https://user-images.githubusercontent.com/75843138/143672116-47c01c61-5fe5-4127-97d2-e3dd508c6fee.png"> |<img src="https://user-images.githubusercontent.com/75843138/143672194-a442c041-ea72-409b-8aa7-a1e949672c2a.png"> |<img src="https://user-images.githubusercontent.com/75843138/143672224-cce20d01-865b-4dcd-be5e-59fbe37bb756.png">
