package util;

import com.jfoenix.controls.JFXTextField;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;

import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class ValidationJFX {
    public static Object validate(LinkedHashMap<JFXTextField, Pattern> map, Button btn) {
        btn.setDisable(true);
        for (JFXTextField textFieldKey : map.keySet()) {
            Pattern patternValue = map.get(textFieldKey);
            if (!patternValue.matcher(textFieldKey.getText()).matches()) {
                if (!textFieldKey.getText().isEmpty()) {
                    textFieldKey.setFocusColor(Paint.valueOf("#ff0000"));
                    textFieldKey.setUnFocusColor(Paint.valueOf("#ff0000"));
                }
                return textFieldKey;
            }
            textFieldKey.setFocusColor(Paint.valueOf("#0000ff"));
            textFieldKey.setUnFocusColor(Paint.valueOf("#0000ff"));
        }
        btn.setDisable(false);
        return true;
    }
}
