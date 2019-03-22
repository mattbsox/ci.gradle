#!/bin/bash
set -e

git clone https://github.com/wasdev/ci.common.git ./ci.common
cd ./ci.common
mvn clean install
cd ..
git clone https://github.com/mattbsox/ci.ant.git ./ci.ant
cd ./ci.ant
git checkout skipCaching
mvn clean install -DskipTests=true