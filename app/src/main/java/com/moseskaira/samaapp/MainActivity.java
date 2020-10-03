package com.moseskaira.samaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etPrice;
    private EditText etDesc;
    private EditText etId;
    private EditText etIdUpdate,etPriceUpdate;
    private EditText etIdDelete;
    private TextView tvName, tvPrice, tvDesc;

    //replace the ip addresses in following urls with yours
    public static final String URL_ADD_PROD = "http://192.168.43.117/SamaMobile/create_product.php";
    public static final String URL_SHOW_PROD = "http://192.168.43.117/SamaMobile/get_product_details.php";
    public static final String URL_UPDT_PROD = "http://192.168.43.117/SamaMobile/update_price.php";
    public static final String URL_DELETE_PROD = "http://192.168.43.117/SamaMobile/delete_product.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.name);
        etPrice = findViewById(R.id.price);
        etDesc = findViewById(R.id.desc);
        etId = findViewById(R.id.id);
        etIdUpdate = findViewById(R.id.id_update);
        etPriceUpdate = findViewById(R.id.price_update);
        etIdDelete = findViewById(R.id.id_delete);

        tvName = findViewById(R.id.showname);
        tvPrice = findViewById(R.id.showprice);
        tvDesc = findViewById(R.id.showdesc);
    }

    //Add Product
    public void add_prod(View view){
        final String name = etName.getText().toString();
        final String price = etPrice.getText().toString();
        final String desc = etDesc.getText().toString();

        class Product extends AsyncTask<Void, Void, String>{

            ProgressDialog pdLoading = new ProgressDialog(MainActivity.this);

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                //this method will be running on UI thread
                pdLoading.setMessage("\tLoading...");
                pdLoading.setCancelable(false);
                pdLoading.show();
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("price", price);
                params.put("description", desc);

                //returing the response
                return requestHandler.sendPostRequest(URL_ADD_PROD, params);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                pdLoading.dismiss();

                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);
                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Exception: "+e, Toast.LENGTH_LONG).show();
                }
            }

        }

        Product prod_exec = new Product();
        prod_exec.execute();
    }

    //Show Product
    public void show_prod(View view){
        final String id = etId.getText().toString();

        class show_prod extends AsyncTask<Void, Void, String>{

            ProgressDialog pdLoading = new ProgressDialog(MainActivity.this);

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                //this method will be running on UI thread
                pdLoading.setMessage("\tLoading...");
                pdLoading.setCancelable(false);
                pdLoading.show();
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("id", id);

                //returing the response
                return requestHandler.sendPostRequest(URL_SHOW_PROD, params);
            }

            @Override
            protected void onPostExecute(String s){
                super.onPostExecute(s);
                pdLoading.dismiss();

                try{
                    //Converting response to JSON Object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")){
                        Toast.makeText(MainActivity.this, obj.getString("message"), Toast.LENGTH_LONG).show();
                        //Make TextViews Visible
                        tvName.setVisibility(View.VISIBLE);
                        tvPrice.setVisibility(View.VISIBLE);
                        tvDesc.setVisibility(View.VISIBLE);
                        //Set retrieved text to TextViews
                        tvName.setText("Name: "+obj.getString("name"));
                        tvPrice.setText("Price: "+obj.getString("price"));
                        tvDesc.setText("Description: "+obj.getString("description"));
                    }
                } catch (Exception e ){
                    Toast.makeText(MainActivity.this, "Exception: "+e, Toast.LENGTH_SHORT).show();
                }
            }
        }

        show_prod show = new show_prod();
        show.execute();
    }

    //Update Price
    public void update_price(View view){
        final String id = etIdUpdate.getText().toString();
        final String price = etPriceUpdate.getText().toString();

        class Update extends AsyncTask<Void, Void, String>{
            ProgressDialog pdLoading = new ProgressDialog(MainActivity.this);

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                //this method will be running on UI thread
                pdLoading.setMessage("\tLoading...");
                pdLoading.setCancelable(false);
                pdLoading.show();
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("id", id);
                params.put("price",price);

                //returing the response
                return requestHandler.sendPostRequest(URL_UPDT_PROD, params);
            }

            @Override
            protected void onPostExecute(String s){
                super.onPostExecute(s);
                pdLoading.dismiss();

                try{
                    //Converting response to JSON Object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")){
                        Toast.makeText(MainActivity.this, obj.getString("message"), Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e ){
                    Toast.makeText(MainActivity.this, "Exception: "+e, Toast.LENGTH_SHORT).show();
                }
            }
        }
        Update update = new Update();
        update.execute();
    }

    public void delete_product(View view){
        final String id = etIdDelete.getText().toString();

        class Delete extends AsyncTask<Void, Void, String>{
            ProgressDialog pdLoading = new ProgressDialog(MainActivity.this);

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                //this method will be running on UI thread
                pdLoading.setMessage("\tLoading...");
                pdLoading.setCancelable(false);
                pdLoading.show();
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("id", id);

                //returing the response
                return requestHandler.sendPostRequest(URL_DELETE_PROD, params);
            }

            @Override
            protected void onPostExecute(String s){
                super.onPostExecute(s);
                pdLoading.dismiss();

                try{
                    //Converting response to JSON Object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")){
                        Toast.makeText(MainActivity.this, obj.getString("message"), Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e ){
                    Toast.makeText(MainActivity.this, "Exception: "+e, Toast.LENGTH_SHORT).show();
                }
            }
        }
        Delete delete = new Delete();
        delete.execute();
    }
}