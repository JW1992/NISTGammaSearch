# NIST Gamma Data
NIST Gamma Data is an Android application to search Gamma ray attenuation data from locally saved NIST database.

The advantage over the original website is:
	(Of course) offline data accessary;
	Faster approach;
	Most importantly, it can search for the best shielding material based on user specification!

## Getting Started

# Prerequisites
[Android Studio](https://developer.android.com/studio/install.html) is needed.

## Build path
See app/build/outputs/apk/debug/app-debug.apk for current version.

## Built With
- Java
- [SQLite](https://www.sqlite.org/) - Data management

## Version
# Version 0.1
Added basic elements and materials. User can search for (1) an element using atom number/symbol/full name and (2) a material using full name/partial name. The searching is not case-sensitive.
The attenuation coefficient is displayed in a listmode. The user can then calculate the attenuation by clicking "Calc. Atten." button and input the desired gamma ray energy at an arbitrary distance.

## Authors
- Jiawei Xia

## Acknowledgements
All the NIST files are fetched from:
https://physics.nist.gov/PhysRefData/Xcom/html/xcom1.html


