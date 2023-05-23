# MasivTodoApp

Esta aplicación es una muestra de como se puede crear un TODO App Utilizando buenas prácticas de desarrollo.
Los temas que se abarcan acá son:
- Clean Architecture
- MVVM
- ViewBinding
- Injección de dependencias
- Android Jetpack
- - Navigation
- - ViewModels
- - Room Database
- Unit Testing
- La aplicación fue creada para también ser usable en modo noche


## Aclaración de requerimiento (Solicitud de permisos para almacenar datos)
Uno de los requerimientos del proyecto era: "La aplicación debe solicitar permisos al usuario para acceder al almacenamiento del dispositivo y guardar
los datos de las tareas de forma persistente", este requerimiento fue reemplazado ya que para almacenar datos simples de la aplicación como es el caso,
el permiso es autopermitido. Para mostrar mi conocimiento en manejo de permisos, agregué la funcionalidad de tomar una foto de la tarea
allí gestiono todos los posibles estados del permiso de acceso a la cámara
- No solicitado
- Permitido
- Denegado

### Estado no solicitado
Cuando el permiso no ha sido solicitado al usuario, se ejecuta la interfaz nativa del sistema operativo para pedirle al usuario acceso a la cámara. Ver imagen.

<img src="https://github.com/diegocalero0/MasivTodoApp/blob/main/readmefiles/screenshot-systempermissionalert.png" width="150">

### Estado permitido
Cuando el permiso ya ha sido concedido por el usuario, se ejecuta la funcionalidad de tomar foto. Ver Imagen

<img src="https://github.com/diegocalero0/MasivTodoApp/blob/main/readmefiles/screenshot-camera.png" width="150">

### Estado denegado
Cuando el permiso ha sido solicitado al usuario y este lo ha denegado, se le muestra una alerta indicando que se requiere el permiso
Y que para activarlo lo debe hacer manualmente. En el botón "Configuración" de la alerta, abre la configuración de la app para
que el usuario active los permisos

<img src="https://github.com/diegocalero0/MasivTodoApp/blob/main/readmefiles/screenshot-handledpermission.png" width="150">
<img src="https://github.com/diegocalero0/MasivTodoApp/blob/main/readmefiles/screenshot-permissionalert.png" width="150">


## Clean Architecture
El proyecto implementa clean architecture con el fin de separar el acceso a los datos, del dominio del negocio, de la presentación de los datos.
Esto se puede evidenciar en el proyecto en la estructura de carpetas:
- data
Esta capa es la encargada de todo el proceso de acceso a los datos, en este caso Room Database.
- domain
Esta capa se encarga de contener los casos de uso del negocio y del repositorio como intermediario entre el negocio y los datos.
- presentation
Esta capa se encarga de contener todos los archivos de las vistas y de contener los ViewModels.

## MVVM
En el proyecto se utiliza el patrón MVVM para presentación de los datos.
Por un lado este patrón es el recomendado por Google
Por otro lado es ideal ya que permite tener una vista totalmente reactiva al cambio de los datos por medio de los observadores.

## ViewBinding
Desde que Google dejó de recomendar DataBinding y empezó a sugerir ViewBinding como su modelo de actualización de vistas, el código fuente
de las aplicaciones modernas es mucho más entendible y escalable.

## Injección de dependencias
Este proyecto utiliza Hilt para el manejo de dependencias, esto permite hacer código más testeable, escalable, y permite
a los desarrolladores concentrarse en crear más código funcional y menos código de configuración.

## Android Jetpack
El proyecto hace uso de varias librerías modernas de Android Jetpack en las que se destaca:

### Android Navigation
Permite crear un grafo de navegación visual y configurar parámetros entre las diferentes pantallas. Ver imagen

<img src="https://github.com/diegocalero0/MasivTodoApp/blob/main/readmefiles/screenshot-navgraph.png" width="300">

### ViewModels
Permite contener información de cada uno de las vistas sin que esa información se vea afectada a cambios externos de configuración como rotación de pantalla,
adicionalmente permite separar la lógica de la interfaz,
Se puede reutilizar en diferentes componentes,
Y permite hacer el código mas testeable y escalable

### Room Database
En el proyecto se utilizó Room Database para almacenar la información de las tareas

## Unit Testing
El proyecto tiene pruebas unitarias en las diferentes capas de la arquitectura. en la siguiente captura se puede observar como todas las
pruebas son exitosas.

<img src="https://github.com/diegocalero0/MasivTodoApp/blob/main/readmefiles/screenshot-unittest.png" width="300">


