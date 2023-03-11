# 03 Beer Service Procesar JSON with Spring Boot

01. Agregar opciones para el manejo del JSON, 
02. Modificacion de los casos de prueba se utiliza @SpringBootTest en lugar de @WebMvcTest
03. The main difference between @SpringBootTest and @WebMvcTest annotations is that :
    @SpringBootTest annotation will start the full application context. 
    Whereas @WebMvcTest annotation will make Spring Framework create application context with a limited number of beans(only those related to the Web Layer)