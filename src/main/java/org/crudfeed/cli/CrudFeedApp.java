package org.crudfeed.cli;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.crudfeed.cli.menu.MenuRouter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CrudFeedApp {


// TODO: CrudFeed CLI 2.0 Check list             ~99%
//  {0}  - [x] Migrate to Hibernate              ~100%
//  {1}  - [x] Add likes                         ~100%
//  {2}  - [x] Add dislikes                      ~100%
//  {3}  - [x] Add list of user posts/likes      ~100%
//  {4}  - [x] Add update/remove posts           ~100%
//  {5}  - [x] Add update/remove messages        ~100%
//  {6}  - [x] Add subscriptions                 ~100%
//  {7}  - [x] Add keybinds                      ~100%
//  {8}  - [x] Add search posts by id/words      ~100%
//  {9}  - [x] Fix app and server work           ~100%
//  {10} - [ ] Fully rework old app code         ~95%
//  {11} - [x] Finish adding commands            ~100%
//  {12} - [x] Rework UI for 2,3,6               ~100%


    static void main() {
        new CrudFeedApp().run();
    }

    public void run() {
        MenuRouter mainApp = new MenuRouter();

        mainApp.run();
    }
}