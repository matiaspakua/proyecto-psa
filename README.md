# proyecto-psa (Mono Repo)

## 0. Build Status Travis-CI
[![Build Status](https://app.travis-ci.com/matiaspakua/proyecto-psa.svg?branch=main)](https://app.travis-ci.com/matiaspakua/proyecto-psa)

URL Travis: https://app.travis-ci.com/github/matiaspakua/proyecto-psa

## 1. Repositorio para el Proyecto PSA

Se propone una estructura de mono-repsitorio. En un mismo proyeecto de Git se arma una estructura de directorios para soportar el producto PSA New Spring ERP.

## 2. Estructura del Repositorio

```shell
proyecto-psa
│   README.md
│   .gitignore
└───develop           # proyecto Java SpringBoot (Desarrollo)
│   │   https://github.com/matiaspakua/psa-frontend
└───testing           # proyecto de testing para el producto PSA New Spring ERP
│   │
│   └───subfolder1
│       │   file111.txt
│       │   file112.txt
│       │   ...
│
└───api-pagos         # proyecto de la API de pagos (terceros)
│   │   https://github.com/frestivos/psa-api-pagos
│
└───deploy            # Proyecto con los scripts de deploy (operaciones)
│   │   file011.txt
│   │   file012.txt
│   │
│   └───subfolder1
```

## 3. Ambiente de Desarrollo

Para configurar el ambiente de desarrollo y testing, se quiere la instalación y configuración del siguiente SW:

### Git (control de versión de código)

Desde la página oficial, descargar el instalar Git:

https://git-scm.com/

### GitHub. Plataforma de gestión de código, issues y otros. (General)

Desde la página oficial de Github:

https://github.com/

Crear o usar una cuenta personal.

### Java JDK (Desarrollo)

Instalar y configurar Java JDK o OpenJDK v11:

https://www.oracle.com/java/technologies/downloads/#java11-windows

NOTA: en caso de Windows, agregar Java en las variables de entorno.

### Maven. Gestión de paquetes para Java, Spring (Desarrollo)

Instalar y configurar Maven:

https://maven.apache.org/download.cgi

NOTA: en caso de Windows, agregar Maven en las variables de entorno.

### IntelliJ (IDE para desarrollo)

Como IDE de desarrollo, descargar e instalar IntelliJ versión "ULTIMATE":

https://www.jetbrains.com/idea/download/#section=windows

IMPORTANTE: para acceder a una licencia de estudiando por 1 año: https://www.jetbrains.com/community/education/#students


### Postman (para Testing)

Para las pruebas de APIs, instalar y configurar Postman:

https://www.postman.com/downloads/?utm_source=postman-home

### Newman (subproyecto de Postman para automatizar la ejecución de collections)

TBD. Requiere NodeJS.

### Python (para testing)

Para el caso de testing, RobotFramework requiere Python, pero aún sin usar Robot, puede ser una herramienta útil para testear o scriptear en la parte de infraestructura.

https://www.python.org/downloads/

### RobotFramwork (para testing)

Para instalar RobotFramework, desde la consola bash de Git, verificar que esté correctamente instalado Python:

```bash
matias@LaptopMatias MINGW64 ~/workspace/proyecto-psa (main)
$ python --version
Python 3.9.5
```

Luego verificar que esté instalado el manejador de paquetes PIP:

```bash
matias@LaptopMatias MINGW64 ~/workspace/proyecto-psa (main)
$ pip --version
pip 21.3 from c:\users\matias\appdata\local\programs\python\python39\lib\site-packages\pip (python 3.9)
```

Instalar RobotFramework==3.2.2:

```bash
matias@LaptopMatias MINGW64 ~/workspace/proyecto-psa (main)
$ pip install robotframework==3.2.2
Collecting robotframework==3.2.2
  Downloading robotframework-3.2.2-py2.py3-none-any.whl (623 kB)
Installing collected packages: robotframework
Successfully installed robotframework-3.2.2
```

### IDE RED Eclipse RobotFramework (para testing):

Descargar, descomprimir y ejecutar Eclipse RED:

https://github.com/nokia/RED/releases/tag/0.9.5

NOTA: Eclipse solamente requiere que Java esté instalado y configurado en las variables de entorno.
