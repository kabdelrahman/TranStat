[![Build Status](https://travis-ci.org/kabdelrahman/TranStat.svg?branch=master)](https://travis-ci.org/kabdelrahman/TranStat) [![Coverage Status](https://coveralls.io/repos/github/kabdelrahman/TranStat/badge.svg?branch=master)](https://coveralls.io/github/kabdelrahman/TranStat?branch=master)
# TranStat

Receive transactions from one side and provide statistics on it from the other side.

# Clean / Compile and Test


Run this command: `sbt clean compile test`

# Run

Run this command: `sbt run`

Application is running by default on `localhost` on port `1234`

# Package

To package the project into a jar you can run `sbt assembly` and it will run all tests and will produce a jar in `target/transtat-{$version}-assembly.jar`.

# API

You can access swagger API documentation through this endpoint `http://localhost:1234/api-docs/swagger.json` (_you need to run the project first_)
Feel free to copy and paste the output json to [http://editor.swagger.io/](http://editor.swagger.io/#/)

----

### License

The library is Open Source Software released under the MIT license. See the LICENSE file for details.
