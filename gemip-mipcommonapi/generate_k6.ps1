<#
.Description
Script per creare un test k6 dal file openapi.yaml
.EXAMPLE
PS> .\generate_k6.ps1 -collection_name 'quarkus_backend'
#> 

docker run -v ${PWD}:/tmp --rm docker-trusted.ecosis.csi.it/openapitools/openapi-generator-cli:v6.2.1 generate -i /tmp/src/main/resources/META-INF/openapi.yaml -g k6 -o /tmp/k6