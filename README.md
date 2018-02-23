# JFTex
JFTex is a library for testing JavaFX applications, based on TestFX.

## Features
There are a lot of useful features, like:

__Test without direct input simulation__
>It means that you can run the Test without mouse is moving or focus must have a JFX Node in pane to test. All values are inserted direct from code into the component. After all you can check your background model or the behavior of your pane components.

__Extended Matcher for Assertions__
>There is an extended matcher to run better assertions in test. So you can test on selected items in selectable components, list sizes, list content and special states for chekcable components

__Test Pane__
>Insert your pane and background model into this pane to test manually. So you can do actions on your pane to test and show model changes in a property based live view. Don't forget to call <code>updateModelView()</code>, if pane view model has changed.

## Usage
### Dependencies
You can get the library from maven central repo:

__Maven__
><pre>&lt;dependency>
>    &lt;groupId>com.github.kleinerhacker&lt;/groupId>
>    &lt;artifactId>jftex&lt;/artifactId>
>    &lt;version>1.0.0&lt;/version>
>&lt;/dependency></pre>

__Gradle__
><pre>compile group: 'com.github.kleinerhacker', name: 'jftex', version: '1.0.0'</pre>

### Startup with test
To start you must extends your test class from <code>ExtendedApplicationTest</code> to use directly input without input device simulation. To use extended assertion use class <code>ExtendedMatchers</code> in JUnit test functions.

To use the test pane create a class in test folder and create a main method with extension to <code>Apllication</code> of JavaFX framework. Launch the application and put <code>TestPane</code> with your own pane to test and its background model into the scene. Now you can run the test app to run a manually JavaFX UI test.
  
## Hints
Please note that the Test Pane based on MVVM-FX so you get a test dependency to a weld container implementation.
