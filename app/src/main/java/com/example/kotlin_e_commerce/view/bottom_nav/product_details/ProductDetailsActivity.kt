package com.example.kotlin_e_commerce.view.bottom_nav.product_details

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.kotlin_e_commerce.R
import com.example.kotlin_e_commerce.model.Product
import com.example.kotlin_e_commerce.model.Transaction
import com.example.kotlin_e_commerce.utils.Constant.BASE_URL_MIDTRANS
import com.example.kotlin_e_commerce.utils.Constant.CLIENT_KEY_MIDTRANS
import com.example.kotlin_e_commerce.view.bottom_nav.BottomNavActivity
import com.example.kotlin_e_commerce.view.bottom_nav.my_products.MyProductActivity
import com.example.kotlin_e_commerce.view.edit_products.EditProductsActivity
import com.example.kotlin_e_commerce.view_model.HomeFragmentViewModel
import com.example.kotlin_e_commerce.view_model.ProductDetailsViewModel
import com.google.firebase.auth.FirebaseAuth
import com.midtrans.sdk.corekit.callback.TransactionFinishedCallback
import com.midtrans.sdk.corekit.core.MidtransSDK
import com.midtrans.sdk.corekit.core.TransactionRequest
import com.midtrans.sdk.corekit.core.themes.CustomColorTheme
import com.midtrans.sdk.corekit.models.BillingAddress
import com.midtrans.sdk.corekit.models.CustomerDetails
import com.midtrans.sdk.corekit.models.ShippingAddress
import com.midtrans.sdk.corekit.models.snap.TransactionResult
import com.midtrans.sdk.uikit.SdkUIFlowBuilder
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_product_details.*
import org.json.JSONObject

class ProductDetailsActivity : AppCompatActivity(), PaymentResultListener {
    private lateinit var viewModel: HomeFragmentViewModel
    private lateinit var viewModelDelete: ProductDetailsViewModel

    private var transactionResult = TransactionResult()
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)

        //
        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser
        var nameUser = "";

        if (currentUser?.displayName != null) {
            nameUser = currentUser.displayName!!
        }

        if (currentUser?.displayName == null || currentUser.displayName!! == "") {
            nameUser = "Anonymous"
        }

        //
        initViewModel()
        initViewModel2()

        //
        createTransactionObservable()

        //
        val name_product = intent.getStringExtra("name_product")
        val image_url = intent.getStringExtra("image_url")
        val description = intent.getStringExtra("description")
        val price = intent.getStringExtra("price")
        val name_user = intent.getStringExtra("name_user")
        val email_user = intent.getStringExtra("email_user")

        // Razorpay
        Checkout.preload(applicationContext)

        //
        SdkUIFlowBuilder.init().setClientKey(CLIENT_KEY_MIDTRANS).setContext(applicationContext)
            .setTransactionFinishedCallback(
                TransactionFinishedCallback {
                    if (TransactionResult.STATUS_SUCCESS == "success") {
                        Toast.makeText(this, "Success transaction", Toast.LENGTH_LONG).show()

                        //TODO: WATCH THIS CODE
                        // ERROR FROM LIBRARY -_- (if u close the payment, this code will run)
                        createTransaction(
                            name_product!!,
                            image_url!!,
                            description!!,
                            price!!,
                            name_user!!,
                            email_user!!,
                            nameUser,
                            currentUser?.email!!
                        )
                    } else if (TransactionResult.STATUS_PENDING == "pending") {
                        Toast.makeText(this, "Pending transaction", Toast.LENGTH_LONG).show()
                    } else if (TransactionResult.STATUS_FAILED == "failed") {
                        Toast.makeText(
                            this,
                            "Failed ${transactionResult.response.statusMessage}",
                            Toast.LENGTH_LONG
                        ).show()
                    } else if (transactionResult.status.equals(
                            TransactionResult.STATUS_INVALID,
                            true
                        )
                    ) {
                        Toast.makeText(this, "Invalid transaction", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this, "Failure transaction", Toast.LENGTH_LONG).show()
                    }
                }).setMerchantBaseUrl(BASE_URL_MIDTRANS).enableLog(true).setColorTheme(
                CustomColorTheme("#FFE51255", "#B61548", "#FFE51255")
            ).setLanguage("id").buildSDK()


        Picasso.get().load(image_url).into(ivProductDetails)
        tvNameProduct.text = name_product
        tvDescriptionProduct.text = description
        tvPriceProductDetails.text = "Rp. " + price
        tvUserName.text = name_user
        tvUserEmail.text = email_user

        if (email_user == currentUser?.email) {
            btnMidtrans.visibility = View.GONE
            btnRazorpay.visibility = View.GONE

            ivDelete.visibility = View.VISIBLE
            ivEdit.visibility = View.VISIBLE
            tvCannotBuy.visibility = View.VISIBLE
        }

        ivBack.setOnClickListener {
            val intent = Intent(this, BottomNavActivity::class.java)
            startActivity(intent)
        }

        btnMidtrans.setOnClickListener {
            Toast.makeText(this, "Open transaction", Toast.LENGTH_LONG).show()

            val transactionRequest = TransactionRequest(
                "Beep-Midtrans" + System.currentTimeMillis().toString(),
                price!!.toDouble()
            )
            val detail = com.midtrans.sdk.corekit.models.ItemDetails(
                name_product,
                price!!.toDouble(),
                1,
                name_product
            )
            val itemDetails = ArrayList<com.midtrans.sdk.corekit.models.ItemDetails>()

            itemDetails.add(detail)
            uiKitDetails(transactionRequest)
            transactionRequest.itemDetails = itemDetails
            MidtransSDK.getInstance().transactionRequest = transactionRequest
            MidtransSDK.getInstance().startPaymentUiFlow(this)

            TransactionResult.STATUS_SUCCESS
        }

        btnRazorpay.setOnClickListener {
            startPayment(name_product!!, description!!, image_url!!, price!!)
        }

        ivDelete.setOnClickListener {
            deleteProduct(name_product!!, email_user!!, price!!)

            val intent = Intent(this, BottomNavActivity::class.java)
            startActivity(intent)
            finish()
        }

        ivEdit.setOnClickListener {
            val intent = Intent(this, EditProductsActivity::class.java)

            intent.putExtra("name_product", name_product)
            intent.putExtra("image_url", image_url)
            intent.putExtra("description", description)
            intent.putExtra("price", price.toString())

            startActivityForResult(intent, 1000)
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(HomeFragmentViewModel::class.java)
    }

    private fun initViewModel2() {
        viewModelDelete = ViewModelProvider(this).get(ProductDetailsViewModel::class.java)
    }

    private fun createTransactionObservable() {
        viewModel.getCreateTransactionObservable().observe(this, Observer<Transaction> {
            if (it == null) {
                Toast.makeText(
                    this,
                    "Failed to create/update new user",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                Toast.makeText(
                    this,
                    "Success to create/update new user",
                    Toast.LENGTH_LONG
                ).show()
                finish()
            }
        })
    }

    private fun deleteProduct(name_product: String, email_user: String, price: String) {
        viewModelDelete.getDeleteProductObservable().observe(this, Observer<Product> {
            if (it == null) {
                Toast.makeText(this@ProductDetailsActivity, "Failed to delete product", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this@ProductDetailsActivity, "Success to delete product", Toast.LENGTH_LONG).show()
                finish()
            }
        })
        viewModelDelete.deleteProduct(name_product, email_user, price)
    }

    private fun createTransaction(
        name_product: String,
        description: String,
        image_url: String,
        price: String,
        name_user: String,
        email_user: String,
        name_buyer: String,
        name_email: String,
    ) {
        val transaction = Transaction(
            name_product,
            description,
            image_url,
            price.toInt(),
            name_user,
            email_user,
            name_buyer,
            name_email
        )

        viewModel.createTransaction(transaction)
    }

    fun uiKitDetails(transactionRequest: TransactionRequest) {

        val customerDetails = CustomerDetails()
        customerDetails.customerIdentifier = "Supriyanto"
        customerDetails.phone = "082345678999"
        customerDetails.firstName = "Supri"
        customerDetails.lastName = "Yanto"
        customerDetails.email = "supriyanto6543@gmail.com"
        val shippingAddress = ShippingAddress()
        shippingAddress.address = "Baturan, Gantiwarno"
        shippingAddress.city = "Klaten"
        shippingAddress.postalCode = "51193"
        customerDetails.shippingAddress = shippingAddress
        val billingAddress = BillingAddress()
        billingAddress.address = "Baturan, Gantiwarno"
        billingAddress.city = "Klaten"
        billingAddress.postalCode = "51193"

        customerDetails.billingAddress = billingAddress
        transactionRequest.customerDetails = customerDetails
    }

    // RAZORPAY PAYMENT
    private fun startPayment(
        name_product: String,
        description: String,
        image_url: String,
        price: String
    ) {
        val activity: Activity = this
        val co = Checkout()

        try {
            val options = JSONObject()
            options.put("name", name_product)
            options.put("description", description)
            //You can omit the image option to fetch the image from dashboard
            options.put("image", image_url)
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");

            //order_id will come from backend
            //options.put("order_id", "order_DBJOWzybf0sJbb");
            options.put("amount", price.toInt())//pass amount in currency subunits

            val prefill = JSONObject()
            prefill.put("email", "himanshudhimanhr@gmail.com")
            prefill.put("contact", "7009029910")

            options.put("prefill", prefill)
            co.open(activity, options)
        } catch (e: Exception) {
            Toast.makeText(activity, "Error in payment: " + e.message, Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    override fun onPaymentSuccess(p0: String?) {
        Toast.makeText(this, "Payment Success", Toast.LENGTH_LONG).show()

        //TODO: WATCH THIS CODE
        // ERORR from lib too -_- (cannot handle payment)
        createTransaction(
            "name_product!!",
            "image_url!!",
            "description!!",
            "price!!",
            "name_user!!",
            "email_user!!",
            "nameUser",
            "currentUser?.email!!"
        )
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        Toast.makeText(this, "Error in payment: " + p1.toString(), Toast.LENGTH_LONG).show()
    }

    override fun onStop() {
        super.onStop()
        Checkout.clearUserData(this)
    }
}