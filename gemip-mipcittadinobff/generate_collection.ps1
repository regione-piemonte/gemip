<#
.Description
Script per creare una collection postman dal file openapi.yaml
.PARAMETER collection_name
Nome della collection che si vuole generare
.EXAMPLE
PS> .\generate_collection.ps1 -collection_name 'quarkus_backend'
#> 
param([Parameter(Mandatory=$true,HelpMessage="Inserire il nome della collection che si vuole generare")] $collection_name)
docker run -v ${PWD}:/app docker-base.ecosis.csi.it/openapi-test-generator:4.2.0-1.8.5-node16-csi-r1 openapi2postmanv2 -s src/main/resources/META-INF/openapi.yaml -o "$collection_name.json" -p -O folderStrategy=Tags,includeAuthInfoInExample=false