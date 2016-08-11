# AssetTracker

This repository houses my and Kyle Hudgins' submission to the General Electric Information Technology Leadership Program Intern Innovation Project. We have designed a system of asset tracking using Raspberry Pi 3 Model B's and Estimote Beacons with Bluetooth Low Energy.

The systems utlizes a network of Pi's and Estimotes such that multiple Pi's recieve signals from multiple Estimotes, and the user is able to determine which Pi any given Estimote is closest to. Each Estimote pings an advertisement packet, and this advertisement packet is recieved and interpretted by each Pi within range. Each Pi controls its own data collection, and one 'mother Pi' is additionally tasked with interpreting and displaying the resulting information to the user.

This repository does not operate in the same way that a normal GitHub repository might. The master branch is the starting point for the configuration of each Pi in the network, but each Pi has its own branch that it constantly pushes its data to (labeled LocationData#). The mother Pi then pulls these seperate branches and groups the data by Estimote ID and pushes the result to its own branch (labeled AssetLocationData).

In this instance we have three Pi's running, each pushing to its own branch its own set of data. Pi 1 is additionally tasked with compiling the information and it pushes the final result to branch AssetLocationData.
