# viacom_desktopWebProject

This is a Java Software Test Automation project which launches a browser on a desktop and execute the steps in the specified test scenarios, 
then generate a test report based on the actual test results. The project leverages TestNG automation framework, using Java as a scripting language, 
and Maven as a build management tool. Web browser automation leverages Selenium Webdriver tools.

## Features and capabilities
*multi browser test execution (supported browsers Chrome, Mozilla Firefox, Microsoft Edge)
*taking a screenshot upon test failure
*logging test scenario steps
*creating reports upon test execution

## Required Tools/Libraries/Dependencies
• Java 8
• Eclipse IDE
•	Maven 
•	Selenium/Webdriver

## Installation
1) Java 8. Check if java already installed in your system by running in your CLI:
```
java -version
```
If not istalled, follow the instructions here: https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html

2) Install the IDE (Integrated Development Environment) of your choice. We use latest version of Eclipse for java developers
(https://www.eclipse.org/downloads/packages/release/2020-06/r/eclipse-ide-java-developers)


##Usage
clone the repo

```
git clone https://github.com/evgeny2019/viacom_desktopWebProject.git
```
Go to your project path

```
 cd /your/project/path
```
## Running tests with Maven

The test scenarios are located in /viacom_desktopWebProject/src/test/java/viacom/testcases

You may run through Maven a single test class with all tests, or set the test property (-Dtest=testClass#testCase ) to a specific test case. 
```
mvn -Dtest=dailyShow_Test test OR mvn -Dtest=dailyShow_Test#getFooterLinksText
```
## Cross browser execution
supported browsers: Chrome, FireFox, MSEdge
- by default the browser is set to Chrome. This default setting is stored in application.properties file, located at src/test/resources/configurations, and can be changed if other browser needed.
- when running from Maven command line, you need to pass the browser property in CLI:
```
-Dbrowser=firefox (OR =edge, OR =Chrome)
```
## Log Networt Calls

You have the ability to log your project test's network calls. It will be displayed in console like below:
`387 performance log entries found`

`First call’s full URL that has domain "http://sc.cc.com": http://sc.cc.com/id?d_visid_ver=2.0.0&d_fieldgroup=A&mcorgid=ED7001AC512D2ABD0A490D4C%40AdobeOrg&mid=23819402477863281164047130615178736977&ts=1598018439677`

`Total calls that have domain http://sc.cc.com: 2`

To enable logging (currently works with Chrome browser only), you can provide the `logNetworkCalls` key in CLI (any non-empty value will work): 

` -DlogNetworkCalls=on`

## Reports, Screenshots

After each test run, the report is being created and saved to the folder `/test-output`

If the test failed, the screenshot would be takn and saved to .png file to folder `/screenshots`
