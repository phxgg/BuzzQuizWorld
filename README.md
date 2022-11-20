# BUZZ! #

This is the finished project on our CSD object-oriented programming course.

Team Members: phxgg, NikosZoros3533

# Installation

* Make sure you have JavaFX SDK 11.0.2 installed on your computer. Download here: https://gluonhq.com/products/javafx/
* Add the JavaFX lib folder in your libraries.<br>
  For IntelliJ IDEA: File -> Project Structure... -> Libraries -> Click the `+` button and select your lib folder
* Add `--module-path "PATH_TO_JAVAFX_SDK/lib" --add-modules=javafx.controls,javafx.fxml` to your VM options.<br>
  <i>Where `PATH_TO_JAVAFX_SDK` is the path to your JavaFX SDK folder.</i><br>
  For IntelliJ IDEA: Run -> Edit Configurations... -> Application; `Main` -> Modify Options -> Select `Add VM Options`.
* You might need to close and re-open the project after those steps, in case the JavaFX library is not found.

### Code Warnings

IntelliJ IDEA will generate several warnings on the project (about 40).
They all are warnings like `if statement could be simplified`, so they can be ignored.

# JAR Usage

### Building

* Add an artifact from the `Buzz` module.<br>
  For IntelliJ IDEA:
  * File -> Project Structure... -> Artifacts -> Click `+` -> JAR -> From modules with dependencies...
  * Select `Main` as the main class.
  * Use the `PROJECT_DIRECTORY/resources` folder in the `Directory for META-INF/MANIFEST.MF` field.<br>
    <i>Where `PROJECT_DIRECTORY` is the path to your project.</i>
  * Apply & OK
  * Your artifact should look something like this: https://i.imgur.com/xfXU8ZX.png
* Build -> Build Artifacts... -> Buzz:jar -> Build
* Your output folder should be `PROJECT_DIRECTORY/out/artifacts/Buzz_jar`.<br>
  Make sure you also copy the `img` folder and the `questions.csv` file inside your output folder.
  

### Running

Because of JavaFX usage, the compiled JAR must be run with the following command:

```
java --module-path "PATH_TO_JAVAFX_SDK/lib" --add-modules=javafx.controls,javafx.fxml -jar Buzz.jar
```

The filename after building should be `Buzz.jar`.<br>
Please do not rename the executable jar file, there might occur issues. 

# Code MR

The CodeMR folder is not included in the repo. You can generate it using these steps:

* Select `src` once.
* CodeMR -> Extract Model -> Click `Extract Model`