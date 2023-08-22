package com.example.observablelists;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.converter.DoubleStringConverter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

import static java.nio.file.Files.readAllLines;

public class HelloController {

    ObservableList<ObservableTriangles> triangles = FXCollections.observableArrayList();

    HashMap<ObservableTriangles, HBox> hBoxMap = new HashMap<>();

    @FXML
    TextField txtSideA, txtSideB, txtSideC;

    @FXML
    VBox boxForTriangles;

    @FXML
    TableView<ObservableTriangles> table;

    @FXML
    Button saveBtn;
    //ObservableList<ObservableTriangles> triangles = FXCollections.observableArrayList();

    @FXML
    Button btnSaveXLS;

    public void initialize() throws IOException {
        triangles.addListener((ListChangeListener<ObservableTriangles>) change -> {
            while (change.next())  {
                if(change.wasAdded()){
                    System.out.println("В список добавлен треугольник.");
                    for(ObservableTriangles oc: change.getAddedSubList()) {
                        paintTriangles(oc);
                    }
                }
                if(change.wasRemoved())  {
                    System.out.println("Из списка удалён треугольник.");
                    for(ObservableTriangles oc: change.getRemoved()) {
                        eraseTriangles(oc);
                    }
                }
            }
        });
        loadFromFile();
        initTable();

        btnSaveXLS.setOnAction(actionEvent -> ExcelSaver.save(triangles, "C:\\trrr.xls"));

    }
    public void loadFromFile() throws IOException {
        List<String> list = readAllLines(new File("HomeWork-Ttiangles/triangles.txt").toPath(), Charset.forName("UTF-8"));
        loadTriangles(list);
    }
    public void loadTriangles(List<String> lines){
        for (String str: lines) {
            ObservableTriangles oTriangles = new ObservableTriangles(str.split(" ")[0], str.split(" ")[1], str.split(" ")[2]);
            triangles.add(oTriangles);
        }
    }

    @FXML
    public void addTriangles(){
        double aaa = Double.parseDouble(txtSideA.getText());
        double bbb = Double.parseDouble(txtSideB.getText());
        double ccc = Double.parseDouble(txtSideC.getText());

        ObservableTriangles eachTriangle = new ObservableTriangles(aaa, bbb, ccc);
        triangles.add(eachTriangle);
        eachTriangle.sideA.addListener((val, o, n)-> System.out.println("1-я сторона треугольника изменилась с "+o+" на "+n));
        eachTriangle.sideB.addListener((val, o, n)-> System.out.println("2-я сторона треугольника изменилась с "+o+" на "+n));
        eachTriangle.sideC.addListener((val, o, n)-> System.out.println("3-я сторона треугольника изменилась с "+o+" на "+n));
    }

    public void initTable()
    {
        table.getColumns().clear();

        TableColumn<ObservableTriangles, Double> columnSideA = new TableColumn<>("1-я сторона");
        columnSideA.setCellValueFactory(new PropertyValueFactory<>("sideA"));
        columnSideA.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

        TableColumn<ObservableTriangles, Double> columnSideB = new TableColumn<>("2-я сторона");
        columnSideB.setCellValueFactory(new PropertyValueFactory<>("sideB"));
        columnSideB.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

        TableColumn<ObservableTriangles, Double> columnSideC = new TableColumn<>("3-я сторона");
        columnSideC.setCellValueFactory(new PropertyValueFactory<>("sideC"));
        columnSideC.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

        TableColumn<ObservableTriangles, Double> columnArea = new TableColumn<>("Площадь");
        columnArea.setCellValueFactory(new PropertyValueFactory<>("area"));
        //columnArea.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

        TableColumn<ObservableTriangles, Button> act = new TableColumn<>("Удаление");
        act.setCellFactory(ActionButtonTableCell.<ObservableTriangles>forTableColumn("Удалить", (ObservableTriangles p) -> {
            table.getItems().remove(p);
            return p;
        }));

        table.getColumns().add(columnSideA);
        table.getColumns().add(columnSideB);
        table.getColumns().add(columnSideC);
        table.getColumns().add(columnArea);
        table.getColumns().add(act);
        table.setItems(triangles);
        table.setEditable(true);
    }

    private void paintTriangles(ObservableTriangles oc) {
        //Label lab = new Label(pc.toString());
        TextField tSideA = new TextField();
        tSideA.textProperty().bindBidirectional(oc.sideAProperty(), new DecimalFormat());

        TextField tSideB = new TextField();
        tSideB.textProperty().bindBidirectional(oc.sideBProperty(), new DecimalFormat());

        TextField tSideC = new TextField();
        tSideC.textProperty().bindBidirectional(oc.sideCProperty(), new DecimalFormat());

        Button but = new Button("-");
        but.setOnAction(q-> triangles.remove(oc));
        HBox hBox=new HBox();
        hBox.getChildren().add(but);
        hBox.getChildren().add(tSideA);
        hBox.getChildren().add(tSideB);
        hBox.getChildren().add(tSideC);
        boxForTriangles.getChildren().add(hBox);
        hBoxMap.put(oc, hBox);
    }

    private void eraseTriangles(ObservableTriangles oc) {
        boxForTriangles.getChildren().remove(hBoxMap.get(oc));
        hBoxMap.remove(oc);
    }

}