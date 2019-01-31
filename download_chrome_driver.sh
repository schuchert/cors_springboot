#!/bin/bash

version='2.45'
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

driver="chromedriver_$name.zip"
uri="https://chromedriver.storage.googleapis.com/$version/$driver"

curl $uri > $driver
unzip $driver
mkdir -p libs
mv chromedriver libs
rm $driver
