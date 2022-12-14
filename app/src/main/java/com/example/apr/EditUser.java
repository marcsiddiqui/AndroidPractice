package com.example.apr;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

public class EditUser extends AppCompatActivity {

    EditText txt_FirstName, txt_LastName, txt_Email, txt_Username, txt_Password, txt_ConfirmPassword, txt_UserType;
    TextView error_firstName, error_lastName, error_email, error_username, error_password, error_confirmPassword, error_userType;
    Button btnSaveChanges, btnDeleteUser;
    ImageView imgProfile;
    DatabaseConfiguration databaseConfiguration;

    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private Button btnSelect;
    private String userChoosenTask;
    byte[] profileBytes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        txt_FirstName = (EditText) findViewById(R.id.txt_FirstName);
        txt_LastName = (EditText) findViewById(R.id.txt_LastName);
        txt_Email = (EditText) findViewById(R.id.txt_Email);
        txt_Username = (EditText) findViewById(R.id.txt_Username);
        txt_Password = (EditText) findViewById(R.id.txt_Password);
        txt_ConfirmPassword = (EditText) findViewById(R.id.txt_ConfirmPassword);
        txt_UserType = (EditText) findViewById(R.id.txt_UserType);

        error_firstName = (TextView) findViewById(R.id.error_firstName);
        error_lastName = (TextView) findViewById(R.id.error_lastName);
        error_email = (TextView) findViewById(R.id.error_email);
        error_username = (TextView) findViewById(R.id.error_username);
        error_password = (TextView) findViewById(R.id.error_password);
        error_confirmPassword = (TextView) findViewById(R.id.error_confirmPassword);
        error_userType = (TextView) findViewById(R.id.error_userType);

        btnSaveChanges = (Button) findViewById(R.id.btnSaveChanges);
        btnDeleteUser = (Button) findViewById(R.id.btnDeleteUser);

        imgProfile = (ImageView) findViewById(R.id.imgProfile);

        databaseConfiguration = new DatabaseConfiguration(this);

        int userId = getIntent().getIntExtra("UserEditId", 0);

        if (userId == 0){
            // Explicit
            Intent obj = new Intent(getApplicationContext(), ActUserList.class);
            startActivity(obj);
        }
        else {
            User user = databaseConfiguration.GetUserById(userId);
            if (user == null){
                // Explicit
                Intent obj = new Intent(getApplicationContext(), ActUserList.class);
                startActivity(obj);
            }
            else {
                txt_FirstName.setText(user.FirstName);
                txt_LastName.setText(user.LastName);
                txt_Email.setText(user.Email);
                txt_Username.setText(user.Username);
                txt_Password.setText(user.Password);
                txt_ConfirmPassword.setText(user.Password);
                txt_UserType.setText(Integer.toString(user.UserType));

                Bitmap bmp= BitmapFactory.decodeByteArray(user.ProfileImage, 0 , user.ProfileImage.length);
                imgProfile.setImageBitmap(bmp);
            }
        }

        btnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String firstNameTxt = txt_FirstName.getText().toString();
                String lastNameTxt = txt_LastName.getText().toString();
                String emailTxt = txt_Email.getText().toString();
                String usernameTxt = txt_Username.getText().toString();
                String passwordTxt = txt_Password.getText().toString();
                String confirmPasswordTxt = txt_ConfirmPassword.getText().toString();
                int userType = Integer.parseInt(txt_UserType.getText().toString());

                if (firstNameTxt.equals("") || firstNameTxt == null){
                    error_firstName.setText("First Name is required!");
                }
                else {
                    error_firstName.setText("");
                }
                if (lastNameTxt.equals("") || lastNameTxt == null){
                    error_lastName.setText("Last Name is required!");
                }
                else {
                    error_lastName.setText("");
                }
                if (emailTxt.equals("") || emailTxt == null){
                    error_email.setText("Email is required!");
                }
                else {
                    error_email.setText("");
                }
                if (usernameTxt.equals("") || usernameTxt == null){
                    error_username.setText("Username is required!");
                }
                else {
                    error_username.setText("");
                }
                if (passwordTxt.equals("") || passwordTxt == null){
                    error_password.setText("Password is required!");
                }
                else {
                    error_password.setText("");
                }
                if (confirmPasswordTxt.equals("") || confirmPasswordTxt == null){
                    error_confirmPassword.setText("Confirm Passowrd is required!");
                }
                else {
                    error_confirmPassword.setText("");
                }
                if (!passwordTxt.equals(confirmPasswordTxt)){
                    error_confirmPassword.setText("Passowrd does not match!");
                }
                else {
                    error_confirmPassword.setText("");
                }
                if (userType < 1 && userType > 2){
                    error_userType.setText("Invalid User Type!");
                }
                else {
                    error_userType.setText("");
                }

                if (
                        firstNameTxt.equals("") || firstNameTxt == null ||
                                lastNameTxt.equals("") || lastNameTxt == null ||
                                emailTxt.equals("") || emailTxt == null ||
                                usernameTxt.equals("") || usernameTxt == null ||
                                passwordTxt.equals("") || passwordTxt == null ||
                                confirmPasswordTxt.equals("") || confirmPasswordTxt == null ||
                                !passwordTxt.equals(confirmPasswordTxt) ||
                                (userType < 1 && userType > 2)
                ) {

                }
                else {
                    User user = new User();
                    user.Id = userId;
                    user.FirstName = firstNameTxt;
                    user.LastName = lastNameTxt;
                    user.Email = emailTxt;
                    user.Username = usernameTxt;
                    user.Password = passwordTxt;
                    //Date date = new Date();
                    //user.CreatedOn = date;
                    user.UserType = userType;
//                    user.ProfileImage = UriToImageBytes(imgProfile);
                    user.ProfileImage = profileBytes;


                    if (databaseConfiguration.Update(user)){
                        Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnDeleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseConfiguration.DeleteUser(userId);
                // Explicit
                Intent obj = new Intent(getApplicationContext(), ActUserList.class);
                startActivity(obj);
            }
        });

        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
    }

    private boolean CheckCameraPermission() {
        boolean check1 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED;
        boolean check2 = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED;
        return check1 && check2;
    }

    private void RequestCameraPermission() {
        requestPermissions(new String[] { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 100);
    }

    private boolean CheckStoragePermission() {
        boolean check1 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED;
        return check1;
    }

    private void RequestStoragePermission() {
        requestPermissions(new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE }, 100);
    }

    private void PickImage() {
//        // start picker to get image for cropping and then use the image in cropping activity
//        CropImage.activity()
//                .setGuidelines(CropImageView.Guidelines.ON)
//                .start(this);
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK) {
//            Uri selectedImageUri = data.getData();
//            try {
//                InputStream stream = getContentResolver().openInputStream(selectedImageUri);
//                Bitmap bitmap = BitmapFactory.decodeStream(stream);
//                imgProfile.setImageBitmap(bitmap);
//            }
//            catch (Exception e){
//                e.printStackTrace();;
//            }
//        }
//    }

    public byte[] UriToImageBytes(ImageView imageView){
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] bytes = stream.toByteArray();
        return bytes;
    }

    public void BitmapToBytes(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        profileBytes = stream.toByteArray();
    }

    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(EditUser.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result=GenericFunctions.checkPermission(EditUser.this);

                if (items[item].equals("Take Photo")) {
                    userChoosenTask ="Take Photo";
                    if(result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask ="Choose from Library";
                    if(result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }

    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case GenericFunctions.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if (userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        imgProfile.setImageBitmap(thumbnail);
        BitmapToBytes(thumbnail);
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        imgProfile.setImageBitmap(bm);
        BitmapToBytes(bm);
    }
}