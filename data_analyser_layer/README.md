# Data Analyser Layer

## Workflow
* Consumes message from rabbitmq sent by the data collection layer
* Analyses the restaurant data in postgres and prepare matplotlib graphs for it
* Store the matplotlib graphs to an external storage

## DB
* Using Postgres addon on Heroku.
* Refers to the restaurants table on heroku to do the analysis

## Routes
* Paginated route to return the list of restaurants from the restaurants table on heroku Postgres