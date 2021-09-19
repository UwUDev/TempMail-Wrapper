# :envelope: TempMail API wrapper
A fast and simple API wrapper for https://temp-mail.org  
  
Please note that you need to get an rapidapi API key.  
See details on https://rapidapi.com/Privatix/api/temp-mail
## Creating a MailBox
To create a random MailBox with all displonible mails you just have to create a new object with your API key : 
```Java
TemporaryMailBox mailBox = new TemporaryMailBox("Your-API-Key");
```
  
But you can also directly force to use a specific mail like this :  
```Java
TemporaryMailBox mailBox = new TemporaryMailBox("Your-API-Key", "sample@mailkept.com");
```

## Getting emails on the MailBox
To get emails on a MailBox you simply have to do that
```Java
mailBox.getEmails()
```  
This will return you an array of Mail objects.

## Waiting a new Email  
You can also wait a new Email with a timout *(in milliseconds)* like this:  
```Java
if (mailBox.waitNewMail(45000))
  System.out.println(Arrays.toString(mailBox.getEmails()));
else System.out.println("Timed out... No emails received");
```
This will return a boolean that will be `true` if you received an email before the timeout delay.  
You can also not set a timeout and it will just wait forever until you receive an Email  
```Java
mailBox.waitNewMail();
System.out.println("New received!");
```

## Getting available domains
```Java
String[] domains = mailBox.availableMails();
System.out.println(Arrays.toString(domains));
```  
This will return you an array for Strings starting with `@...`

## Generaing a random email
```Java
mailBox.randomMail()
```
This will return you a random email from available domains wich is used when you don't force the mail on the MailBox.  
The mail start with random 33 hex chars like this `8013d2cb811f45f1b00df00076d1614b@mailkept.com`
