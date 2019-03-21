#!/bin/bash
set -e

export JAVA_HOME=${JAVA_HOME:-/c/wasdevTemp/jdk}
export MAVEN_HOME=${MAVEN_HOME:-/c/wasdevTemp/maven}
export PATH=${JAVA_HOME}/bin:${MAVEN_HOME}/bin:${PATH}

rm -rf /c/wasdevTemp
mkdir -p /c/wasdevTemp