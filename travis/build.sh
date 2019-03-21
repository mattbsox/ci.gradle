#!/bin/bash
set -e

export GRADLE_OPTS="-Dorg.gradle.daemon=true -Dorg.gradle.jvmargs='-XX:MaxPermSize=1024m -XX:+CMSClassUnloadingEnabled -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Xmx2048m'"
./gradlew install check -Druntime=$RUNTIME -DruntimeVersion=$RUNTIME_VERSION --stacktrace --info --no-daemon