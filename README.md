# Guess Who

## Description
Guess Who is an interactive crime-solving game developed by a team of 3 as a group project in Software Engineering 206. 

The game allows players to explore detailed crime scenes, uncover interactive clues, and chat with suspects powered by OpenAI's GPT. Each suspect has a unique personality and responds intelligently, creating a lifelike and engaging investigative process.


## To setup the API to access Chat Completions and TTS

- add in the root of the project (i.e., the same level where `pom.xml` is located) a file named `apiproxy.config`
- put inside the credentials that you received from no-reply@digitaledu.ac.nz (put the quotes "")

  ```
  email: "UPI@aucklanduni.ac.nz"
  apiKey: "YOUR_KEY"
  ```
  These are your credentials to invoke the APIs. 

  The token credits are charged as follows:
  - 1 token credit per 1 character for Googlel "Standard" Text-to-Speech. 
  - 4 token credit per 1 character for Google "WaveNet" and "Neural2" Text-to-Speech.
  - 1 token credit per 1 character for OpenAI Text-to-Text.
  - 1 token credit per 1 token for OpenAI Chat Completions (as determined by OpenAI, charging both input and output tokens).


## Free TTS

There is a free TTS service available for testing purposes. You will see this in the `nz.ac.auckland.se206.speech.FreeTextToSpeech` class. The voice here is not as good as the Google and OpenAI TTS services, but it is free and can be used for testing purposes.

You will see an example of this in the `ChatController` class. 



## To setup codestyle's API

- add in the root of the project (i.e., the same level where `pom.xml` is located) a file named `codestyle.config`
- put inside the credentials that you received from gradestyle@digitaledu.ac.nz (put the quotes "")

  ```
  email: "UPI@aucklanduni.ac.nz"
  accessToken: "YOUR_KEY"
  ```

 these are your credentials to invoke gradestyle

## To run the game

`./mvnw clean javafx:run`

## To debug the game

`./mvnw clean javafx:run@debug` then in VS Code "Run & Debug", then run "Debug JavaFX"

## To run codestyle

`./mvnw clean compile exec:java@style`