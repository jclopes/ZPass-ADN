ZPass-ADN
=========

Generate strong passwords from a salt and a keyword.

Using the same password everywhere is a big mistake that we all commit and we keep it as our dirty secrect that we think nobody suspects we are doing it.

But how are we suppose to remember all those "strong" passwords for the millions of sites and services and apps...

Tired of having to memorize passwords or saving them somewhere I decided to create a method that would generate and reproduce a password for me whenever I need one.

It works just like a one password kind of service but the password is never stored or shared. It will walways be generated on the spot. Another advantage is that you don't have to trust anyone with your password (except the service where you use it to login).


How to use ZPass
----------------

To generate a password you need two things:
 - Salt    - this is used to make it even harder to break your passwords should be something personal that you can always recall. (ex: myMomFirstName)
 - Keyword - this will be the only secret that you will ever need to memorize. (ex: password)
 - URI     - this is a reference to the service the password belongs to. (ex: facebook.com)
 
The generated password will look like this:
 978ac6e970b5bce9c1524bccb46d8d43


How it works
------------

The password is the _md5sum_ of your _Salt_+_URI_+_Keyword_
To keep it safe, the _Keyword_ should never be disclosed.
The _URI_ is public information and the _Salt_ is semi-private.

To be clear, this algorithm is as good as using a very long password. But it makes using a strong password everywhere a lot easier.
