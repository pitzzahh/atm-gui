package io.github.pitzzahh.atm.util;

import javafx.animation.PauseTransition;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class Util {

    public static void remove(Label label, Duration duration) {
        var visiblePause = new PauseTransition(duration);
        visiblePause.setOnFinished(
                event -> label.setVisible(false)
        );
        visiblePause.play();
    }

}
