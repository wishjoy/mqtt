package com;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Random;

public class ScatterPlotFromExcel extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // 创建X轴和Y轴
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("时间（小时）");
        yAxis.setLabel("上报次数");

        // 创建散点图
        ScatterChart<Number, Number> scatterChart = new ScatterChart<>(xAxis, yAxis);
        scatterChart.setTitle("一天中的人感上报散点图");

        // 创建数据系列
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("上报次数");

        // 从Excel文件中读取时间数据
        FileInputStream excelFile = new FileInputStream(new File("E:\\论文\\data.xlsx"));
        Workbook workbook = new XSSFWorkbook(excelFile);
        Sheet sheet = workbook.getSheetAt(0); // 假设数据在第一个工作表中

        Random random = new Random();

        for (Row row : sheet) {
            // 假设时间（小时）在第一列
            Cell timeCell = row.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);

            if (timeCell.getCellType() == CellType.NUMERIC) {
                double time = timeCell.getNumericCellValue();
                double count = random.nextInt(2000); // 随机生成0到2000之间的上报次数
                series.getData().add(new XYChart.Data<>(time, count));
            }
        }

        // 将数据系列添加到散点图中
        scatterChart.getData().add(series);

        // 创建场景并显示图表
        Scene scene = new Scene(scatterChart, 800, 600);
        stage.setScene(scene);
        stage.setTitle("一天中的人感上报散点图");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
