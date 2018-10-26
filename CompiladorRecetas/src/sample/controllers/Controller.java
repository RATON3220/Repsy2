package sample.controllers;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;
import org.reactfx.Subscription;
import sample.Constants.Configs;

import java.io.File;
import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static sample.Constants.Configs.*;

public class Controller extends Application {
    private Stage stage;
    @FXML HBox panesote;
    @FXML TextArea txt_consola;
    CodeArea codeArea = new CodeArea();
    @FXML protected void initialize(){
        codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));
            Subscription cleanupWhenNoLongerNeedIt = codeArea
               .multiPlainChanges().successionEnds(Duration.ofMillis(500))
                .subscribe(ignore -> codeArea.setStyleSpans(0,
                        computeHighlighting(codeArea.getText())));
        codeArea.getStyleClass().add("editor");
        codeArea.replaceText(0, 0, sampleCode);
        HBox.setHgrow(codeArea, Priority.ALWAYS);
        panesote.getChildren().add(codeArea);

    }

    public void ejecutar(ActionEvent e){
        compilar();
    }//ejecutar
    public void compilar(){

        txt_consola.setText("");
        long tInicial=System.currentTimeMillis();
        String texto=codeArea.getText();
        String[] renglones=texto.split("\\n");

        for(int x=0;x<renglones.length;x++) {
            boolean bandera = false;
            if (!renglones[x].trim().equals("")) {
                for (int y = 0; y < Configs.EXPRESIONES.length && bandera == false; y++) {
                    Pattern patron = Pattern.compile(Configs.EXPRESIONES[y]);
                    Matcher matcher = patron.matcher(renglones[x]);
                    if (matcher.matches()) {
                        bandera = true;
                    }//if
                }//llave for Y
                if (bandera == false) {
                    txt_consola.setText(txt_consola.getText() + "\n" + "Error de sintaxis en la linea " + (x + 1));
                }
            }//llave for X
        }//if
        long tFinal = System.currentTimeMillis() - tInicial;
        txt_consola.setText(txt_consola.getText() + "\n" + "Compilado en: " + tFinal + " milisegundos");
    }//compilar
    public void evtSalir(ActionEvent event){
        System.exit(0);
    }
    public void evtAbrir(ActionEvent event){
        FileChooser of=new FileChooser();
        of.setTitle("Abrir archivo Repsy");
        FileChooser.ExtensionFilter filtro= new FileChooser.ExtensionFilter("Archivos .repsy","*.repsy");
        of.getExtensionFilters().add(filtro);
        File file=of.showOpenDialog(stage);
    }//llave abrir
   @Override
    public void start(Stage stage) throws Exception {
        this.stage=stage;
    }
}
