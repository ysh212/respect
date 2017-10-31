package com.example.user.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.skp.Tmap.TMapMarkerItem;
import com.skp.Tmap.TMapPOIItem;
import com.skp.Tmap.TMapPoint;
import com.skp.Tmap.TMapView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{
    TMapView tmapview;
    List<TMapMarkerItem> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //tmapview = new TMapView(this);
        tmapview = (TMapView)findViewById(R.id.tMap);
        //tmapview.setLayoutParams(params);

        tmapview.setSKPMapApiKey("19d9a144-6063-33ba-ab09-4af38c5add0d");
        tmapview.setLanguage(TMapView.LANGUAGE_KOREAN);
        //지도의 중심 위치좌표 - 없으면 기기의 현위치로 잡게됨(ex. 경찰서에 어플이 있다면 경찰서 위치)
        tmapview.setCenterPoint(126.8423617, 35.5025595);
        //tmapview.setIconVisibility(true);

        //신고가 온 위치에 마커 표시
        //addMarker();

        //위치1 마커
        TMapPoint point1 = new TMapPoint(35.5025595, 126.8423617);
        TMapMarkerItem markerItem1 = new TMapMarkerItem();
        markerItem1.setTMapPoint(point1);
        markerItem1.setVisible(TMapMarkerItem.VISIBLE);
        //tmapview.addMarkerItem("marker1", markerItem1);

        //위치2 마커
        TMapPoint point2 = new TMapPoint(35.509, 126.8423617);
        TMapMarkerItem markerItem2 = new TMapMarkerItem();
        markerItem2.setTMapPoint(point2);
        markerItem2.setVisible(TMapMarkerItem.VISIBLE);
        //tmapview.addMarkerItem("marker2", markerItem2);


        list = new ArrayList<TMapMarkerItem>();
        list.add(0,markerItem1);
        list.add(1,markerItem2);


        //지도에 마커 추가
        for(int i =0; i<list.size(); i++){
            tmapview.addMarkerItem("marker"+i, list.get(i));
        }

        //지도를 주어진 넓이와 높이에 맞게 줌레벨을 조정
        //마커가 많아지면 다시 해서 봐야할듯
        double latSpan = list.get(0).getPositionX() - list.get(1).getPositionX();
        double lonSpan = list.get(0).getPositionY() - list.get(1).getPositionY();
        tmapview.zoomToSpan(latSpan, lonSpan);


        tmapview.setZoomLevel(14);
        tmapview.setMapType(TMapView.MAPTYPE_STANDARD);
        tmapview.setCompassMode(true);
        //화면 중심을 단말의 현재위치로 이동시켜주는 트래핑 모드 설정
        tmapview.setTrackingMode(false);

    }

    //마커에 위치 부여하는 메소드
    public void addMarker(){
        //위도
        double latitude = 126.8423617;
        //경도
        double longitude = 35.5025595;
        //TMapPoint 생성자는 위도, 경도 위치 다름..쓰레기같음ㅡㅡ
        TMapPoint point = new TMapPoint(longitude, latitude);
        //마커 만들기 위한 객체생성
        TMapMarkerItem markerItem = new TMapMarkerItem();
        markerItem.setTMapPoint(point);
        markerItem.setVisible(TMapMarkerItem.VISIBLE);
        tmapview.addMarkerItem("marker", markerItem);
    }

}