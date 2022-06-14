# Cheat Sheet for Tutorial Commands


## Install and configure libraries

### Apollo Gradle plugin

Include the dependency in `app/build.gradle.kts` in the `plugins` block
```kts
id("com.apollographql.apollo3").version("3.1.0")
```

Then configure the plugin settings

```kts
apollo {
    packageName.set("com.example.rocketreserver")
}
```

### Apollo runtime

Include the dependency in `app/build.gradle.kts` in the `dependencies` block
```kts
implementation("com.apollographql.apollo3:apollo-runtime:3.1.0")
```

## Adding the GraphQL schema

Generate the schema from introspection with a Gradle command

```shell
./gradlew :app:downloadApolloSchema --endpoint='https://apollo-fullstack-tutorial.herokuapp.com/graphql' --schema=`pwd`/app/src/main/graphql/com/example/rocketreserver/schema.graphqls
```

## Writing your first Query
Run the query in Apollo Explorer, then save it in `app/src/main/graphql/LaunchList.graphql`
```graphql
query LaunchList {
  launches {
    cursor
    hasMore
    launches {
      id
      site
    }
  }
}
```

## Exectuing your fist Query

TODO

