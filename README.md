# MLQuery

A small java command line application for running adhoc queries against MarkLogic.

## Usage

MLQuery takes a script from the standard input and writes the result to the standard output. It requires the user to provide the server, port, username and password.

An example invocation may look like this:

```bash
echo 'xdmp:host-name()'|java -jar mlquery.jar -u <username>:<password> -s <server>:<port>
```

The output of this line would be the hostname of the MarkLogic server it is being run against
