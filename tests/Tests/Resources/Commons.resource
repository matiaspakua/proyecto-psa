*** Settings ***
Documentation     A resource file with reusable keywords and variables.
...
...               The system specific keywords created here form our own ${\n}
...               domain specific language. They utilize keywords provided ${\n}
...               by the imported SeleniumLibrary. ${\n}

Library    SeleniumLibrary    WITH NAME    Selenium 
Library    OperatingSystem    WITH NAME    OS

*** Variables ***
${URL_ISSUES_GITHUB}    https://github.com/matiaspakua/proyecto-psa/issues
${SERVER_LOCAL}         localhost:8080/
${SERVER_HEROKU}         https://calm-lake-70360.herokuapp.com/
${BROWSER}        Chrome
${DELAY}          1
