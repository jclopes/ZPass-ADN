ZPass-ADN
=========

Generate strong passwords from a salt and a keyword.

Using the same password everywhere is a big mistake that we all commit and we keep it as our dirty secrect that we think nobody suspects we are doing it.

But how are we suppose to remember all those "strong" passwords for the millions of sites and services and apps...

Tired of having to memorize passwords or saving them somewhere I decided to create a method that would generate and reproduce a password for me whenever I need one.

It works just like a one password kind of service but the password is never stored or shared. It will walways be generated on the spot. Another advantage is that you don't have to trust anyone with your password (except the service where you use it to login).


How to use ZPass
----------------

To generate a password you need tree things:
 - Salt    - this is used to make it even harder to break your passwords should be something personal that you can always recall. (ex: myMomFirstName)
 - Keyword - this will be the only secret that you will ever need to memorize. (ex: password)
 - URI     - this is a reference to the service the password belongs to. (ex: facebook.com)
 
The generated password will look like this:
 7a70b4e7059a7d283c883288be3de0bed02d10fda4602c8de4699debcf6afbf2

The first time you use __ZPass__ you need to go to the settings and create your _Salt_.
Then just type your _Keyword_ (this is the thing you want to remember forever) and then type in the _URI_ field some text that identifies the service you're going to use. I like to use domain names like "facebook.com". Use something logic because __you will have to remember these values__.


How it works
------------

The password is the _sha256_ of your _Salt_+_URI_+_Keyword_.
To keep it safe, the _Keyword_ should never be disclosed.
The _URI_ is public information and the _Salt_ is semi-private.


Disclaimer
----------

To be clear, this algorithm is as good as using a very long password. But it makes using a strong password everywhere a lot easier.

If you forget any of the three parts of the password (_Keyword_, _URI_, _Salt_), you won't be able to recover it. These values are not saved anywhere. And that is the beauty of it.

The algorithm is case sensitive, so be aware of where you use capital letters.

Use it at your own risk.

This code is still in beta state and I might/will change the algorithm.