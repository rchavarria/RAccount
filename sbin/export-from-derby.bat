echo off

call create-classpath.bat

set MAIN_CLASS=es.rchavarria.raccount.frontend.script.ExportFromDerby
@rem echo %MAIN_CLASS%

java -cp %PRIVATE_CLASSPATH% %MAIN_CLASS%
pause