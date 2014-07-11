nonsql-app-bluemix
==================

This is an application working with nonsql DB--MongoLab, running on IBM Bluemix.

==================================
What you'll need before next step?
==================================

1. A Bluemix ID, apply from https://ace.ng.bluemix.net
2. Install Cloud Foundry Command Line Too on your local -- CF CLI

=============================
How to run this application?
=============================

1. Download this app to your local, and packaged to a WAR file, for example nonsql-app-bluemix.war
2. Log in Bluemix with CF CLI
3. Push application with below command: cf push app_name -p nonsql-app-bluemix.war
4. Log in Bluemix UI https://ace.ng.bluemix.net with your Bluemix ID
5. Bind a MongoLab service to your application deployed at #2
6. Restart it.
7. Access your application with http://app_name.mybluemix.net
