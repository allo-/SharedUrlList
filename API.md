# The SharedUrlList API

## Warning: API is unfinished
At the moment, the API is not considered stable. 
It will be extended for more features, and maybe even some of the current functions will change.

## Concept
The API uses HTTP with only one URL using GET-Parameters to call the different API-Functions.
The data format for the responses is JSON.

This allows server implementations to be simple, without any special requirement i.e. for mod_rewrite.

## The API
To call the API-Functions, you need to set the GET-Parameter "api" to "true".

### Success / Error
Every API-Function returns a map, containing a "status" key, which is either:

* "success", possibly combined with other key-value pairs
* or "error" combined with a "errormessage" entry, which contains a human-readable error message.

### Methods
The parameters of the following methods are given as GET-Parameters to the server URL, i.e. "http://server/myapi/?api=true&token=foo&add=true&url=http%3A%2F%2Fwww.example.com".
Of course, all parameters should be URL encoded.

#### Tokenrequest
Parameters:

* user: the username, the token should be created for
* password: the password of the user
* device: the name of the device

returns:

* success status with {"token": "THE-NEW-TOKEN"} when everything went fine
* error status with an error message, if something went wrong (i.e. wrong password)

#### Get URLs
Parameters:

* token: an active device token

returns:

* success with "hosts" as a list of hosts, as described below.
* error with an error message

##### device-URL-map structure
URL: a map:

* id: a unique url id
* link: the actual URL
* created: the date/time the entry was created as human readable string

host: a map:

* hostname: name of the device
* urls: a list of URLs, as defined above

example:
```
{
"status": "success",
"hosts": [
    {
        "hostname": "examplehost",
        "urls": 
        [
            {"link": "http://example.com", "id": 1, "created": "1.1.1970 00:00"},
            {"link": "http://example.com/example/", "id": 2, "created": "yesterday"},
        ]
    }
]
}
```

#### Add an URL
Parameters:

* token: an active device token
* url: the URL to be added

returns: 

* success with no additional data
* error with errormessage, if something went wrong

The user and device name are inferred on the server from the token.

#### Delete an URL
Parameters:
* token: an active device token
* id: the id of the URL to be deleted

returns:

* success with no additional data
* error with errormessage, if something went wrong

The user and device name are inferred on the server from the token.
