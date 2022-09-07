# ShotTrack

ShotTrack is application from tracking biathlon shooting. You can save trainings or solo shootings.

# Architecture

Application is written in MVC style. Main entry point is `App` class. In `Run` method is started second Thread with a
main loop. This loop wait for user input, and it runs until an active controller is alive. When user type some command
it execute action on an active Controller.

## Controller

Every controller must be child of abstract class `Controller`. This class provides helper methods for changing routes or
setting new view.

## Model

Model is represented by a single instance of `ShotTrackModel` class. This class consists of classes in
`entities` package.

## View

Every view must be child of abstract class `View`. This class provides methods for register components.

### Components

Components are parts of UI. Every component should provide one Screen. Basic components are located in
`components` package.

More complex parts of UI can be composed into appComponent. AppComponent is basically container which can hold multiple
normal components.

# Display engine

Display engine is mechanism for creating UIs in terminal. There are only two classes `Screen` and `Frame`.

## Screen

Screen is block of text which can bee displayed in terminal.

## Frame

Frame is container for Screens. This container performs rendering of Screens. On each render is window of terminal
cleared and active screens are printed one by one in order of activation. 