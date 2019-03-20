#!/bin/bash
set -e

git clone https://github.com/wasdev/ci.common.git ./ci.common
cd ./ci.common
mvn clean install