*** Settings ***
Library    Dialogs    WITH NAME    screen

***Keywords***
Mi Propia Keyword
    [Documentation]    La documentación de mi keyword
    [Return]    True
    Log to Console    Keyword personal

***Test Cases***
TC-001 prueba
    [Documentation]    Esto es un Test Case sencillo
    [Tags]    Smoke Test

    Log To Console     Prueba paso 1
    screen.Execute Manual Step    Por favor realizar la siguiente ACCIÓN    
    ${resultado} =    Mi Propia Keyword

    Should Be True     ${resultado}
    # hacer muchas cosas

    Log to Console     Prueba Paso N