package com.example.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //This method is called when the order button is clicked.
    public void submitOrder(View view) {
        CheckBox checkBoxWhipped = findViewById(R.id.checkbox_wipped);
        boolean cbw = checkBoxWhipped.isChecked();
        CheckBox checkBoxHotChoco = findViewById(R.id.checkbox_hotchoco);
        boolean cbhc = checkBoxHotChoco.isChecked();
        CheckBox checkBoxCaramel = findViewById(R.id.checkbox_caramel);
        boolean cbc = checkBoxCaramel.isChecked();
        CheckBox checkBoxCIce = findViewById(R.id.checkbox_icechoco);
        boolean cbci = checkBoxCIce.isChecked();
        CheckBox checkBoxVIce = findViewById(R.id.checkbox_icevanilla);
        boolean cbvi = checkBoxVIce.isChecked();
        EditText name = findViewById(R.id.input_name_view);
        String nm = name.getText().toString();
        double getPrice = calculatePrice(quantity, cbw, cbhc, cbc, cbci, cbvi);
        String getData = orderSummary(cbw, cbhc, cbc, cbci, cbvi, nm, getPrice);
        displayMessage(String.format("%.2f", getPrice));
        composeEmail(getData, nm);
    }

    public void composeEmail(String body, String nm) {
        String[] strArray = {"order@justjava.com"};
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this && */* = text/plain
        intent.putExtra(Intent.EXTRA_EMAIL, strArray);
        intent.putExtra(Intent.EXTRA_SUBJECT,  getString(R.string.order_summ) + " " + nm);
        intent.putExtra(Intent.EXTRA_TEXT, body);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    //Calculate price
    private double calculatePrice(int quantity, boolean cbw, boolean cbhc, boolean cbc, boolean cbci, boolean cbvi) {

        int basePrice = 5;

        if(cbw) {
            basePrice += 1;
        }
        if(cbhc) {
            basePrice += 2;
        }
        if(cbc) {
            basePrice += 3;
        }
        if(cbci) {
            basePrice += 5;
        }
        if(cbvi) {
            basePrice += 5;
        }

        return basePrice * quantity;
    }

    //Order Summary
    private String orderSummary(boolean cbw, boolean cbhc, boolean cbc, boolean cbci, boolean cbvi, String nm, double getPrice) {
        String orderSum = nm + "\n\n";
        orderSum += getString(R.string.add_whipp) + cbw + "\n";
        orderSum += getString(R.string.add_hotchoco) + cbhc + "\n";
        orderSum += getString(R.string.add_cara) + cbc + "\n";
        orderSum += getString(R.string.add_chocoice) + cbci + "\n";
        orderSum += getString(R.string.add_vanillaice) + cbvi + "\n\n";
        orderSum += getString(R.string.qt) +" "+ quantity + "\n\n";
        orderSum += getString(R.string.tot) + " " + String.format("%.2f", getPrice) + "\n\n"+getString(R.string.ty);
        return (orderSum);
    }

    //This method displays the given quantity value on the screen.
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    //This method displays the given text on the screen.
    public void displayMessage(String message) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText("₹ " + message);
    }

    // increment function to add coffee
    public void increment1(View view) {
        if(quantity < 100) {
            quantity += 1;
            display(quantity);
        }
        else {
            quantity = 100;
            Toast.makeText(this,getString(R.string.mqr_toast), Toast.LENGTH_SHORT).show();
            display(quantity);
        }
    }

    //decremrnt funtion to remove coffee
    public void decrement1(View view) {
        if (quantity > 0) {
            quantity -= 1;
            display(quantity);
        } else {
            quantity = 0;
            Toast.makeText(this,getString(R.string.mqr_toast2), Toast.LENGTH_SHORT).show();
            display(quantity);
        }
    }
}
