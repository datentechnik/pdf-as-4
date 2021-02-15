Release notes

Overview of necessary steps
1. Release on Joinup page
2. Push code to public git repo: git.egiz.gv.at
3. Add release news to the website

1.Release on Joinup page
To add contribution on Joinup page, both release folder and maven repository are necessary.

Create release folder by running:

./gradle jar sourcesJar war distZip distTar releases

With this command, a release folder is created. 
Note: The release version should be without snapshot!
Release folder needs to be uploaded to https://apps.egiz.gv.at/releases/pdf-as/release/ 

Create mvn repo folder by running:

./gradle uploadArchives

The generated mvn repo needs to be uploaded to https://apps.egiz.gv.at/maven/at/gv/egiz/pdfas/

After this, the release on Joinup page is necessary. 
For this purpose, make sure to have corresponding role in PDF-AS project. 
Make a new "release" on PDF-AS page on Joinup. The release version should contain a new version number, distribution links, and notes about the major changes in that version.
https://joinup.ec.europa.eu/collection/e-government-innovation-center-egiz/solution/pdf 


2. Push source code with tag also to  http://git.egiz.gv.at/pdf-as-4/ (this is the public EGIZ GIT REPO) 

3. Add release notes to EGIZ or ASIT website! 
