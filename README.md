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
