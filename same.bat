@echo off

rem Slurp the command line arguments.  This loop allows for an unlimited number of 
rem agruments (up to the command line limit, anyway).

set SAME_CMD_LINE_ARGS=

:setupArgs
if %1a==a goto doneArgs
set SAME_CMD_LINE_ARGS=%SAME_CMD_LINE_ARGS% %1
shift
goto setupArgs

:doneArgs
rem The doneArgs label is here just to provide a place for the argument list loop
rem to break out to.

call jview /cp:p same-classes.zip same.textui.SameCLI %SAME_CMD_LINE_ARGS%

set SAME_CMD_LINE_ARGS=

