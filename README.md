# proyecto-psa (Mono Repo)

## Repositorio para el Proyecto PSA

Se propone una estructura de mono-repsitorio. En un mismo proyeecto de Git se arma una estructura de directorios para soportar el producto PSA New Spring ERP.

## Estructura del Repositorio

```
proyecto-psa
│   README.md
│   .gitignore
└───develop           # proyecto Java SpringBoot (Desarrollo)
│   │   file011.txt
│   │   file012.txt
│   │
│   └───subfolder1
│       │   file111.txt
│       │   file112.txt
│       │   ...
└───testing           # proyecto de testing para el producto PSA New Spring ERP
│   │
│   └───subfolder1
│       │   file111.txt
│       │   file112.txt
│       │   ...
│
└───api-pagos         # proyecto de la API de pagos (terceros)
│   │   file021.txt
│   │   file022.txt
│   └───subfolder1
│       │   ...
│
└───deploy            # Proyecto con los scripts de deploy (operaciones)
│   │   file011.txt
│   │   file012.txt
│   │
│   └───subfolder1
```

## Ambiente de Desarrollo

Para configurar el ambiente de desarrollo y testing, se quiere la instalación y configuración del siguiente SW:

### Git

Desde la página oficial, descargar el instalar Git:

https://git-scm.com/

### GitHub

Desde la página oficial de Github:

https://github.com/

Crear o usar una cuenta personal.

### Java JDK

Instalar y configurar Java JDK o OpenJDK v11:

https://www.oracle.com/java/technologies/downloads/#java11-windows

NOTA: en caso de Windows, agregar Java en las variables de entorno.

### Maven

Instalar y configurar Maven:

https://maven.apache.org/download.cgi

NOTA: en caso de Windows, agregar Maven en las variables de entorno.

### IntelliJ

Como IDE de desarrollo, descargar e instalar IntelliJ versión "ULTIMATE":

https://www.jetbrains.com/idea/download/#section=windows

IMPORTANTE: para acceder a una licencia de estudiando por 1 año: https://www.jetbrains.com/community/education/#students

