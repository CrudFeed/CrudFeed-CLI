package org.crudfeed.cli;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.crudfeed.cli.menu.MenuRouter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CrudFeedApp {

    static void main() {
        new CrudFeedApp().run();
    }

    public void run() {
        MenuRouter mainApp = new MenuRouter();

        mainApp.run();
    }
}
