# ButtonClicker
  
Source code of the game [ButtonClicker  on itch.io](https://alekgamedev.itch.io/button-clicker)  
If you want to report a bug open a [new issue](https://github.com/ChillyPigeon742/Button-Clicker/issues/new) in the issues panel
  
## How to Build

**PREREQUISITES**
- an IDE or any editor of your choice
- `JDK 21.0.6` installed on your system  

> Disclaimer: probably any Java 21 **JDK** would work, this is just what **ButtonClicker** is developed with.
  
Setup your project, use any IDE you'd like although keep in mind that this code is provided as-is as an IntelliJ IDEA project; however using another IDE should be trivial.

From there you can simply build using Maven, you can use the command `mvn package` to generate the jars necessary to run the application  

Look inside your project folder for a folder called `target` and you should find a folder in it called `mods` this contains all the exported jars including the app jar, we will use this as our module path  

---

### Finding Dependencies

Simply open a terminal window in the `mods` folder you found, and run the below command,  


`jdeps --module-path mods --module net.alek.buttonclicker --multi-release 21 --list-deps`  

this will then print a list of dependencies which we need, we're going to use this soon.

---

### Generating a trimmed `JDK`

Recall the dependencies that the last command printed, we're now going to use them here like so 
 
`jlink --module-path "%JAVA_HOME%\jmods" --add-modules WHATEVER,JDEPS,PRINTED,SEPARATED,LIKE,THIS --output runtime`

this will output a trimmed version of the **JDK** with only what we need from the standard library to a folder called `runtime`  

> Disclaimer: Only include in the command arguments packages which correspond with the `Java Standard Library` such as `java.base` not third-party dependency packages like `com.formdev.flatlaf`

---

### Generating the final `executable` package

Now that we have our trimmed runtime **JDK** that includes only what we need we need to now generate the actual app.  
To do so we will use the `jpackage` command like so,  

`jpackage --module net.alek.buttonclicker/net.alek.buttonclicker.core.Spark --module-path mods --runtime-image runtime --type app-image --icon icon.ico --name ButtonClicker --dest output`  

Here we use our `mods` folder we generated previously as the `module-path` and our `runtime` folder as the application's runtime  

We also use a custom `.ico` icon (Optional) and set the name of the app to **ButtonClicker**  

Now you can delete the `mods` and `runtime` folders we generated previously as those have been added into the app  

And that's it! You can simply launch the executable now to run the game