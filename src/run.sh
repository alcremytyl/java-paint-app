#!/usr/bin/sh

javac --module-path 
java --module-path $JAVA_FX_HOME --add-modules $JAVA_FX_MODULES Main
