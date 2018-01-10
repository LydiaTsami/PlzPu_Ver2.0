[![CircleCI](https://circleci.com/gh/Unbounds/trakt.svg?style=shield)](https://circleci.com/gh/Unbounds/trakt)

# trakt
Trakt android application

## How to make debug a build:

1. Create a trakt staging account on [Staging Trakt](https://staging.trakt.tv) and add some TV shows and movies to your list
1. Clone the project
1. Create `trakt.properties` file in the root folder of the project and add the following key-value combinations to it: `client_id` & `client_secret`

```properties
# trakt.properties example
# You can get these values from https://staging.trakt.tv/oauth/applications
staging_client_id=value
staging_client_secret=value
```
