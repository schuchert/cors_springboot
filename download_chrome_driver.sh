#!/bin/bash

FALLBACK_VERSION='75.0.3770.90'
BASE_URI="https://chromedriver.storage.googleapis.com"

function downloadDriver() {
    local name=$1
    local version=$2
    driver="chromedriver_$name.zip"
    local uri="$BASE_URI/$version/$driver"
    curl $uri > $driver
    unzip $driver
}

function driverOsName() {
    os=`uname`
    
    case $os in
    Linux)
      name='linux64'
    ;;
    Darwin)
      name='mac64'
    ;;
    *)
      name='win32'
    ;;
    esac
}

function version() {
    os=`uname`
    
    case $os in
    Linux)
      version=`google-chrome --version | awk '{ print $3; }'`
    ;;
    Darwin)
      version=$FALLBACK_VERSION
    ;;
    *)
      version=$FALLBACK_VERSION
    ;;
    esac
}

function moveDriver() {
    mkdir -p libs
    mv chromedriver libs
    rm $driver
}

if [ ! -f ./libs/chromedriver ]; then
    driverOsName
    version
    downloadDriver $name $version || downloadDriver $name $FALLBACK_VERSION
    moveDriver 
fi
