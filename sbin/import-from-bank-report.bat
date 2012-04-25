echo off

call create-classpath.bat

set MAIN_CLASS=es.rchavarria.raccount.frontend.script.ImportFromBankReport
@rem echo %MAIN_CLASS%

@rem start javaw -cp %PRIVATE_CLASSPATH% %MAIN_CLASS%
java -cp %PRIVATE_CLASSPATH% %MAIN_CLASS%