#!/bin/bash

clear

n=1
while [ $n -le 10 ]
do

rm estimotePartNumberList.xml
wget https://raw.githubusercontent.com/eherbert/InnovationProject/AssetLocation/estimotePartNumberList.xml

rm piLocationList.xml
wget https://raw.githubusercontent.com/eherbert/InnovationProject/AssetLocation/piLocationList.xml

cd PulledData
rm -rf *
wget https://raw.githubusercontent.com/eherbert/InnovationProject/LocationData1/LocationData/fileTwo_1.xml
wget https://raw.githubusercontent.com/eherbert/InnovationProject/LocationData2/LocationData/fileTwo_2.xml
wget https://raw.githubusercontent.com/eherbert/InnovationProject/LocationData3/LocationData/fileTwo_3.xml

cd ..
scala scriptTwo.scala

git checkout AssetLocation
git commit -am "update"
git push -u -f origin AssetLocation

done

sudo reboot
