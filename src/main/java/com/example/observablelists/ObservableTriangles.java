package com.example.observablelists;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class ObservableTriangles {

    ObservableList<ObservableTriangles> triangles = FXCollections.observableArrayList();

    SimpleDoubleProperty sideA = new SimpleDoubleProperty();
    SimpleDoubleProperty sideB = new SimpleDoubleProperty();
    SimpleDoubleProperty sideC = new SimpleDoubleProperty();



    private SimpleDoubleProperty area = new SimpleDoubleProperty();

    {//секция инициализации
        this.sideA.addListener((s1, o,n)->{
            //this.perimeter.set(perimeter());
            this.area.set(area());
        });
        this.sideB.addListener((s1, o,n)->{
            //this.perimeter.set(perimeter());
            this.area.set(area());
        });
        this.sideC.addListener((s1, o,n)->{
            //this.perimeter.set(perimeter());
            this.area.set(area());
        });
    }

    public ObservableTriangles(double sideA, double sideB, double sideC) {
        this.sideA.set(sideA);
        this.sideB.set(sideB);
        this.sideC.set(sideC);
    }

    public SimpleDoubleProperty sideAProperty() {
        return sideA;
    }

    public SimpleDoubleProperty sideBProperty() {
        return sideB;
    }

    public SimpleDoubleProperty sideCProperty() {
        return sideC;
    }



    public ObservableTriangles(SimpleDoubleProperty sideA, SimpleDoubleProperty sideB, SimpleDoubleProperty sideC) {
        this.sideA = sideA;
        this.sideB = sideB;
        this.sideC = sideC;
    }

    public ObservableTriangles(String sideA, String sideB, String sideC) {
        this.sideA.set(Double.parseDouble(sideA));
        this.sideB.set(Double.parseDouble(sideB));
        this.sideC.set(Double.parseDouble(sideC));
    }

    //public ObservableTriangle(double a, double b, double c) {
        //setSideA(a);
        //setSideB(b);
        //setSideC(c);
    //}

    //double perimeter()
    //{
        //return sideA.get()+sideB.get()+sideC.get();
    //}

    public SimpleDoubleProperty areaProperty() {
        return area;
    }

    double area()
    {

        double p = (sideA.get()+sideB.get()+sideC.get()) / 2;
        return Math.sqrt(p * (p - sideA.get()) * (p - sideB.get()) * (p - sideC.get()));
    }

    public void loadTriangles(List<String> lines){
        for (String str: lines) {
            ObservableTriangles oTriangles = new ObservableTriangles(str.split(" ")[0], str.split(" ")[1], str.split(" ")[2]);
            triangles.add(oTriangles);
        }
    }

}
