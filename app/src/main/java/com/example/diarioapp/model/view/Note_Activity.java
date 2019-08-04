package com.example.diarioapp.model.view;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.diarioapp.R;
import com.example.diarioapp.entities.pojo.Note;
import com.example.diarioapp.entities.pojo.Paragraph;
import com.example.diarioapp.entities.pojo.Photo;
import com.example.diarioapp.model.db.AppDataBase;
import com.example.diarioapp.utils.Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Note_Activity extends AppCompatActivity {
    FloatingActionButton fab_add, fab_text, fab_audio, fab_img;
    LinearLayout linearLayout;
    ArrayList<EditText> listEdt;
    ArrayList<Bitmap> listBitmap;
    ArrayList<Photo> listPhoto;
    TextView titleNote;
    Animation fabOpen, fabClose, rotateForward, rotateBackward;
    boolean isOpen = false;
    private static int REQUEST_IMAGE_GALLERY = 100;
    private String title_get;
    private long noteId;
    long resulNoteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        titleNote = findViewById(R.id.titleNote);
        Intent intent = getIntent();
        linearLayout = findViewById(R.id.container_ll);
        listEdt = new ArrayList<>();
        listBitmap = new ArrayList<>();
        listPhoto = new ArrayList<>();
        fabOpen = AnimationUtils.loadAnimation(this, R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(this, R.anim.fab_close);
        rotateForward = AnimationUtils.loadAnimation(this, R.anim.rotate_forward);
        rotateBackward = AnimationUtils.loadAnimation(this, R.anim.rotate_backward);
        Bundle bundle=getIntent().getExtras();
        title_get = bundle.getString("title_note");
        noteId = bundle.getLong("noteId");

        if (!title_get.isEmpty()) {
            titleNote.setText(title_get);
        }

        fab_add = findViewById(R.id.fab_add);
        fab_text = findViewById(R.id.fab_text);
        fab_audio = findViewById(R.id.fab_audio);
        fab_img = findViewById(R.id.fab_img);

        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleAnimation();
            }
        });
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            fab_text.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.celeste)));
            fab_audio.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.naranja)));
            fab_img.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.bground)));
        }

        fab_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View constraintLayout = layoutInflater.inflate(R.layout.each_paragraph_to_write, null);
                LinearLayout container = constraintLayout.findViewById(R.id.container);

                EditText editText = new EditText(getApplicationContext());
                editText.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                editText.setBackground(null);
                editText.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                editText.setPadding(35, 30, 35, 30);

                listEdt.add(editText);
                container.addView(editText);
                linearLayout.addView(constraintLayout);
                editText.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                fab_audio.startAnimation(fabClose);
                fab_img.startAnimation(fabClose);
                fab_text.startAnimation(fabClose);
                isOpen = false;
            }
        });
        fab_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermissionGallery();
                fab_audio.startAnimation(fabClose);
                fab_img.startAnimation(fabClose);
                fab_text.startAnimation(fabClose);
                isOpen = false;
            }
        });

    }

    private void requestPermissionGallery() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_IMAGE_GALLERY);
        } else {
            goGallery();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_IMAGE_GALLERY) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                goGallery();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_GALLERY && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            String x = getPath(imageUri);
            if (data.getData() != null) {
                try {
                    InputStream imageStream = getContentResolver().openInputStream(imageUri);
                    Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    ImageView imageView = new ImageView(getApplicationContext());
                    imageView.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
                    imageView.setPadding(35, 35, 35, 35);
                    imageView.setImageBitmap(selectedImage);
                    linearLayout.addView(imageView);

                    FileInputStream fileInputStream = new FileInputStream(x);
                    try {
                        byte[] imgbyte = new byte[fileInputStream.available()];
                        fileInputStream.read(imgbyte);
                        Photo photo = new Photo();
                        photo.setPath(x);
                        photo.setNoteId(resulNoteId);
                        photo.setImg(imgbyte);
                        listPhoto.add(photo);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String getPath(Uri imageUri) {
        if (imageUri == null) {
            return null;
        }
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(imageUri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        return imageUri.getPath();
    }

    private void goGallery() {
        Intent pickPhotoIntent = new Intent(Intent.ACTION_PICK);
        File pictureDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String pictuPath = pictureDir.getPath();
        Uri data = Uri.parse(pictuPath);
        pickPhotoIntent.setDataAndType(data, "image/*");
        startActivityForResult(pickPhotoIntent, REQUEST_IMAGE_GALLERY);
    }

    private void handleAnimation() {
        if (isOpen) {
            fab_add.startAnimation(rotateForward);
            fab_audio.startAnimation(fabClose);
            fab_img.startAnimation(fabClose);
            fab_text.startAnimation(fabClose);
            fab_text.setClickable(false);
            fab_audio.setClickable(false);
            fab_img.setClickable(false);
            isOpen = false;
        } else {
            fab_add.startAnimation(rotateBackward);
            fab_audio.startAnimation(fabOpen);
            fab_img.startAnimation(fabOpen);
            fab_text.startAnimation(fabOpen);
            fab_text.setClickable(true);
            fab_audio.setClickable(true);
            fab_img.setClickable(true);
            isOpen = true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
                if (!listEdt.isEmpty()) {
                    for (int k = 0; k < listEdt.size(); k++) {
                        EditText editText = listEdt.get(k);
                        String paragraphTxt = editText.getText().toString();
                        if (!paragraphTxt.isEmpty()) {
                            Paragraph paragraph = new Paragraph();
                            paragraph.setText(paragraphTxt);
                            paragraph.setPosition(k);
                            paragraph.setNoteId(noteId);
                            insertParagraphDB(paragraph);
                        }
                    }
                }
                Intent intent = new Intent(getApplicationContext(), ActivityListNotes.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }
        return true;
    }

    private void insertPhotoDB(Photo photo) {
        new TaskAddPhoto().execute(photo);
    }


    private void insertParagraphDB(Paragraph paragraph) {
        new TaskAddParagraph().execute(paragraph);
    }

    private class TaskAddParagraph extends AsyncTask<Paragraph, Void, Void> {
        @Override
        protected Void doInBackground(Paragraph... paragraphs) {
            try {
                AppDataBase.getInstanceParagraphBD(getApplicationContext()).getParagraphDao().insertParagraph(paragraphs);
            } catch (Exception e) {
                System.out.println("Excepcion - Paragraph " + e.getMessage() + " - " + e.getCause());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(getApplicationContext(), "Se guardaron titulos con Notas", Toast.LENGTH_SHORT).show();
        }
    }

    private class TaskAddPhoto extends AsyncTask<Photo, Void, Void> {
        @Override
        protected Void doInBackground(Photo... photos) {
            try {
                AppDataBase.getInstancePhotoBD(getApplicationContext()).getPhotoDao().inserPhoto(photos);
            } catch (Exception e) {
                System.out.println("Excepcion Guardado Foto " + e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Util.showToast(getApplicationContext(), "Se guardó la foto correctamente");
        }
    }
}
