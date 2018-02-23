# JFTex
JFTex is a library for testing JavaFX applications, based on TestFX.

## Features
There are a lot of useful features, like:

__Test without direct input simulation__
>It means that you can run the Test without mouse is moving or focus must have a JFX Node in pane to test. All values are inserted direct from code into the component. After all you can check your background model or the behavior of your pane components.

__Extended Matcher for Assertions__
>There is an extended matcher to run better assertions in test. So you can test on selected items in selectable components, list sizes, list content and special states for chekcable components

__Test Pane__
>Insert your pane to test manually in this pane and put the background model into it, so you can do actions on your pane to test and show model changes in a property based live view. Don't forget to call <code>updateModelView()</code>, if pane view model has changed.
  
