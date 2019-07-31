package com.example.diarioapp.model.view;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.example.diarioapp.model.db.AppDataBase;
import com.example.diarioapp.utils.Util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.diarioapp.model.view.ActivityListNotes.FOLDER_PICTURE;

public class Note_Activity extends AppCompatActivity {
    FloatingActionButton fab_add, fab_text, fab_audio, fab_img;
    LinearLayout linearLayout;
    ArrayList<EditText> listEdt;
    ArrayList<Bitmap> listBitmap;
    TextView titleNote;
    Animation fabOpen, fabClose, rotateForward, rotateBackward;
    boolean isOpen = false;
    private static int REQUEST_IMAGE_GALLERY = 100;
    AlertDialog.Builder showPictureOptions;
    private String title_get;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        titleNote = findViewById(R.id.titleNote);
        Intent intent = getIntent();
        linearLayout = findViewById(R.id.container_ll);
        listEdt = new ArrayList<>();
        listBitmap = new ArrayList<>();
        fabOpen = AnimationUtils.loadAnimation(this, R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(this, R.anim.fab_close);
        rotateForward = AnimationUtils.loadAnimation(this, R.anim.rotate_forward);
        rotateBackward = AnimationUtils.loadAnimation(this, R.anim.rotate_backward);

        title_get = intent.getExtras().getString("title_note");
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
                View customDialog = LayoutInflater.from(getApplicationContext()).inflate(R.layout.picture_source, null);
                showPictureOptions = new AlertDialog.Builder(Note_Activity.this);
                showPictureOptions.setView(customDialog);
                ImageView pick_gallery = customDialog.findViewById(R.id.pick_gallery);
                ImageView pick_camera = customDialog.findViewById(R.id.pick_camera);
                pick_gallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        requestPermissionGallery();
                    }
                });
                pick_camera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Util.showToast(getApplicationContext(), "Seleccionaste Camera");
                    }
                });
                showPictureOptions.show();
                fab_audio.startAnimation(fabClose);
                fab_img.startAnimation(fabClose);
                fab_text.startAnimation(fabClose);
                isOpen = false;
            }
        });

    }

    private void requestPermissionGallery() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
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
            if (data.getData() != null) {
                try {
                    Uri imageUri = data.getData();
                    InputStream imageStream = getContentResolver().openInputStream(imageUri);
                    Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    ImageView imageView = new ImageView(getApplicationContext());

                    imageView.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
                    imageView.setPadding(35, 35, 35, 35);
                    imageView.setImageBitmap(selectedImage);
                    listBitmap.add(selectedImage);
                    linearLayout.addView(imageView);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
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
                insertNoteDB();
        }
        return true;
    }

    private void insertNoteDB() {
        new TaskAddNote().execute();
    }

    private class TaskAddNote extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Date date = new Date();
                DateFormat dateFormat = DateFormat.getDateTimeInstance();
                String formattedDate = dateFormat.format(date);
                Note note = new Note();
                note.setTitle(titleNote.getText().toString());
                note.setDateTitlenote(formattedDate);
                long resul = AppDataBase.getInstanceNoteBD(getApplicationContext()).getNoteDao().insertNote(note);  //inserta Nota

                for (int k = 0; k < listEdt.size(); k++) {
                    EditText editText = listEdt.get(k);
                    String paragraphTxt = editText.getText().toString();
                    if (!paragraphTxt.isEmpty()) {
                        Paragraph paragraph = new Paragraph();
                        paragraph.setText(paragraphTxt);
                        paragraph.setPosition(k);
                        paragraph.setNoteId(resul);
                        insertParagraphDB(paragraph);
                    }
                }

                for (int j = 0; j < listBitmap.size(); j++) {
                    saveImageToExternal(listBitmap.get(j));
                }
                Intent intent = new Intent(getApplicationContext(), ActivityListNotes.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            } catch (Exception e) {
                System.out.println("Excepcion - Note " + e.getMessage() + " - " + e.getCause());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(getApplicationContext(), "Se guardaron notas", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveImageToExternal(Bitmap bitmap) {
        Date date = new Date();
        DateFormat dateFormat = DateFormat.getDateTimeInstance();
        String formattedDate = dateFormat.format(date);
        String bitmapName = title_get + "_" + formattedDate+".jpg";
        File fileBitmap = new File(FOLDER_PICTURE, bitmapName);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(fileBitmap);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
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
}
