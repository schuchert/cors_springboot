This is a trivial example that demonstrates mixing Spring Security with Spring CORS support.

This example is hard-coded to use headless chrome. You'll need to have chrome version 71+ installed.

Preparing to Run Integration Test:
Run a script (One time only, though rerunning won't hurt)
```./download_chrome_driver.sh```

This creates a libs directory (ignored by git) with chromedriver. It should select your
OS and download the correct version (only verified on Linux, probably works on Mac, defaults to Windows otherwise).

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

