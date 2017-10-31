package com.example.user.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.firebase.iid.FirebaseInstanceId;

/**
 * Created by User on 2017-09-21.
 */

public class RootActivity extends Activity {
    myDBHelper myRoot;
    //RootDao rootDao;
    Spinner officeSpinner;
    Button completeBtn;
    SQLiteDatabase sqlDB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.root);
        setTitle("첫 화면");

        //String token = FirebaseInstanceId.getInstance().getToken();
        //Log.d("FCM_Token", token);

        final String[] office = {"경찰서", "소방서", "보건소", "대사관", "우체국"};

        officeSpinner = (Spinner)findViewById(R.id.spinner);
        completeBtn = (Button)findViewById(R.id.button2);

        //drop 박스 형태로 관공서를 보여주기
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, office);
        officeSpinner.setAdapter(adapter);

        //순찰 중인 현재 위치를 GPS로 받아오기
        //예시
        final Double latitude = 126.8423617;
        final Double longitude = 35.5025595;
        ///
        ///////////////////////////////////////

        //officeDB 파일을 생성
        //rootDao = new RootDao(this);
        myRoot = new myDBHelper(this);

        //완료 버튼을 누르면 토큰값, 선택된 관공서와 GPS로 받은 현재 위치가 SQLite DB로 저장되도록 한다.
        completeBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                sqlDB = myRoot.getWritableDatabase();
                //sqlDB.execSQL("INSERT INTO officeTB VALUES('"+officeSpinner.getSelectedItem().toString()+"', "+latitude+", "+longitude+");");
                sqlDB.execSQL("INSERT INTO officeTB VALUES('"+123+"', "+latitude+", "+longitude+");");
                sqlDB.close();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });


    }

    public class myDBHelper extends SQLiteOpenHelper{
        public myDBHelper(Context context){
            super(context, "officeDB", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            //db.execSQL("CREATE TABLE officeTB(token CHAR(60) PRIMARY KEY, officeName CHAR(20), curLatitude DOUBLE, curLongitude DOUBLE;");
            db.execSQL("CREATE TABLE officeTB(officeName CHAR(20) PRIMARY KEY, curLatitude REAL, curLongitude REAL;");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS officeTB;");
            onCreate(db);
        }
    }
}
