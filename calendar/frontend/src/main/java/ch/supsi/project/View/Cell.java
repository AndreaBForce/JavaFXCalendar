package ch.supsi.project.View;

import ch.supsi.project.View.CalendarView;
import ch.supsi.project.model.Event;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

import static java.util.Calendar.DAY_OF_MONTH;

public class Cell extends StackPane {
    ResourceBundle resourceBundle;

    private BorderPane casella;
    private BorderPane text;
    private VBox eventiVerticali;
    private Label num_day;

    private Rectangle r;

    public static SimpleDateFormat ft = new SimpleDateFormat("yyyy.MM.dd");
    public static SimpleDateFormat ora = new SimpleDateFormat("HH:mm");

    public Cell(double size, Calendar date, List<Event> events, ResourceBundle resourceBundle, CalendarView calendarView){
        this.resourceBundle = resourceBundle;

        this.casella = new BorderPane();
        this.text = new BorderPane();
        this.eventiVerticali = new VBox();
        this.num_day = new Label();

        r = new Rectangle(size, size);

        r.setStyle("-fx-stroke: black; -fx-stroke-width: 2;");
        r.setFill(Color.WHITE);

        this.num_day.setStyle("-fx-font-weight: bold");
        this.num_day.setText(date.get(DAY_OF_MONTH)+"");

        this.casella.setCenter(this.r);

        this.num_day.setAlignment(Pos.TOP_LEFT);

        this.text.setTop(num_day);

        this.getChildren().add(this.eventiVerticali);
        this.eventiVerticali.setAlignment(Pos.TOP_CENTER);

        this.getChildren().add(this.casella);
        this.text.setCenter(eventiVerticali);

        this.getChildren().add(this.text);

        //da metter qua dentro la gestione migliore della data
        this.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 2) {
                calendarView.newEventModal();
            }
        });


        for (Event e : events) {
            HBox hBoxDay = new HBox();
            hBoxDay.setSpacing(5);
            String titolo = e.getTitle();
            hBoxDay.setStyle("-fx-background-color: " + e.getType().getColour().getHexCode() + ";");
            Label impegno = new Label();
            Label orario = new Label();
            orario.setText(" " + ora.format(e.getStart()) + " - " + ora.format(e.getEnd()));

            if (titolo.length() > 4) {
                impegno.setText(titolo.substring(0, 4));
            } else {
                impegno.setText(e.getTitle());
            }
            hBoxDay.getChildren().addAll(impegno, orario);
            hBoxDay.setAlignment(Pos.CENTER);
            this.eventiVerticali.getChildren().add(hBoxDay);


            hBoxDay.setOnMouseClicked(mouseEvent -> {
                Stage mostrami = new Stage();
                mostrami.setAlwaysOnTop(true);
                mostrami.setTitle(resourceBundle.getString("eventSelected.testo"));

                BorderPane mostraDati = new BorderPane();
                VBox dati = new VBox();
                Label nome = new Label();
                Label data = new Label();
                Label startE = new Label();
                Label end = new Label();
                Label importanzaE = new Label();
                Label coloreE = new Label();
                nome.setText(resourceBundle.getString("eventTitle.testo") + ": " + e.getTitle());
                data.setText(resourceBundle.getString("eventDate.testo") + ": " + e.getDay().toString());
                startE.setText(resourceBundle.getString("eventStart.testo") + ": " + ora.format(e.getStart()));
                end.setText(resourceBundle.getString("eventEnd.testo") + ": " + ora.format(e.getEnd()));

                importanzaE.setText(resourceBundle.getString("eventType.testo") + ": "+e.getType().toString());
                coloreE.setText("                                                                                                       ");
                coloreE.setStyle("-fx-background-color: " + e.getType().getColour() + ";");
                dati.getChildren().addAll(nome, data, startE, end, importanzaE,coloreE);
                mostraDati.setCenter(dati);

                Scene scenaEvento = new Scene(mostraDati, 400, 100);
                mostrami.setScene(scenaEvento);

                mostrami.initModality(Modality.APPLICATION_MODAL);
                mostrami.showAndWait();
            });
        }
    }

    public void isDayOfCurrMonth(boolean isCurrMonth){
        if(isCurrMonth)
            this.r.setFill(Color.WHITE);
        else
            this.r.setFill(Color.GRAY);
    }

}
