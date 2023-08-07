package com.example.momstouchapps;

import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CALL_PERMISSION = 1;

    String phoneNumber = "081901904779";
    String location = "Tanggulmas Timur XI/482, Panggung Lor, Semarang";
    private static final int REQUEST_CALL_PHONE = 1;
    private static final int REQUEST_LOCATION_PERMISSION = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton buttonEmail = findViewById(R.id.email);
        buttonEmail.setOnClickListener(view -> email());

        ImageButton buttonCall = findViewById(R.id.call);
        buttonCall.setOnClickListener(view -> dial(phoneNumber));

        ImageButton buttonLocation = findViewById(R.id.location);
        buttonLocation.setOnClickListener(view -> requestLocationPermissionAndShowLocation());
    }

    @SuppressLint("QueryPermissionsNeeded")
    private void ShowLocation() {
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(location));
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);

        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        } else {
            // Fallback: Open the location in a web browser
            String mapUrl = "https://www.google.com/maps/search/?api=1&query=" + Uri.encode(location);
            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mapUrl));
            startActivity(webIntent);
        }
    }

    private void requestLocationPermissionAndShowLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Permission already granted, show the location
            ShowLocation();
        } else {
            // Permission not granted, request it
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
        }
    }
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, show the location
                ShowLocation();
            } else {
                // Permission denied, handle accordingly (e.g., show a message or do nothing)
                Toast.makeText(this, "Location permission denied.", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void email() {
        String recipientEmail = "haslingingslicer@gmail.com";
        String subject = "";
        String body = "";

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:" + recipientEmail));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, body);

        if (emailIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(emailIntent);
        }
    }

    private void dial(String phoneNumber) {
        Intent dialIntent = new Intent(Intent.ACTION_DIAL);
        dialIntent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(dialIntent);
    }

}
