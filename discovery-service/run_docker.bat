docker run ^
  --name=eureka.cedesistemas.local ^
  --hostname eureka.cedesistemas.local ^
  --net cedesistemas_network ^
  -p 8761:8761 ^
  -e "eureka.client.serviceUrl.defaultZone=http://eureka.cedesistemas.local:8761/eureka" ^
  -d registry.cedesistemas.internal:5000/commerce/discovery-service:dev
