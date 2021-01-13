## About
Application for local online radio named "Frankivsk Wave"
that allows users to listen to the radio, see the list of programs and schedule,
watch recent as well as recommended by the radio team videos
 and live streams from connected youtube channel.

## Description
Application consists of two modules: `radio-service-api`
(backend and admin panel of the application) and `radio-service-ui`
(UI of the application).
Each of them can be built and run independently for dev purposes
(as Java and React apps accordingly).

## Start locally
```
mvn <env variables: -DvariableName='value'> clean install
java <env variables: -DvariableName='value'> -jar <jar location>
```
## Environment variables:

#### Maven build env:
- REACT_APP_SITE_URL **(required)** - _your API url_
- REACT_APP_STREAM_URL **(required)** - _your radio stream url_

#### Java env:
- YOUTUBE_API_KEY **(required)** - _your youtube API key_
- YOUTUBE_CHANNEL_ID **(required)** - _id of connected channel_
- MONGO_DB_URI (_default_: 'mongodb://localhost/radio_service') - _URI for MongoDB_
- ADMIN_USERNAME (_default_: 'admin') - _admin panel username_
- ADMIN_PASSWORD (_default_: 'admin') - _admin panel password_

## Spring profiles
- default - *profile used in production*
- local - *run application for local development(No need in YOUTUBE_API_KEY)*
- production = *run application in production environment(YOUTUBE_API_KEY is required)*

## Docker

#### Local development:
```
mvn <env variables: -DvariableName='value'> clean install
docker-compose -f docker-compose-dev.yml up
```

#### Start standalone instance:
```
mvn <env variables: -DvariableName='value'> clean install
docker-compose -f docker-compose-prod.yml up
```