@echo off

echo Cleaning old classes...
if exist bin rmdir /s /q bin

echo Compiling Java files...

if not exist bin mkdir bin

set LWJGL_PATH=libs\lwjgl

set CLASSPATH=bin;
set CLASSPATH=%CLASSPATH%%LWJGL_PATH%\lwjgl.jar;
set CLASSPATH=%CLASSPATH%%LWJGL_PATH%\lwjgl-glfw.jar;
set CLASSPATH=%CLASSPATH%%LWJGL_PATH%\lwjgl-opengl.jar;


javac -d bin -sourcepath src src\main\Main.java

echo Running the program...
java -cp "%CLASSPATH%" -Dorg.lwjgl.librarypath=%LWJGL_PATH%\natives main.Main