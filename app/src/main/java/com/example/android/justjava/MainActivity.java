package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    int quantity = 1;
    double coffeePrice = 3.45;
    double whippedCreamPrice = .5;
    double chocolatePrice = .75;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //Decrement quantity
    public void decrementQuantity(View view) {
        if (quantity > 1) {
            quantity -= 1;
        } else {
            quantity = 1;
            Toast.makeText(this, "You can't order less coffee.", Toast.LENGTH_SHORT).show();
        }

        displayQuantity();
    }

    //Increment quantity
    public void incrementQuantity(View view) {
        quantity += 1;
        displayQuantity();
    }

    //Submit Order
    public void submitOrder(View view) {
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whippedCream_checkbox);
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        String whippedCream = "No";
        String chocolate = "No";

        double result = quantity * coffeePrice;
        if (whippedCreamCheckBox.isChecked()) {
            result += whippedCreamPrice;
            whippedCream = "Yes";
        }
        if (chocolateCheckBox.isChecked()) {
            result += chocolatePrice;
            chocolate = "Yes";
        }

        displayOrder(result, whippedCream, chocolate);
    }

    //Launch Map
    public void launchMap(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("geo:47.6, -122.3"));

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    //Launch Email
    public void launchEmail(View view) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        TextView orderSummary = (TextView) findViewById(R.id.orderSummary_textview);
        String orderSummaryString = orderSummary.getText().toString();

        if (orderSummaryString.length() > 0) {
            intent.setData(Uri.parse("mailto:"));
            intent.putExtra(Intent.EXTRA_EMAIL, "richardl890@gmail.com");
            intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java Order Summary.");
            intent.putExtra(Intent.EXTRA_TEXT, orderSummaryString);

           if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        } else {
            Toast.makeText(this, "There is no Order Summary to send.", Toast.LENGTH_SHORT).show();
        }
    }

    //Display Quantity
    private void displayQuantity() {
        TextView quantityTextView = (TextView) findViewById(R.id.textview_quantity);
        quantityTextView.setText(String.valueOf(quantity));
    }

    //Display Total Order
    private void displayOrder(double total, String whippedCream, String chocolate) {
        TextView totalTextView = (TextView) findViewById(R.id.orderSummary_textview);
        EditText nameEditText = (EditText) findViewById(R.id.name_edittext);

        NumberFormat currency = NumberFormat.getCurrencyInstance(Locale.getDefault());
        StringBuilder message = new StringBuilder();

        String nameString = nameEditText.getText().toString();
        String nameHeader = getResources().getString(R.string.name);
        String quantityHeader = getResources().getString(R.string.quantity);
        String totalHeader = getResources().getString(R.string.total);
        String thankyouHeader = getResources().getString(R.string.thankyou);

        message.append(nameHeader + ": " + nameString);
        message.append("\n\n" + quantityHeader + ": " + quantity);
        message.append("\n\nWhipped Cream: " + whippedCream);
        message.append("\n\nChocolate: " + chocolate);
        message.append("\n\n" + totalHeader + ": " + currency.format(total) + ".");
        message.append("\n\n" + thankyouHeader);

        totalTextView.setText(message);
    }

}