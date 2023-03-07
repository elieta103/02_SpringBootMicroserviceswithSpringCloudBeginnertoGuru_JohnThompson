# 02 Beer Brewery Agregar documentacion

01. Agregar dependencias y plugin ascii doctor
02. Crear directorio src/main/asciidoc
03. Creacion del template basico  index.adoc en : src/main/asciidoc
04. Se deben crear los snippets(fragmentos) y el plugin los insertara de acuerdo al template, se crean dentro de los tests.
05. Configurar Spring Mock MVC en los test cases :
   - @ExtendWith(RestDocumentationExtension.class)
   - @AutoConfigureRestDocs
06. Modificar el import :
  - Remover : import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
  - Agregar : import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*; 
07. Para Documentar las restricciones de los DTO
  - Crear directorio : test/resources
  - Crear subdirectorios : org/springframework/restdocs/templates
  - Crear archivo : request-fields.snippet
  - Realizar un clean para refrescar la estructura
  - Al correr el test =>  org.springframework.restdocs.mustache.MustacheException$Context: No method or field with name 'constraints' on line 8
  - Se debe agregar :  private static class ConstrainedFields 
  - Agregar : ConstrainedFields fields = new ConstrainedFields(BeerDto.class);
  - Reeemplazar : fieldWithPath -> fields.withPath
08. Personalizar la URI
  - @AutoConfigureRestDocs(uriScheme = "https", uriHost = "dev.springframework.guru", uriPort = 80)
  - clean & package
  - Verificar en curl-request.adoc
09. Agregar el plugin para agregar la documentacion desde /generated-docs hacia /static/docs
10. Recomendable hacer un clean & package  despues de los cambios y revisar el target/
11. La publicacion final de la documentacion : http://localhost:8080/docs/index.html