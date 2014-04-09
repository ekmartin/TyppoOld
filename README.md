Typpo
=====================

This game was created as our entry for the game competition in the course TDT4100 (Introduction to Object Oriented Programming). 

## Built JARs
OS X: [JAR](https://github.com/ekmartin/Typpo/releases/download/1.0/TyppoOSX.jar)

Windows: [JAR](https://github.com/ekmartin/Typpo/releases/download/1.0/TyppoWindows.jar)/[EXE](https://github.com/ekmartin/Typpo/releases/download/1.0/TyppoWindows.exe)

Linux: [JAR](https://github.com/ekmartin/Typpo/releases/download/1.0/TyppoLinux.jar)/[SH](https://github.com/ekmartin/Typpo/releases/download/1.0/TyppoLinux.sh)

Server: [JAR](https://github.com/ekmartin/Typpo/releases/download/1.0/TyppoServer.jar)

## About
Typpo is a typing game where you play either alone or versus an opponent over the internet. The goal of the game is simple, write the words on the falling blocks and survive as long as possible. 

The music in the game was created by [martinhath](https://github.com/martinhath). 

![Typpo](http://i.imgur.com/NJ8qFAA.png)


## Running
If you want to run the code without the pre-built JARs you need to add the JARs in the lib folder to your classpath. Additionally you need to mark the lib-folder in lib/kryonet as a sources root. You also need to add the following to your VM-options:

```
-Djava.library.path=lib/lwjgl-2.9.1/native/macosx
```
