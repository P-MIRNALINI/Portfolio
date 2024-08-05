package com.example.expensetracker;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ExpenseTrackerApp extends Application {

    private TextField salaryField;
    private ComboBox<String> expenseComboBox;
    private TextField amountField;
    private Button addExpenseButton;
    private Label remainingSavingsLabel;

    private double totalExpenses = 0.0;
    private double salary = 0.0;
    private LineChart<Number, Number> expenseChart;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Expense Tracker");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);

        Label salaryLabel = new Label("Salary:");
        GridPane.setConstraints(salaryLabel, 0, 0);

        salaryField = new TextField();
        GridPane.setConstraints(salaryField, 1, 0);

        Label expenseLabel = new Label("Expense Category:");
        GridPane.setConstraints(expenseLabel, 0, 1);

        expenseComboBox = new ComboBox<>();
        expenseComboBox.getItems().addAll("Groceries", "Utilities", "Entertainment", "Other");
        GridPane.setConstraints(expenseComboBox, 1, 1);

        Label amountLabel = new Label("Amount:");
        GridPane.setConstraints(amountLabel, 0, 2);

        amountField = new TextField();
        GridPane.setConstraints(amountField, 1, 2);

        addExpenseButton = new Button("Add Expense");
        GridPane.setConstraints(addExpenseButton, 2, 2);

        addExpenseButton.setOnAction(e -> addExpense());

        remainingSavingsLabel = new Label("Remaining Savings: ");
        GridPane.setConstraints(remainingSavingsLabel, 0, 3, 3, 1);

        // Create a line chart for expense history
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Expense Entry");
        yAxis.setLabel("Expense Amount");
        expenseChart = new LineChart<>(xAxis, yAxis);
        expenseChart.setTitle("Expense History");
        GridPane.setConstraints(expenseChart, 0, 4, 3, 1);

        grid.getChildren().addAll(salaryLabel, salaryField, expenseLabel, expenseComboBox, amountLabel,
                amountField, addExpenseButton, remainingSavingsLabel, expenseChart);

        Scene scene = new Scene(grid, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void addExpense() {
        String selectedExpense = expenseComboBox.getValue();
        String amountText = amountField.getText();

        if (selectedExpense != null && !amountText.isEmpty()) {
            double expenseAmount = Double.parseDouble(amountText);
            totalExpenses += expenseAmount;
            updateRemainingSavingsLabel();
            updateExpenseHistoryChart(totalExpenses, expenseAmount);
            amountField.clear();
        }
    }

    private void updateRemainingSavingsLabel() {
        if (!salaryField.getText().isEmpty()) {
            salary = Double.parseDouble(salaryField.getText());
            double remainingSavings = salary - totalExpenses;
            remainingSavingsLabel.setText("Remaining Savings: " + remainingSavings);
        }
    }

    private void updateExpenseHistoryChart(double xValue, double yValue) {
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>(xValue, yValue));
        expenseChart.getData().add(series);
    }
}