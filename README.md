# Getting Started
There are three ways to start and test the program, choose either one that works the best for you.
Source files are located in the `src/`, unit tests are in `test/`. Compiled files are in `output/`. 
Executing `run.sh` will compile the source code in your environment and generate a `.jar` file in the `output/` folder.


### Method 1: Compile it on your machine
If you do not have Java JDK setup, check out the last `Setup Java JDK` section below first.
1. Download the `CreateDirectory.zip` file: https://drive.google.com/drive/folders/1MqDJCwd3is793AeqPpOC44CefpcwlXAd?usp=sharing, then unzip it.
2. In your terminal, go to the directory where the unzipped files are
3. If you have Java JDK installed on your machine, you can follow the below instructions. Otherwise, follow instructions on the **Use Docker** section
4. You should be inside the `CreateDirectory` folder. There are two shell scripts `run.sh` and `test.sh`. First, run
   ```
   chmod 755 run.sh
   chmod 755 test.sh
   ```
   then, run the following line to start the program.
   ```
   ./run.sh
   ```
   You'll see the terminal is waiting for your input to interacting with the program. Example commands:
   ```
   CREATE fruits
   CREATE vegetables
   CREATE grains
   CREATE fruits/apples
   CREATE fruits/apples/fuji
   LIST
   CREATE grains/squash
   MOVE grains/squash vegetables
   CREATE foods
   MOVE grains foods
   MOVE fruits foods
   MOVE vegetables foods
   LIST
   DELETE fruits/apples
   DELETE foods/fruits/apples
   LIST
   ```
5. Enter `q` or `Q` to exit.
6. Run the test script:
   ```
   ./test.sh
   ```
   It runs the above example commands one by one and generates output to the terminal.

### Method 2: Use Docker
If you do not have docker setup, check out the [docker docs](https://docs.docker.com/desktop/install/mac-install/).
1. Pull the image from Docker Hub
   ```
   docker image pull jingnu/directory-project:latest
   ```
2. Create and run a new container from the image we just pulled
   ```
   docker run -it jingnu/directory-project:latest
   ```
3. Start input command to the program, enter `q` or `Q` to exit.

### Method 3: Run the .jar file directly
   I've complied and created a jar file from my local dev environment. You can try to run the jar file directly:
1. Download the `CreateDirectory.zip` file: https://drive.google.com/drive/folders/1MqDJCwd3is793AeqPpOC44CefpcwlXAd?usp=sharing, then unzip it.
2. In your terminal, go to the directory where the unzipped files are, you should be inside the `CreateDirectory` folder.
   ```
   java -jar output/directory-project.jar
   ```

---
### Setup Java JDK

1. Visit the Oracle Java SE Downloads page: [https://www.oracle.com/java/technologies/downloads/#java17](https://www.oracle.com/java/technologies/downloads/#java17)

2. Accept the license agreement and download the appropriate JDK distribution for your operating system. Choose the version that suits your needs (e.g., JDK 17).

3. Once the download is complete, locate the downloaded file and run the installer.

4. Follow the instructions provided by the installer to complete the installation process. Make sure to select the desired installation location.

5. After the installation is complete, set up the `JAVA_HOME` environment variable:

   - On Windows:
      - Open the System Properties dialog by pressing Win + Pause/Break or right-clicking on "This PC" and selecting "Properties".
      - Click on "Advanced system settings".
      - In the System Properties dialog, click on the "Environment Variables" button.
      - In the "System Variables" section, click on "New".
      - Enter `JAVA_HOME` as the variable name and the installation path of the JDK (e.g., `C:\Program Files\Java\jdk11`) as the variable value.
      - Click "OK" to save the variable.
      - In the "System Variables" section, locate the "Path" variable and click on "Edit".
      - Add `%JAVA_HOME%\bin` at the beginning of the "Variable value" field.
      - Click "OK" to save the changes.

   - On macOS or Linux:
      - Open a terminal.
      - Edit the `.bash_profile` or `.bashrc` file in your home directory using a text editor.
      - Add the following line at the end of the file:
        ```shell
        export JAVA_HOME=/path/to/jdk
        ```
        Replace `/path/to/jdk` with the actual installation path of the JDK.
      - Save the file and exit the text editor.
      - Run the following command to apply the changes:
        ```shell
        source ~/.bash_profile
        ```
        or
        ```shell
        source ~/.bashrc
        ```

6. To verify that Java JDK is installed correctly, open a new terminal or command prompt window and run the following command:
   ```shell
   java -version
   ```
   It should display the version information of the installed Java JDK.

---
### The problem
A common method of organizing files on a computer is to store them in hierarchical directories. For instance:

```
photos/
  birthdays/
    joe/
    mary/
  vacations/
  weddings/
```

In this challenge, you will implement commands that allow a user to create, move and delete directories.

A successful solution will take the following input:

```
CREATE fruits
CREATE vegetables
CREATE grains
CREATE fruits/apples
CREATE fruits/apples/fuji
LIST
CREATE grains/squash
MOVE grains/squash vegetables
CREATE foods
MOVE grains foods
MOVE fruits foods
MOVE vegetables foods
LIST
DELETE fruits/apples
DELETE foods/fruits/apples
LIST
```

and produce the following output

```
CREATE fruits
CREATE vegetables
CREATE grains
CREATE fruits/apples
CREATE fruits/apples/fuji
LIST
fruits
  apples
    fuji
grains
vegetables
CREATE grains/squash
MOVE grains/squash vegetables
CREATE foods
MOVE grains foods
MOVE fruits foods
MOVE vegetables foods
LIST
foods
  fruits
    apples
      fuji
  grains
  vegetables
    squash
DELETE fruits/apples
Cannot delete fruits/apples - fruits does not exist
DELETE foods/fruits/apples
LIST
foods
  fruits
  grains
  vegetables
    squash
```

**Helpful hints** 

Please solve the challenge on your own and without using any helper libraries as this would not show us the skillset we are interested in.
Your solution should not actually create folders on the host machine.
Your solution should take the above input and produce exactly the output shown above.
