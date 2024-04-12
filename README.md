# yz-java-example

App.java演示jwt加解密範例.

後續請透過API拿到accessToken,並做JWT Verify Signature驗證

```bash
java -version   
openjdk version "22" 2024-03-19
OpenJDK Runtime Environment (build 22+36-2370)
OpenJDK 64-Bit Server VM (build 22+36-2370, mixed mode, sharing)
```
App.java
```java
------------ JWT header ------------
{"alg":"HS256","typ":"JWT"}
------------ JWT payload ------------
{"Request":{"password":"password","username":"username"},"Action":"AGLogin"}
------------ JWT Verify Signature ------------
Vo5ndKByIhZ2U4QfVHFawT734_xPRsksJn1RaoE55ro
------------ JWT ------------
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJSZXF1ZXN0Ijp7InBhc3N3b3JkIjoicGFzc3dvcmQiLCJ1c2VybmFtZSI6InVzZXJuYW1lIn0sIkFjdGlvbiI6IkFHTG9naW4ifQ==.Vo5ndKByIhZ2U4QfVHFawT734_xPRsksJn1RaoE55ro
------------ Decode JWT ------------
~~~~~~~~~ JWT Header ~~~~~~~
JWT Header : {"alg":"HS256","typ":"JWT"}
~~~~~~~~~ JWT Body ~~~~~~~
JWT Body : {"Request":{"password":"password","username":"username"},"Action":"AGLogin"}
```



測試
```bash
curl --location --max-time 10 --request POST '$(RUL)' \
--header 'Content-Type: text/plain' \
--data-raw '$(JWT)'
```
