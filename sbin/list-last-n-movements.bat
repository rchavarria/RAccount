echo off

call create-classpath.bat

set MAIN_CLASS=es.rchavarria.raccount.frontend.script.ListLastNMovements
@rem echo %MAIN_CLASS%

start javaw -cp %PRIVATE_CLASSPATH% %MAIN_CLASS%