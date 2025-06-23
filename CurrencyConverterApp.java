import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CurrencyConverterApp extends Application {
    private StringBuilder conversionHistory = new StringBuilder();
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Currency Converter");

        Label amountLabel = new Label("Amount:");
        TextField amountField = new TextField();
        Label fromCurrencyLabel = new Label("From Currency:");
        ComboBox<String> fromCurrency = new ComboBox<>();
        fromCurrency.getItems().addAll("USD", "EUR", "INR", "GBP", "AUD", "CAD", "JPY", "CHF", "CNY", "SEK");
        fromCurrency.setValue("USD");
        Label toCurrencyLabel = new Label("To Currency:");
        ComboBox<String> toCurrency = new ComboBox<>();
        toCurrency.getItems().addAll("USD", "EUR", "INR", "GBP", "AUD", "CAD", "JPY", "CHF", "CNY", "SEK");
        toCurrency.setValue("EUR");
        Button convertButton = new Button("Convert");
        Label resultLabel = new Label();
        Button showHideHistoryButton = new Button("Show/Hide History");
        VBox historyBox = new VBox();
        ScrollPane historyScrollPane = new ScrollPane(historyBox);
        historyScrollPane.setPrefHeight(100);
        historyScrollPane.setPrefWidth(380);
        historyScrollPane.setVisible(false);
        Button knowMoreButton = new Button("Know More");

        GridPane grid = new GridPane();
        grid.add(amountLabel, 0, 0);
        grid.add(amountField, 1, 0);
        grid.add(fromCurrencyLabel, 0, 1);
        grid.add(fromCurrency, 1, 1);
        grid.add(toCurrencyLabel, 0, 2);
        grid.add(toCurrency, 1, 2);
        grid.add(convertButton, 1, 3);
        grid.add(resultLabel, 1, 4);
        grid.add(showHideHistoryButton, 1, 5);
        grid.add(knowMoreButton, 1, 6);
        grid.add(historyScrollPane, 0, 7, 2, 1);

        convertButton.setOnAction(e -> {
            try {
                double amount = Double.parseDouble(amountField.getText());
                BaseCurrency from = getCurrency(fromCurrency.getValue());
                BaseCurrency to = getCurrency(toCurrency.getValue());

                double amountInUSD = from.toUSD(amount);
                double convertedAmount = to.fromUSD(amountInUSD);

                resultLabel.setText(String.format("%.2f %s = %.2f %s", amount, fromCurrency.getValue(), convertedAmount, toCurrency.getValue()));

                conversionHistory.append(String.format("%.2f %s = %.2f %s%n", amount, fromCurrency.getValue(), convertedAmount, toCurrency.getValue()));
                historyBox.getChildren().clear();
                historyBox.getChildren().add(new Label(conversionHistory.toString()));
            } catch (NumberFormatException ex) {
                resultLabel.setText("Please enter a valid number for the amount.");
            }
        });
        showHideHistoryButton.setOnAction(e -> historyScrollPane.setVisible(!historyScrollPane.isVisible()));

        knowMoreButton.setOnAction(e -> {
            ComboBox<String> currencySelector = new ComboBox<>();
            currencySelector.getItems().addAll("USD", "EUR", "INR", "GBP", "AUD", "CAD", "JPY", "CHF", "CNY", "SEK");
            currencySelector.setValue("USD");
            Label currencyInfoLabel = new Label();
            currencyInfoLabel.setWrapText(true);
            Button getInfoButton = new Button("Get Info");
            getInfoButton.setOnAction(event -> {
                String selectedCurrency = currencySelector.getValue();
                String currencyInfo = getCurrencyInfo(selectedCurrency);
                currencyInfoLabel.setText(currencyInfo);
            });
            VBox infoBox = new VBox(currencySelector, getInfoButton, currencyInfoLabel);
            infoBox.setPrefWidth(400);
            infoBox.setStyle("-fx-padding: 10;");
            Scene infoScene = new Scene(infoBox, 500, 300);
            Stage infoStage = new Stage();
            infoStage.setTitle("Currency Information");
            infoStage.setScene(infoScene);
            infoStage.show();
        });
        Scene scene = new Scene(grid, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    private BaseCurrency getCurrency(String currencyCode) {
        switch (currencyCode) {
            case "USD": return new USD();
            case "EUR": return new EUR();
            case "INR": return new INR();
            case "GBP": return new GBP();
            case "AUD": return new AUD();
            case "CAD": return new CAD();
            case "JPY": return new JPY();
            case "CHF": return new CHF();
            case "CNY": return new CNY();
            case "SEK": return new SEK();
            default: return new USD();
        }
    }
    private String getCurrencyInfo(String currencyCode) {
        switch (currencyCode) {
            case "USD": return "The United States Dollar (USD) is the official currency of the United States...";
            case "EUR": return "The Euro (EUR) is the official currency of the Eurozone...";
            case "INR": return "The Indian Rupee (INR) is the official currency of India...";
            case "GBP": return "The British Pound Sterling (GBP) is the official currency of the United Kingdom...";
            case "AUD": return "The Australian Dollar (AUD) is the official currency of Australia...";
            case "CAD": return "The Canadian Dollar (CAD) is the official currency of Canada...";
            case "JPY": return "The Japanese Yen (JPY) is the official currency of Japan...";
            case "CHF": return "The Swiss Franc (CHF) is the official currency of Switzerland...";
            case "CNY": return "The Chinese Yuan (CNY), also known as the Renminbi (RMB), is the official currency of China...";
            case "SEK": return "The Swedish Krona (SEK) is the official currency of Sweden...";
            default: return "No information available for the selected currency.";
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}
class BaseCurrency {
    protected double rateToUSD;
    public BaseCurrency(double rateToUSD) {
        this.rateToUSD = rateToUSD;
    }
    public Double toUSD(Double amount) {
        return amount / rateToUSD;
    }
    public Double fromUSD(Double amount) {
        return amount * rateToUSD;
    }
}
class AUD extends BaseCurrency { public AUD() { super(1.4); }}
class CAD extends BaseCurrency { public CAD() { super(1.34); }}
class CHF extends BaseCurrency { public CHF() { super(0.91); }}
class CNY extends BaseCurrency { public CNY() { super(6.45); }}
class EUR extends BaseCurrency { public EUR() { super(0.91); }}
class GBP extends BaseCurrency { public GBP() { super(0.79); }}
class INR extends BaseCurrency { public INR() { super(82.86); }}
class JPY extends BaseCurrency { public JPY() { super(110.0); }}
class SEK extends BaseCurrency { public SEK() { super(8.6); }}
class USD extends BaseCurrency { public USD() { super(1.0); }}
