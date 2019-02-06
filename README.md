## Overview
This is a trivial example that demonstrates mixing Spring Security with Spring CORS support.

This example is hard-coded to use headless chrome. You'll need to have chrome version 71+ installed.

## Notes

### Running Integration Test:
This solution uses chromedriver to execute javascript in the project.

Chromedriver requres chrome version 71+ to be installed. Chrome is
not automatically installed.

Chromedriver is automatically installed in the .libs directory 
(which is in the .gitignore).

You can manually install it:
```./download_chrome_driver.sh```

However, when running tests, this script is automatically run every time.
The script only downloads the driver if it is missing from the ./libs 
directory.

This download the correct version based on your current OS (only 
verified on Linux, Mac, and Windows 10).

Run the tests:
```./gradlew test```

You'll see chrome open up three times. Have a look at ApplicationTest.java to see what it is testing.

The Application:

It has 2 endpoints:
* GET /greeting
* POST /greeting

It is configured to support CORS request on all GET requests. It rejects all other CORS requests.

Running the Main class starts both the server and the client.

To see the work (after cloning the repo):
* Run the system:
```./gradlew bootRun```
* Go to your favorite browser, open on the javascript console
* Go to http://localhost:9000/index.html
  * Click on the GET button, you should see a successful result
  * Click on the POST button, you should see a failed request
* Go to http://localhost:8080/idex.html
  * Click on the GET button, you should see a successful result
  * Click on the POST button, you should see a successful result

