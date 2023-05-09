# Coding challenge (Leaving company name out so that it's not searchable ;)
 
## How to run

- to run the application 
```shell
./mvnw spring-boot:run
```
- to run the tests

```shell
./mvnw test
```

## Assumptions

- no concurrency controls are necessary for this implementation
- a tap can be uniquely identified by the combination of a PAN , stopId, dateTimeUTC & tap type(on or off)

## Architecture, tech choice & rationales


- I chose to use spring boot because it's a fast way to bootstrap a project & there were no specific requirements to optimise for run time speed
- I think it's the best way to show how I would write enterprise backend apis, to show the use of layers to an application
- I chose to use mapstruct & lombok to speed up development time 
- H2 database - if I chose to use purely in memory data structures ``java.util.List`` or ``java.util.Map`` 
the code that matches tap ons to tap offs would be much less readable


## What I could do differently with more time

- better error handling
- a proper arg parser 
- useful help text
- optimised sql queries - the date property is duplicated to simplify the query logic at the expense of efficiency
- better test coverage, more cases 
- not include credentials in git - done ONLY for efficiency given the mock data 

