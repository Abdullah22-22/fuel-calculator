package controller;

import i18n.Messages;
import model.FuelCalculator;

import javafx.fxml.FXML;
import javafx.geometry.NodeOrientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Locale;

public class MainController {

    @FXML private Label lblLanguage;
    @FXML private Label lblTitle;
    @FXML private Label lblDistance;
    @FXML private Label lblConsumption;
    @FXML private Label lblPrice;
    @FXML private Label lblResult;
    @FXML private Label lblName;

    @FXML private TextField txtDistance;
    @FXML private TextField txtConsumption;
    @FXML private TextField txtPrice;

    @FXML private Button btnCalculate;
    @FXML private Button btnEN;
    @FXML private Button btnFR;
    @FXML private Button btnJP;
    @FXML private Button btnIR;

    private final FuelCalculator calculator = new FuelCalculator();

    private Double lastFuel = null;
    private Double lastCost = null;
    private String errorKey = null;

    @FXML
    private void onEnglish() {
        switchLanguage(Messages.ENGLISH);
    }

    @FXML
    private void onFrench() {
        switchLanguage(Messages.FRENCH);
    }

    @FXML
    private void onJapanese() {
        switchLanguage(Messages.JAPANESE);
    }

    @FXML
    private void onPersian() {
        switchLanguage(Messages.PERSIAN);
    }

    private void switchLanguage(Locale locale) {
        Messages.setLocale(locale);
        refreshTexts();
    }

    private void refreshTexts() {
        lblLanguage.setText(Messages.get("language.label"));
        lblTitle.setText(Messages.get("app.title"));
        lblDistance.setText(Messages.get("distance.label"));
        lblConsumption.setText(Messages.get("consumption.label"));
        lblPrice.setText(Messages.get("price.label"));
        btnCalculate.setText(Messages.get("calculate.button"));

        Scene scene = lblTitle.getScene();
        if (scene != null) {
            scene.setNodeOrientation(Messages.isRightToLeft()
                    ? NodeOrientation.RIGHT_TO_LEFT
                    : NodeOrientation.LEFT_TO_RIGHT);

            if (scene.getWindow() instanceof Stage stage) {
                stage.setTitle(Messages.get("app.title"));
            }
        }

        renderResult();
    }

    @FXML
    private void onCalculate() {
        String d = txtDistance.getText();
        String c = txtConsumption.getText();
        String p = txtPrice.getText();

        if (isBlank(d) || isBlank(c) || isBlank(p)) {
            showError("empty.input");
            return;
        }

        try {
            double distance = Double.parseDouble(d.trim());
            double consumption = Double.parseDouble(c.trim());
            double price = Double.parseDouble(p.trim());

            if (distance < 0 || consumption < 0 || price < 0) {
                showError("invalid.input");
                return;
            }

            lastFuel = calculator.calculateFuel(consumption, distance);
            lastCost = calculator.calculateCost(lastFuel, price);
            errorKey = null;
            renderResult();

        } catch (NumberFormatException ex) {
            showError("invalid.input");
        }
    }

    private void showError(String key) {
        errorKey = key;
        lastFuel = null;
        lastCost = null;
        renderResult();
    }

    private void renderResult() {
        if (errorKey != null) {
            lblResult.setStyle("-fx-text-fill: red;");
            lblResult.setText(Messages.get(errorKey));
            return;
        }

        if (lastFuel == null) {
            lblResult.setText("");
            return;
        }

        Locale locale = Messages.getLocale();
        lblResult.setStyle("-fx-text-fill: green;");
        lblResult.setText(Messages.get("result.label",
                String.format(locale, "%.2f", lastFuel),
                String.format(locale, "%.2f", lastCost)));
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}