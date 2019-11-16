package bmi;

import java.text.NumberFormat;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class BMIOutput extends Application {

    BMIEvents event = new BMIEvents();
    BMIValidator validator = new BMIValidator();
    BMICalculator calc = new BMICalculator();

    Text h1 = new Text("Kalkulator BMI");
    Text p = new Text("Podaj swoje wymiary");
    TextField edit1 = new TextField();
    TextField edit2 = new TextField();
    Label lab1 = new Label("WAGA");
    Label lab2 = new Label("WZROST");
    Button submit = new Button("Sprawdź");
    Button back = new Button("Ukryj wynik");
    Label outputText = new Label("0");
    Label outputDescr = new Label("Niedowaga");

    Boolean callBackData = false;
    Boolean isHeaderVisible = true;
    Boolean isOutputVisible = false;
    String[] funCaps = { "Wygłodzenie", "Wychudzenie", "Niedowaga", "Wartość prawidłowa", "Nadwaga",
            "Otyłość I-wszego stopnia", "Otyłość II-giego stopnia", "Otyłość skrajna" };

    VBox header;
    VBox descr;
    VBox outputScore;
    GridPane output;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("BMI Calculator - Michał Janocha");
        edit1.getStyleClass().clear();
        edit2.getStyleClass().clear();
        edit1.getStyleClass().add("editfield");
        edit1.setMaxWidth(90);
        edit2.setMaxWidth(90);
        edit2.getStyleClass().add("editfield");

        outputText.setStyle("-fx-font-weight: 900; -fx-font-size: 33px; -fx-text-fill: #2EAF57; opacity: 0;");
        outputText.translateYProperty().setValue(-200);
        outputDescr.translateYProperty().setValue(-200);
        back.translateYProperty().setValue(200);

        submit.setPrefSize(120, 30);
        back.setPrefSize(120, 30);

        edit1.setOnKeyTyped(ActioEvent -> applyBorders(event.checkIfNumber(edit1.getText()), edit1, lab1));
        edit2.setOnKeyTyped(ActioEvent -> applyBorders(event.checkIfNumber(edit2.getText()), edit2, lab2));

        submit.setOnMouseClicked(ActioEvent -> turnOffHeader(edit1.getText(), edit2.getText()));

        back.setOnMouseClicked(ActioEvent -> turnOnHeader());
        // edit1.setOnKeyPressed(e -> event.check(edit1.getText()));

        h1.setStyle("-fx-font-size: 22px; fx-font-weight: 900;");
        p.setStyle("-fx-opacity: 0.5;");

        lab1.setLabelFor(edit1);
        lab2.setLabelFor(edit2);
        GridPane grid = new GridPane();
        header = new VBox();
        HBox edits = new HBox();
        HBox labels = new HBox();
        GridPane footer = new GridPane();
        output = new GridPane();
        descr = new VBox();
        outputScore = new VBox();

        header.setAlignment(Pos.CENTER);
        header.getChildren().addAll(h1, p);

        labels.getChildren().addAll(lab1, lab2);
        labels.setPadding(new Insets(10, 15, -10, 20));
        labels.setSpacing(25);
        labels.setAlignment(Pos.CENTER);
        labels.setStyle("-fx-opacity: 0.5");

        edits.getChildren().addAll(edit1, edit2);
        edits.setPadding(new Insets(0, 20, 0, 20));
        edits.setSpacing(20);
        edits.setAlignment(Pos.CENTER);

        footer.getChildren().addAll(submit, back);
        footer.setAlignment(Pos.CENTER);

        submit.getStyleClass().add("btn");
        submit.setStyle("-fx-border-radius: 50px; -fx-border-color: rgba(0,0,0,0.1);");

        back.getStyleClass().add("btn");
        back.setStyle("-fx-border-radius: 50px; -fx-border-color: rgba(0,0,0,0.1);");

        // output.getChildren().addAll(outputText, outputDescr);
        descr.getChildren().add(outputDescr);
        descr.setAlignment(Pos.CENTER);

        outputScore.getChildren().add(outputText);
        outputScore.setAlignment(Pos.CENTER);

        output.add(outputScore, 0, 0);
        output.add(descr, 0, 1);
        output.setAlignment(Pos.CENTER);

        grid.setHgap(10);
        grid.setVgap(12);
        grid.setAlignment(Pos.CENTER);

        grid.add(output, 0, 0);
        grid.add(header, 0, 0);
        grid.add(edits, 0, 2);
        grid.add(labels, 0, 1);
        grid.add(footer, 0, 3);
        // grid.add(edit1, 0, 2);
        // grid.add(lab1, 1, 2);
        // grid.add(edit2, 0, 3);
        // grid.add(lab2, 1, 3);

        Scene scene = new Scene(grid, 600, 300);
        scene.getStylesheets().add(getClass().getResource("bmi_main.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

    }

    private void applyBorders(Boolean checkIfNumber, TextField source, Label responsibleLabel) {
        if (checkIfNumber) {
            source.setStyle("-fx-border-color: #2EAF57;");
            responsibleLabel.setStyle("-fx-text-fill: #2EAF57;");
        } else {
            responsibleLabel.setStyle("-fx-text-fill: #dd0034;");
            source.setStyle("-fx-border-color: #dd0034;");
        }
    }

    public void turnOffHeader(String edit1, String edit2) {
        TranslateTransition x = new TranslateTransition();
        TranslateTransition x2 = new TranslateTransition();
        TranslateTransition x3 = new TranslateTransition();
        Boolean edit1v = event.checkIfNumber(edit1);
        Boolean edit2v = event.checkIfNumber(edit2);

        if (edit1v && edit2v) {
            String bmi = calc.countBmi(Integer.parseInt(edit1), Double.parseDouble(edit2));
            String bmiR = bmi.replace(",", ".");
            Double bmiInt = Double.parseDouble(bmiR);
            String color = "#2EAF57;";
            if (bmiInt < 16) {
                color = "#0582b2";
                outputDescr.setText(funCaps[0]);
            } else if (bmiInt >= 16 && bmiInt <= 16.99) {
                color = "#02b9ff";
                outputDescr.setText(funCaps[1]);
            } else if (bmiInt >= 17 && bmiInt <= 18.49) {
                color = "#41caff";
                outputDescr.setText(funCaps[2]);
            } else if (bmiInt >= 18.50 && bmiInt <= 24.99) {
                color = "#2EAF57;";
                outputDescr.setText(funCaps[3]);
            } else if (bmiInt >= 25 && bmiInt <= 29.99) {
                color = "#ffa61e";
                outputDescr.setText(funCaps[4]);
            } else if (bmiInt >= 30 && bmiInt <= 34.99) {
                color = "#ff571e";
                outputDescr.setText(funCaps[5]);
            } else if (bmiInt >= 35 && bmiInt <= 39.99) {
                color = "#ff2a1e";
                outputDescr.setText(funCaps[6]);
            } else if (bmiInt >= 40) {
                color = "#ff0202";
                outputDescr.setText(funCaps[7]);
            }
            outputText.setStyle("-fx-text-fill: " + color + "; -fx-font-size: 33px; -fx-font-weight: 900;");
            outputText.setText(String.valueOf(bmi));
        }

        if (edit1v && edit2v && isHeaderVisible && !isOutputVisible) {
            x.setDuration(Duration.millis(500));
            x.setByY(-200);
            x.setAutoReverse(false);
            x.setNode(header);
            x.play();
            x.setOnFinished(e -> header.setStyle("-fx-opacity: 0;"));
            isHeaderVisible = false;

            output.setStyle("-fx-opacity:1 ;");
            x2.setDuration(Duration.millis(500));
            x2.setByY(200);
            x2.setAutoReverse(false);
            x2.setNode(output);
            x2.play();
            isOutputVisible = true;

            back.setStyle("-fx-opacity:1 ;");
            x3.setDuration(Duration.millis(500));
            x3.setByY(-160);
            x3.setAutoReverse(false);
            x3.setNode(back);
            x3.play();
        } else if (!isHeaderVisible && isOutputVisible) {
        }
    }

    public void turnOnHeader() {
        TranslateTransition x = new TranslateTransition();
        TranslateTransition x2 = new TranslateTransition();
        TranslateTransition x3 = new TranslateTransition();

        if (!isHeaderVisible && isOutputVisible) {
            header.setStyle("-fx-opacity:1 ;");
            x.setDuration(Duration.millis(500));
            x.setByY(200);
            x.setAutoReverse(false);
            x.setNode(header);
            x.play();
            isHeaderVisible = true;

            x2.setDuration(Duration.millis(500));
            x2.setByY(-200);
            x2.setAutoReverse(false);
            x2.setNode(output);
            x2.play();
            x2.setOnFinished(e -> output.setStyle("-fx-opacity: 0;"));
            isOutputVisible = false;

            x3.setDuration(Duration.millis(500));
            x3.setByY(160);
            x3.setAutoReverse(false);
            x3.setNode(back);
            x3.setOnFinished(e -> back.setStyle("-fx-opacity: 0;"));
            x3.play();
        }
    }
}