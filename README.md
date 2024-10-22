# Contabilidad básica empresarial

Proyecto desarrollado con spring boot, Spring Web, Spring Security, thymeleaf y base de datos postgree en nube, en el cual se refleja un CRUD en busca de organizar y controlar, de manera basica, los ingresos y egresos de una Empresa realizado por medio de los empleados autorizados.

Se trabajo con la arquitectura MVC y patrón de desarrollo TDD.

Se realizaron pruebas unitarias en JUnit 5 y Mockito.

![image](https://user-images.githubusercontent.com/71468355/217278761-24436226-de5d-4bb6-9275-1cea82a8fa3a.png)

El proyecto usa Java 1.8, Maven y Springboot 2.7.8., Spring Security, JUnit5, Mockito, Thymeleaf y postgre en nube (supabase.com).

Lo que se busca con este proyecto, es generar un dashboard básico con el cual se lleve un control de transacciones financieras realizada por
los empleados de la respectiva empresa. Dentro de las lmitaciones como usuario normal, solo está el poder crear un perfil y registrar transacciones.
Como usuario administrador, está el poder registrar, modificar o eliminar los objetos empresa, empleado y transacciones.

## Estructura de carpetas del proyecto

* **Codigo Java**: consta del `pom.xml` y la carpeta `src`
* **Herramientas externas**: 
    * *Spring Security: Libreria necesaria para autenticación y autorización.
    * *base de datos postgree*: La base de datos empleado está en la nube. Se puede crear una en https://supabase.com/  
    * *JUnit 5: Herramienta para realizar pruebas unitarias.
    * *Mockito: Herramienta para desligar dependencias en pruebas unitarias.
* **Documentacion**: La funcionalidad del programa es práctica y se puede aplicar en la sesión de pruebas.

---

## Despliegues

* [JDK y Maven]
* [EC2]

---

## Pruebas

![image](https://user-images.githubusercontent.com/71468355/219412819-6746f050-21be-41e7-b855-b7ad61c9ab3b.png)

Para usar la aplicación web debes ingresar tu correo y contrasena registrada en base de datos
o si aún no estás registrado, debes registrar tu usuario siguiendo el enlace "Create una cuenta!".
Posteriormente deberás llenar los siguientes datos:

![image](https://user-images.githubusercontent.com/71468355/219413365-2538d0eb-8ff5-431a-9201-f7c829ba3485.png)

Finalmente podras realizar un login e interactura con la aplicación web.

---

## Configuracion

En la ruta *src/main/resources/* se encuentran los archivos de properties de spring y la aplicacion que son la configuracion predeterminada, estas variables se pueden configurar fuera del archivo usando variables de ambiente o variables del server donde esta corriendo.

El orden de prioridad es de la siguiente manera: 
`ambiente > server > archivos .properties`


---

## Contacto

Si tienes dudas sobre este u otro proyecto, o quieres contactarme, puedes realizarlo por medio de
mi mail john.chamorroc@gmail.com o mi [perfil](https://www.linkedin.com/in/john-edisson-chamorro-coral-76ab74228/) de LinkedIn
