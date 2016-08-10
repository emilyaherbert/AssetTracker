#!/bin/bash

clear

echo "Starting: Script."
echo "Hello, $USER!"

n=1
while [ $n -le 10 ]
do

echo " "
echo "Starting: Reading time, ID, and RSSI of nearby Bluetooth devices into fileOne_1.txt."
sudo hcitool lescan --duplicates & sudo hcidump -t | egrep '201|bdaddr|RSSI' > LocationData/fileOne_1.txt &
TASK_PID=$!
sleep 30
kill $TASK_PID
echo "Completed: Reading time, ID, and RSSI of nearby Bluetooth devices into fileOne_1.txt."

echo " "
echo "Starting: Organzing fileOne_1.txt into XML in fileTwo_1.xml."
scala scriptOne.scala
echo "Completed: Organzing fileOne_1.txt into XML in fileTwo_1.xml."

echo " "
echo "Starting: Pushing fileTwo_1.xml to GitHub."

rm -r -f .git
git init
git remote add origin https://github.com/eherbert/AssetTracker.git
sudo git config user.name "eherbert"
sudo git config user.email "eherbert@trinity.edu"
git config credential.helper store

git checkout -b LocationData1
git add LocationData/fileTwo_1.xml LocationData/fileOne_1.txt
git commit -m"update"
git push -f origin LocationData1
echo "Completed: Pushing fileTwo_1.xml to GitHub."

done

sudo reboot
